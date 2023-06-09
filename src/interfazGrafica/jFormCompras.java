/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package interfazGrafica;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import Clases.Compra;
import Clases.Proveedor;
import Clases.Usuario;
import DataAcces.DaCompra;
import DataAcces.DaProveedor;
import DataAcces.DaUsuario;
import Utilidades.ConexionBD;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class jFormCompras extends JFrame {

    private DaCompra daCompra;
    private DefaultTableModel tableModel;
    private JTable table;
    private JTextField idCompraField;
    private JTextField fechaCompraField;
    private JComboBox<Proveedor> proveedorComboBox;
    private JComboBox<Usuario> usuarioComboBox;

    public jFormCompras() throws SQLException {
        daCompra = new DaCompra();
setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        // Configurar el JFrame
        setTitle("Gestión de Compras");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);
        setLayout(null);

        // Configurar la tabla
        tableModel = new DefaultTableModel();
        tableModel.addColumn("ID Compra");
        tableModel.addColumn("Fecha Compra");
        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(20, 20, 600, 400);
        add(scrollPane);

        // Configurar los campos de texto
        idCompraField = new JTextField();
        idCompraField.setBounds(660, 30, 100, 25);
        add(idCompraField);

        fechaCompraField = new JTextField();
        fechaCompraField.setBounds(660, 70, 100, 25);
        add(fechaCompraField);

        // Configurar los combo boxes
        proveedorComboBox = new JComboBox<>();
        proveedorComboBox.setBounds(660, 110, 150, 25);
        add(proveedorComboBox);

        usuarioComboBox = new JComboBox<>();
        usuarioComboBox.setBounds(660, 150, 150, 25);
        add(usuarioComboBox);

        // Configurar los botones
        JButton agregarButton = new JButton("Agregar");
        agregarButton.setBounds(660, 190, 100, 25);
        agregarButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                agregarCompra();
            }
        });
        add(agregarButton);

        JButton actualizarButton = new JButton("Actualizar");
        actualizarButton.setBounds(660, 230, 100, 25);
        actualizarButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                actualizarCompra();
            }
        });
        add(actualizarButton);

        JButton eliminarButton = new JButton("Eliminar");
        eliminarButton.setBounds(660, 270, 100, 25);
        eliminarButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                eliminarCompra();
            }
        });
        add(eliminarButton);

        // Cargar los proveedores y usuarios en los combo boxes
        cargarProveedores();
        cargarUsuarios();

        // Actualizar la tabla con las compras existentes
        actualizarTabla();
    }

    public void agregarCompra() {
        int idCompra = Integer.parseInt(idCompraField.getText());
        String fechaCompraString = fechaCompraField.getText();
        Proveedor proveedor = (Proveedor) proveedorComboBox.getSelectedItem();
        Usuario usuario = (Usuario) usuarioComboBox.getSelectedItem();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate fechaCompra = LocalDate.parse(fechaCompraString, formatter);

        Compra compra = new Compra(idCompra, fechaCompra, proveedor, usuario);

        if (daCompra.agregar(compra)) {
            JOptionPane.showMessageDialog(null, "Compra agregada correctamente");
            limpiarCampos();
            actualizarTabla();
        }
    }

    public void actualizarCompra() {
        int idCompra = Integer.parseInt(idCompraField.getText());
        String fechaCompraString = fechaCompraField.getText();
        Proveedor proveedor = (Proveedor) proveedorComboBox.getSelectedItem();
        Usuario usuario = (Usuario) usuarioComboBox.getSelectedItem();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate fechaCompra = LocalDate.parse(fechaCompraString, formatter);

        Compra compra = new Compra(idCompra, fechaCompra, proveedor, usuario);

        if (daCompra.actualizar(compra)) {
            JOptionPane.showMessageDialog(null, "Compra actualizada correctamente");
            limpiarCampos();
            actualizarTabla();
        }
    }

    public void eliminarCompra() {
        int idCompra = Integer.parseInt(idCompraField.getText());

        int confirmacion = JOptionPane.showConfirmDialog(null, "¿Estás seguro de eliminar esta compra?", "Confirmar Eliminación", JOptionPane.YES_NO_OPTION);
        if (confirmacion == JOptionPane.YES_OPTION) {
            if (daCompra.eliminar(idCompra)) {
                JOptionPane.showMessageDialog(null, "Compra eliminada correctamente");
                limpiarCampos();
                actualizarTabla();
            }
        }
    }

    private void cargarProveedores() throws SQLException {
        DaProveedor daProveedor = new DaProveedor();
        List<Proveedor> proveedores = daProveedor.obtenerProveedores();

        for (Proveedor proveedor : proveedores) {
            // Hacer lo que necesites con cada proveedor
            System.out.println(proveedor);
        }
    }

    private void cargarUsuarios() throws SQLException {
        DaUsuario daUsuario = new DaUsuario();
        List<Usuario> usuarios = daUsuario.obtenerTodos();

        for (Usuario usuario : usuarios) {
            // Hacer lo que necesites con cada usuario
            System.out.println(usuario);
        }
    }

    public void mostrarCompras(List<Compra> compras) {
        tableModel.setRowCount(0); // Limpiar la tabla antes de agregar los datos

        for (Compra compra : compras) {
            Object[] rowData = {compra.getId_compra(), compra.getFecha_compra(), compra.getProveedor().getNombre_proveedor(), compra.getUsuario().getNombre()};
            tableModel.addRow(rowData);
        }
    }

    public void limpiarCampos() {
        idCompraField.setText("");
        fechaCompraField.setText("");
        proveedorComboBox.setSelectedIndex(0);
        usuarioComboBox.setSelectedIndex(0);
    }

    public void actualizarTabla() {
        List<Compra> compras = daCompra.obtenerTodas();
        if (compras != null) {
            mostrarCompras(compras);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                jFormCompras frame = new jFormCompras();
                frame.setVisible(true);
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        });
    }
}
