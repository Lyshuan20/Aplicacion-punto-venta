package Adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.lyshdev.applicationakami.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

import BD.BD_Detalle_Carrito;
import BD.BD_Producto;
import Inventario.Editar_Producto;
import utils.Carrito;

public class Adapter_Venta_MenuP extends RecyclerView.Adapter<Adapter_Venta_MenuP.MyViewHolder>{

    Context context;
    ArrayList<BD_Producto> list;
    DatabaseReference mDatabase;
    Activity activity;

    public Adapter_Venta_MenuP(Context context, ArrayList<BD_Producto> list, Activity activity) {
        this.context = context;
        this.list = list;
        this.activity = activity;
    }

    @NonNull
    @Override
    public Adapter_Venta_MenuP.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_producto_venta,parent,false);
        return new Adapter_Venta_MenuP.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Adapter_Venta_MenuP.MyViewHolder holder, int position) {
        BD_Producto p = list.get(position);
        holder.nombreP.setText(p.getNombre_producto());

        String precioPP = Integer.toString(p.getPrecio_venta());
        holder.precioP.setText("$ " + precioPP);

        holder.btnAgregarCarrito.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BD_Detalle_Carrito detalle_carrito = new BD_Detalle_Carrito();
                detalle_carrito.setProducto(p);
                detalle_carrito.setCantidadP(1);
                detalle_carrito.setPrecio(p.getPrecio_venta());
                Toast.makeText(activity, Carrito.agregarPlatillos(detalle_carrito), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
    public static class MyViewHolder extends RecyclerView.ViewHolder{


        TextView nombreP,  precioP;
        CardView cardView;
        Button btnAgregarCarrito;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            nombreP = itemView.findViewById(R.id.nombreP);
            precioP = itemView.findViewById(R.id.precioP);
            cardView = itemView.findViewById(R.id.cardViewPV);
            btnAgregarCarrito = itemView.findViewById(R.id.btnAgregarCarrito);
        }

    }
}
