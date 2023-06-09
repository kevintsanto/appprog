/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DataAcces;

import Clases.Usuario;
import Clases.Cliente;
import Utilidades.ConexionBD;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

/**
 * Esta clase proporciona métodos para acceder y manipular los datos de la tabla
 * cliente en la base de datos.
 *
 * @author gg
 */
public class DaCliente {

    private Connection connection;

    public DaCliente() throws SQLException {
        connection = ConexionBD.getConnection();
    }
    
    public  int obtenerNuevoIdCliente() {
        int nuevoID = 0;
        try {
            PreparedStatement ps = connection.prepareStatement("SELECT MAX(id_cliente) FROM cliente");
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                nuevoID = rs.getInt(1) + 1;
            } else {
                nuevoID = 1;
            }
            rs.close();
            ps.close();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al obtener el nuevo ID del cliente: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
        return nuevoID;
    }

    /**
     * Inserta un nuevo cliente en la base de datos.
     *
     * @param cliente El cliente a insertar.
     * @return true si la inserción fue exitosa, false en caso contrario.
     */
    public boolean agregar(Cliente cliente) {
        try {
            // Preparar la consulta SQL para insertar un nuevo cliente
            String query = "INSERT INTO cliente (id_cliente, id_usuario, telefono, nombre, apellido, direccion, ciudad) VALUES (?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setInt(1, cliente.getId_cliente());
            ps.setInt(2, cliente.getUsuario().getId_usuario());
            ps.setString(3, cliente.getTelefono());
            ps.setString(4, cliente.getNombre());
            ps.setString(5, cliente.getApellido());
            ps.setString(6, cliente.getDireccion());
            ps.setString(7, cliente.getCiudad());

            int filasInsertadas = ps.executeUpdate(); // Ejecutar la consulta de inserción

            return filasInsertadas > 0;
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al insertar el cliente: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    /**
     * Actualiza un cliente existente en la base de datos.
     *
     * @param cliente El cliente a actualizar.
     * @return true si la actualización fue exitosa, false en caso contrario.
     */
    public boolean actualizar(Cliente cliente) {
        try {
            // Preparar la consulta SQL para actualizar un cliente
            String query = "UPDATE cliente SET id_usuario = ?, telefono = ?, nombre = ?, apellido = ?, direccion = ?, ciudad = ? WHERE id_cliente = ?";
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setInt(1, cliente.getUsuario().getId_usuario());
            ps.setString(2, cliente.getTelefono());
            ps.setString(3, cliente.getNombre());
            ps.setString(4, cliente.getApellido());
            ps.setString(5, cliente.getDireccion());
            ps.setString(6, cliente.getCiudad());
            ps.setInt(7, cliente.getId_cliente());

            int filasActualizadas = ps.executeUpdate(); // Ejecutar la consulta de actualización

            return filasActualizadas > 0;
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al actualizar el cliente: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    /**
     * Elimina un cliente existente de la base de datos.
     *
     * @param idCliente El ID del cliente a eliminar.
     * @return true si la eliminación fue exitosa, false en caso contrario.
     */
    public boolean eliminar(int idCliente) {
        try {
            // Preparar la consulta SQL para eliminar un cliente
            String query = "DELETE FROM cliente WHERE id_cliente = ?";
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setInt(1, idCliente);

            int filasEliminadas = ps.executeUpdate(); // Ejecutar la consulta de eliminación

            return filasEliminadas > 0;
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al eliminar el cliente: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    /**
     * Obtiene todos los clientes de la base de datos.
     *
     * @return Una lista de todos los clientes en la base de datos.
     */
    public List<Cliente> obtenerTodos() {
        List<Cliente> clientes = new ArrayList<>();
        try {
            // Preparar la consulta SQL para obtener todos los clientes
            String query = "SELECT * FROM cliente";
            PreparedStatement ps = connection.prepareStatement(query);

            ResultSet rs = ps.executeQuery(); // Ejecutar la consulta de selección

            while (rs.next()) {
                Cliente cliente = new Cliente();
                cliente.setId_cliente(rs.getInt("id_cliente"));
                // Recuperar el usuario asociado al cliente (requiere una consulta adicional a la tabla usuario)
                int idUsuario = rs.getInt("id_usuario");
                Usuario usuario = buscarUsuarioPorId(idUsuario); // Método para buscar un usuario por su ID
                cliente.setUsuario(usuario);
                cliente.setTelefono(rs.getString("telefono"));
                cliente.setNombre(rs.getString("nombre"));
                cliente.setApellido(rs.getString("apellido"));
                cliente.setDireccion(rs.getString("direccion"));
                cliente.setCiudad(rs.getString("ciudad"));

                clientes.add(cliente); // Agregar el cliente a la lista
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al obtener los clientes: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
        return clientes;
    }

    /**
     * Busca un usuario por su ID en la base de datos.
     *
     * @param idUsuario El ID del usuario a buscar.
     * @return El usuario encontrado o null si no se encontró.
     */
    /**
 * Busca un cliente por su ID en la base de datos.
 *
 * @param idCliente El ID del cliente a buscar.
 * @return El cliente encontrado o null si no se encontró.
 */
public Cliente obtenerClientePorId(int idCliente) {
    try {
        // Preparar la consulta SQL para buscar un cliente por su ID
        String query = "SELECT * FROM cliente WHERE id_cliente = ?";
        PreparedStatement ps = connection.prepareStatement(query);
        ps.setInt(1, idCliente);

        ResultSet rs = ps.executeQuery(); // Ejecutar la consulta de selección

        if (rs.next()) {
            Cliente cliente = new Cliente();
            cliente.setId_cliente(rs.getInt("id_cliente"));
            // Recuperar el usuario asociado al cliente (requiere una consulta adicional a la tabla usuario)
            int idUsuario = rs.getInt("id_usuario");
            Usuario usuario = buscarUsuarioPorId(idUsuario); // Método para buscar un usuario por su ID
            cliente.setUsuario(usuario);
            cliente.setTelefono(rs.getString("telefono"));
            cliente.setNombre(rs.getString("nombre"));
            cliente.setApellido(rs.getString("apellido"));
            cliente.setDireccion(rs.getString("direccion"));
            cliente.setCiudad(rs.getString("ciudad"));

            return cliente;
        }
    } catch (SQLException ex) {
        JOptionPane.showMessageDialog(null, "Error al buscar el cliente: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
    }
    return null;
}

    private Usuario buscarUsuarioPorId(int idUsuario) {
        Usuario usuario = null;
        try {
            // Preparar la consulta SQL para buscar un usuario por su ID
            String query = "SELECT * FROM usuario WHERE id_usuario = ?";
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setInt(1, idUsuario);

            ResultSet rs = ps.executeQuery(); // Ejecutar la consulta de selección

            if (rs.next()) {
                usuario = new Usuario();
                usuario.setId_usuario(rs.getInt("id_usuario"));
                usuario.setNombre(rs.getString("nombre"));
                // Recuperar más atributos del usuario si es necesario
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al buscar el usuario: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
        return usuario;
    }

    /**
     * Busca clientes que coincidan con un criterio de búsqueda en la base de
     * datos.
     *
     * @param criterio El criterio de búsqueda.
     * @return Una lista de clientes que coinciden con el criterio de búsqueda.
     */
    public List<Cliente> buscar(String criterio) {
        List<Cliente> clientes = new ArrayList<>();
        try {
            // Preparar la consulta SQL con un filtro LIKE para buscar en los campos relevantes
            String query = "SELECT * FROM cliente WHERE nombre LIKE ? OR apellido LIKE ? OR direccion LIKE ? OR ciudad LIKE ?";
            PreparedStatement ps = connection.prepareStatement(query);
            String likePattern = "%" + criterio + "%"; // Agregar los caracteres comodín al criterio
            ps.setString(1, likePattern);
            ps.setString(2, likePattern);
            ps.setString(3, likePattern);
            ps.setString(4, likePattern);

            ResultSet rs = ps.executeQuery(); // Ejecutar la consulta de selección

            while (rs.next()) {
                Cliente cliente = new Cliente();
                cliente.setId_cliente(rs.getInt("id_cliente"));
                // Recuperar el usuario asociado al cliente (requiere una consulta adicional a la tabla usuario)
                int idUsuario = rs.getInt("id_usuario");
                Usuario usuario = buscarUsuarioPorId(idUsuario); // Método para buscar un usuario por su ID
                cliente.setUsuario(usuario);
                cliente.setTelefono(rs.getString("telefono"));
                cliente.setNombre(rs.getString("nombre"));
                cliente.setApellido(rs.getString("apellido"));
                cliente.setDireccion(rs.getString("direccion"));
                cliente.setCiudad(rs.getString("ciudad"));

                clientes.add(cliente); // Agregar el cliente a la lista
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al buscar clientes: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
        return clientes;
    }

}
