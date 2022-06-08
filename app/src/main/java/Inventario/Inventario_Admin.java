package Inventario;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.lyshdev.applicationakami.R;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import Adapter.Adapter_Inventario_Admin;
import BD.BD_Categorias;
import BD.BD_Producto;

public class Inventario_Admin extends AppCompatActivity {

    RecyclerView recyclerView;
    Adapter_Inventario_Admin mainAdapter;
    ArrayList<BD_Producto> list;

    private EditText nombre_producto, precio_compra, precio_venta, desc_producto, cant_product;
    private Button AbrirDialog;
    private Spinner mspinner;
    private AlertDialog dialog;

    String categoriaSeleccionada = "";
    DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventario_admin);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        mDatabase = FirebaseDatabase.getInstance().getReference();

        AbrirDialog = (Button) findViewById(R.id.button);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        recyclerView = (RecyclerView)findViewById(R.id.listado_producto);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        list = new ArrayList<>();
        mainAdapter = new Adapter_Inventario_Admin(this,list, this);
        recyclerView.setAdapter(mainAdapter);

        mDatabase.child("Productos").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    BD_Producto p = dataSnapshot.getValue(BD_Producto.class);
                    list.add(p);
                }
                mainAdapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        View view = getLayoutInflater().inflate(R.layout.popup_producto_inventario_admin, null);
        nombre_producto= view.findViewById(R.id.nombre_producto);
        precio_compra= view.findViewById(R.id.precio_compra);
        precio_venta= view.findViewById(R.id.precio_venta);
        desc_producto= view.findViewById(R.id.desc_producto);
        mspinner = view.findViewById(R.id.cat_producto);
        cant_product = view.findViewById(R.id.cantidad_producto);
        MostrarCategorias();
        Button Guardarbtn = view.findViewById(R.id.Guardarbtn);
        Button Cerrarbtn = view.findViewById(R.id.Cerrarbtn);


        Guardarbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int precioCP, precioVP, cantidadPP;
                String nombreP = nombre_producto.getText().toString();
                String precioC = precio_compra.getText().toString();
                String precioV = precio_venta.getText().toString();
                String descP = desc_producto.getText().toString();
                String cantidadP = cant_product.getText().toString();

                precioCP = Integer.parseInt(precioC);
                precioVP = Integer.parseInt(precioV);
                cantidadPP = Integer.parseInt(cantidadP);

                if (nombreP.equals("") && precioC.equals("") && precioV.equals("") && descP.equals("") && cantidadP.equals("") ){
                    Toast.makeText(Inventario_Admin.this, "NO DEJE CAMPOS VACIOS", Toast.LENGTH_SHORT).show();
                }else {
                    BD_Producto p = new BD_Producto();
                    p.setPid(UUID.randomUUID().toString());
                    p.setNombre_producto(nombreP);
                    p.setPrecio_compra(precioCP);
                    p.setPrecio_venta(precioVP);
                    p.setDesc_producto(descP);
                    p.setCategoria(categoriaSeleccionada);
                    p.setCantidad_disponible(cantidadPP);

                    mDatabase.child("Productos").child(p.getPid()).setValue(p);
                    LimpiarCampos();
                    Toast.makeText(Inventario_Admin.this, "PRODUCTO AGREGADO", Toast.LENGTH_SHORT).show();
                }
            }
        });
        Cerrarbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        //-----------
        builder.setView(view);
        //------------
        dialog = builder.create();


        AbrirDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.show();
            }
        });
    }
    public void LimpiarCampos(){
        nombre_producto.setText("");
        precio_venta.setText("");
        precio_compra.setText("");
        desc_producto.setText("");
        cant_product.setText("");
    }
    public void MostrarCategorias(){
        List<BD_Categorias> categorias = new ArrayList<>();
        mDatabase.child("Categorias").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    for(DataSnapshot ds: snapshot.getChildren()){
                        String id = ds.getKey();
                        String nombre_producto = ds.child("nombre").getValue().toString();
                        categorias.add(new BD_Categorias(id,nombre_producto));
                    }

                    ArrayAdapter<BD_Categorias> arrayAdapter = new ArrayAdapter<>(Inventario_Admin.this, android.R.layout.simple_dropdown_item_1line, categorias);
                    mspinner.setAdapter(arrayAdapter);
                    mspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                            categoriaSeleccionada = adapterView.getItemAtPosition(i).toString();
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {

                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}