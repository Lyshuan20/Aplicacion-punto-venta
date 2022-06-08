package HacerVenta;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.lyshdev.applicationakami.R;

import java.util.ArrayList;

import Adapter.Adapter_Listado_Ventas;
import Adapter.Adapter_inventario_Normal;
import BD.BD_Generar_Pedido;
import BD.BD_Producto;

public class VerVentas extends AppCompatActivity {

    RecyclerView recyclerView;
    Adapter_Listado_Ventas AdapterVentas;
    ArrayList<BD_Generar_Pedido> list;

    DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_ventas);

        mDatabase = FirebaseDatabase.getInstance().getReference();

        recyclerView = (RecyclerView)findViewById(R.id.listado_ventas);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        list = new ArrayList<>();
        AdapterVentas = new Adapter_Listado_Ventas(this,list);
        recyclerView.setAdapter(AdapterVentas);
        mDatabase.child("Ventas").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    BD_Generar_Pedido GP = dataSnapshot.getValue(BD_Generar_Pedido.class);
                    list.add(GP);
                }
                AdapterVentas.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
