package com.example.trabajopractico3_laboratorio3.ui.login;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;


import com.example.trabajopractico3_laboratorio3.model.Usuario;
import com.example.trabajopractico3_laboratorio3.request.ApiClient;
import com.example.trabajopractico3_laboratorio3.ui.registro.RegistroActivity;

import java.io.BufferedInputStream;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;

public class LoginViewModel extends AndroidViewModel {
    private Context context;

    public LoginViewModel(@NonNull Application application) {
        super(application);
        context = application.getApplicationContext();
    }
    public void login (String mail, String password){
        Usuario usuario = ApiClient.login(context, mail, password);
        if(usuario != null){
            Intent intent = new Intent(context, RegistroActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.putExtra("flag", 1);
            context.startActivity(intent);
        }else{
            Toast.makeText(context, "Mail o Contraseña Incorrencta...", Toast.LENGTH_SHORT).show();
        }
    }
    public void registro(){
        Intent intent = new Intent(context, RegistroActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }
}
