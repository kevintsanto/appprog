package interfazGrafica;

import DataAcces.DaUsuario;
import org.jdesktop.swingx.JXLabel;
import org.jdesktop.swingx.JXPanel;
import org.jdesktop.swingx.JXTitledSeparator;
import Utilidades.AutenticacionUsuario;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;
import java.awt.Font;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class jFormLogin extends JFrame {

    private JButton jButtonIngresar;
    private JButton jButtonCancelar;
    private JTextField jTextFieldUsuario;
    private JPasswordField jPasswordField1;
    private AutenticacionUsuario autenticacionUsuario;

    public jFormLogin(AutenticacionUsuario autenticacionUsuario) {
        this.autenticacionUsuario = autenticacionUsuario;
        initComponents();
    }

    public jFormLogin() {
        try {
            DaUsuario daUsuario = new DaUsuario(); // Reemplaza esto con la creación de tu instancia de DaUsuario válida
            this.autenticacionUsuario = new AutenticacionUsuario(daUsuario);
        } catch (SQLException ex) {
            Logger.getLogger(jFormLogin.class.getName()).log(Level.SEVERE, null, ex);
        }
        initComponents();
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    private void initComponents() {
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);

        // Configurar el contenedor principal con un BorderLayout
        Container contentPane = getContentPane();
        contentPane.setLayout(new BorderLayout());
        contentPane.setBackground(Color.WHITE);

        // Configurar el panel superior para el logo
        JXPanel panelLogo = new JXPanel(new BorderLayout());
        panelLogo.setBackground(Color.WHITE);
        ImageIcon logoIcon = new ImageIcon("C:\\Users\\gg\\Documents\\NetBeansProjects\\ASistemaVentas\\src\\interfazGrafica\\logo tachisetino.png");
        Image image = logoIcon.getImage().getScaledInstance(150, 150, Image.SCALE_SMOOTH);
        ImageIcon scaledLogoIcon = new ImageIcon(image);
        JLabel logoLabel = new JLabel(scaledLogoIcon);
        panelLogo.add(logoLabel, BorderLayout.CENTER);
        contentPane.add(panelLogo, BorderLayout.WEST);

        // Configurar el panel central para los campos de usuario y contraseña
        JXPanel panelForm = new JXPanel(new GridBagLayout());
        panelForm.setBackground(Color.WHITE);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(5, 5, 5, 5);

        JXTitledSeparator separator = new JXTitledSeparator("Iniciar Sesión");
        panelForm.add(separator, gbc);

        gbc.gridy++;
        JXLabel usuarioLabel = new JXLabel("Usuario:");
        panelForm.add(usuarioLabel, gbc);

        gbc.gridy++;
        JXLabel contraseñaLabel = new JXLabel("Contraseña:");
        panelForm.add(contraseñaLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        jTextFieldUsuario = new JTextField(15);
        panelForm.add(jTextFieldUsuario, gbc);

        gbc.gridy = 2;
        jPasswordField1 = new JPasswordField(15);
        panelForm.add(jPasswordField1, gbc);

        contentPane.add(panelForm, BorderLayout.CENTER);

        // Configurar el panel inferior para los botones
        JXPanel panelBotones = new JXPanel(new FlowLayout(FlowLayout.CENTER, 10, 5));
        panelBotones.setBackground(Color.WHITE);

        jButtonIngresar = new JButton("Ingresar");
        jButtonIngresar.setPreferredSize(new Dimension(100, 30));
        jButtonIngresar.addActionListener(this::jButtonIngresarActionPerformed);
        panelBotones.add(jButtonIngresar);

        jButtonCancelar = new JButton("Cancelar");
        jButtonCancelar.setPreferredSize(new Dimension(100, 30));
        jButtonCancelar.addActionListener(this::jButtonCancelarActionPerformed);
        panelBotones.add(jButtonCancelar);

        contentPane.add(panelBotones, BorderLayout.SOUTH);

        // Agregar KeyAdapter para mover el enfoque y realizar la acción de ingreso con "Enter"
        jTextFieldUsuario.addActionListener(e -> jPasswordField1.requestFocus());
        jPasswordField1.addActionListener(this::jButtonIngresarActionPerformed);

        // Configurar el título del JFrame con una fuente personalizada
        setTitle("Tachisetino");
        try {
            Font customFont = Font.createFont(Font.TRUETYPE_FONT, new File("src\\interfazGrafica\\Montserrat-Bold.ttf")).deriveFont(30f);
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(customFont);
            setFont(customFont);
        } catch (IOException | FontFormatException e) {
            e.printStackTrace();
        }

        pack();
        setLocationRelativeTo(null);
    }

    private void jButtonIngresarActionPerformed(ActionEvent evt) {
        // Lógica para verificar las credenciales de inicio de sesión
        String nombreUsuario = jTextFieldUsuario.getText();
        String contraseña = new String(jPasswordField1.getPassword());

        boolean inicioSesionExitoso = autenticacionUsuario.inicio(nombreUsuario, contraseña);

        if (inicioSesionExitoso) {
            // Sesión iniciada correctamente, realizar acciones adicionales aquí
            jFormMenuPrincipal menuPrincipal = new jFormMenuPrincipal();
            menuPrincipal.setVisible(true);
            dispose(); // Cerrar la ventana de inicio de sesión

        }
    }

    private void jButtonCancelarActionPerformed(ActionEvent evt) {
        // Lógica para cancelar el inicio de sesión
        jTextFieldUsuario.setText("");
        jPasswordField1.setText("");
        
    }

    public static void main(String args[]) {
        // Establecer el aspecto Nimbus para una apariencia más moderna
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception e) {
            // Manejar la excepción si no se puede establecer el aspecto Nimbus
        }

        java.awt.EventQueue.invokeLater(() -> {
            try {
                DaUsuario daUsuario = new DaUsuario(); // Reemplaza esto con la creación de tu instancia de DaUsuario válida
                AutenticacionUsuario autenticacionUsuario = new AutenticacionUsuario(daUsuario);
                new jFormLogin(autenticacionUsuario).setVisible(true);
            } catch (SQLException ex) {
                Logger.getLogger(jFormLogin.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }
}
