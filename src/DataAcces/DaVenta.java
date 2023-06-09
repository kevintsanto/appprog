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
import Clases.Cliente;
import Clases.Usuario;
import Clases.Venta;
import Utilidades.ConexionBD;

/**
 *Esta clase representa una venta con sus atributos correspondientes.
 * @author gg
 */
public class DaVenta {
      private Connection connection;

    public DaVenta() throws SQLException {
        connection = ConexionBD.getConnection();
    }
    public boolean agregar(Venta venta) {
        try {
            PreparedStatement ps = connection.prepareStatement("INSERT INTO venta (id_venta, fecha_venta, id_cliente, id_usuario) VALUES (?, ?, ?, ?)");
            ps.setInt(1, venta.getId_venta());
            ps.setDate(2, java.sql.Date.valueOf(venta.getFecha_venta()));
            ps.setInt(3, venta.getCliente().getId_cliente());
            ps.setInt(4, venta.getUsuario().getId_usuario());

            int filasInsertadas = ps.executeUpdate(); // Ejecutar la consulta de inserción
            if (filasInsertadas > 0) {
                return true; // La venta se agregó correctamente
            } else {
                JOptionPane.showMessageDialog(null, "Error al agregar la venta", "Error", JOptionPane.ERROR_MESSAGE);
                return false; // Hubo un error al agregar la venta
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al agregar la venta: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            return false; // Hubo un error al ejecutar la consulta
        }
    }

    public boolean actualizar(Venta venta) {
        try {
            PreparedStatement ps = connection.prepareStatement("UPDATE venta SET fecha_venta = ?, id_cliente = ?, id_usuario = ? WHERE id_venta = ?");
            ps.setDate(1, java.sql.Date.valueOf(venta.getFecha_venta()));
            ps.setInt(2, venta.getCliente().getId_cliente());
            ps.setInt(3, venta.getUsuario().getId_usuario());
            ps.setInt(4, venta.getId_venta());

            int filasActualizadas = ps.executeUpdate(); // Ejecutar la consulta de actualización
            if (filasActualizadas > 0) {
                return true; // La venta se actualizó correctamente
            } else {
                JOptionPane.showMessageDialog(null, "Error al actualizar la venta", "Error", JOptionPane.ERROR_MESSAGE);
                return false; // Hubo un error al actualizar la venta
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al actualizar la venta: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            return false; // Hubo un error al ejecutar la consulta
        }
    }

    public boolean eliminar(int id_venta) {
        try {
            PreparedStatement ps = connection.prepareStatement("DELETE FROM venta WHERE id_venta = ?");
            ps.setInt(1, id_venta);

            int filasEliminadas = ps.executeUpdate(); // Ejecutar la consulta de eliminación
            if (filasEliminadas > 0) {
                return true; // La venta se eliminó correctamente
            } else {
                JOptionPane.showMessageDialog(null, "Error al eliminar la venta", "Error", JOptionPane.ERROR_MESSAGE);
                return false; // Hubo un error al eliminar la venta
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al eliminar la venta: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            return false; // Hubo un error al ejecutar la consulta
        }
    }

    public Venta obtenerPorId(int id_venta) {
        try {
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM venta WHERE id_venta = ?");
            ps.setInt(1, id_venta);

            ResultSet rs = ps.executeQuery(); // Ejecutar la consulta de selección
            if (rs.next()) {
                Venta venta = new Venta();
                venta.setId_venta(rs.getInt("id_venta"));
                venta.setFecha_venta(rs.getDate("fecha_venta").toLocalDate());
                venta.setCliente(buscarCliente(rs.getInt("id_cliente")));
                venta.setUsuario(buscarUsuario(rs.getInt("id_usuario")));

                return venta; // Devolver la venta encontrada
            } else {
                return null; // No se encontró ninguna venta con el ID especificado
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al obtener la venta: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            return null; // Hubo un error al ejecutar la consulta
        }
    }

    public List<Venta> obtenerTodos() {
        try {
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM venta");

            ResultSet rs = ps.executeQuery(); // Ejecutar la consulta de selección
            List<Venta> ventas = new ArrayList<>();
            while (rs.next()) {
                Venta venta = new Venta();
                venta.setId_venta(rs.getInt("id_venta"));
                venta.setFecha_venta(rs.getDate("fecha_venta").toLocalDate());
                venta.setCliente(buscarCliente(rs.getInt("id_cliente")));
                venta.setUsuario(buscarUsuario(rs.getInt("id_usuario")));

                ventas.add(venta); // Agregar cada venta a la lista
            }
            return ventas; // Devolver la lista de ventas
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al obtener las ventas: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            return null; // Hubo un error al ejecutar la consulta
        }
    }

    public List<Venta> buscar(String criterio) {
        List<Venta> ventas = new ArrayList<>();

        try {
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM venta WHERE id_venta LIKE ? OR fecha_venta LIKE ?");
            ps.setString(1, "%" + criterio + "%");
            ps.setString(2, "%" + criterio + "%");

            ResultSet rs = ps.executeQuery(); // Ejecutar la consulta

            while (rs.next()) {
                Venta venta = new Venta();
                venta.setId_venta(rs.getInt("id_venta"));
                venta.setFecha_venta(rs.getDate("fecha_venta").toLocalDate());
                venta.setCliente(buscarCliente(rs.getInt("id_cliente")));
                venta.setUsuario(buscarUsuario(rs.getInt("id_usuario")));

                ventas.add(venta); // Agregar la venta a la lista de ventas encontradas
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return ventas; // Devolver la lista de ventas encontradas
    }

    public Cliente buscarCliente(int id_cliente) {
        try {
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM cliente WHERE id_cliente = ?");
            ps.setInt(1, id_cliente);

            ResultSet rs = ps.executeQuery(); // Ejecutar la consulta de selección
            if (rs.next()) {
                Cliente cliente = new Cliente();
                cliente.setId_cliente(rs.getInt("id_cliente"));
                cliente.setNombre(rs.getString("nombre"));
                cliente.setApellido(rs.getString("apellido"));
                cliente.setTelefono(rs.getString("telefono"));
                cliente.setDireccion(rs.getString("direccion"));
                cliente.setCiudad(rs.getString("ciudad"));

                return cliente; // Devolver el cliente encontrado
            } else {
                return null; // No se encontró ningún cliente con el ID especificado
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al buscar el cliente: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            return null; // Hubo un error al ejecutar la consulta
        }
    }

    private Usuario buscarUsuario(int id_usuario) {
        try {
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM usuario WHERE id_usuario = ?");
            ps.setInt(1, id_usuario);

            ResultSet rs = ps.executeQuery(); // Ejecutar la consulta de selección
            if (rs.next()) {
                Usuario usuario = new Usuario();
                usuario.setId_usuario(rs.getInt("id_usuario"));
                usuario.setNombre(rs.getString("nombre"));
                // Otros atributos del usuario...

                return usuario; // Devolver el usuario encontrado
            } else {
                return null; // No se encontró ningún usuario con el ID especificado
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al buscar el usuario: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            return null; // Hubo un error al ejecutar la consulta
        }
    }
}
