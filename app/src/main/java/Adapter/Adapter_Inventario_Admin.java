package Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.lyshdev.applicationakami.R;

import java.util.ArrayList;

import BD.BD_Producto;
import Inventario.Editar_Producto;

public class Adapter_Inventario_Admin extends RecyclerView.Adapter<Adapter_Inventario_Admin.MyViewHolder>{

        DatabaseReference mDatabase;
        Activity activity;
        Context context;
        ArrayList<BD_Producto> list;

    public Adapter_Inventario_Admin(Context context, ArrayList<BD_Producto> list, Activity activity) {
        this.context = context;
        this.list = list;
        this.activity = activity;
        mDatabase = FirebaseDatabase.getInstance().getReference();
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_producto_admin,parent,false);
        return new MyViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {

        BD_Producto p = list.get(position);
        holder.nombreP.setText(p.getNombre_producto());
        holder.cantidadP.setText("Cantidad: " + p.getCantidad_disponible());
        holder.precioP.setText("$ "+ p.getPrecio_venta());

        holder.btnEditarP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(activity, Editar_Producto.class);
                intent.putExtra("articulo ID", p.getPid());
                activity.startActivity(intent);
            }
        });

        holder.btnEliminarP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDatabase.child("Productos").child(p.getPid()).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(activity, "PRODUCTO ELIMINADO", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(activity, "ERROR AL ELIMINAR EL PRODUCTO", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView nombreP, cantidadP, precioP;
        CardView cardView;
        ImageView btnEditarP,btnEliminarP;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            nombreP = itemView.findViewById(R.id.nombreP);
            cantidadP = itemView.findViewById(R.id.CantidadP);
            precioP = itemView.findViewById(R.id.precioP);
            cardView = itemView.findViewById(R.id.cardViewPA);
            btnEditarP = itemView.findViewById(R.id.btnEditarP);
            btnEliminarP = itemView.findViewById(R.id.btnEliminarP);
        }

    }
}
