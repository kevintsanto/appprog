/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Clases;

import java.time.LocalDate;

/**
 *
 * @author gg
 */
public class Producto {

    private int id_producto;
    private String nombre;
    private String descripcion;
    private double precio_unitario;
    private int stock;
    private LocalDate fecha_creacion;
    private LocalDate fecha_actualizacion;
    private Marca marca;       // Relación con la tabla Marca
    private TipoRopa tipoRopa; // Relación con la tabla TipoRopa
    private String color;

    /**
     * Constructor por defecto de la clase Producto.
     */
    public Producto() {
    }

    /**
     * Constructor de la clase Producto que inicializa todos los atributos.
     *
     * @param id_producto El ID del producto.
     * @param nombre El nombre del producto.
     * @param descripcion La descripción del producto.
     * @param precio_unitario El precio unitario del producto.
     * @param stock El stock del producto.
     * @param fecha_creacion La fecha de creación del producto.
     * @param fecha_actualizacion La fecha de actualización del producto.
     * @param marca marca asociada al producto.
     * @param tiporopa tipo asociado al producto.
     * @param color El color del producto.
     */
    public Producto(int id_producto, String nombre, String descripcion, Double precio_unitario, int stock, LocalDate fecha_creacion, LocalDate fecha_actualizacion, Marca marca, TipoRopa tiporopa, String color) {
        this.id_producto = id_producto;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precio_unitario = precio_unitario;
        this.stock = stock;
        this.fecha_creacion = fecha_creacion;
        this.fecha_actualizacion = fecha_actualizacion;
        this.marca = marca;
        this.tipoRopa = tiporopa;
        this.color = color;
    }


    // Getters y setters
    public int getId_producto() {
        return id_producto;
    }

    public void setId_producto(int id_producto) {
        this.id_producto = id_producto;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Double getPrecio_unitario() {
        return precio_unitario;
    }

    public void setPrecio_unitario(Double precio_unitario) {
        this.precio_unitario = precio_unitario;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public LocalDate getFecha_creacion() {
        return fecha_creacion;
    }

    public void setFecha_creacion(LocalDate fecha_creacion) {
        this.fecha_creacion = fecha_creacion;
    }

    public LocalDate getFecha_actualizacion() {
        return fecha_actualizacion;
    }

    public void setFecha_actualizacion(LocalDate fecha_actualizacion) {
        this.fecha_actualizacion = fecha_actualizacion;
    }

    public Marca getMarca() {
        return marca;
    }

    public void setMarca(Marca marca) {
        this.marca = marca;
    }

    public TipoRopa getTipoRopa() {
        return tipoRopa;
    }

    public void setTipoRopa(TipoRopa tipoRopa) {
        this.tipoRopa = tipoRopa;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    /**
     * Devuelve una representación en cadena de texto del objeto Producto.
     *
     * @return La representación en cadena de texto del objeto Producto.
     */
    @Override
    public String toString() {
        return "Producto{"
                + "id_producto=" + id_producto
                + ", nombre='" + nombre + '\''
                + ", descripcion='" + descripcion + '\''
                + ", precio_unitario=" + precio_unitario
                + ", stock=" + stock
                + ", fecha_creacion=" + fecha_creacion
                + ", fecha_actualizacion=" + fecha_actualizacion
                + ", marca=" + marca
                + ", tipoRopa=" + tipoRopa
                + ", color='" + color + '\''
                + '}';
    }
}
