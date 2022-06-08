package login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.lyshdev.applicationakami.R;

import java.util.HashMap;
import java.util.Map;

public class CrearCuenta extends AppCompatActivity {

    private EditText EditUsuario;
    private EditText EditContra;
    private EditText EditConfirmContra;
    private RadioGroup radioGrupo;
    private RadioButton RadioEmpleado;

    //VARIABLES USARIO
    private String usuario = "";
    private String contraseña = "";
    private String tipo_empleado = "";

    //FIRBASE
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_cuenta);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        mDatabase = FirebaseDatabase.getInstance().getReference();

        //DATOS A REGISTRAR DEL USUARIO
        radioGrupo = findViewById(R.id.TipoEmpleado);
        EditUsuario = (EditText) findViewById(R.id.txtUsuario);
        EditContra = (EditText) findViewById(R.id.txtContraseña);
        EditConfirmContra = (EditText) findViewById(R.id.txtConfirmarContra);
    }
    public void irIniciarSesion(View vista){
        startActivity(new Intent(CrearCuenta.this,IniciarSesion.class));
        finish();
    }
    public void registrarUsuario(View view){
        int radioId = radioGrupo.getCheckedRadioButtonId();
        RadioEmpleado = findViewById(radioId);

        tipo_empleado = RadioEmpleado.getText().toString();
        usuario = EditUsuario.getText().toString();
        contraseña = EditContra.getText().toString();

        if(!usuario.isEmpty() && !contraseña.isEmpty()){
            if(contraseña.length()>=6){
                //----REGISTRAR USUARIO
                if(contraseña.equals(EditConfirmContra.getText().toString())){
                    //AGREGAR DATOS A BD
                    Map<String, Object> map = new HashMap<>();
                    map.put("Nombre_Usuario",usuario);
                    map.put("Tipo_Empleado",tipo_empleado);
                    map.put("Contraseña",contraseña);

                    mDatabase.child("Usuarios").child(usuario).setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task2) {
                            if (task2.isSuccessful()){
                                startActivity(new Intent(CrearCuenta.this,com.lyshdev.applicationakami.MainActivity.class));
                                finish();
                                Toast.makeText(CrearCuenta.this,"Datos agregados con exito",Toast.LENGTH_SHORT).show();
                            }else{
                                Toast.makeText(CrearCuenta.this,"Error al agregar los datos",Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }else{
                    Toast.makeText(CrearCuenta.this,"Las contraseñas no coinciden",Toast.LENGTH_SHORT).show();
                }
            }else{
                Toast.makeText(CrearCuenta.this,"La contraseña debe tener minimo 6 caracteres",Toast.LENGTH_SHORT).show();
            }
        }else{
            Toast.makeText(CrearCuenta.this,"Debe completar los campos",Toast.LENGTH_SHORT).show();
        }
    }
}