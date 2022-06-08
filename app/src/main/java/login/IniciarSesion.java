package login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.lyshdev.applicationakami.R;

import Dashboard.Dashboard_Admin;
import Dashboard.Dashboard_Normal;

public class IniciarSesion extends AppCompatActivity {

    private EditText usuarioLogin, contraseñaLogin;
    Switch active;
    //VARIABLES USARIO
    private String usuario = "";
    private String contra = "";

    //FIRBASE
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_iniciar_sesion);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        mDatabase = FirebaseDatabase.getInstance().getReference();

        usuarioLogin = findViewById(R.id.txtUsuarioLogin);
        contraseñaLogin = findViewById(R.id.txtContraseñaLogin);
    }
    public void irCrearCuenta(View vista){
        startActivity(new Intent(IniciarSesion.this,CrearCuenta.class));
        finish();
    }
    public void Login(View view){
        usuario = usuarioLogin.getText().toString();
        contra = contraseñaLogin.getText().toString();

        if(!usuario.isEmpty() && !contra.isEmpty()) {
            mDatabase.child("Usuarios").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.exists()) {
                        if (snapshot.child(usuario).exists()) {
                            String getpassword = snapshot.child(usuario).child("Contraseña").getValue(String.class);
                            if (getpassword.equals(contra)) {
                                String getipoEmpleado = snapshot.child(usuario).child("Tipo_Empleado").getValue(String.class);
                                Toast.makeText(IniciarSesion.this, "BIENVENIDO", Toast.LENGTH_SHORT).show();
                                //----------------------------------------------------------
                                String NameUserBD = snapshot.child(usuario).child("Nombre_Usuario").getValue(String.class);
                                if (getipoEmpleado.equals("Administrador")) {
                                    //----------------------------------------------------------
                                    Intent intent = new Intent(getApplicationContext(), Dashboard_Admin.class);
                                    intent.putExtra("Nombre_Usuario",NameUserBD);
                                    startActivity(intent);
                                    finish();
                                } else {
                                    //----------------------------------------------------------
                                    Intent intent = new Intent(getApplicationContext(), Dashboard_Normal.class);
                                    intent.putExtra("Nombre_Usuario",NameUserBD);
                                    startActivity(intent);
                                    finish();
                                }
                            } else {
                                Toast.makeText(IniciarSesion.this, "CONTRASEÑA INCORRECTA", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(IniciarSesion.this, "USUARIO INCORRECTO", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(IniciarSesion.this, "ERROR EN LA BASE DE DATOS", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }else{
            Toast.makeText(IniciarSesion.this, "NO DEJE ESPACIO VACIOS", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        String pref = preferences.getString("Usuarios","");
        if(!pref.equals("")){
            Toast.makeText(IniciarSesion.this, "Se detecto una sesion activa", Toast.LENGTH_SHORT).show();
            this.startActivity(new Intent(this,com.lyshdev.applicationakami.MainActivity.class));
        }
    }
}