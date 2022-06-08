package BD;

public class BD_Categorias {
    String id_producto;
    String nombre_producto;

    public BD_Categorias(String id_producto, String nombre_producto) {
        this.id_producto = id_producto;
        this.nombre_producto = nombre_producto;
    }

    public String getId_producto() {
        return id_producto;
    }

    public void setId_producto(String id_producto) {
        this.id_producto = id_producto;
    }

    public String getNombre_producto() {
        return nombre_producto;
    }

    public void setNombre_producto(String nombre_producto) {
        this.nombre_producto = nombre_producto;
    }
    @Override
    public String toString() {
        return nombre_producto;
    }
}
