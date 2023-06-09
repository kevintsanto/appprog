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
import Clases.DetalleVenta;

import Clases.Producto;

import Clases.Venta;
import Utilidades.ConexionBD;

/**
 * Esta clase representa un detalle de venta con sus atributos correspondientes.
 *
 * @author gg
 */
public class DaDetalleVenta {

       private Connection connection;

    public DaDetalleVenta() throws SQLException {
        connection = ConexionBD.getConnection();
    }

    public boolean agregar(DetalleVenta detalleVenta) {
        try {
            PreparedStatement ps = connection.prepareStatement("INSERT INTO detalle_venta (id_detalle_venta, id_venta, id_producto, precio_venta, cantidad, iva) VALUES (?, ?, ?, ?, ?, ?)");
            ps.setInt(1, detalleVenta.getId_detalle_venta());
            ps.setInt(2, detalleVenta.getVenta().getId_venta());
            ps.setInt(3, detalleVenta.getProducto().getId_producto());
            ps.setDouble(4, detalleVenta.getPrecio_venta());
            ps.setInt(5, detalleVenta.getCantidad());
            ps.setBoolean(6, detalleVenta.isIva());

            int filasInsertadas = ps.executeUpdate(); // Ejecutar la consulta de inserción
            if (filasInsertadas > 0) {
                return true; // El detalle de venta se agregó correctamente
            } else {
                JOptionPane.showMessageDialog(null, "Error al agregar el detalle de venta", "Error", JOptionPane.ERROR_MESSAGE);
                return false; // Hubo un error al agregar el detalle de venta
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al agregar el detalle de venta: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            return false; // Hubo un error al ejecutar la consulta
        }
    }

    public boolean actualizar(DetalleVenta detalleVenta) {
        try {
            PreparedStatement ps = connection.prepareStatement("UPDATE detalle_venta SET id_venta = ?, id_producto = ?, precio_venta = ?, cantidad = ?, iva = ? WHERE id_detalle_venta = ?");
            ps.setInt(1, detalleVenta.getVenta().getId_venta());
            ps.setInt(2, detalleVenta.getProducto().getId_producto());
            ps.setDouble(3, detalleVenta.getPrecio_venta());
            ps.setInt(4, detalleVenta.getCantidad());
            ps.setBoolean(5, detalleVenta.isIva());
            ps.setInt(6, detalleVenta.getId_detalle_venta());

            int filasActualizadas = ps.executeUpdate(); // Ejecutar la consulta de actualización
            if (filasActualizadas > 0) {
                return true; // El detalle de venta se actualizó correctamente
            } else {
                JOptionPane.showMessageDialog(null, "Error al actualizar el detalle de venta", "Error", JOptionPane.ERROR_MESSAGE);
                return false; // Hubo un error al actualizar el detalle de venta
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al actualizar el detalle de venta: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            return false; // Hubo un error al ejecutar la consulta
        }
    }

    public boolean eliminar(int id_detalle_venta) {
        try {
            PreparedStatement ps = connection.prepareStatement("DELETE FROM detalle_venta WHERE id_detalle_venta = ?");
            ps.setInt(1, id_detalle_venta);

            int filasEliminadas = ps.executeUpdate(); // Ejecutar la consulta de eliminación
            if (filasEliminadas > 0) {
                return true; // El detalle de venta se eliminó correctamente
            } else {
                JOptionPane.showMessageDialog(null, "Error al eliminar el detalle de venta", "Error", JOptionPane.ERROR_MESSAGE);
                return false; // Hubo un error al eliminar el detalle de venta
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al eliminar el detalle de venta: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            return false; // Hubo un error al ejecutar la consulta
        }
    }

    public DetalleVenta buscarPorId(int id_detalle_venta) {
        try {
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM detalle_venta WHERE id_detalle_venta = ?");
            ps.setInt(1, id_detalle_venta);

            ResultSet rs = ps.executeQuery(); // Ejecutar la consulta de selección
            if (rs.next()) {
                DetalleVenta detalleVenta = new DetalleVenta();
                detalleVenta.setId_detalle_venta(rs.getInt("id_detalle_venta"));

                // Obtener la venta asociada al detalle de venta
                int id_venta = rs.getInt("id_venta");
                Venta venta = obtenerVentaPorId(id_venta);
                detalleVenta.setVenta(venta);

                // Obtener el producto asociado al detalle de venta
                int id_producto = rs.getInt("id_producto");
                Producto producto = obtenerProductoPorId(id_producto);
                detalleVenta.setProducto(producto);

                detalleVenta.setPrecio_venta(rs.getDouble("precio_venta"));
                detalleVenta.setCantidad(rs.getInt("cantidad"));
                detalleVenta.setIva(rs.getBoolean("iva"));

                return detalleVenta; // Devolver el detalle de venta encontrado
            } else {
                return null; // No se encontró ningún detalle de venta con el ID especificado
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al buscar el detalle de venta: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            return null; // Hubo un error al ejecutar la consulta
        }
    }

    public List<DetalleVenta> buscar(String criterio) {
        List<DetalleVenta> detallesVenta = new ArrayList<>();

        try {
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM detalle_venta WHERE id_detalle_venta LIKE ? OR id_venta LIKE ? OR id_producto LIKE ?");
            ps.setString(1, "%" + criterio + "%");
            ps.setString(2, "%" + criterio + "%");
            ps.setString(3, "%" + criterio + "%");

            ResultSet rs = ps.executeQuery(); // Ejecutar la consulta

            while (rs.next()) {
                DetalleVenta detalleVenta = new DetalleVenta();
                detalleVenta.setId_detalle_venta(rs.getInt("id_detalle_venta"));

                // Obtener la venta asociada al detalle de venta
                int id_venta = rs.getInt("id_venta");
                Venta venta = obtenerVentaPorId(id_venta);
                detalleVenta.setVenta(venta);

                // Obtener el producto asociado al detalle de venta
                int id_producto = rs.getInt("id_producto");
                Producto producto = obtenerProductoPorId(id_producto);
                detalleVenta.setProducto(producto);

                detalleVenta.setPrecio_venta(rs.getDouble("precio_venta"));
                detalleVenta.setCantidad(rs.getInt("cantidad"));
                detalleVenta.setIva(rs.getBoolean("iva"));

                detallesVenta.add(detalleVenta); // Agregar el detalle de venta a la lista de detalles encontrados
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return detallesVenta; // Devolver la lista de detalles de venta encontrados
    }

    public List<DetalleVenta> obtenerTodos() {
        try {
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM detalle_venta");

            ResultSet rs = ps.executeQuery(); // Ejecutar la consulta de selección
            List<DetalleVenta> detalleVentas = new ArrayList<>();
            while (rs.next()) {
                DetalleVenta detalleVenta = new DetalleVenta();
                detalleVenta.setId_detalle_venta(rs.getInt("id_detalle_venta"));
                detalleVenta.setVenta(obtenerVentaPorId(rs.getInt("id_venta")));
                detalleVenta.setProducto(obtenerProductoPorId(rs.getInt("id_producto")));
                detalleVenta.setPrecio_venta(rs.getDouble("precio_venta"));
                detalleVenta.setCantidad(rs.getInt("cantidad"));
                detalleVenta.setIva(rs.getBoolean("iva"));
                detalleVentas.add(detalleVenta);
            }
            return detalleVentas;
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al obtener los detalles de venta: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            return null; // Hubo un error al ejecutar la consulta
        }
    }

    private Venta obtenerVentaPorId(int id_venta) throws SQLException{
        DaVenta daVenta = new DaVenta(); // Instanciar la clase DaVenta

        // Utilizar el método obtenerPorId de DaVenta para obtener la venta por ID
        return daVenta.obtenerPorId(id_venta);
    }

    private Producto obtenerProductoPorId(int id_producto) throws SQLException {
        DaProducto daProducto = new DaProducto(); // Instanciar la clase DaProducto

        // Utilizar el método obtenerPorId de DaProducto para obtener el producto por ID
        return daProducto.obtenerPorId(id_producto);
    }

}
