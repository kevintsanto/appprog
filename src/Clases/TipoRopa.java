/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Clases;

/**
 *
 * @author gg
 */
public class TipoRopa {

    private int id_tipo;
    private String nombre_tipo;

    /**
     * Constructor por defecto de la clase Tipo.
     */
    public TipoRopa() {
    }

    /**
     * Constructor de la clase Tipo que inicializa todos los atributos.
     *
     * @param id_tipo El ID del tipo.
     * @param nombre_tipo El nombre del tipo.
     */
    public TipoRopa(int id_tipo, String nombre_tipo) {
        this.id_tipo = id_tipo;
        this.nombre_tipo = nombre_tipo;
    }

    // Getters y setters
    public int getId_tipo() {
        return id_tipo;
    }

    public void setId_tipo(int id_tipo) {
        this.id_tipo = id_tipo;
    }

    public String getNombre_tipo() {
        return nombre_tipo;
    }

    public void setNombre_tipo(String nombre_tipo) {
        this.nombre_tipo = nombre_tipo;
    }

    /**
     * Devuelve una representación en cadena de texto del objeto Tipo.
     *
     * @return La representación en cadena de texto del objeto Tipo.
     */
    @Override
    public String toString() {
        return "TipoRopa{"
                + "id_tipo=" + id_tipo
                + ", nombre_tipo='" + nombre_tipo + '\''
                + '}';
    }
}
