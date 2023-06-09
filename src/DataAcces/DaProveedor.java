/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DataAcces;
import Clases.Proveedor;
import Utilidades.ConexionBD;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
/**
 *Esta clase representa un proveedor con sus atributos correspondientes.
 * @author gg
 */
public class DaProveedor {
      private Connection connection;

    public DaProveedor() throws SQLException {
        connection = ConexionBD.getConnection();
    }
    /**
     * Inserta un proveedor en la base de datos.
     *
     * @param proveedor El proveedor a insertar.
     * @return True si se insertó correctamente, false en caso contrario.
     */
    public boolean agregar(Proveedor proveedor) {
        try {
            PreparedStatement ps = connection.prepareStatement("INSERT INTO proveedor (id_proveedor, nombre_proveedor, direccion, correo_electronico, telefono) VALUES (?, ?, ?, ?, ?)");
            ps.setInt(1, proveedor.getId_proveedor());
            ps.setString(2, proveedor.getNombre_proveedor());
            ps.setString(3, proveedor.getDireccion());
            ps.setString(4, proveedor.getCorreo_electronico());
            ps.setString(5, proveedor.getTelefono());

            int filasAfectadas = ps.executeUpdate(); // Ejecutar la consulta de inserción
            return filasAfectadas > 0; // Verificar si se insertó al menos una fila
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al insertar el proveedor: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            return false; // Hubo un error al ejecutar la consulta
        }
    }
/**
 * Obtiene un nuevo ID para un proveedor en la base de datos.
 *
 * @return El nuevo ID para el proveedor.
 */
public int obtenerNuevoID() {
    int nuevoID = 0;
    try {
        PreparedStatement ps = connection.prepareStatement("SELECT MAX(id_proveedor) FROM proveedor");
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            nuevoID = rs.getInt(1) + 1;
        } else {
            nuevoID = 1;
        }
        rs.close();
        ps.close();
    } catch (SQLException ex) {
        JOptionPane.showMessageDialog(null, "Error al obtener el nuevo ID del proveedor: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
    }
    return nuevoID;
}

    /**
     * Actualiza los datos de un proveedor en la base de datos.
     *
     * @param proveedor El proveedor con los datos actualizados.
     * @return True si se actualizó correctamente, false en caso contrario.
     */
    public boolean actualizar(Proveedor proveedor) {
        try {
            PreparedStatement ps = connection.prepareStatement("UPDATE proveedor SET nombre_proveedor = ?, direccion = ?, correo_electronico = ?, telefono = ? WHERE id_proveedor = ?");
            ps.setString(1, proveedor.getNombre_proveedor());
            ps.setString(2, proveedor.getDireccion());
            ps.setString(3, proveedor.getCorreo_electronico());
            ps.setString(4, proveedor.getTelefono());
            ps.setInt(5, proveedor.getId_proveedor());

            int filasAfectadas = ps.executeUpdate(); // Ejecutar la consulta de actualización
            return filasAfectadas > 0; // Verificar si se actualizó al menos una fila
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al actualizar el proveedor: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            return false; // Hubo un error al ejecutar la consulta
        }
    }

    /**
     * Elimina un proveedor de la base de datos.
     *
     * @param id_proveedor El ID del proveedor a eliminar.
     * @return True si se eliminó correctamente, false en caso contrario.
     */
    public boolean eliminar(int id_proveedor) {
        try {
            PreparedStatement ps = connection.prepareStatement("DELETE FROM proveedor WHERE id_proveedor = ?");
            ps.setInt(1, id_proveedor);

            int filasAfectadas = ps.executeUpdate(); // Ejecutar la consulta de eliminación
            return filasAfectadas > 0; // Verificar si se eliminó al menos una fila
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al eliminar el proveedor: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            return false; // Hubo un error al ejecutar la consulta
        }
    }

    /**
     * Busca un proveedor por su ID en la base de datos.
     *
     * @param id_proveedor El ID del proveedor a buscar.
     * @return El proveedor encontrado, o null si no se encontró ningún proveedor con el ID especificado.
     */
    public Proveedor buscarPorId (int id_proveedor) {
        try {
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM proveedor WHERE id_proveedor = ?");
            ps.setInt(1, id_proveedor);

            ResultSet rs = ps.executeQuery(); // Ejecutar la consulta de selección
            if (rs.next()) {
                Proveedor proveedor = new Proveedor();
                proveedor.setId_proveedor(rs.getInt("id_proveedor"));
                proveedor.setNombre_proveedor(rs.getString("nombre_proveedor"));
                proveedor.setDireccion(rs.getString("direccion"));
                proveedor.setCorreo_electronico(rs.getString("correo_electronico"));
                proveedor.setTelefono(rs.getString("telefono"));

                return proveedor; // Devolver el proveedor encontrado
            } else {
                return null; // No se encontró ningún proveedor con el ID especificado
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al obtener el proveedor: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            return null; // Hubo un error al ejecutar la consulta
        }
    }

    /**
     * Obtiene una lista de todos los proveedores almacenados en la base de datos.
     *
     * @return Una lista de proveedores.
     */
    public List<Proveedor> obtenerProveedores() {
        List<Proveedor> proveedores = new ArrayList<>();
        try {
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM proveedor");
            ResultSet rs = ps.executeQuery(); // Ejecutar la consulta de selección

            while (rs.next()) {
                Proveedor proveedor = new Proveedor();
                proveedor.setId_proveedor(rs.getInt("id_proveedor"));
                proveedor.setNombre_proveedor(rs.getString("nombre_proveedor"));
                proveedor.setDireccion(rs.getString("direccion"));
                proveedor.setCorreo_electronico(rs.getString("correo_electronico"));
                proveedor.setTelefono(rs.getString("telefono"));

                proveedores.add(proveedor); // Agregar el proveedor a la lista
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al obtener los proveedores: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
        return proveedores;
    }
/**
 * Busca proveedores que coincidan con un criterio de búsqueda en la base de datos.
 *
 * @param criterio El criterio de búsqueda.
 * @return Una lista de proveedores que coinciden con el criterio de búsqueda.
 */
public List<Proveedor> buscar(String criterio) {
    List<Proveedor> proveedores = new ArrayList<>();
    try {
        // Preparar la consulta SQL con un filtro LIKE para buscar en los campos relevantes
        String query = "SELECT * FROM proveedor WHERE nombre_proveedor LIKE ? OR direccion LIKE ? OR correo_electronico LIKE ? OR telefono LIKE ?";
        PreparedStatement ps = connection.prepareStatement(query);
        String likePattern = "%" + criterio + "%"; // Agregar los caracteres comodín al criterio
        ps.setString(1, likePattern);
        ps.setString(2, likePattern);
        ps.setString(3, likePattern);
        ps.setString(4, likePattern);

        ResultSet rs = ps.executeQuery(); // Ejecutar la consulta de selección

        while (rs.next()) {
            Proveedor proveedor = new Proveedor();
            proveedor.setId_proveedor(rs.getInt("id_proveedor"));
            proveedor.setNombre_proveedor(rs.getString("nombre_proveedor"));
            proveedor.setDireccion(rs.getString("direccion"));
            proveedor.setCorreo_electronico(rs.getString("correo_electronico"));
            proveedor.setTelefono(rs.getString("telefono"));

            proveedores.add(proveedor); // Agregar el proveedor a la lista
        }
    } catch (SQLException ex) {
        JOptionPane.showMessageDialog(null, "Error al buscar proveedores: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
    }
    return proveedores;
}
}
