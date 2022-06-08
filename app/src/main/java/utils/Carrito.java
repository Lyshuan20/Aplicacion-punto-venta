package utils;

import java.util.ArrayList;

import BD.BD_Detalle_Carrito;

public class Carrito {
    //Creamos un arrayList de la clase detallePedido
    private static final ArrayList<BD_Detalle_Carrito> detallePedidoCarrito = new ArrayList<>();

    //Método para agregar productos al carrito(bolsa)
    public static String agregarPlatillos(BD_Detalle_Carrito detallePedido) {
        int index = 0;
        boolean b = false;
        for (BD_Detalle_Carrito dc : detallePedidoCarrito) {
            if (dc.getProducto().getPid() == detallePedido.getProducto().getPid()) {
                detallePedidoCarrito.set(index, detallePedido);
                b = true;
                return "El producto ha sido agregado al carrito, se actualizará la cantidad";
            }
            index++;
        }
        if (!b) {
            detallePedidoCarrito.add(detallePedido);
            return "PRODUCTO AGREGADO";
        }
        return ". . . . ";
    }

    //Método para eliminar un producto del carrito(bolsa)
    public static void eliminar(final String idp) {
        BD_Detalle_Carrito dpE = null;
        for (BD_Detalle_Carrito dc : detallePedidoCarrito) {
            if (dc.getProducto().getPid() == idp) {
                dpE = dc;
                break;
            }
        }
        if (dpE != null) {
            detallePedidoCarrito.remove(dpE);
            System.out.println("Se elimino el producto del detalle de pedido");
        }
    }

    //Método para conseguir los detalles del pedido
    public static ArrayList<BD_Detalle_Carrito> getDetallePedidos() {
        return detallePedidoCarrito;
    }

    //Método para limpiar
    public static void limpiar() {
        detallePedidoCarrito.clear();
    }
}
