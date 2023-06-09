package asistemaventas;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import DataAcces.*;
import Utilidades.ConexionBD;
import Clases.*;

public class main {

    private DaUsuario daUsuario;
    private DaMarca daMarca;
    private DaTipoRopa daTipoRopa;
    private DaProducto daProducto;
    private DaProveedor daProveedor;
    private DaCompra daCompra;
    private DaDetalleCompra daDetalleCompra;
    private DaCliente daCliente;
    private DaVenta daVenta;
    private DaDetalleVenta daDetalleVenta;

    public main(Connection conn) {
        try {
            this.daUsuario = new DaUsuario();
            this.daMarca = new DaMarca();
            this.daTipoRopa = new DaTipoRopa(); 
            this.daProducto = new DaProducto();
            this.daProveedor = new DaProveedor();
            this.daCompra = new DaCompra();
            this.daDetalleCompra = new DaDetalleCompra();
            this.daCliente = new DaCliente();
            this.daVenta = new DaVenta();
            this.daDetalleVenta = new DaDetalleVenta();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        try {
            // Establecer conexión a la base de datos
            Connection connection = ConexionBD.getConnection();

            if (connection == null) {
                System.out.println("La conexión es nula. Verifica la configuración de la base de datos.");
                return;
            }

            main sistemaVentas = new main(connection);

            // Obtener todos los usuarios
            List<Usuario> usuarios = sistemaVentas.daUsuario.obtenerTodos();
            System.out.println("Usuarios: " + usuarios);

            // Obtener todas las marcas
            List<Marca> marcas = sistemaVentas.daMarca.obtenerTodas();
            System.out.println("Marcas: " + marcas);

            // Obtener todos los tipos de ropa
            List<TipoRopa> tiposRopa = sistemaVentas.daTipoRopa.obtenerTodos();
            System.out.println("Tipos de Ropa: " + tiposRopa);

            // Obtener todos los productos
            List<Producto> productos = sistemaVentas.daProducto.obtenerTodos();
            System.out.println("Productos: " + productos);

            // Obtener todos los proveedores
            List<Proveedor> proveedores = sistemaVentas.daProveedor.obtenerProveedores();
            System.out.println("Proveedores: " + proveedores);

            // Obtener todas las compras
            List<Compra> compras = sistemaVentas.daCompra.obtenerTodas();
            System.out.println("Compras: " + compras);

            // Obtener todos los detalles de compra
            List<DetalleCompra> detallesCompra = sistemaVentas.daDetalleCompra.obtenerTodos();
            System.out.println("Detalles de Compra: " + detallesCompra);

            // Obtener todos los clientes
            List<Cliente> clientes = sistemaVentas.daCliente.obtenerTodos();
            System.out.println("Clientes: " + clientes);

            // Obtener todas las ventas
            List<Venta> ventas = sistemaVentas.daVenta.obtenerTodos();
            System.out.println("Ventas: " + ventas);

            // Obtener todos los detalles de venta
            List<DetalleVenta> detallesVenta = sistemaVentas.daDetalleVenta.obtenerTodos();
            System.out.println("Detalles de Venta: " + detallesVenta);

            // Cerrar la conexión a la base de datos
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
