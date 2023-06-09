package interfazGrafica;

import Clases.Proveedor;
import DataAcces.DaProveedor;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.TableColumn;
import javax.swing.table.TableRowSorter;
import org.jdesktop.swingx.JXTable;
import org.jdesktop.swingx.search.TableSearchable;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class jFormProveedores extends JFrame {

    private JTextField idField;
    private JTextField nombreField;
    private JTextField direccionField;
    private JTextField correoField;
    private JTextField telefonoField;
    private JXTable proveedoresTable;
    private DefaultTableModel tableModel;
    private DaProveedor proveedorDAO;
    private JButton actualizarButton;
    private List<Proveedor> proveedores;

    public jFormProveedores() throws SQLException {
        setTitle("Proveedores");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(600, 400);
        setLocationRelativeTo(null);

        // Establecer el aspecto FlatLaf
        try {
            UIManager.setLookAndFeel(new com.formdev.flatlaf.FlatLightLaf());
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        // Crear los componentes
        JLabel idLabel = new JLabel("ID:");
        idField = new JTextField(10);
        idField.setEnabled(false);
        JLabel nombreLabel = new JLabel("Nombre:");
        nombreField = new JTextField(20);
        JLabel direccionLabel = new JLabel("Dirección:");
        direccionField = new JTextField(30);
        JLabel correoLabel = new JLabel("Correo electrónico:");
        correoField = new JTextField(30);
        JLabel telefonoLabel = new JLabel("Teléfono:");
        telefonoField = new JTextField(15);

        JButton agregarButton = new JButton("Agregar");
        JButton buscarButton = new JButton("Buscar");
        JButton eliminarButton = new JButton("Eliminar");
        JButton nuevoButton = new JButton("Nuevo");
        actualizarButton = new JButton("Actualizar");

        // Crear el panel de ingreso de datos del proveedor
        JPanel proveedorPanel = new JPanel(new GridLayout(5, 2));
        proveedorPanel.add(idLabel);
        proveedorPanel.add(idField);
        proveedorPanel.add(nombreLabel);
        proveedorPanel.add(nombreField);
        proveedorPanel.add(direccionLabel);
        proveedorPanel.add(direccionField);
        proveedorPanel.add(correoLabel);
        proveedorPanel.add(correoField);
        proveedorPanel.add(telefonoLabel);
        proveedorPanel.add(telefonoField);

        // Crear el panel de botones
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(agregarButton);
        buttonPanel.add(buscarButton);
        buttonPanel.add(eliminarButton);
        buttonPanel.add(nuevoButton);
        buttonPanel.add(actualizarButton);

        // Crear el panel principal
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(proveedorPanel, BorderLayout.NORTH);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        // Crear la tabla de proveedores
        tableModel = new DefaultTableModel(new Object[]{"ID", "Nombre", "Dirección", "Correo Electrónico", "Teléfono"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Hacer las celdas no editables
            }
        };
        proveedoresTable = new JXTable(tableModel);
        JScrollPane tableScrollPane = new JScrollPane(proveedoresTable);

        // Configurar el filtro y búsqueda en la tabla
        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(tableModel);
        proveedoresTable.setRowSorter(sorter);
        proveedoresTable.setSearchable(new TableSearchable(proveedoresTable));

        // Desactivar la edición de las celdas de la tabla
        for (int i = 0; i < tableModel.getColumnCount(); i++) {
            TableColumn column = proveedoresTable.getColumnModel().getColumn(i);
            column.setCellEditor(null);
        }

        // Agregar los componentes al JFrame
        add(mainPanel, BorderLayout.NORTH);
        add(tableScrollPane, BorderLayout.CENTER);

        // Conectar con la base de datos
        proveedorDAO = new DaProveedor();

        // Acción del botón "Agregar"
        agregarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int id = Integer.parseInt(idField.getText());
                    String nombre = nombreField.getText();
                    String direccion = direccionField.getText();
                    String correo = correoField.getText();
                    String telefono = telefonoField.getText();

                    Proveedor proveedor = new Proveedor(id, nombre, direccion, correo, telefono);
                    boolean agregado = proveedorDAO.agregar(proveedor);

                    if (agregado) {
                        JOptionPane.showMessageDialog(null, "Proveedor agregado exitosamente.");
                        limpiarCampos();
                        actualizarTablaProveedores();
                    } else {
                        JOptionPane.showMessageDialog(null, "Error al agregar el proveedor.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "ID inválido. Ingrese un número entero.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // Acción del botón "Buscar"
        buscarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String criterio = JOptionPane.showInputDialog(null, "Ingrese el criterio de búsqueda:", "Búsqueda de Proveedores", JOptionPane.PLAIN_MESSAGE);

                if (criterio != null) {
                    List<Proveedor> proveedores = proveedorDAO.buscar(criterio);
                    mostrarProveedores(proveedores);
                }
            }
        });

        // Acción del botón "Eliminar"
        eliminarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = proveedoresTable.getSelectedRow();
                if (selectedRow != -1) {
                    int id = (int) proveedoresTable.getValueAt(selectedRow, 0);
                    boolean eliminado = proveedorDAO.eliminar(id);

                    if (eliminado) {
                        JOptionPane.showMessageDialog(null, "Proveedor eliminado exitosamente.");
                        limpiarCampos();
                        actualizarTablaProveedores();
                    } else {
                        JOptionPane.showMessageDialog(null, "Error al eliminar el proveedor.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Seleccione un proveedor de la tabla.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // Acción del botón "Nuevo"
        nuevoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                limpiarCampos();
                generarNuevoID();
                actualizarTablaProveedores();
            }
        });

        // Acción del botón "Actualizar"
        actualizarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                actualizarProveedor();

            }
        });

        // Listener de selección de la tabla
        proveedoresTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent e) {
                int selectedRow = proveedoresTable.getSelectedRow();
                if (selectedRow != -1) {
                    Proveedor proveedor = proveedores.get(selectedRow);
                    mostrarProveedorEnCampos(proveedor);
                }
            }
        });

        // Mostrar todos los proveedores en la tabla al iniciar
        actualizarTablaProveedores();
    }

    private void actualizarProveedor() {
        try {
            int id = Integer.parseInt(idField.getText());
            String nombre = nombreField.getText();
            String direccion = direccionField.getText();
            String correo = correoField.getText();
            String telefono = telefonoField.getText();

            Proveedor proveedor = new Proveedor(id, nombre, direccion, correo, telefono);

            if (proveedorDAO.actualizar(proveedor)) {
                JOptionPane.showMessageDialog(this, "Proveedor actualizado correctamente", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                limpiarCampos();
                actualizarTablaProveedores();
            } else {
                JOptionPane.showMessageDialog(this, "Error al actualizar el proveedor", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "ID inválido. Ingrese un número entero.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void limpiarCampos() {
        idField.setText("");
        nombreField.setText("");
        direccionField.setText("");
        correoField.setText("");
        telefonoField.setText("");
    }

    private void generarNuevoID() {
        int nuevoID = proveedorDAO.obtenerNuevoID();
        idField.setText(String.valueOf(nuevoID));
    }

    private void actualizarTablaProveedores() {
        tableModel.setRowCount(0); // Limpiar la tabla antes de actualizarla
        proveedores = proveedorDAO.obtenerProveedores();
        mostrarProveedores(proveedores);
    }

    private void mostrarProveedores(List<Proveedor> proveedores) {
        tableModel.setRowCount(0); // Limpiar la tabla antes de agregar los nuevos datos

        for (Proveedor proveedor : proveedores) {
            Object[] rowData = new Object[]{proveedor.getId_proveedor(), proveedor.getNombre_proveedor(), proveedor.getDireccion(), proveedor.getCorreo_electronico(), proveedor.getTelefono()};
            tableModel.addRow(rowData);
        }
    }

    private void mostrarProveedorEnCampos(Proveedor proveedor) {
        idField.setText(String.valueOf(proveedor.getId_proveedor()));
        nombreField.setText(proveedor.getNombre_proveedor());
        direccionField.setText(proveedor.getDireccion());
        correoField.setText(proveedor.getCorreo_electronico());
        telefonoField.setText(proveedor.getTelefono());
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                try {
                    new jFormProveedores().setVisible(true);
                } catch (SQLException ex) {
                    Logger.getLogger(jFormProveedores.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }
}
