/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Clases;

/**
 *
 * @author gg
 */
public class Marca {

    private int id_marca;
    private String nombre_marca;

    /**
     * Constructor por defecto de la clase Marca.
     */
    public Marca() {
    }

    /**
     * Constructor de la clase Marca que inicializa todos los atributos.
     *
     * @param id_marca El ID de la marca.
     * @param nombre_marca El nombre de la marca.
     */
    public Marca(int id_marca, String nombre_marca) {
        this.id_marca = id_marca;
        this.nombre_marca = nombre_marca;
    }

    // Getters y setters
    public int getId_marca() {
        return id_marca;
    }

    public void setId_marca(int id_marca) {
        this.id_marca = id_marca;
    }

    public String getNombre_marca() {
        return nombre_marca;
    }

    public void setNombre_marca(String nombre_marca) {
        this.nombre_marca = nombre_marca;
    }

    /**
     * Devuelve una representación en cadena de texto del objeto Marca.
     *
     * @return La representación en cadena de texto del objeto Marca.
     */
    @Override
    public String toString() {
        return "Marca{"
                + "id_marca=" + id_marca
                + ", nombre_marca='" + nombre_marca + '\''
                + '}';
    }
}
