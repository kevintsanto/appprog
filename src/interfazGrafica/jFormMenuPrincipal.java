package interfazGrafica;

import com.formdev.flatlaf.FlatLightLaf;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;
import com.toedter.calendar.JCalendar;
import org.jdesktop.swingx.JXStatusBar;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class jFormMenuPrincipal extends JFrame {

    public jFormMenuPrincipal() {
        initComponents();
    }

    private void initComponents() {
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);
        setTitle("Tachisetino ");

        // Configurar el panel superior para el logo
        JPanel panelLogo = new JPanel();
        panelLogo.setBackground(Color.WHITE);
        panelLogo.setPreferredSize(new Dimension(150, 50));
        JLabel logoLabel = new JLabel("TACHISETINO", SwingConstants.CENTER);
        logoLabel.setFont(new Font("Arial", Font.BOLD, 18));
        panelLogo.add(logoLabel);

        // Configurar el menú bar
        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);

        // Menú "INICIO"
        JMenu menuInicio = new JMenu("Inicio");
        menuBar.add(menuInicio);

        JMenuItem menuItemInicioSesion = new JMenuItem("Inicio Sesión");
        menuItemInicioSesion.addActionListener(this::menuItemInicioSesionActionPerformed);
        menuInicio.add(menuItemInicioSesion);

        JMenuItem menuItemSalir = new JMenuItem("Salir");
        menuItemSalir.addActionListener(this::menuItemSalirActionPerformed);
        menuInicio.add(menuItemSalir);

        // Menú "Registros"
        JMenu menuRegistros = new JMenu("Registros");
        menuBar.add(menuRegistros);

        JMenuItem menuItemClientes = new JMenuItem("Clientes");
        menuItemClientes.addActionListener(this::menuItemClientesActionPerformed);
        menuRegistros.add(menuItemClientes);

        JMenuItem menuItemProveedores = new JMenuItem("Proveedores");
        menuItemProveedores.addActionListener(this::menuItemProveedoresActionPerformed);
        menuRegistros.add(menuItemProveedores);

        JMenuItem menuItemUsuarios = new JMenuItem("Usuarios");
        menuItemUsuarios.addActionListener(this::menuItemUsuariosActionPerformed);
        menuRegistros.add(menuItemUsuarios);

        // Menú "Operaciones"
        JMenu menuOperaciones = new JMenu("Operaciones");
        menuBar.add(menuOperaciones);

        JMenuItem menuItemVentas = new JMenuItem("Ventas");
        menuItemVentas.addActionListener(this::menuItemVentasActionPerformed);
        menuOperaciones.add(menuItemVentas);

        JMenuItem menuItemCompras = new JMenuItem("Compras");
        menuItemCompras.addActionListener(this::menuItemComprasActionPerformed);
        menuOperaciones.add(menuItemCompras);

        // Menú "Almacen"
        JMenu menuAlmacen = new JMenu("Almacen");
        menuBar.add(menuAlmacen);

        JMenuItem menuItemProductos = new JMenuItem("Productos");
        menuItemProductos.addActionListener(this::menuItemProductosActionPerformed);
        menuAlmacen.add(menuItemProductos);

        JMenuItem menuItemMarcas = new JMenuItem("Marcas");
        menuItemMarcas.addActionListener(this::menuItemMarcasActionPerformed);
        menuAlmacen.add(menuItemMarcas);

        JMenuItem menuItemTiposRopa = new JMenuItem("Tipos de Ropa");
        menuItemTiposRopa.addActionListener(this::menuItemTiposRopaActionPerformed);
        menuAlmacen.add(menuItemTiposRopa);

        // Menú "Configuración"
        JMenu menuConfiguracion = new JMenu("Configuración");
        menuBar.add(menuConfiguracion);

        JMenuItem menuItemCambiarTema = new JMenuItem("Cambiar Tema");
        menuItemCambiarTema.addActionListener(this::menuItemCambiarTemaActionPerformed);
        menuConfiguracion.add(menuItemCambiarTema);

        // Menú "Plus"
        JMenu menuPlus = new JMenu("Plus");
        menuBar.add(menuPlus);

        JMenuItem menuItemCalculadora = new JMenuItem("Calculadora");
        menuItemCalculadora.addActionListener(this::menuItemCalculadoraActionPerformed);
        menuPlus.add(menuItemCalculadora);

        JMenuItem menuItemCalendario = new JMenuItem("Calendario");
        menuItemCalendario.addActionListener(this::menuItemCalendarioActionPerformed);
        menuPlus.add(menuItemCalendario);

        JMenuItem menuItemBlocNotas = new JMenuItem("Bloc de Notas");
        menuItemBlocNotas.addActionListener(this::menuItemBlocNotasActionPerformed);
        menuPlus.add(menuItemBlocNotas);

        // Configurar el JXStatusBar
        JXStatusBar statusBar = new JXStatusBar();
        statusBar.add(new JLabel("𝐓𝐀𝐂𝐇𝐈𝐒𝐄𝐓𝐈𝐍𝐎"));

        // Crear el layout para el panel superior y el menú bar
        FormLayout layout = new FormLayout(
                "pref:grow",
                "pref,pref"
        );
        CellConstraints cc = new CellConstraints();
        JPanel contentPane = new JPanel(layout);
        contentPane.add(panelLogo, cc.xy(1, 1));
        contentPane.add(menuBar, cc.xy(1, 2));

        // Agregar el contenido al frame
        getContentPane().add(contentPane, BorderLayout.NORTH);
        getContentPane().add(statusBar, BorderLayout.SOUTH);
        setExtendedState(JFrame.MAXIMIZED_BOTH); // Maximizar el JFrame para ocupar toda la pantalla
        setUndecorated(true); // Eliminar la barra de título del JFrame
        pack();
        setSize(800, 600);
        setLocationRelativeTo(null);
    }

    private void menuItemInicioSesionActionPerformed(ActionEvent evt) {
        // Abrir la ventana de inicio de sesión (jFormLogin)
        jFormLogin loginForm = new jFormLogin();
        loginForm.setVisible(true);
    }

    private void menuItemSalirActionPerformed(ActionEvent evt) {
        // Cerrar la aplicación
        System.exit(0);
    }

    private void menuItemClientesActionPerformed(ActionEvent evt) {
        try {
            // Operaciones de base de datos que pueden lanzar SQLException
            // ...

            jFormClientes formClientes = new jFormClientes();
            formClientes.setVisible(true);
        } catch (SQLException ex) {
            // Manejo de la excepción SQLException
            ex.printStackTrace(); // O cualquier otro manejo deseado
        }
    }

    private void menuItemProveedoresActionPerformed(ActionEvent evt) {
        try {
            jFormProveedores formProveedores = new jFormProveedores();
            formProveedores.setVisible(true);
        } catch (SQLException ex) {
            Logger.getLogger(jFormMenuPrincipal.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void menuItemUsuariosActionPerformed(ActionEvent evt) {
        jFormUsuarios usuariosForm = new jFormUsuarios(); // Pass the reference of the parent menu
        usuariosForm.setVisible(true);
    }

    private void menuItemVentasActionPerformed(ActionEvent evt) {
        jFormVentas usuariosForm = new jFormVentas(); // Pass the reference of the parent menu
        usuariosForm.setVisible(true);// Implementa la lógica para mostrar la ventana de registro de ventas
    }

    private void menuItemComprasActionPerformed(ActionEvent evt) {
        try {
            jFormCompras compras = new jFormCompras();
            compras.setVisible(true);// Implementa la lógica para mostrar la ventana de registro de compras
        } catch (SQLException ex) {
            Logger.getLogger(jFormMenuPrincipal.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void menuItemProductosActionPerformed(ActionEvent evt) {
        jFormProductos usuariosForm = new jFormProductos(); // Pass the reference of the parent menu
        usuariosForm.setVisible(true);// Implementa la lógica para mostrar la ventana de productos del almacén
    }

    private void menuItemMarcasActionPerformed(ActionEvent evt) {
        try {
            jFormMarca marca = new jFormMarca();
            marca.setVisible(true);// Implementa la lógica para mostrar la ventana de marcas del almacén
        } catch (SQLException ex) {
            Logger.getLogger(jFormMenuPrincipal.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void menuItemTiposRopaActionPerformed(ActionEvent evt) {
        try {
            JFormTipoRopa nuevoTipoRopa = new JFormTipoRopa();
            nuevoTipoRopa.setVisible(true); // Implementa la lógica para mostrar la ventana de tipos de ropa del almacén
        } catch (SQLException ex) {
            Logger.getLogger(jFormMenuPrincipal.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void menuItemCambiarTemaActionPerformed(ActionEvent evt) {
        // Abrir la ventana para cambiar el tema
        JFrame themeFrame = new JFrame("Cambiar Tema");
        themeFrame.setSize(300, 200);
        themeFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        themeFrame.setLocationRelativeTo(this);

        JPanel themePanel = new JPanel();
        themePanel.setLayout(new GridLayout(3, 2));
        themeFrame.add(themePanel);

        JButton btnMetal = new JButton("Metal");
        btnMetal.addActionListener(e -> changeTheme("javax.swing.plaf.metal.MetalLookAndFeel"));
        themePanel.add(btnMetal);

        JButton btnNimbus = new JButton("Nimbus");
        btnNimbus.addActionListener(e -> changeTheme("javax.swing.plaf.nimbus.NimbusLookAndFeel"));
        themePanel.add(btnNimbus);

        JButton btnCDEMotif = new JButton("CDE/Motif");
        btnCDEMotif.addActionListener(e -> changeTheme("com.sun.java.swing.plaf.motif.MotifLookAndFeel"));
        themePanel.add(btnCDEMotif);

        JButton btnWindows = new JButton("Windows");
        btnWindows.addActionListener(e -> changeTheme("com.sun.java.swing.plaf.windows.WindowsLookAndFeel"));
        themePanel.add(btnWindows);

        JButton btnWindowsClassic = new JButton("Windows Classic");
        btnWindowsClassic.addActionListener(e -> changeTheme("com.sun.java.swing.plaf.windows.WindowsClassicLookAndFeel"));
        themePanel.add(btnWindowsClassic);

        themeFrame.setVisible(true);
    }

    private void changeTheme(String theme) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(theme);
                SwingUtilities.updateComponentTreeUI(this);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    private void menuItemCalculadoraActionPerformed(ActionEvent evt) {
        // Crear una instancia de la clase Calculadora
        Calculadora calculadora = new Calculadora();

        // Mostrar la ventana de la calculadora
        calculadora.setVisible(true);
    }

    private void menuItemCalendarioActionPerformed(ActionEvent evt) {
        // Abrir la ventana del calendario
        JFrame calendarFrame = new JFrame("Calendario");
        calendarFrame.setSize(600, 400);
        calendarFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        calendarFrame.setLocationRelativeTo(this);

        // Crear el componente del calendario
        JCalendar calendar = new JCalendar();

        // Agregar el componente al frame del calendario
        calendarFrame.add(calendar);

        calendarFrame.setVisible(true);
    }

    private void menuItemBlocNotasActionPerformed(ActionEvent evt) {
        // Abrir la ventana del bloc de notas
        JFrame notepadFrame = new JFrame("Bloc de Notas");
        notepadFrame.setSize(800, 600);
        notepadFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        notepadFrame.setLocationRelativeTo(this);

        // Crear el componente del área de texto
        JTextArea textArea = new JTextArea();

        // Agregar el componente al frame del bloc de notas
        notepadFrame.add(new JScrollPane(textArea));

        notepadFrame.setVisible(true);
    }

    public static void main(String args[]) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(new FlatLightLaf());
                jFormMenuPrincipal menuForm = new jFormMenuPrincipal();
                menuForm.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}
