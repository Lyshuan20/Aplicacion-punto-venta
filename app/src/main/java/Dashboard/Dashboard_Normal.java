package Dashboard;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.lyshdev.applicationakami.EnviarAyuda;
import com.lyshdev.applicationakami.R;

import HacerVenta.Menu_Productos_Carrito;
import HacerVenta.VerVentas;
import Inventario.Inventario_Normal;

public class Dashboard_Normal extends AppCompatActivity implements View.OnClickListener{

    private TextView Nametxt;
    private DatabaseReference mDataBase;
    private CardView card1, card2, card3, card4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard_normal);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        Nametxt = (TextView) findViewById(R.id.nombre_usuariotxt);
        mDataBase = FirebaseDatabase.getInstance().getReference();
        getNameUser();

        card1 = (CardView) findViewById(R.id.c1);
        card1.setOnClickListener(this);

        card2 = (CardView) findViewById(R.id.c2);
        card2.setOnClickListener(this);

        card3 = (CardView) findViewById(R.id.c3);
        card3.setOnClickListener(this);

        card4 = (CardView) findViewById(R.id.c4);
        card4.setOnClickListener(this);

    }
    private void getNameUser(){
        Intent intent = getIntent();
        String user_username = intent.getStringExtra("Nombre_Usuario");
        Nametxt.setText(user_username);
    }
    @Override
    public void onClick(View view) {
        Intent i;
        switch (view.getId()){
            case R.id.c1:
                Bundle enviarDatos = new Bundle();
                enviarDatos.putString("Nombre_Usuario",Nametxt.getText().toString());

                i = new Intent(this, Menu_Productos_Carrito.class);
                i.putExtras(enviarDatos);
                startActivity(i);
                break;
            case R.id.c2:
                i = new Intent(this, VerVentas.class);
                startActivity(i);
                break;
            case R.id.c3:
                i = new Intent(this, Inventario_Normal.class);
                startActivity(i);
                break;
            case R.id.c4:
                i = new Intent(this, EnviarAyuda.class);
                startActivity(i);
                break;
        }
    }
}