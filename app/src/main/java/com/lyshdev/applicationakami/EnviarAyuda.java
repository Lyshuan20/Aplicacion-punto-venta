package com.lyshdev.applicationakami;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class EnviarAyuda extends AppCompatActivity {

    private Button EnviarMensaje;
    private EditText MensajeW;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enviar_ayuda);

        EnviarMensaje = findViewById(R.id.btnEnviarW);
        MensajeW = findViewById(R.id.msjW);
        String num = "523222003248";
        EnviarMensaje.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_VIEW);
                String uri = "whatsapp://send?phone="+num+"&text="+MensajeW.getText().toString();
                sendIntent.setData(Uri.parse(uri));
                startActivity(sendIntent);

                MensajeW.setText("");
            }
        });
    }
}