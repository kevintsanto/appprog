/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Clases;

/**
 *
 * @author gg
 */
public class Usuario {

    private int id_usuario;
    private String nombre;
    private String apellido;
    private String email;
    private String contraseña;
    private String tipo_usuario;
    private String permiso;

    /**
     * Constructor por defecto de la clase Usuario.
     */
    public Usuario() {
    }

    /**
     * Constructor de la clase Usuario que inicializa todos los atributos.
     *
     * @param id_usuario El ID del usuario.
     * @param nombre El nombre del usuario.
     * @param apellido El apellido del usuario.
     * @param email El correo electrónico del usuario.
     * @param contraseña La contraseña del usuario.
     * @param tipo_usuario El tipo de usuario.
     * @param permiso El permiso del usuario.
     */
    public Usuario(int id_usuario, String nombre, String apellido, String email, String contraseña, String tipo_usuario, String permiso) {
        this.id_usuario = id_usuario;
        this.nombre = nombre;
        this.apellido = apellido;
        this.email = email;
        this.contraseña = contraseña;
        this.tipo_usuario = tipo_usuario;
        this.permiso = permiso;
    }


    // Getters y setters
    public int getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(int id_usuario) {
        this.id_usuario = id_usuario;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContraseña() {
        return contraseña;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }

    public String getTipo_usuario() {
        return tipo_usuario;
    }

    public void setTipo_usuario(String tipo_usuario) {
        this.tipo_usuario = tipo_usuario;
    }

    public String getPermiso() {
        return permiso;
    }

    public void setPermiso(String permiso) {
        this.permiso = permiso;
    }

    /**
     * Devuelve una representación en cadena de texto del objeto Usuario.
     *
     * @return La representación en cadena de texto del objeto Usuario.
     */
    @Override
    public String toString() {
        return "Usuario{"
                + "id_usuario=" + id_usuario
                + ", nombre='" + nombre + '\''
                + ", apellido='" + apellido + '\''
                + ", email='" + email + '\''
                + ", contraseña='" + contraseña + '\''
                + ", tipo_usuario='" + tipo_usuario + '\''
                + ", permiso='" + permiso + '\''
                + '}';
    }
}
