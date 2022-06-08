package BD;

public class BD_Detalle_Carrito {
    private int id;
    private int cantidadP;
    private int precio;
    private BD_Producto producto;
    private BD_Pedido pedido;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCantidadP() {
        return cantidadP;
    }

    public void setCantidadP(int cantidadP) {
        this.cantidadP = cantidadP;
    }

    public int getPrecio() {
        return precio;
    }

    public void setPrecio(int precio) {
        this.precio = precio;
    }

    public BD_Producto getProducto() {
        return producto;
    }

    public void setProducto(BD_Producto producto) {
        this.producto = producto;
    }

    public BD_Pedido getPedido() {
        return pedido;
    }

    public void setPedido(BD_Pedido pedido) {
        this.pedido = pedido;
    }
    public void addOne() {
        this.cantidadP++;
    }

    public void removeOne() {
        this.cantidadP--;
    }

    public double getTotal() {
        return this.cantidadP * this.precio;
    }
}
