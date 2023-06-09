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
import Clases.DetalleCompra;
import Clases.Producto;
import Utilidades.ConexionBD;

/**
 * Esta clase representa un detalle de compra con sus atributos
 * correspondientes.
 *
 * @author gg
 */
public class DaDetalleCompra {
    private Connection connection;

    public DaDetalleCompra() throws SQLException {
        connection = ConexionBD.getConnection();
    }

    public boolean agregar(DetalleCompra detalleCompra) {
        try {
            PreparedStatement ps = connection.prepareStatement("INSERT INTO detalle_compra (id_detalle_compra, id_compra, id_producto, precio_compra, cantidad) VALUES (?, ?, ?, ?, ?)");
            ps.setInt(1, detalleCompra.getId_detalle_compra());
            ps.setInt(2, detalleCompra.getCompra().getId_compra());
            ps.setInt(3, detalleCompra.getProducto().getId_producto());
            ps.setDouble(4, detalleCompra.getPrecio_compra());
            ps.setInt(5, detalleCompra.getCantidad());

            int filasInsertadas = ps.executeUpdate(); // Ejecutar la consulta de inserción
            if (filasInsertadas > 0) {
                return true; // El detalle de compra se agregó correctamente
            } else {
                JOptionPane.showMessageDialog(null, "Error al agregar el detalle de compra", "Error", JOptionPane.ERROR_MESSAGE);
                return false; // Hubo un error al agregar el detalle de compra
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al agregar el detalle de compra: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            return false; // Hubo un error al ejecutar la consulta
        }
    }

    public boolean actualizar(DetalleCompra detalleCompra) {
        try {
            PreparedStatement ps = connection.prepareStatement("UPDATE detalle_compra SET id_compra = ?, id_producto = ?, precio_compra = ?, cantidad = ? WHERE id_detalle_compra = ?");
            ps.setInt(1, detalleCompra.getCompra().getId_compra());
            ps.setInt(2, detalleCompra.getProducto().getId_producto());
            ps.setDouble(3, detalleCompra.getPrecio_compra());
            ps.setInt(4, detalleCompra.getCantidad());
            ps.setInt(5, detalleCompra.getId_detalle_compra());

            int filasActualizadas = ps.executeUpdate(); // Ejecutar la consulta de actualización
            if (filasActualizadas > 0) {
                return true; // El detalle de compra se actualizó correctamente
            } else {
                JOptionPane.showMessageDialog(null, "Error al actualizar el detalle de compra", "Error", JOptionPane.ERROR_MESSAGE);
                return false; // Hubo un error al actualizar el detalle de compra
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al actualizar el detalle de compra: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            return false; // Hubo un error al ejecutar la consulta
        }
    }

    public boolean eliminar(int id_detalle_compra) {
        try {
            PreparedStatement ps = connection.prepareStatement("DELETE FROM detalle_compra WHERE id_detalle_compra = ?");
            ps.setInt(1, id_detalle_compra);

            int filasEliminadas = ps.executeUpdate(); // Ejecutar la consulta de eliminación
            if (filasEliminadas > 0) {
                return true; // El detalle de compra se eliminó correctamente
            } else {
                JOptionPane.showMessageDialog(null, "Error al eliminar el detalle de compra", "Error", JOptionPane.ERROR_MESSAGE);
                return false; // Hubo un error al eliminar el detalle de compra
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al eliminar el detalle de compra: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            return false; // Hubo un error al ejecutar la consulta
        }
    }

    public DetalleCompra obtenerPorId(int id_detalle_compra) {
        try {
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM detalle_compra WHERE id_detalle_compra = ?");
            ps.setInt(1, id_detalle_compra);

            ResultSet rs = ps.executeQuery(); // Ejecutar la consulta de selección
            if (rs.next()) {
                DetalleCompra detalleCompra = new DetalleCompra();
                detalleCompra.setId_detalle_compra(rs.getInt("id_detalle_compra"));
                detalleCompra.setCompra(obtenerCompraPorId(rs.getInt("id_compra")));
                detalleCompra.setProducto(obtenerProductoPorId(rs.getInt("id_producto")));
                detalleCompra.setPrecio_compra(rs.getDouble("precio_compra"));
                detalleCompra.setCantidad(rs.getInt("cantidad"));
                return detalleCompra;
            } else {
                return null; // No se encontró el detalle de compra con el ID especificado
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al obtener el detalle de compra: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            return null; // Hubo un error al ejecutar la consulta
        }
    }

    public List<DetalleCompra> obtenerTodos() {
        try {
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM detalle_compra");

            ResultSet rs = ps.executeQuery(); // Ejecutar la consulta de selección
            List<DetalleCompra> detalleCompras = new ArrayList<>();
            while (rs.next()) {
                DetalleCompra detalleCompra = new DetalleCompra();
                detalleCompra.setId_detalle_compra(rs.getInt("id_detalle_compra"));
                detalleCompra.setCompra(obtenerCompraPorId(rs.getInt("id_compra")));
                detalleCompra.setProducto(obtenerProductoPorId(rs.getInt("id_producto")));
                detalleCompra.setPrecio_compra(rs.getDouble("precio_compra"));
                detalleCompra.setCantidad(rs.getInt("cantidad"));
                detalleCompras.add(detalleCompra);
            }
            return detalleCompras;
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al obtener los detalles de compra: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            return null; // Hubo un error al ejecutar la consulta
        }
    }

    private Compra obtenerCompraPorId(int id_compra) {
        try {
            DaCompra daCompra = new DaCompra(); // Instanciar la clase DaCompra

            // Utilizar el método obtenerPorId de DaCompra para obtener la compra por ID
            return daCompra.obtenerPorId(id_compra);
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            return null; // Manejar el error apropiadamente según tus necesidades
        }
    }

    private Producto obtenerProductoPorId(int id_producto) throws SQLException {
        DaProducto daProducto = new DaProducto(); // Instanciar la clase DaProducto
        // Utilizar el método obtenerPorId de DaProducto para obtener el producto por ID
        return daProducto.obtenerPorId(id_producto);
    }

}
