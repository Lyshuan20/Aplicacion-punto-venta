package Inventario;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.lyshdev.applicationakami.R;

import java.util.HashMap;
import java.util.Map;

public class Editar_Producto extends AppCompatActivity {

    private EditText nombre_producto, precio_compra, precio_venta, desc_producto, cant_product;
    private Button btnActualizar;

    private String productoId;
    DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_producto);

        mDatabase = FirebaseDatabase.getInstance().getReference();
        productoId = getIntent().getStringExtra("articulo ID");

        ObtenerDatos();

        nombre_producto= findViewById(R.id.nombre_producto);
        precio_compra= findViewById(R.id.precio_compra);
        precio_venta= findViewById(R.id.precio_venta);
        desc_producto= findViewById(R.id.desc_producto);
        cant_product = findViewById(R.id.cantidad_producto);

        btnActualizar = findViewById(R.id.Actualizarbtn);

        precio_compra= findViewById(R.id.precio_compra);
        precio_venta= findViewById(R.id.precio_venta);
        desc_producto= findViewById(R.id.desc_producto);
        cant_product = findViewById(R.id.cantidad_producto);

        btnActualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                actualizarDatos();
            }
        });
    }
    private void ObtenerDatos(){

        mDatabase.child("Productos").child(productoId).get().addOnSuccessListener(new OnSuccessListener<DataSnapshot>() {
            @Override
            public void onSuccess(DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    String nombreP = dataSnapshot.child("nombre_producto").getValue(String.class);
                    int precioC = dataSnapshot.child("precio_compra").getValue(Integer.class);
                    int precioV = dataSnapshot.child("precio_venta").getValue(Integer.class);
                    String descP = dataSnapshot.child("desc_producto").getValue(String.class);
                    int cantidadP = dataSnapshot.child("cantidad_disponible").getValue(Integer.class);

                    String precioCC = Integer.toString(precioC);
                    String precioVV = Integer.toString(precioV);
                    String cantidadPP = Integer.toString(cantidadP);

                    nombre_producto.setText(nombreP);
                    precio_compra.setText(precioCC);
                    precio_venta.setText(precioVV);
                    desc_producto.setText(descP);
                    cant_product.setText(cantidadPP);
                }
            }
        });
    }
    private void actualizarDatos(){
        String nombreP = nombre_producto.getText().toString();
        int precioC = Integer.parseInt(precio_compra.getText().toString());
        int precioV = Integer.parseInt(precio_venta.getText().toString());
        String descP = desc_producto.getText().toString();
        int cantidadP = Integer.parseInt(cant_product.getText().toString());

        Map<String, Object> map = new HashMap<>();
        map.put("nombre_producto", nombreP);
        map.put("precio_compra", precioC);
        map.put("precio_venta", precioV);
        map.put("desc_producto", descP);
        map.put("cantidad_disponible", cantidadP);

        mDatabase.child("Productos").child(productoId).updateChildren(map);
        Toast.makeText(Editar_Producto.this, "PRODUCTO ACTUALIZADO", Toast.LENGTH_SHORT).show();
        finish();
    }
}