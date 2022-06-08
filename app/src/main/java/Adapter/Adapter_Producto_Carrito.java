package Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.lyshdev.applicationakami.R;

import java.util.List;
import java.util.Locale;

import BD.BD_Detalle_Carrito;
import Listener.CarritoComunnication;

public class Adapter_Producto_Carrito extends RecyclerView.Adapter<Adapter_Producto_Carrito.ViewHolder> {

    private final List<BD_Detalle_Carrito> detalles;
    private final CarritoComunnication c;

    public Adapter_Producto_Carrito(List<BD_Detalle_Carrito> detalles, CarritoComunnication c) {
        this.detalles = detalles;
        this.c = c;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_producto_carrito,parent, false);
        return new ViewHolder(v,this.c);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.setItem(this.detalles.get(position));
    }

    @Override
    public int getItemCount() {
        return this.detalles.size();
    }

    protected class ViewHolder extends RecyclerView.ViewHolder{
        private final ImageView btnDecrement,btnAdd,btnEliminarCarrito;
        private final EditText edtCantidad;
        private final TextView NombreP, PrecioP;
        private final CarritoComunnication c;

        public ViewHolder(@NonNull View itemView, CarritoComunnication c) {
            super(itemView);
            this.c = c;
            this.btnDecrement = itemView.findViewById(R.id.btnDecrease);
            this.btnAdd = itemView.findViewById(R.id.btnAdd);
            this.btnEliminarCarrito = itemView.findViewById(R.id.btnEliminarPCarrito);
            this.edtCantidad = itemView.findViewById(R.id.edtCantidad);
            this.NombreP = itemView.findViewById(R.id.tvNombreProductoDC);
            this.PrecioP = itemView.findViewById(R.id.tvPrecioPDC);
        }
        public void setItem(final BD_Detalle_Carrito dc) {
            this.NombreP.setText(dc.getProducto().getNombre_producto());
            String precioPP = Integer.toString(dc.getProducto().getPrecio_venta());
            this.PrecioP.setText("$ " + precioPP);
            int cant = dc.getCantidadP();
            this.edtCantidad.setText(Integer.toString(cant));

            //-------------Actualizar Cantidad del Carrito-------------------------
            btnAdd.setOnClickListener(v -> {
                if (dc.getCantidadP() != 10) {//Si el valor todavía no llega a 10, que siga aumentando
                    dc.addOne();
                    Adapter_Producto_Carrito.this.notifyDataSetChanged();
                }
            });
            btnDecrement.setOnClickListener(v -> {
                if (dc.getCantidadP() != 1) {
                    dc.removeOne();
                    Adapter_Producto_Carrito.this.notifyDataSetChanged();
                }
            });

            //------------------Eliminar item del carrito-----------------------
            this.btnEliminarCarrito.setOnClickListener(v -> {

                c.elminarDetalle(dc.getProducto().getPid());

            });
        }

    }
}
