package interfazGrafica;

import Clases.TipoRopa;
import DataAcces.DaTipoRopa;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import org.jdesktop.swingx.JXTable;
import org.jdesktop.swingx.decorator.HighlighterFactory;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class JFormTipoRopa extends JFrame {

    private JLabel lblId;
    private JLabel lblNombre;
    private JTextField txtId;
    private JTextField txtNombre;
    private JButton btnAgregar;
    private JButton btnActualizar;
    private JButton btnEliminar;
    private JButton btnBuscar;
    private JButton btnNuevo; // Nuevo botón agregado
    private JXTable tablaTipos;
    private DefaultTableModel modeloTabla;
    private DaTipoRopa daTipoRopa;
    private int nuevoId = 0; // Variable para generar nuevos IDs

    public JFormTipoRopa() throws SQLException {
        daTipoRopa = new DaTipoRopa();
        inicializarComponentes();
        cargarDatosTabla();

        setTitle("Tipos de Ropa");
setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(600, 400);
        setLocationRelativeTo(null);
    }

    private void inicializarComponentes() {
        lblId = new JLabel("ID:");
        lblNombre = new JLabel("Nombre:");

        txtId = new JTextField();
        txtNombre = new JTextField();

        btnAgregar = new JButton("Agregar");
        btnActualizar = new JButton("Actualizar");
        btnEliminar = new JButton("Eliminar");
        btnBuscar = new JButton("Buscar");
        btnNuevo = new JButton("Nuevo");

        tablaTipos = new JXTable();
        modeloTabla = new DefaultTableModel();
        modeloTabla.addColumn("ID");
        modeloTabla.addColumn("Nombre");
        tablaTipos.setModel(modeloTabla);
        tablaTipos.setColumnControlVisible(true);
        tablaTipos.setSortable(false);
        tablaTipos.setHighlighters(HighlighterFactory.createAlternateStriping());

        JScrollPane scrollPane = new JScrollPane(tablaTipos);

        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(5, 5, 5, 5);

        add(lblId, gbc);
        gbc.gridx++;
        add(txtId, gbc);
        gbc.gridx = 0;
        gbc.gridy++;
        add(lblNombre, gbc);
        gbc.gridx++;
        add(txtNombre, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        add(btnAgregar, gbc);
        gbc.gridx++;
        add(btnActualizar, gbc);
        gbc.gridx++;
        add(btnEliminar, gbc);
        gbc.gridx++;
        add(btnBuscar, gbc);
        gbc.gridx++;
        add(btnNuevo, gbc); // Agregar el botón "Nuevo"

        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 5;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        add(scrollPane, gbc);

        btnAgregar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    agregarTipoRopa();
                } catch (SQLException ex) {
                    Logger.getLogger(JFormTipoRopa.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });

        btnActualizar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    actualizarTipoRopa();
                } catch (SQLException ex) {
                    Logger.getLogger(JFormTipoRopa.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });

        btnEliminar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    eliminarTipoRopa();
                } catch (SQLException ex) {
                    Logger.getLogger(JFormTipoRopa.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });

        btnBuscar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                buscarTiposRopa();
            }
        });

        btnNuevo.addActionListener(new ActionListener() { // Acción para el botón "Nuevo"
            public void actionPerformed(ActionEvent e) {
                try {
                    generarNuevoId();
                } catch (SQLException ex) {
                    Logger.getLogger(JFormTipoRopa.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });

        // Agregar un listener al formulario para mostrar los datos en los campos de texto
        tablaTipos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                mostrarDatosSeleccionados();
            }
        });
    }

    private void agregarTipoRopa() throws SQLException {
        String nombre = txtNombre.getText().trim();

        if (nombre.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Por favor, ingresa el nombre del tipo de ropa", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        TipoRopa tipoRopa = new TipoRopa();
        tipoRopa.setId_tipo(nuevoId); // Asignar el nuevo ID
        tipoRopa.setNombre_tipo(nombre);

        if (daTipoRopa.agregar(tipoRopa)) {
            JOptionPane.showMessageDialog(this, "Tipo de ropa agregado correctamente", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            limpiarCampos();
            cargarDatosTabla();
            generarNuevoId(); // Generar un nuevo ID después de agregar
        }
    }

    private void actualizarTipoRopa() throws SQLException {
        String idText = txtId.getText().trim();
        String nombre = txtNombre.getText().trim();

        if (idText.isEmpty() || nombre.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Por favor, ingresa el ID y el nombre del tipo de ropa", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int id;
        try {
            id = Integer.parseInt(idText);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "El ID debe ser un número entero válido", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        TipoRopa tipoRopa = daTipoRopa.obtenerPorId(id);
        if (tipoRopa == null) {
            JOptionPane.showMessageDialog(this, "No se encontró un tipo de ropa con el ID especificado", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        tipoRopa.setNombre_tipo(nombre);

        if (daTipoRopa.actualizar(tipoRopa)) {
            JOptionPane.showMessageDialog(this, "Tipo de ropa actualizado correctamente", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            limpiarCampos();
            cargarDatosTabla();
            generarNuevoId(); // Generar un nuevo ID después de actualizar
        }
    }

    private void eliminarTipoRopa() throws SQLException {
        String idText = txtId.getText().trim();

        if (idText.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Por favor, ingresa el ID del tipo de ropa a eliminar", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int id;
        try {
            id = Integer.parseInt(idText);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "El ID debe ser un número entero válido", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        TipoRopa tipoRopa = daTipoRopa.obtenerPorId(id);
        if (tipoRopa == null) {
            JOptionPane.showMessageDialog(this, "No se encontró un tipo de ropa con el ID especificado", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int opcion = JOptionPane.showConfirmDialog(this, "¿Estás seguro de eliminar el tipo de ropa?", "Confirmación", JOptionPane.YES_NO_OPTION);
        if (opcion == JOptionPane.YES_OPTION) {
            if (daTipoRopa.eliminar(id)) {
                JOptionPane.showMessageDialog(this, "Tipo de ropa eliminado correctamente", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                limpiarCampos();
                cargarDatosTabla();
                generarNuevoId(); // Generar un nuevo ID después de eliminar
            }
        }
    }

    private void buscarTiposRopa() {
        String criterio = txtNombre.getText().trim();

        List<TipoRopa> tiposRopa = daTipoRopa.buscar(criterio);

        limpiarTabla();

        if (tiposRopa != null) {
            for (TipoRopa tipoRopa : tiposRopa) {
                Object[] fila = {tipoRopa.getId_tipo(), tipoRopa.getNombre_tipo()};
                modeloTabla.addRow(fila);
            }
        }
    }

    private void cargarDatosTabla() {
        List<TipoRopa> tiposRopa = daTipoRopa.obtenerTodos();

        limpiarTabla();

        if (tiposRopa != null) {
            for (TipoRopa tipoRopa : tiposRopa) {
                Object[] fila = {tipoRopa.getId_tipo(), tipoRopa.getNombre_tipo()};
                modeloTabla.addRow(fila);
            }
        }
    }

    private void limpiarTabla() {
        modeloTabla.setRowCount(0);
    }

    private void limpiarCampos() {
        txtId.setText("");
        txtNombre.setText("");
    }

    private void generarNuevoId() throws SQLException {
        int maxId = daTipoRopa.obtenerMaxId(); // Obtener el valor máximo actual de la tabla
        nuevoId = maxId + 1; // Incrementar en 1 para obtener el nuevo ID
        txtId.setText(String.valueOf(nuevoId)); // Actualizar el campo de ID en la interfaz
    }

    private void mostrarDatosSeleccionados() {
        int filaSeleccionada = tablaTipos.getSelectedRow();

        if (filaSeleccionada >= 0) {
            int id = (int) modeloTabla.getValueAt(filaSeleccionada, 0);
            String nombre = (String) modeloTabla.getValueAt(filaSeleccionada, 1);

            txtId.setText(Integer.toString(id));
            txtNombre.setText(nombre);
        } else {
            limpiarCampos();
        }
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(new com.formdev.flatlaf.FlatLightLaf());
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                try {
                    JFormTipoRopa formulario = new JFormTipoRopa();
                    formulario.setVisible(true);
                } catch (SQLException ex) {
                    Logger.getLogger(JFormTipoRopa.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }
}
