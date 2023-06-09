/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DataAcces;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import Clases.Producto;
import Clases.Marca;
import Clases.TipoRopa;
import Utilidades.ConexionBD;
import java.util.Random;

/**
 * Esta clase representa un producto con sus atributos correspondientes
 *
 * @author gg
 */
public class DaProducto {

    private Connection connection;
    private final Random random;

    public DaProducto() throws SQLException {
        connection = ConexionBD.getConnection();
        this.random = new Random();
    }

    public boolean agregar(Producto producto) {
        try {
            int idProducto = generarIdAleatorio(); // Generar un ID único para el producto
            producto.setId_producto(idProducto);

            PreparedStatement ps = connection.prepareStatement("INSERT INTO producto (id_producto, nombre, descripcion, precio_unitario, stock, fecha_creacion, fecha_actualizacion, id_marca, id_tipo, color) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
            ps.setInt(1, producto.getId_producto());
            ps.setString(2, producto.getNombre());
            ps.setString(3, producto.getDescripcion());
            ps.setDouble(4, producto.getPrecio_unitario());
            ps.setInt(5, producto.getStock());
            ps.setDate(6, Date.valueOf(producto.getFecha_creacion()));
            ps.setDate(7, Date.valueOf(producto.getFecha_actualizacion()));
            ps.setInt(8, producto.getMarca().getId_marca());
            ps.setInt(9, producto.getTipoRopa().getId_tipo());
            ps.setString(10, producto.getColor());

            int filasInsertadas = ps.executeUpdate(); // Ejecutar la consulta de inserción
            if (filasInsertadas > 0) {
                return true; // El producto se agregó correctamente
            } else {
                JOptionPane.showMessageDialog(null, "Error al agregar el producto", "Error", JOptionPane.ERROR_MESSAGE);
                return false; // Hubo un error al agregar el producto
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al agregar el producto: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            return false; // Hubo un error al ejecutar la consulta
        }
    }

    private int generarIdAleatorio() {
        int idProducto;
        boolean idUnico = false;

        do {
            idProducto = random.nextInt(1000); // Generar un ID aleatorio entre 0 y 999

            // Verificar si el ID ya está en uso
            if (!existeProductoConId(idProducto)) {
                idUnico = true;
            }
        } while (!idUnico);

        return idProducto;
    }

    private boolean existeProductoConId(int id_producto) {
        try {
            String query = "SELECT COUNT(*) FROM producto WHERE id_producto = ?";
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setInt(1, id_producto);
            ResultSet rs = ps.executeQuery();
            rs.next();
            int count = rs.getInt(1);
            return count > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean actualizar(Producto producto) {
        try {
            PreparedStatement ps = connection.prepareStatement("UPDATE producto SET nombre = ?, descripcion = ?, precio_unitario = ?, stock = ?, fecha_creacion = ?, fecha_actualizacion = ?, id_marca = ?, id_tipo = ?, color = ? WHERE id_producto = ?");
            ps.setString(1, producto.getNombre());
            ps.setString(2, producto.getDescripcion());
            ps.setDouble(3, producto.getPrecio_unitario());
            ps.setInt(4, producto.getStock());
            ps.setDate(5, Date.valueOf(producto.getFecha_creacion()));
            ps.setDate(6, Date.valueOf(producto.getFecha_actualizacion()));
            ps.setInt(7, producto.getMarca().getId_marca());
            ps.setInt(8, producto.getTipoRopa().getId_tipo());
            ps.setString(9, producto.getColor());
            ps.setInt(10, producto.getId_producto());

            int filasActualizadas = ps.executeUpdate(); // Ejecutar la consulta de actualización
            if (filasActualizadas > 0) {
                return true; // El producto se actualizó correctamente
            } else {
                JOptionPane.showMessageDialog(null, "Error al actualizar el producto", "Error", JOptionPane.ERROR_MESSAGE);
                return false; // Hubo un error al actualizar el producto
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al actualizar el producto: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            return false; // Hubo un error al ejecutar la consulta
        }
    }

    public boolean eliminar(int idProducto) {
        try {
            PreparedStatement ps = connection.prepareStatement("DELETE FROM producto WHERE id_producto = ?");
            ps.setInt(1, idProducto);

            int filasEliminadas = ps.executeUpdate(); // Ejecutar la consulta de eliminación
            if (filasEliminadas > 0) {
                return true; // El producto se eliminó correctamente
            } else {
                JOptionPane.showMessageDialog(null, "Error al eliminar el producto", "Error", JOptionPane.ERROR_MESSAGE);
                return false; // Hubo un error al eliminar el producto
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al eliminar el producto: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            return false; // Hubo un error al ejecutar la consulta
        }
    }

    public Producto obtenerPorId(int idProducto) {
        try {
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM producto WHERE id_producto = ?");
            ps.setInt(1, idProducto);

            ResultSet rs = ps.executeQuery(); // Ejecutar la consulta de búsqueda
            if (rs.next()) {
                Producto producto = new Producto();
                producto.setId_producto(rs.getInt("id_producto"));
                producto.setNombre(rs.getString("nombre"));
                producto.setDescripcion(rs.getString("descripcion"));
                producto.setPrecio_unitario(rs.getDouble("precio_unitario"));
                producto.setStock(rs.getInt("stock"));
                producto.setFecha_creacion(rs.getDate("fecha_creacion").toLocalDate());
                producto.setFecha_actualizacion(rs.getDate("fecha_actualizacion").toLocalDate());

                // Obtener la marca del producto
                int idMarca = rs.getInt("id_marca");
                DaMarca daMarca = new DaMarca();
                Marca marca = daMarca.obtenerPorId(idMarca);
                producto.setMarca(marca);

                // Obtener el tipo de ropa del producto
                int idTipoRopa = rs.getInt("id_tipo");
                DaTipoRopa daTipoRopa = new DaTipoRopa();
                TipoRopa tipoRopa = daTipoRopa.obtenerPorId(idTipoRopa);
                producto.setTipoRopa(tipoRopa);

                producto.setColor(rs.getString("color"));

                return producto; // Devolver el producto encontrado
            } else {
                return null; // No se encontró ningún producto con el ID especificado
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al obtener el producto: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            return null;
        }
    }

    public List<Producto> obtenerTodos() {
        try {
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM producto");

            ResultSet rs = ps.executeQuery(); // Ejecutar la consulta de búsqueda
            List<Producto> productos = new ArrayList<>();
            while (rs.next()) {
                Producto producto = new Producto();
                producto.setId_producto(rs.getInt("id_producto"));
                producto.setNombre(rs.getString("nombre"));
                producto.setDescripcion(rs.getString("descripcion"));
                producto.setPrecio_unitario(rs.getDouble("precio_unitario"));
                producto.setStock(rs.getInt("stock"));
                producto.setFecha_creacion(rs.getDate("fecha_creacion").toLocalDate());
                producto.setFecha_actualizacion(rs.getDate("fecha_actualizacion").toLocalDate());

                // Obtener la marca del producto
                int idMarca = rs.getInt("id_marca");
                DaMarca daMarca = new DaMarca();
                Marca marca = daMarca.obtenerPorId(idMarca);
                producto.setMarca(marca);

                // Obtener el tipo de ropa del producto
                int idTipoRopa = rs.getInt("id_tipo");
                DaTipoRopa daTipoRopa = new DaTipoRopa();
                TipoRopa tipoRopa = daTipoRopa.obtenerPorId(idTipoRopa);
                producto.setTipoRopa(tipoRopa);

                producto.setColor(rs.getString("color"));

                productos.add(producto);
            }

            return productos; // Devolver la lista de productos encontrados
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al obtener los productos: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            return null;
        }
    }

    public List<Producto> buscar(String criterio) {
        List<Producto> productos = new ArrayList<>();

        try {
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM producto WHERE nombre LIKE ? OR descripcion LIKE ?");
            ps.setString(1, "%" + criterio + "%");
            ps.setString(2, "%" + criterio + "%");

            ResultSet rs = ps.executeQuery(); // Ejecutar la consulta

            while (rs.next()) {
                Producto producto = new Producto();
                producto.setId_producto(rs.getInt("id_producto"));
                producto.setNombre(rs.getString("nombre"));
                producto.setDescripcion(rs.getString("descripcion"));
                producto.setPrecio_unitario(rs.getDouble("precio_unitario"));
                producto.setStock(rs.getInt("stock"));
                producto.setFecha_creacion(rs.getDate("fecha_creacion").toLocalDate());
                producto.setFecha_actualizacion(rs.getDate("fecha_actualizacion").toLocalDate());
                producto.setMarca(buscarMarca(rs.getInt("id_marca")));
                producto.setTipoRopa(buscarTipoRopa(rs.getInt("id_tipo")));
                producto.setColor(rs.getString("color"));

                productos.add(producto); // Agregar el producto a la lista de productos encontrados
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return productos; // Devolver la lista de productos encontrados
    }

    /**
     * Busca un tipo de ropa por su ID en la base de datos.
     *
     * @param idTipoRopa El ID del tipo de ropa a buscar.
     * @return El tipo de ropa encontrado o null si no se encontró.
     */
    private TipoRopa buscarTipoRopa(int id_tipo) {
        TipoRopa tipoRopa = null;
        try {
            String query = "SELECT * FROM tipo_ropa WHERE id_tipo = ?";
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setInt(1, id_tipo);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                tipoRopa = new TipoRopa();
                tipoRopa.setId_tipo(rs.getInt("id_tipo"));
                tipoRopa.setNombre_tipo(rs.getString("nombre"));

            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return tipoRopa;
    }

    /**
     * Busca una marca por su ID en la base de datos.
     *
     * @param idMarca El ID de la marca a buscar.
     * @return La marca encontrada o null si no se encontró.
     */
    private Marca buscarMarca(int id_marca) {
        Marca marca = null;
        try {
            String query = "SELECT * FROM marca WHERE id_marca = ?";
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setInt(1, id_marca);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                marca = new Marca();
                marca.setId_marca(rs.getInt("id_marca"));
                marca.setNombre_marca(rs.getString("nombre"));

            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return marca;
    }

}
