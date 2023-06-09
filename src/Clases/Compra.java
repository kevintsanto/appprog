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
public class Compra {

    private int id_compra;
    private LocalDate fecha_compra;
    private Proveedor proveedor;
    private Usuario usuario;

    /**
     * Constructor por defecto de la clase Compra.
     */
    public Compra() {
    }

    /**
     * Constructor de la clase Compra que inicializa todos los atributos.
     *
     * @param id_compra El ID de la compra.
     * @param fecha_compra La fecha de la compra.
     * @param proveedor El ID del proveedor.
     * @param usuario El ID del usuario.
     */
    public Compra(int id_compra, LocalDate fecha_compra, Proveedor proveedor, Usuario usuario) {
        this.id_compra = id_compra;
        this.fecha_compra = fecha_compra;
        this.proveedor = proveedor;
        this.usuario = usuario;
    }

    // Getters y setters
    public int getId_compra() {
        return id_compra;
    }

    public void setId_compra(int id_compra) {
        this.id_compra = id_compra;
    }

    public LocalDate getFecha_compra() {
        return fecha_compra;
    }

    public void setFecha_compra(LocalDate fecha_compra) {
        this.fecha_compra = fecha_compra;
    }

    public Proveedor getProveedor() {
        return proveedor;
    }

    public void setProveedor(Proveedor proveedor) {
        this.proveedor = proveedor;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    @Override
    public String toString() {
        return "Compra{"
                + "idCompra=" + id_compra
                + ", fechaCompra=" + fecha_compra
                + ", proveedor=" + proveedor
                + ", usuario=" + usuario
                + '}';
    }
}
