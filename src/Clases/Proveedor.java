/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Clases;

/**
 *
 * @author gg
 */
public class Proveedor {

    private int id_proveedor;
    private String nombre_proveedor;
    private String direccion;
    private String correo_electronico;
    private String telefono;

    /**
     * Constructor por defecto de la clase Proveedor.
     */
    public Proveedor() {
    }

    /**
     * Constructor de la clase Proveedor que inicializa todos los atributos.
     *
     * @param id_proveedor El ID del proveedor.
     * @param nombre_proveedor El nombre del proveedor.
     * @param direccion La dirección del proveedor.
     * @param correo_electronico El correo electrónico del proveedor.
     * @param telefono El teléfono del proveedor.
     */
    public Proveedor(int id_proveedor, String nombre_proveedor, String direccion, String correo_electronico, String telefono) {
        this.id_proveedor = id_proveedor;
        this.nombre_proveedor = nombre_proveedor;
        this.direccion = direccion;
        this.correo_electronico = correo_electronico;
        this.telefono = telefono;
    }

    // Getters y setters
    public int getId_proveedor() {
        return id_proveedor;
    }

    public void setId_proveedor(int id_proveedor) {
        this.id_proveedor = id_proveedor;
    }

    public String getNombre_proveedor() {
        return nombre_proveedor;
    }

    public void setNombre_proveedor(String nombre_proveedor) {
        this.nombre_proveedor = nombre_proveedor;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getCorreo_electronico() {
        return correo_electronico;
    }

    public void setCorreo_electronico(String correo_electronico) {
        this.correo_electronico = correo_electronico;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    /**
     * Devuelve una representación en cadena de texto del objeto Proveedor.
     *
     * @return La representación en cadena de texto del objeto Proveedor.
     */
    @Override
    public String toString() {
        return "Proveedor{"
                + "id_proveedor=" + id_proveedor
                + ", nombre_proveedor='" + nombre_proveedor + '\''
                + ", direccion='" + direccion + '\''
                + ", correo_electronico='" + correo_electronico + '\''
                + ", telefono='" + telefono + '\''
                + '}';
    }
}
