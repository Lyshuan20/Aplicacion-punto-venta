package HacerVenta;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.Notification;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.lyshdev.applicationakami.R;
import com.nex3z.notificationbadge.NotificationBadge;

import java.util.ArrayList;

import Adapter.Adapter_Venta_MenuP;
import Adapter.Adapter_inventario_Normal;
import BD.BD_Producto;
import butterknife.BindView;

public class Menu_Productos_Carrito extends AppCompatActivity {

    RecyclerView recyclerView;
    Adapter_Venta_MenuP AdapterVenta;
    ArrayList<BD_Producto> list;
    @BindView(R.id.mainCar1)
    RelativeLayout mainCar1;
    Button btnVerVenta;
    DatabaseReference mDatabase;
    TextView Nametxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_productos_carrito);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        mDatabase = FirebaseDatabase.getInstance().getReference();

        Nametxt = (TextView) findViewById(R.id.nameUsuarioV);

        recyclerView = (RecyclerView)findViewById(R.id.listado_producto);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        btnVerVenta = findViewById(R.id.btnverVenta);
        getNameUser();

        btnVerVenta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MostrarCarrito();
            }
        });
        //---------------------
        list = new ArrayList<>();
        AdapterVenta = new Adapter_Venta_MenuP(this,list,this);
        recyclerView.setAdapter(AdapterVenta);
        mDatabase.child("Productos").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    BD_Producto p = dataSnapshot.getValue(BD_Producto.class);
                    list.add(p);
                }
                AdapterVenta.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    private void MostrarCarrito(){
        Bundle enviarDatos = new Bundle();
        enviarDatos.putString("Nombre_Usuario",Nametxt.getText().toString());

        Intent i = new Intent(this,Carrito_Venta.class);
        i.putExtras(enviarDatos);
        startActivity(i);
    }
    private void getNameUser(){
        Bundle recibeDatos = getIntent().getExtras();
        String name = recibeDatos.getString("Nombre_Usuario");

        Nametxt.setText(name);
    }
}