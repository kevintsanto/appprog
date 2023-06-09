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
public class Venta {

    private int id_venta;
    private LocalDate fecha_venta;
    private Cliente cliente;
    private Usuario usuario;

    /**
     * Constructor por defecto de la clase Venta.
     */
    public Venta() {
    }

    /**
     * Constructor de la clase Venta que inicializa todos los atributos.
     *
     * @param id_venta El ID de la venta.
     * @param fecha_venta La fecha de la venta.
     * @param cliente El cliente asociado a la venta.
     * @param usuario El usuario asociado a la venta.
     */
    public Venta(int id_venta, LocalDate fecha_venta, Cliente cliente, Usuario usuario) {
        this.id_venta = id_venta;
        this.fecha_venta = fecha_venta;
        this.cliente = cliente;
        this.usuario = usuario;
    }

    // Getters y setters
    public int getId_venta() {
        return id_venta;
    }

    public void setId_venta(int id_venta) {
        this.id_venta = id_venta;
    }

    public LocalDate getFecha_venta() {
        return fecha_venta;
    }

    public void setFecha_venta(LocalDate fecha_venta) {
        this.fecha_venta = fecha_venta;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    /**
     * Devuelve una representación en cadena de texto del objeto Venta.
     *
     * @return La representación en cadena de texto del objeto Venta.
     */
    @Override
    public String toString() {
        return "Venta{"
                + "id_venta=" + id_venta
                + ", fecha_venta=" + fecha_venta
                + ", cliente=" + cliente
                + ", usuario=" + usuario
                + '}';
    }
}
