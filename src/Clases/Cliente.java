/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Clases;

/**
 *
 * @author gg
 */
public class Cliente {

    private int id_cliente;
    private Usuario usuario;
    private String telefono;
    private String nombre;
    private String apellido;
    private String direccion;
    private String ciudad;

    /**
     * Constructor por defecto de la clase Cliente.
     */
    public Cliente() {
    }
  
    /**
     * Constructor de la clase Cliente que inicializa todos los atributos.
     *
     * @param id_cliente El ID del cliente.
     * @param usuario El objeto Usuario al que está asociado el cliente.
     * @param telefono El teléfono del cliente.
     * @param nombre El nombre del cliente.
     * @param apellido El apellido del cliente.
     * @param direccion La dirección del cliente.
     * @param ciudad La ciudad del cliente.
     *
     */
   
    public Cliente(int id_cliente, Usuario usuario, String telefono, String nombre, String apellido, String direccion, String ciudad) {
        this.id_cliente = id_cliente;
        this.usuario = usuario;
        this.telefono = telefono;
        this.nombre = nombre;
        this.apellido = apellido;
        this.direccion = direccion;
        this.ciudad = ciudad;

    }


    // Getters y setters
    public int getId_cliente() {
        return id_cliente;
    }

    public void setId_cliente(int id_cliente) {
        this.id_cliente = id_cliente;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    /**
     * Devuelve una representación en cadena de texto del objeto Cliente.
     *
     * @return La representación en cadena de texto del objeto Cliente.
     */
    @Override
    public String toString() {
        return "Cliente{"
                + "id_cliente=" + id_cliente
                + ", usuario=" + usuario
                + ", telefono='" + telefono + '\''
                + ", nombre='" + nombre + '\''
                + ", apellido='" + apellido + '\''
                + ", direccion='" + direccion + '\''
                + ", ciudad='" + ciudad + '}';

    }
}
