/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Clases;

/**
 *
 * @author gg
 */
public class DetalleCompra {

    private int id_detalle_compra;
    private Compra compra;
    private Producto producto;
    private double precio_compra;
    private int cantidad;

    /**
     * Constructor por defecto de la clase DetalleCompra.
     */
    public DetalleCompra() {
    }

    /**
     * Constructor de la clase DetalleCompra que inicializa todos los atributos.
     * 
     * @param id_detalle_compra  El ID del detalle de compra.
     * @param compra             La compra asociada al detalle de compra.
     * @param producto           El producto asociado al detalle de compra.
     * @param precio_compra      El precio de compra del producto.
     * @param cantidad           La cantidad comprada del producto.
     */
    public DetalleCompra(int id_detalle_compra, Compra compra, Producto producto, double precio_compra, int cantidad) {
        this.id_detalle_compra = id_detalle_compra;
        this.compra = compra;
        this.producto = producto;
        this.precio_compra = precio_compra;
        this.cantidad = cantidad;
    }

    // Getters y setters

    public int getId_detalle_compra() {
        return id_detalle_compra;
    }

    public void setId_detalle_compra(int id_detalle_compra) {
        this.id_detalle_compra = id_detalle_compra;
    }

    public Compra getCompra() {
        return compra;
    }

    public void setCompra(Compra compra) {
        this.compra = compra;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public double getPrecio_compra() {
        return precio_compra;
    }

    public void setPrecio_compra(double precio_compra) {
        this.precio_compra = precio_compra;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    /**
     * Devuelve una representación en cadena de texto del objeto DetalleCompra.
     * 
     * @return La representación en cadena de texto del objeto DetalleCompra.
     */
    @Override
    public String toString() {
        return "DetalleCompra{"
                + "id_detalle_compra=" + id_detalle_compra
                + ", compra=" + compra
                + ", producto=" + producto
                + ", precio_compra=" + precio_compra
                + ", cantidad=" + cantidad
                + '}';
    }
}
