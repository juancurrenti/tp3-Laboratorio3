package com.example.trabajopractico3_laboratorio3.ui.registro;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.trabajopractico3_laboratorio3.databinding.ActivityRegistroBinding;
import com.example.trabajopractico3_laboratorio3.model.Usuario;


public class RegistroActivity extends AppCompatActivity {
    private RegistroViewModel rm;
    private ActivityRegistroBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegistroBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        rm = ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication()).create(RegistroViewModel.class);

        rm.getMUsuario().observe(this, new Observer<Usuario>() {
            @Override
            public void onChanged(Usuario usuario) {
                if (usuario != null) {
                    binding.etDni.setText(String.valueOf(usuario.getDni()));
                    binding.etApellido.setText(usuario.getApellido());
                    binding.etNombre.setText(usuario.getNombre());
                    binding.etEmail.setText(usuario.getMail());
                    binding.etPassword.setText(usuario.getPassword());
                    rm.leerFoto(usuario.getFoto());
                }
            }
        });
        Intent intent = getIntent();
        int i = (int)intent.getIntExtra("flag", 0);
        if(i == 1){
            rm.LeerUsuario();
        }
        binding.btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rm.guardarUsuario(
                        binding.etDni.getText().toString(),
                        binding.etApellido.getText().toString(),
                        binding.etNombre.getText().toString(),
                        binding.etEmail.getText().toString(),
                        binding.etPassword.getText().toString()                )
                ;
            }
        });
        rm.getMFoto().observe(this, new Observer<Bitmap>() {
            @Override
            public void onChanged(Bitmap bitmap) {
                binding.ivFoto.setImageBitmap(bitmap);
            }
        });
        binding.btnSacarFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("salida", "Saco Foto");
                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(takePictureIntent, 1);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d("salida", requestCode + " " + resultCode+ " "+ data.toString());
        rm.rCamara(requestCode, resultCode, data, 1);
    }
}
