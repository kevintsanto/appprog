package interfazGrafica;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.time.LocalDate;
import java.util.List;
import Clases.Cliente;
import Clases.Usuario;
import Clases.Venta;
import DataAcces.DaCliente;
import DataAcces.DaUsuario;
import DataAcces.DaVenta;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import javax.swing.JOptionPane;

public class jFormVentas extends JFrame {

    private JPanel contentPane;
    private JTable table;
    private DefaultTableModel tableModel;
    private JTextField textFieldIdVenta;
    private JTextField textFieldFechaVenta;
    private JComboBox<Cliente> comboBoxCliente;
    private JComboBox<Usuario> comboBoxUsuario;
    private DaVenta daVenta;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    jFormVentas frame = new jFormVentas();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Create the frame.
     */
    public jFormVentas() {
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 600, 400);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(10, 11, 564, 143);
        contentPane.add(scrollPane);

        table = new JTable();
        scrollPane.setViewportView(table);

        JButton btnAgregar = new JButton("Agregar");
        btnAgregar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                agregarVenta();
            }
        });
        btnAgregar.setBounds(10, 165, 89, 23);
        contentPane.add(btnAgregar);

        JButton btnEditar = new JButton("Editar");
        btnEditar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                actualizarVenta();
            }
        });
        btnEditar.setBounds(109, 165, 89, 23);
        contentPane.add(btnEditar);

        JButton btnEliminar = new JButton("Eliminar");
        btnEliminar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                eliminarVenta();
            }
        });
        btnEliminar.setBounds(208, 165, 89, 23);
        contentPane.add(btnEliminar);

        JLabel lblIdVenta = new JLabel("ID Venta:");
        lblIdVenta.setBounds(10, 199, 70, 14);
        contentPane.add(lblIdVenta);

        textFieldIdVenta = new JTextField();
        textFieldIdVenta.setBounds(90, 196, 86, 20);
        contentPane.add(textFieldIdVenta);
        textFieldIdVenta.setColumns(10);

        JLabel lblFechaVenta = new JLabel("Fecha Venta:");
        lblFechaVenta.setBounds(10, 224, 70, 14);
        contentPane.add(lblFechaVenta);

        textFieldFechaVenta = new JTextField();
        textFieldFechaVenta.setBounds(90, 221, 86, 20);
        contentPane.add(textFieldFechaVenta);
        textFieldFechaVenta.setColumns(10);

        JLabel lblCliente = new JLabel("Cliente:");
        lblCliente.setBounds(10, 249, 70, 14);
        contentPane.add(lblCliente);

        comboBoxCliente = new JComboBox<Cliente>();
        comboBoxCliente.setBounds(90, 246, 150, 20);
        contentPane.add(comboBoxCliente);

        JLabel lblUsuario = new JLabel("Usuario:");
        lblUsuario.setBounds(10, 274, 70, 14);
        contentPane.add(lblUsuario);

        comboBoxUsuario = new JComboBox<Usuario>();
        comboBoxUsuario.setBounds(90, 271, 150, 20);
        contentPane.add(comboBoxUsuario);

        // Crear el modelo de la tabla
        tableModel = new DefaultTableModel();
        tableModel.addColumn("ID Venta");
        tableModel.addColumn("Fecha Venta");
        tableModel.addColumn("Cliente");
        tableModel.addColumn("Usuario");
        table.setModel(tableModel);

        // Obtener la instancia de DaVenta
        try {
            daVenta = new DaVenta();
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al conectar a la base de datos", "Error", JOptionPane.ERROR_MESSAGE);
            System.exit(1);
        }

        // Cargar los clientes y usuarios en los combo boxes
        try {
            cargarClientes();
            cargarUsuarios();
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al cargar los datos", "Error", JOptionPane.ERROR_MESSAGE);
        }

        // Cargar las ventas en la tabla
        cargarVentas();

        // Agregar el listener de clic en la tabla
        table.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tableMouseClicked(evt);
            }
        });
    }

    private void cargarVentas() {
        // Limpiar la tabla
        tableModel.setRowCount(0);

        // Obtener todas las ventas
        List<Venta> ventas = daVenta.obtenerTodos();
        // Agregar cada venta a la tabla
        for (Venta venta : ventas) {
            Object[] row = new Object[4];
            row[0] = venta.getId_venta();
            row[1] = venta.getFecha_venta();
            row[2] = venta.getCliente().getNombre() + " " + venta.getCliente().getApellido();
            row[3] = venta.getUsuario().getNombre();
            tableModel.addRow(row);
        }
    }

    private void cargarClientes() throws SQLException {
        DaCliente daClientes = new DaCliente();
        List<Cliente> clientes = daClientes.obtenerTodos();

        comboBoxCliente.removeAllItems();

        for (Cliente cliente : clientes) {
            comboBoxCliente.addItem(cliente);
        }
    }

    private void cargarUsuarios() throws SQLException {
        DaUsuario daUsuario = new DaUsuario();
        List<Usuario> usuarios = daUsuario.obtenerTodos();

        comboBoxUsuario.removeAllItems();

        for (Usuario usuario : usuarios) {
            comboBoxUsuario.addItem(usuario);
        }
    }

    private void agregarVenta() {
        try {
            int idVenta = Integer.parseInt(textFieldIdVenta.getText());
            LocalDate fechaVenta = LocalDate.parse(textFieldFechaVenta.getText());
            Cliente cliente = (Cliente) comboBoxCliente.getSelectedItem();
            Usuario usuario = (Usuario) comboBoxUsuario.getSelectedItem();

            // Crear una nueva venta
            Venta venta = new Venta();
            venta.setId_venta(idVenta);
            venta.setFecha_venta(fechaVenta);
            venta.setCliente(cliente);
            venta.setUsuario(usuario);

            // Agregar la venta a la base de datos
            if (daVenta.agregar(venta)) {
                JOptionPane.showMessageDialog(null, "Venta agregada correctamente", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                limpiarCampos();
                cargarVentas();
            } else {
                JOptionPane.showMessageDialog(null, "Error al agregar la venta", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "ID de Venta inválido", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al agregar la venta", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void actualizarVenta() {
        try {
            int idVenta = Integer.parseInt(textFieldIdVenta.getText());
            LocalDate fechaVenta = LocalDate.parse(textFieldFechaVenta.getText());
            Cliente cliente = (Cliente) comboBoxCliente.getSelectedItem();
            Usuario usuario = (Usuario) comboBoxUsuario.getSelectedItem();

            // Crear una venta con los nuevos datos
            Venta venta = new Venta();
            venta.setId_venta(idVenta);
            venta.setFecha_venta(fechaVenta);
            venta.setCliente(cliente);
            venta.setUsuario(usuario);

            // Actualizar la venta en la base de datos
            if (daVenta.actualizar(venta)) {
                JOptionPane.showMessageDialog(null, "Venta actualizada correctamente", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                limpiarCampos();
                cargarVentas();
            } else {
                JOptionPane.showMessageDialog(null, "Error al actualizar la venta", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "ID de Venta inválido", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al actualizar la venta", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void eliminarVenta() {
        try {
            int idVenta = Integer.parseInt(textFieldIdVenta.getText());

            // Eliminar la venta de la base de datos
            if (daVenta.eliminar(idVenta)) {
                JOptionPane.showMessageDialog(null, "Venta eliminada correctamente", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                limpiarCampos();
                cargarVentas();
            } else {
                JOptionPane.showMessageDialog(null, "Error al eliminar la venta", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "ID de Venta inválido", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al eliminar la venta", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void limpiarCampos() {
        textFieldIdVenta.setText("");
        textFieldFechaVenta.setText("");
        comboBoxCliente.setSelectedIndex(0);
        comboBoxUsuario.setSelectedIndex(0);
    }

    private void tableMouseClicked(java.awt.event.MouseEvent evt) {
        // Obtener el índice de la fila seleccionada
        int row = table.getSelectedRow();

        // Obtener los valores de la fila seleccionada
        String idVenta = table.getValueAt(row, 0).toString();
        String fechaVenta = table.getValueAt(row, 1).toString();
        String cliente = table.getValueAt(row, 2).toString();
        String usuario = table.getValueAt(row, 3).toString();

        // Establecer los valores en los campos correspondientes
        textFieldIdVenta.setText(idVenta);
        textFieldFechaVenta.setText(fechaVenta);

        // Seleccionar el cliente y usuario en los combo boxes
        for (int i = 0; i < comboBoxCliente.getItemCount(); i++) {
            Cliente c = comboBoxCliente.getItemAt(i);
            if ((c.getNombre() + " " + c.getApellido()).equals(cliente)) {
                comboBoxCliente.setSelectedIndex(i);
                break;
            }
        }

        for (int i = 0; i < comboBoxUsuario.getItemCount(); i++) {
            Usuario u = comboBoxUsuario.getItemAt(i);
            if (u.getNombre().equals(usuario)) {
                comboBoxUsuario.setSelectedIndex(i);
                break;
            }
        }
    }
}
