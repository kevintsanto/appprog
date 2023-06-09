package interfazGrafica;

import Clases.Cliente;
import Clases.Usuario;
import DataAcces.DaCliente;
import DataAcces.DaUsuario;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class jFormClientes extends JFrame {

    private DaCliente daCliente;
    private JTextField txtIdCliente;
    private JComboBox<Usuario> usuarioComboBox;
    private JTextField txtTelefono;
    private JTextField txtNombre;
    private JTextField txtApellido;
    private JTextField txtDireccion;
    private JTextField txtCiudad;
    private JTable tableClientes;
    private DefaultTableModel tableModel;

    public jFormClientes() throws SQLException {

        daCliente = new DaCliente();

        setTitle("Interfaz de Cliente");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(800, 400);
        setLayout(new BorderLayout());
        setLocationRelativeTo(null);
        JPanel panelSuperior = new JPanel(new GridLayout(6, 2));

        JLabel lblIdCliente = new JLabel("ID Cliente:");
        txtIdCliente = new JTextField();
        txtIdCliente.setEditable(false);
        JLabel lblIdUsuario = new JLabel("ID Usuario:");
        usuarioComboBox = new JComboBox<>();
        JLabel lblTelefono = new JLabel("Teléfono:");
        txtTelefono = new JTextField();
        JLabel lblNombre = new JLabel("Nombre:");
        txtNombre = new JTextField();
        JLabel lblApellido = new JLabel("Apellido:");
        txtApellido = new JTextField();
        JLabel lblDireccion = new JLabel("Dirección:");
        txtDireccion = new JTextField();
        JLabel lblCiudad = new JLabel("Ciudad:");
        txtCiudad = new JTextField();

        panelSuperior.add(lblIdCliente);
        panelSuperior.add(txtIdCliente);
        panelSuperior.add(lblIdUsuario);
        panelSuperior.add(usuarioComboBox);
        panelSuperior.add(lblTelefono);
        panelSuperior.add(txtTelefono);
        panelSuperior.add(lblNombre);
        panelSuperior.add(txtNombre);
        panelSuperior.add(lblApellido);
        panelSuperior.add(txtApellido);
        panelSuperior.add(lblDireccion);
        panelSuperior.add(txtDireccion);
        panelSuperior.add(lblCiudad);
        panelSuperior.add(txtCiudad);

        JButton btnNuevo = new JButton("Nuevo");
        btnNuevo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                limpiarCampos();
                generarNuevoIdCliente();
            }
        });

        JButton btnAgregar = new JButton("Agregar");
        btnAgregar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (validarCamposVacios()) {
                    int idCliente = Integer.parseInt(txtIdCliente.getText());
                    Usuario usuario = (Usuario) usuarioComboBox.getSelectedItem();
                    String telefono = txtTelefono.getText();
                    String nombre = txtNombre.getText();
                    String apellido = txtApellido.getText();
                    String direccion = txtDireccion.getText();
                    String ciudad = txtCiudad.getText();

                    Cliente cliente = new Cliente();
                    cliente.setId_cliente(idCliente);
                    cliente.setUsuario(usuario);
                    cliente.setTelefono(telefono);
                    cliente.setNombre(nombre);
                    cliente.setApellido(apellido);
                    cliente.setDireccion(direccion);
                    cliente.setCiudad(ciudad);

                    boolean exito = daCliente.agregar(cliente);

                    if (exito) {
                        JOptionPane.showMessageDialog(jFormClientes.this, "Cliente agregado exitosamente.");
                        limpiarCampos();
                        actualizarTablaClientes();
                    } else {
                        JOptionPane.showMessageDialog(jFormClientes.this, "Error al agregar el cliente.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(jFormClientes.this, "Por favor, complete todos los campos.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        JButton btnActualizar = new JButton("Actualizar");
        btnActualizar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (validarCamposVacios()) {
                    int idCliente = Integer.parseInt(txtIdCliente.getText());
                    Usuario usuario = (Usuario) usuarioComboBox.getSelectedItem();
                    String telefono = txtTelefono.getText();
                    String nombre = txtNombre.getText();
                    String apellido = txtApellido.getText();
                    String direccion = txtDireccion.getText();
                    String ciudad = txtCiudad.getText();

                    Cliente cliente = new Cliente();
                    cliente.setId_cliente(idCliente);
                    cliente.setUsuario(usuario);
                    cliente.setTelefono(telefono);
                    cliente.setNombre(nombre);
                    cliente.setApellido(apellido);
                    cliente.setDireccion(direccion);
                    cliente.setCiudad(ciudad);

                    boolean exito = daCliente.actualizar(cliente);

                    if (exito) {
                        JOptionPane.showMessageDialog(jFormClientes.this, "Cliente actualizado exitosamente.");
                        limpiarCampos();
                        actualizarTablaClientes();
                    } else {
                        JOptionPane.showMessageDialog(jFormClientes.this, "Error al actualizar el cliente.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(jFormClientes.this, "Por favor, complete todos los campos.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        JButton btnEliminar = new JButton("Eliminar");
        btnEliminar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int idCliente = Integer.parseInt(txtIdCliente.getText());

                boolean exito = daCliente.eliminar(idCliente);

                if (exito) {
                    JOptionPane.showMessageDialog(jFormClientes.this, "Cliente eliminado exitosamente.");
                    limpiarCampos();
                    actualizarTablaClientes();
                } else {
                    JOptionPane.showMessageDialog(jFormClientes.this, "Error al eliminar el cliente.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }

        });

        panelSuperior.add(btnNuevo);
        panelSuperior.add(btnAgregar);
        panelSuperior.add(new JLabel());
        panelSuperior.add(btnActualizar);
        panelSuperior.add(new JLabel());
        panelSuperior.add(btnEliminar);

        add(panelSuperior, BorderLayout.NORTH);

        tableModel = new DefaultTableModel();
        tableModel.addColumn("ID Cliente");
        tableModel.addColumn("Usuario");
        tableModel.addColumn("Teléfono");
        tableModel.addColumn("Nombre");
        tableModel.addColumn("Apellido");
        tableModel.addColumn("Dirección");
        tableModel.addColumn("Ciudad");

        tableClientes = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(tableClientes);
        add(scrollPane, BorderLayout.CENTER);

        actualizarTablaClientes();
        cargarUsuarios();
        tableClientes.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int filaSeleccionada = tableClientes.getSelectedRow();
                if (filaSeleccionada != -1) {
                    int idCliente = Integer.parseInt(tableClientes.getValueAt(filaSeleccionada, 0).toString());
                    Cliente cliente = daCliente.obtenerClientePorId(idCliente);
                    mostrarCliente(cliente);
                }
            }
        });

    }

    private void mostrarCliente(Cliente cliente) {
        txtIdCliente.setText(String.valueOf(cliente.getId_cliente()));
        usuarioComboBox.setSelectedItem(cliente.getUsuario());
        txtTelefono.setText(cliente.getTelefono());
        txtNombre.setText(cliente.getNombre());
        txtApellido.setText(cliente.getApellido());
        txtDireccion.setText(cliente.getDireccion());
        txtCiudad.setText(cliente.getCiudad());
    }

    private void generarNuevoIdCliente() {
        int nuevoIdCliente = daCliente.obtenerNuevoIdCliente();
        txtIdCliente.setText(String.valueOf(nuevoIdCliente));
    }

    private void cargarUsuarios() throws SQLException {
        DaUsuario daUsuario = new DaUsuario();
        List<Usuario> usuarios = daUsuario.obtenerTodos();
        DefaultComboBoxModel<Usuario> model = new DefaultComboBoxModel<>();
        for (Usuario usuario : usuarios) {
            model.addElement(usuario);
        }
        usuarioComboBox.setModel(model);
        usuarioComboBox.setRenderer(new UsuarioComboBoxRenderer());
    }

    class UsuarioComboBoxRenderer extends DefaultListCellRenderer {

        @Override
        public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
            if (value instanceof Usuario) {
                value = ((Usuario) value).getNombre();
            }
            return super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
        }
    }

    private void actualizarTablaClientes() {
        List<Cliente> clientes = daCliente.obtenerTodos();
        tableModel.setRowCount(0);
        for (Cliente cliente : clientes) {
            Object[] row = new Object[7];
            row[0] = cliente.getId_cliente();
            row[1] = cliente.getUsuario().getId_usuario();
            row[2] = cliente.getTelefono();
            row[3] = cliente.getNombre();
            row[4] = cliente.getApellido();
            row[5] = cliente.getDireccion();
            row[6] = cliente.getCiudad();
            tableModel.addRow(row);
        }
    }

    private void limpiarCampos() {
        txtIdCliente.setText("");
        usuarioComboBox.setSelectedIndex(0);
        txtTelefono.setText("");
        txtNombre.setText("");
        txtApellido.setText("");
        txtDireccion.setText("");
        txtCiudad.setText("");
    }

    private boolean validarCamposVacios() {
        return !txtIdCliente.getText().isEmpty()
                && usuarioComboBox.getSelectedItem() != null
                && !txtTelefono.getText().isEmpty()
                && !txtNombre.getText().isEmpty()
                && !txtApellido.getText().isEmpty()
                && !txtDireccion.getText().isEmpty()
                && !txtCiudad.getText().isEmpty();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                try {
                    jFormClientes formClientes = new jFormClientes();
                    formClientes.setVisible(true);
                    formClientes.setLocationRelativeTo(null); // Centrar la ventana en la pantalla
                } catch (SQLException ex) {
                    Logger.getLogger(jFormClientes.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }
}
