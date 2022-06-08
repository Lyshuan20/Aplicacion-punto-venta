package BD;

import java.util.ArrayList;
import java.util.List;

public class BD_Generar_Pedido {
    private BD_Pedido pedido;
    private ArrayList<BD_Detalle_Carrito> detallePedidos;

    public BD_Generar_Pedido() {
        this.pedido = new BD_Pedido();
        this.detallePedidos = new ArrayList<>();
    }

    public BD_Pedido getPedido() {
        return pedido;
    }

    public void setPedido(BD_Pedido pedido) {
        this.pedido = pedido;
    }

    public ArrayList<BD_Detalle_Carrito> getDetallePedidos() {
        return detallePedidos;
    }

    public void setDetallePedidos(ArrayList<BD_Detalle_Carrito> detallePedidos) {
        this.detallePedidos = detallePedidos;
    }
}
