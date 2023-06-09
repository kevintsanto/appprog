package interfazGrafica;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.util.List;

import Clases.Producto;
import Clases.Marca;
import Clases.TipoRopa;
import DataAcces.DaProducto;
import DataAcces.DaMarca;
import DataAcces.DaTipoRopa;
import java.time.format.DateTimeParseException;

public class jFormProductos extends JFrame {

    private DaProducto daProducto;
    private DaMarca daMarca;
    private DaTipoRopa daTipoRopa;

    private JTable table;
    private DefaultTableModel model;

    private JTextField idField;
    private JTextField nombreField;
    private JTextField descripcionField;
    private JTextField precioField;
    private JTextField stockField;
    private JTextField fechaCreacionField;
    private JTextField fechaActualizacionField;
    private JComboBox<Marca> marcaComboBox;
    private JComboBox<TipoRopa> tipoRopaComboBox;
    private JTextField colorField;

    public jFormProductos() {
        setTitle("Gestión de Productos");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        // Inicializar las instancias de acceso a datos
        try {
            daProducto = new DaProducto();
            daMarca = new DaMarca();
            daTipoRopa = new DaTipoRopa();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Crear la tabla para mostrar los productos
        model = new DefaultTableModel();
        model.addColumn("ID");
        model.addColumn("Nombre");
        model.addColumn("Descripción");
        model.addColumn("Precio");
        model.addColumn("Stock");
        model.addColumn("Fecha de Creación");
        model.addColumn("Fecha de Actualización");
        model.addColumn("Marca");
        model.addColumn("Tipo de Ropa");
        model.addColumn("Color");

        table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        // Crear el panel para los botones y el formulario de producto
        JPanel panel = new JPanel(new BorderLayout());

        // Crear el panel para los botones
        JPanel buttonPanel = new JPanel();

        JButton agregarButton = new JButton("Agregar");
        agregarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                agregarProducto();
            }
        });
        buttonPanel.add(agregarButton);

        JButton editarButton = new JButton("Editar");
        editarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                editarProducto();
            }
        });
        buttonPanel.add(editarButton);

        JButton eliminarButton = new JButton("Eliminar");
        eliminarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                eliminarProducto();
            }
        });
        buttonPanel.add(eliminarButton);

        panel.add(buttonPanel, BorderLayout.NORTH);

        // Crear el formulario para agregar/editar producto
        JPanel formPanel = new JPanel(new GridLayout(10, 2));

        JLabel idLabel = new JLabel("ID:");
        idField = new JTextField();
        idField.setEditable(false);
        formPanel.add(idLabel);
        formPanel.add(idField);

        JLabel nombreLabel = new JLabel("Nombre:");
        nombreField = new JTextField();
        formPanel.add(nombreLabel);
        formPanel.add(nombreField);

        JLabel descripcionLabel = new JLabel("Descripción:");
        descripcionField = new JTextField();
        formPanel.add(descripcionLabel);
        formPanel.add(descripcionField);

        JLabel precioLabel = new JLabel("Precio:");
        precioField = new JTextField();
        formPanel.add(precioLabel);
        formPanel.add(precioField);

        JLabel stockLabel = new JLabel("Stock:");
        stockField = new JTextField();
        formPanel.add(stockLabel);
        formPanel.add(stockField);

        JLabel fechaCreacionLabel = new JLabel("Fecha de Creación:");
        fechaCreacionField = new JTextField();
        formPanel.add(fechaCreacionLabel);
        formPanel.add(fechaCreacionField);

        JLabel fechaActualizacionLabel = new JLabel("Fecha de Actualización:");
        fechaActualizacionField = new JTextField();
        formPanel.add(fechaActualizacionLabel);
        formPanel.add(fechaActualizacionField);

        JLabel marcaLabel = new JLabel("Marca:");
        marcaComboBox = new JComboBox<>(getMarcas());
        formPanel.add(marcaLabel);
        formPanel.add(marcaComboBox);

        JLabel tipoRopaLabel = new JLabel("Tipo de Ropa:");
        tipoRopaComboBox = new JComboBox<>(getTiposRopa());
        formPanel.add(tipoRopaLabel);
        formPanel.add(tipoRopaComboBox);

        JLabel colorLabel = new JLabel("Color:");
        colorField = new JTextField();
        formPanel.add(colorLabel);
        formPanel.add(colorField);

        panel.add(formPanel, BorderLayout.CENTER);

        add(panel, BorderLayout.SOUTH);

        pack();
        setLocationRelativeTo(null);

        // Cargar los productos en la tabla
        cargarProductos();

        // Agregar el listener para capturar los clics en la tabla
        table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    mostrarDatosProductoSeleccionado();
                }
            }
        });
    }

    private void cargarProductos() {
        List<Producto> productos = daProducto.obtenerTodos();

        model.setRowCount(0);

        for (Producto producto : productos) {
            model.addRow(new Object[]{
                producto.getId_producto(),
                producto.getNombre(),
                producto.getDescripcion(),
                producto.getPrecio_unitario(),
                producto.getStock(),
                producto.getFecha_creacion(),
                producto.getFecha_actualizacion(),
                producto.getMarca().getNombre_marca(),
                producto.getTipoRopa().getNombre_tipo(),
                producto.getColor()
            });
        }
    }

    private void agregarProducto() {
        String idText = idField.getText();
        if (idText.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Ingrese un valor válido para el ID.", "Agregar Producto", JOptionPane.WARNING_MESSAGE);
            return;
        }
        int id = Integer.parseInt(idText);
        String nombre = nombreField.getText();
        String descripcion = descripcionField.getText();
        double precio = Double.parseDouble(precioField.getText());
        int stock = Integer.parseInt(stockField.getText());
        LocalDate fechaCreacion = LocalDate.parse(fechaCreacionField.getText());
        LocalDate fechaActualizacion = LocalDate.parse(fechaActualizacionField.getText());
        Marca marca = (Marca) marcaComboBox.getSelectedItem();
        TipoRopa tipoRopa = (TipoRopa) tipoRopaComboBox.getSelectedItem();
        String color = colorField.getText();

        Producto producto = new Producto(id, nombre, descripcion, precio, stock, fechaCreacion, fechaActualizacion, marca, tipoRopa, color);
        daProducto.agregar(producto);

        cargarProductos();

        limpiarFormulario();
    }

    private void editarProducto() {
        int row = table.getSelectedRow();

        if (row >= 0) {
            int id = (int) table.getValueAt(row, 0);
            String nombre = nombreField.getText();
            String descripcion = descripcionField.getText();
            double precio = Double.parseDouble(precioField.getText());
            int stock = Integer.parseInt(stockField.getText());
            LocalDate fechaCreacion = LocalDate.parse(fechaCreacionField.getText());
            LocalDate fechaActualizacion = LocalDate.parse(fechaActualizacionField.getText());
            Marca marca = (Marca) marcaComboBox.getSelectedItem();
            TipoRopa tipoRopa = (TipoRopa) tipoRopaComboBox.getSelectedItem();
            String color = colorField.getText();

            Producto producto = new Producto(id, nombre, descripcion, precio, stock, fechaCreacion, fechaActualizacion, marca, tipoRopa, color);
            daProducto.actualizar(producto);

            cargarProductos();

            limpiarFormulario();
        } else {
            JOptionPane.showMessageDialog(this, "Seleccione un producto de la tabla.", "Editar Producto", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void eliminarProducto() {
        int row = table.getSelectedRow();

        if (row >= 0) {
            int id = (int) table.getValueAt(row, 0);

            int confirm = JOptionPane.showConfirmDialog(this, "¿Está seguro de eliminar el producto seleccionado?", "Eliminar Producto", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                daProducto.eliminar(id);

                cargarProductos();

                limpiarFormulario();
            }
        } else {
            JOptionPane.showMessageDialog(this, "Seleccione un producto de la tabla.", "Eliminar Producto", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void mostrarDatosProductoSeleccionado() {
        int row = table.getSelectedRow();

        if (row >= 0) {
            int id = (int) table.getValueAt(row, 0);
            String nombre = (String) table.getValueAt(row, 1);
            String descripcion = (String) table.getValueAt(row, 2);
            double precio = (double) table.getValueAt(row, 3);
            int stock = (int) table.getValueAt(row, 4);
            String fechaCreacionStr = (String) table.getValueAt(row, 5);
            String fechaActualizacionStr = (String) table.getValueAt(row, 6);
            LocalDate fechaCreacion = LocalDate.parse(fechaCreacionStr);
            LocalDate fechaActualizacion = LocalDate.parse(fechaActualizacionStr);

            String marcaNombre = (String) table.getValueAt(row, 7);
            String tipoRopaNombre = (String) table.getValueAt(row, 8);
            String color = (String) table.getValueAt(row, 9);

            idField.setText(String.valueOf(id));
            nombreField.setText(nombre);
            descripcionField.setText(descripcion);
            precioField.setText(String.valueOf(precio));
            stockField.setText(String.valueOf(stock));
            fechaCreacionField.setText(fechaCreacion.toString());
            fechaActualizacionField.setText(fechaActualizacion.toString());

            // Seleccionar la marca y el tipo de ropa en los ComboBox
            Marca marcaSeleccionada = buscarMarcaPorNombre(marcaNombre);
            if (marcaSeleccionada != null) {
                marcaComboBox.setSelectedItem(marcaSeleccionada);
            }

            TipoRopa tipoRopaSeleccionado = buscarTipoRopaPorNombre(tipoRopaNombre);
            if (tipoRopaSeleccionado != null) {
                tipoRopaComboBox.setSelectedItem(tipoRopaSeleccionado);
            }

            colorField.setText(color);
        }
    }

    private void limpiarFormulario() {
        idField.setText("");
        nombreField.setText("");
        descripcionField.setText("");
        precioField.setText("");
        stockField.setText("");
        fechaCreacionField.setText("");
        fechaActualizacionField.setText("");
        marcaComboBox.setSelectedIndex(0);
        tipoRopaComboBox.setSelectedIndex(0);
        colorField.setText("");
    }

    private Marca[] getMarcas() {
        List<Marca> marcas = daMarca.obtenerTodas();
        return marcas.toArray(new Marca[0]);
    }

    private TipoRopa[] getTiposRopa() {
        List<TipoRopa> tiposRopa = daTipoRopa.obtenerTodos();
        return tiposRopa.toArray(new TipoRopa[0]);
    }

    private Marca buscarMarcaPorNombre(String nombre) {
        List<Marca> marcas = daMarca.obtenerTodas();

        for (Marca marca : marcas) {
            if (marca.getNombre_marca().equals(nombre)) {
                return marca;
            }
        }

        return null;
    }

    private TipoRopa buscarTipoRopaPorNombre(String nombre) {
        List<TipoRopa> tiposRopa = daTipoRopa.obtenerTodos();

        for (TipoRopa tipoRopa : tiposRopa) {
            if (tipoRopa.getNombre_tipo().equals(nombre)) {
                return tipoRopa;
            }
        }

        return null;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new jFormProductos().setVisible(true);
            }
        });
    }
}
