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

import BD.BD_Detalle_Carrito;
import BD.BD_Generar_Pedido;
import BD.BD_Producto;
import utils.Carrito;

public class Adapter_Listado_Ventas extends RecyclerView.Adapter<Adapter_Listado_Ventas.MyViewHolder>{

    Context context;
    ArrayList<BD_Generar_Pedido> list;

    public Adapter_Listado_Ventas(Context context, ArrayList<BD_Generar_Pedido> list) {
        this.context = context;
        this.list = list;
    }
    @NonNull
    @Override
    public Adapter_Listado_Ventas.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_detalle_ventas,parent,false);
        return new Adapter_Listado_Ventas.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Adapter_Listado_Ventas.MyViewHolder holder, int position) {

        BD_Generar_Pedido GP = list.get(position);

        String TotalVV = Integer.toString(GP.getPedido().getMonto());
        holder.TotalV.setText("TOTAL: "+TotalVV);

        holder.CantidadV.setText("Empleado: "+GP.getPedido().getEmpleado());

        holder.FechaV.setText("Fecha: " + GP.getPedido().getFechaCompra());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
    public static class MyViewHolder extends RecyclerView.ViewHolder{


        TextView TotalV, FechaV, CantidadV;
        CardView cardView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            TotalV = itemView.findViewById(R.id.TotalVenta);
            FechaV = itemView.findViewById(R.id.FechaVenta);
            CantidadV = itemView.findViewById(R.id.Cantidad_Venta);
            cardView = itemView.findViewById(R.id.cardViewDV);
        }

    }
}
