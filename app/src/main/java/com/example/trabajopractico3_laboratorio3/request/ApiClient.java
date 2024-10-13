package com.example.trabajopractico3_laboratorio3.request;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.example.trabajopractico3_laboratorio3.model.Usuario;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class ApiClient {
    public static void guardar(Context context, Usuario usuario){
        Log.d("salida", usuario.toString());
        Log.d("salida", context.getFilesDir().toString());
        File archivo = new File(context.getFilesDir(), "usuario.dat");
        try{
            FileOutputStream fos = new FileOutputStream(archivo);
            BufferedOutputStream bos = new BufferedOutputStream(fos);
            ObjectOutputStream oos = new ObjectOutputStream(bos);
            oos.writeObject(usuario);
            bos.flush();
            fos.close();
        }catch (FileNotFoundException e){
            Toast.makeText(context, "Error al guardad", Toast.LENGTH_SHORT).show();
        }catch (IOException io){
            io.printStackTrace();
            Toast.makeText(context, "Error de E/S" + io.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    public static Usuario leer(Context context){
        File archivo = new File(context.getFilesDir(), "usuario.dat");
        if(!archivo.exists()){
            return null;
        }
        Usuario usuario = null;
        try{
            FileInputStream fis = new FileInputStream(archivo);
            BufferedInputStream bis = new BufferedInputStream(fis);
            ObjectInputStream ois = new ObjectInputStream(bis);
            usuario = (Usuario) ois.readObject();
            Log.d("salida" , usuario.toString());
            ois.close();
            fis.close();
        }catch (FileNotFoundException e){
            Toast.makeText(context, "Error al guardar", Toast.LENGTH_SHORT).show();
        }catch (ClassNotFoundException e){
            Toast.makeText(context, "Error de clase", Toast.LENGTH_SHORT).show();
        }catch (IOException io){
            Toast.makeText(context, "Error de E/S", Toast.LENGTH_SHORT).show();
        }
        return usuario;
    }
    public static Usuario login(Context context, String mail, String password){
        Usuario usuario = leer(context);
        if(usuario.getMail().equals(mail) && usuario.getPassword().equals(password)){
            return usuario;
        }
        return null;
    }
}
