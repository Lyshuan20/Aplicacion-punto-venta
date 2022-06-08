package HacerVenta;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.lyshdev.applicationakami.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import Adapter.Adapter_Producto_Carrito;
import BD.BD_Detalle_Carrito;
import BD.BD_Generar_Pedido;
import BD.BD_Producto;
import Listener.CarritoComunnication;
import utils.Carrito;

public class Carrito_Venta extends AppCompatActivity implements CarritoComunnication {

    private Adapter_Producto_Carrito adapter;
    private RecyclerView rcvCarritoCompra;
    private Button btnFinalizarCompra;
    private TextView Total;

    int dia,mes,año;
    String Fecha;

    DatabaseReference mDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carrito_venta);

        mDatabase = FirebaseDatabase.getInstance().getReference();
        init();
        initAdapter();
    }
    private void init(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        rcvCarritoCompra = findViewById(R.id.recycler_car);
        btnFinalizarCompra = findViewById(R.id.btnFinalizaVenta);
        Total = findViewById(R.id.txtTotal);

        btnFinalizarCompra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RegistrarPedido();
            }
        });

    }
    private void initAdapter(){
        adapter = new Adapter_Producto_Carrito(Carrito.getDetallePedidos(),this);
        rcvCarritoCompra.setLayoutManager(new LinearLayoutManager(this));
        rcvCarritoCompra.setAdapter(adapter);
    }
    private int getTotalV(List<BD_Detalle_Carrito> detalles){
        int total = 0;
        for (BD_Detalle_Carrito dc: detalles){
            total += dc.getTotal();
        }
        return total;
    }
    @Override
    public void elminarDetalle(String Pid) {
        Carrito.eliminar(Pid);
        this.adapter.notifyDataSetChanged();
    }
    private void RegistrarPedido(){
        ArrayList<BD_Detalle_Carrito> detallepedidos = Carrito.getDetallePedidos();
        BD_Generar_Pedido GP = new BD_Generar_Pedido();
        //FECHA
        Calendar fecha = Calendar.getInstance();
        dia= fecha.get(Calendar.DAY_OF_MONTH);
        mes = fecha.get(Calendar.MONTH);
        año = fecha.get(Calendar.YEAR);
        Fecha = (dia+"/"+(mes + 1)+"/"+año);
        //Nombre usuario
        Bundle recibeDatos = getIntent().getExtras();
        String name = recibeDatos.getString("Nombre_Usuario");

        GP.getPedido().setId(UUID.randomUUID().toString());
        GP.getPedido().setFechaCompra(Fecha);
        GP.getPedido().setEmpleado(name);
        GP.getPedido().setMonto(getTotalV(detallepedidos));
        GP.setDetallePedidos(detallepedidos);

        Total.setText("TOTAL: $ " + GP.getPedido().getMonto());
        mDatabase.child("Ventas").child(GP.getPedido().getId()).setValue(GP);
        //-----------------------
        Carrito.limpiar();

    }
}