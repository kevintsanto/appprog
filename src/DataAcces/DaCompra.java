/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DataAcces;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import Clases.Compra;
import Clases.Proveedor;
import Clases.Usuario;
import Utilidades.ConexionBD;

/**
 * Esta clase representa una compra con sus atributos correspondientes.
 *
 * @author gg
 */
public class DaCompra {

    private Connection connection;

    public DaCompra() throws SQLException {
        connection = ConexionBD.getConnection();
    }

    public boolean agregar(Compra compra) {
        try {
            PreparedStatement ps = connection.prepareStatement("INSERT INTO compra (id_compra, fecha_compra, id_proveedor, id_usuario) VALUES (?, ?, ?, ?)");
            ps.setInt(1, compra.getId_compra());
            ps.setDate(2, java.sql.Date.valueOf(compra.getFecha_compra()));
            ps.setInt(3, compra.getProveedor().getId_proveedor());
            ps.setInt(4, compra.getUsuario().getId_usuario());

            int filasInsertadas = ps.executeUpdate(); // Ejecutar la consulta de inserción
            if (filasInsertadas > 0) {
                return true; // La compra se agregó correctamente
            } else {
                JOptionPane.showMessageDialog(null, "Error al agregar la compra", "Error", JOptionPane.ERROR_MESSAGE);
                return false; // Hubo un error al agregar la compra
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al agregar la compra: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            return false; // Hubo un error al ejecutar la consulta
        }
    }

    public boolean actualizar(Compra compra) {
        try {
            PreparedStatement ps = connection.prepareStatement("UPDATE compra SET fecha_compra = ?, id_proveedor = ?, id_usuario = ? WHERE id_compra = ?");
            ps.setDate(1, java.sql.Date.valueOf(compra.getFecha_compra()));
            ps.setInt(2, compra.getProveedor().getId_proveedor());
            ps.setInt(3, compra.getUsuario().getId_usuario());
            ps.setInt(4, compra.getId_compra());

            int filasActualizadas = ps.executeUpdate(); // Ejecutar la consulta de actualización
            if (filasActualizadas > 0) {
                return true; // La compra se actualizó correctamente
            } else {
                JOptionPane.showMessageDialog(null, "Error al actualizar la compra", "Error", JOptionPane.ERROR_MESSAGE);
                return false; // Hubo un error al actualizar la compra
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al actualizar la compra: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            return false; // Hubo un error al ejecutar la consulta
        }
    }

    public boolean eliminar(int id_compra) {
        try {
            PreparedStatement ps = connection.prepareStatement("DELETE FROM compra WHERE id_compra = ?");
            ps.setInt(1, id_compra);

            int filasEliminadas = ps.executeUpdate(); // Ejecutar la consulta de eliminación
            if (filasEliminadas > 0) {
                return true; // La compra se eliminó correctamente
            } else {
                JOptionPane.showMessageDialog(null, "Error al eliminar la compra", "Error", JOptionPane.ERROR_MESSAGE);
                return false; // Hubo un error al eliminar la compra
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al eliminar la compra: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            return false; // Hubo un error al ejecutar la consulta
        }
    }

    public Compra obtenerPorId(int id_compra) {
        try {
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM compra WHERE id_compra = ?");
            ps.setInt(1, id_compra);

            ResultSet rs = ps.executeQuery(); // Ejecutar la consulta de selección
            if (rs.next()) {
                Compra compra = new Compra();
                compra.setId_compra(rs.getInt("id_compra"));
                compra.setFecha_compra(rs.getDate("fecha_compra").toLocalDate());
                compra.setProveedor(buscarProveedor(rs.getInt("id_proveedor")));
                compra.setUsuario(buscarUsuario(rs.getInt("id_usuario")));

                return compra; // Devolver la compra encontrada
            } else {
                return null; // No se encontró ninguna compra con el ID especificado
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al obtener la compra: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            return null; // Hubo un error al ejecutar la consulta
        }
    }

    public List<Compra> obtenerTodas() {
        try {
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM compra");

            ResultSet rs = ps.executeQuery(); // Ejecutar la consulta de selección
            List<Compra> compras = new ArrayList<>();
            while (rs.next()) {
                Compra compra = new Compra();
                compra.setId_compra(rs.getInt("id_compra"));
                compra.setFecha_compra(rs.getDate("fecha_compra").toLocalDate());
                compra.setProveedor(buscarProveedor(rs.getInt("id_proveedor")));
                compra.setUsuario(buscarUsuario(rs.getInt("id_usuario")));

                compras.add(compra); // Agregar cada compra a la lista
            }
            return compras; // Devolver la lista de compras
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al obtener las compras: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            return null; // Hubo un error al ejecutar la consulta
        }
    }

    public List<Compra> buscar(String criterio) {
        List<Compra> compras = new ArrayList<>();

        try {
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM compra WHERE id_compra LIKE ? OR fecha_compra LIKE ?");
            ps.setString(1, "%" + criterio + "%");
            ps.setString(2, "%" + criterio + "%");

            ResultSet rs = ps.executeQuery(); // Ejecutar la consulta

            while (rs.next()) {
                Compra compra = new Compra();
                compra.setId_compra(rs.getInt("id_compra"));
                compra.setFecha_compra(rs.getDate("fecha_compra").toLocalDate());
                compra.setProveedor(buscarProveedor(rs.getInt("id_proveedor")));
                compra.setUsuario(buscarUsuario(rs.getInt("id_usuario")));

                compras.add(compra); // Agregar la compra a la lista de compras encontradas
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return compras; // Devolver la lista de compras encontradas
    }

    public Proveedor buscarProveedor(int id_proveedor) {
        try {
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM proveedor WHERE id_proveedor = ?");
            ps.setInt(1, id_proveedor);

            ResultSet rs = ps.executeQuery(); // Ejecutar la consulta de selección
            if (rs.next()) {
                Proveedor proveedor = new Proveedor();
                proveedor.setId_proveedor(rs.getInt("id_proveedor"));
                proveedor.setNombre_proveedor(rs.getString("nombre_proveedor"));

                return proveedor; // Devolver el proveedor encontrado
            } else {
                return null; // No se encontró ningún proveedor con el ID especificado
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al obtener el proveedor: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            return null; // Hubo un error al ejecutar la consulta
        }
    }

    public Usuario buscarUsuario(int id_usuario) {
        try {
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM usuario WHERE id_usuario = ?");
            ps.setInt(1, id_usuario);

            ResultSet rs = ps.executeQuery(); // Ejecutar la consulta de selección
            if (rs.next()) {
                Usuario usuario = new Usuario();
                usuario.setId_usuario(rs.getInt("id_usuario"));
                usuario.setNombre(rs.getString("nombre"));

                return usuario; // Devolver el usuario encontrado
            } else {
                return null; // No se encontró ningún usuario con el ID especificado
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al obtener el usuario: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            return null; // Hubo un error al ejecutar la consulta
        }
    }
}
