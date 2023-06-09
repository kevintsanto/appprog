/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Clases;

/**
 *
 * @author gg
 */
public class DetalleVenta {

    private int id_detalle_venta;
    private Venta venta;
    private Producto producto;
    private double precio_venta;
    private int cantidad;
    private boolean iva;

    /**
     * Constructor por defecto de la clase DetalleVenta.
     */
    public DetalleVenta() {
    }

    /**
     * Constructor de la clase DetalleVenta que inicializa todos los atributos.
     *
     * @param id_detalle_venta El ID del detalle de venta.
     * @param venta La venta asociada al detalle de venta.
     * @param producto El producto asociado al detalle de venta.
     * @param precio_venta El precio de venta del producto.
     * @param cantidad La cantidad vendida del producto.
     * @param iva Indicador de si se aplica IVA al detalle de venta.
     */
    public DetalleVenta(int id_detalle_venta, Venta venta, Producto producto, double precio_venta, int cantidad, boolean iva) {
        this.id_detalle_venta = id_detalle_venta;
        this.venta = venta;
        this.producto = producto;
        this.precio_venta = precio_venta;
        this.cantidad = cantidad;
        this.iva = iva;
    }



    // Getters y setters
    public int getId_detalle_venta() {
        return id_detalle_venta;
    }

    public void setId_detalle_venta(int id_detalle_venta) {
        this.id_detalle_venta = id_detalle_venta;
    }

    public Venta getVenta() {
        return venta;
    }

    public void setVenta(Venta venta) {
        this.venta = venta;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public double getPrecio_venta() {
        return precio_venta;
    }

    public void setPrecio_venta(double precio_venta) {
        this.precio_venta = precio_venta;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public boolean isIva() {
        return iva;
    }

    public void setIva(boolean iva) {
        this.iva = iva;
    }

    /**
     * Devuelve una representación en cadena de texto del objeto DetalleVenta.
     *
     * @return La representación en cadena de texto del objeto DetalleVenta.
     */
    @Override
    public String toString() {
        return "DetalleVenta{"
                + "id_detalle_venta=" + id_detalle_venta
                + ", venta=" + venta
                + ", producto=" + producto
                + ", precio_venta=" + precio_venta
                + ", cantidad=" + cantidad
                + ", iva=" + iva
                + '}';
    }
}
