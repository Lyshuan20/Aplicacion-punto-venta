package Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.lyshdev.applicationakami.R;

import java.util.ArrayList;

import BD.BD_Producto;

public class Adapter_inventario_Normal extends RecyclerView.Adapter<Adapter_inventario_Normal.MyViewHolder>{

    Context context;
    ArrayList<BD_Producto> list;

    public Adapter_inventario_Normal(Context context, ArrayList<BD_Producto> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_producto_normal,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Adapter_inventario_Normal.MyViewHolder holder, int position) {
        BD_Producto p = list.get(position);
        holder.nombreP.setText(p.getNombre_producto());

        String cantidadPP = Integer.toString(p.getCantidad_disponible());
        holder.cantidadP.setText("Cantidad: " + cantidadPP);

        String precioPP = Integer.toString(p.getPrecio_venta());
        holder.precioP.setText("$ " + precioPP);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
    public static class MyViewHolder extends RecyclerView.ViewHolder{


        TextView nombreP, cantidadP, precioP;
        CardView cardView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            nombreP = itemView.findViewById(R.id.nombreP);
            cantidadP = itemView.findViewById(R.id.CantidadP);
            precioP = itemView.findViewById(R.id.precioP);
            cardView = itemView.findViewById(R.id.cardViewPN);
        }

    }
}
