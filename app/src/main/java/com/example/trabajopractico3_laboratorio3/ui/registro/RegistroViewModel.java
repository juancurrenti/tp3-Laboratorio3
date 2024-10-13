package com.example.trabajopractico3_laboratorio3.ui.registro;

import static android.app.Activity.RESULT_OK;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;


import com.example.trabajopractico3_laboratorio3.model.Usuario;
import com.example.trabajopractico3_laboratorio3.request.ApiClient;
import com.example.trabajopractico3_laboratorio3.ui.login.MainActivity;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class RegistroViewModel extends AndroidViewModel {
    private Context context;
    private MutableLiveData<Usuario> mUsuario;
    private MutableLiveData <Bitmap> mFoto;
    public RegistroViewModel(@NonNull Application application) {
        super(application);
        context = application.getApplicationContext();
    }
    public LiveData<Usuario> getMUsuario(){
        if(mUsuario == null){
            mUsuario = new MutableLiveData<>();
        }
        return mUsuario;
    }
    public LiveData<Bitmap> getMFoto(){
        if(mFoto == null){
            mFoto = new MutableLiveData<>();
        }
        return mFoto;
    }
    public void LeerUsuario(){
        Usuario u = ApiClient.leer(context);
        if(u!=null){
            mUsuario.setValue(u);
        }
    }
    public void guardarUsuario(String dni, String apellido, String nombre, String email, String password){
        Usuario u = new Usuario(Long.parseLong(dni), apellido, nombre, email, password);
        ApiClient.guardar(context, u);
        Intent intent = new Intent(context, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }
    public void rCamara(int requestCode, int resultCode, @NonNull Intent data, int REQUEST_IMAGE_CAPTURE){
        if(requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK){
            //Recupero los datos provenientes de la camara
            Bundle extras = data.getExtras();
            //Casteo a bitmap lo obtenido de la camara
            Bitmap iBitmap = (Bitmap) extras.get("data");
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            iBitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
            //Rutina para convertir a un arreglo de byte los datos de la imagen
            byte[] b = baos.toByteArray();
            File archivo = new File(context.getFilesDir(), "foto.png");
            if(archivo.exists()){
                archivo.delete();
            }
            try{
                FileOutputStream fos = new FileOutputStream(archivo);
                BufferedOutputStream bos = new BufferedOutputStream(fos);
                bos.write(b);
                bos.flush();
                bos.close();
            } catch (IOException e){
                e.printStackTrace();
            }
            mFoto.setValue(iBitmap);
        }
    }
    public void leerFoto(String nombre){
        File archivo = new File(context.getFilesDir(), nombre);

        try {
            FileInputStream fis = new FileInputStream(archivo);
            BufferedInputStream bis = new BufferedInputStream(fis);
            byte b[];
            b = new byte[bis.available()];
            bis.read(b);
            Bitmap bm = BitmapFactory.decodeByteArray(b, 0, b.length);
            mFoto.setValue(bm);
            bis.close();
            fis.close();
        } catch (FileNotFoundException e){
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
