package com.lyshdev.applicationakami;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }
    public void irIniciarSesion(View vista){
        startActivity(new Intent(MainActivity.this,login.IniciarSesion.class));
    }
    public void irCrearCuenta(View vista){
        startActivity(new Intent(MainActivity.this,login.CrearCuenta.class));
    }
}