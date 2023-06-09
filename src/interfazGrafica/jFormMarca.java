package interfazGrafica;

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

import Clases.Marca;
import DataAcces.DaMarca;

public class jFormMarca extends JFrame {

    private JTable tablaMarcas;
    private DefaultTableModel modeloTabla;
    private JButton btnAgregar;
    private JButton btnEditar;
    private JButton btnEliminar;
    private JButton btnNuevo;
    private JTextField txtIdMarca;
    private JTextField txtNombreMarca;

    private DaMarca daMarca;

    public jFormMarca() throws SQLException {
        super("Gestión de Marcas");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);

        // Inicializar la instancia del Data Access Object (DAO) para las marcas
        daMarca = new DaMarca();

        // Crear la tabla y el modelo de datos
        modeloTabla = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tablaMarcas = new JTable(modeloTabla);
        JScrollPane scrollPane = new JScrollPane(tablaMarcas);
        add(scrollPane, BorderLayout.CENTER);

        // Crear los botones
        btnAgregar = new JButton("Agregar");
        btnEditar = new JButton("Editar");
        btnEliminar = new JButton("Eliminar");
        btnNuevo = new JButton("Nuevo");

        // Crear el panel superior con los botones
        JPanel panelSuperior = new JPanel();
        panelSuperior.add(btnAgregar);
        panelSuperior.add(btnEditar);
        panelSuperior.add(btnEliminar);
        panelSuperior.add(btnNuevo);

        add(panelSuperior, BorderLayout.NORTH);

        // Crear el formulario para agregar/editar marcas
        JPanel panelFormulario = new JPanel(new GridLayout(2, 2));
        panelFormulario.add(new JLabel("ID Marca:"));
        txtIdMarca = new JTextField(10);
        panelFormulario.add(txtIdMarca);
        panelFormulario.add(new JLabel("Nombre Marca:"));
        txtNombreMarca = new JTextField(30);
        panelFormulario.add(txtNombreMarca);

        add(panelFormulario, BorderLayout.SOUTH);

        // Cargar los datos iniciales de la tabla
        cargarDatosTabla();

        // Asignar eventos a los botones
        btnAgregar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                agregarMarca();
            }
        });

        btnEditar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                editarMarca();
            }
        });

        btnEliminar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                eliminarMarca();
            }
        });

        btnNuevo.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                limpiarFormulario();
                generarNuevoID();
                
            }
        });

        // Asignar evento al hacer clic en la tabla
        tablaMarcas.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int filaSeleccionada = tablaMarcas.getSelectedRow();
                if (filaSeleccionada >= 0) {
                    String idMarca = tablaMarcas.getValueAt(filaSeleccionada, 0).toString();
                    String nombreMarca = tablaMarcas.getValueAt(filaSeleccionada, 1).toString();
                    txtIdMarca.setText(idMarca);
                    txtNombreMarca.setText(nombreMarca);
                }
            }
        });
    }

    private void cargarDatosTabla() {
        modeloTabla.setColumnIdentifiers(new Object[]{"ID Marca", "Nombre Marca"});

        List<Marca> marcas = daMarca.obtenerTodas();
        if (marcas != null) {
            for (Marca marca : marcas) {
                modeloTabla.addRow(new Object[]{marca.getId_marca(), marca.getNombre_marca()});
            }
        }
    }

    private void agregarMarca() {
        String idMarcaText = txtIdMarca.getText().trim();
        if (idMarcaText.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Ingrese un ID de marca válido", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int idMarca;
        try {
            idMarca = Integer.parseInt(idMarcaText);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(null, "Ingrese un ID de marca válido", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String nombreMarca = txtNombreMarca.getText().trim();

        Marca marca = new Marca();
        marca.setId_marca(idMarca);
        marca.setNombre_marca(nombreMarca);

        boolean agregado = daMarca.agregar(marca);
        if (agregado) {
            JOptionPane.showMessageDialog(null, "Marca agregada correctamente", "Información", JOptionPane.INFORMATION_MESSAGE);
            limpiarFormulario();
            actualizarTabla();
        } else {
            JOptionPane.showMessageDialog(null, "Error al agregar la marca", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void editarMarca() {
        String idMarcaText = txtIdMarca.getText().trim();
        if (idMarcaText.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Ingrese un ID de marca válido", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int idMarca;
        try {
            idMarca = Integer.parseInt(idMarcaText);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(null, "Ingrese un ID de marca válido", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String nombreMarca = txtNombreMarca.getText().trim();

        Marca marca = new Marca();
        marca.setId_marca(idMarca);
        marca.setNombre_marca(nombreMarca);

        boolean actualizado = daMarca.actualizar(marca);
        if (actualizado) {
            JOptionPane.showMessageDialog(null, "Marca actualizada correctamente", "Información", JOptionPane.INFORMATION_MESSAGE);
            limpiarFormulario();
            actualizarTabla();
        } else {
            JOptionPane.showMessageDialog(null, "Error al actualizar la marca", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void eliminarMarca() {
        String idMarcaText = txtIdMarca.getText().trim();
        if (idMarcaText.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Ingrese un ID de marca válido", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            int idMarca = Integer.parseInt(idMarcaText);

            int confirmacion = JOptionPane.showConfirmDialog(null, "¿Desea eliminar esta marca?", "Confirmación", JOptionPane.YES_NO_OPTION);
            if (confirmacion == JOptionPane.YES_OPTION) {
                boolean eliminado = daMarca.eliminar(idMarca);
                if (eliminado) {
                    JOptionPane.showMessageDialog(null, "Marca eliminada correctamente", "Información", JOptionPane.INFORMATION_MESSAGE);
                    limpiarFormulario();
                    actualizarTabla();
                } else {
                    JOptionPane.showMessageDialog(null, "Error al eliminar la marca", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(null, "Ingrese un ID de marca válido", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void generarNuevoID() {
        int maxId = daMarca.obtenerNuevoID();
        int nuevoId = maxId + 1;
        txtIdMarca.setText(String.valueOf(nuevoId));
    }

    private void limpiarFormulario() {
        txtIdMarca.setText("");
        txtNombreMarca.setText("");
    }

    private void actualizarTabla() {
        modeloTabla.setRowCount(0);
        cargarDatosTabla();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                try {
                    jFormMarca vista = new jFormMarca();
                    vista.setVisible(true);
                } catch (SQLException ex) {
                    Logger.getLogger(jFormMarca.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }
}
