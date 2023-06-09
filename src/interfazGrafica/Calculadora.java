/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package interfazGrafica;

import com.formdev.flatlaf.FlatLightLaf;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class Calculadora extends JFrame implements ActionListener {

    // Componentes de la calculadora
    private JTextField display;
    private JButton[] botonesNumericos;
    private JButton botonSuma, botonResta, botonMultiplicacion, botonDivision, botonIgual, botonBorrar;

    // Variables auxiliares para realizar los cálculos
    private double operando1, operando2;
    private char operador;

    public Calculadora() {
        // Configuración de la ventana
        setTitle("Calculadora");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);
        FlatLightLaf.install();
        // Crear el panel principal y establecer el diseño
        JPanel panelPrincipal = new JPanel(new BorderLayout());

        // Crear el campo de texto para mostrar el resultado
        display = new JTextField();
        display.setEditable(false);
        panelPrincipal.add(display, BorderLayout.NORTH);

        // Crear el panel para los botones numéricos
        JPanel panelNumeros = new JPanel(new GridLayout(4, 3));

        // Crear los botones numéricos del 0 al 9
        botonesNumericos = new JButton[10];
        for (int i = 0; i < 10; i++) {
            botonesNumericos[i] = new JButton(String.valueOf(i));
            botonesNumericos[i].addActionListener(this);
            panelNumeros.add(botonesNumericos[i]);
        }

        // Crear el botón de suma
        botonSuma = new JButton("+");
        botonSuma.addActionListener(this);

        // Crear el botón de resta
        botonResta = new JButton("-");
        botonResta.addActionListener(this);

        // Crear el botón de multiplicación
        botonMultiplicacion = new JButton("*");
        botonMultiplicacion.addActionListener(this);

        // Crear el botón de división
        botonDivision = new JButton("/");
        botonDivision.addActionListener(this);

        // Crear el botón de igual
        botonIgual = new JButton("=");
        botonIgual.addActionListener(this);

        // Crear el botón de borrar
        botonBorrar = new JButton("C");
        botonBorrar.addActionListener(this);

        // Crear el panel para los botones de operaciones
        JPanel panelOperaciones = new JPanel(new GridLayout(4, 1));
        panelOperaciones.add(botonSuma);
        panelOperaciones.add(botonResta);
        panelOperaciones.add(botonMultiplicacion);
        panelOperaciones.add(botonDivision);

        // Crear el panel para el botón de igual y borrar
        JPanel panelIgualBorrar = new JPanel(new GridLayout(2, 1));
        panelIgualBorrar.add(botonIgual);
        panelIgualBorrar.add(botonBorrar);

        // Agregar los paneles de botones al panel principal
        panelPrincipal.add(panelNumeros, BorderLayout.CENTER);
        panelPrincipal.add(panelOperaciones, BorderLayout.EAST);
        panelPrincipal.add(panelIgualBorrar, BorderLayout.SOUTH);

        // Agregar el panel principal al frame
        add(panelPrincipal);

        // Ajustar el tamaño de la calculadora
        setSize(300, 400);

        // Mostrar la calculadora
        setLocationRelativeTo(null);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();

        // Verificar si se ha presionado un botón numérico
        for (int i = 0; i < 10; i++) {
            if (source == botonesNumericos[i]) {
                String valor = display.getText() + i;
                display.setText(valor);
                return;
            }
        }

        // Verificar el botón de suma
        if (source == botonSuma) {
            operando1 = Double.parseDouble(display.getText());
            operador = '+';
            display.setText("");
        }

        // Verificar el botón de resta
        if (source == botonResta) {
            operando1 = Double.parseDouble(display.getText());
            operador = '-';
            display.setText("");
        }

        // Verificar el botón de multiplicación
        if (source == botonMultiplicacion) {
            operando1 = Double.parseDouble(display.getText());
            operador = '*';
            display.setText("");
        }

        // Verificar el botón de división
        if (source == botonDivision) {
            operando1 = Double.parseDouble(display.getText());
            operador = '/';
            display.setText("");
        }

        // Verificar el botón de igual
        if (source == botonIgual) {
            operando2 = Double.parseDouble(display.getText());
            double resultado = realizarOperacion(operando1, operando2, operador);
            display.setText(String.valueOf(resultado));
        }

        // Verificar el botón de borrar
        if (source == botonBorrar) {
            display.setText("");
        }
    }

    // Método para realizar la operación matemática
    private double realizarOperacion(double operando1, double operando2, char operador) {
        double resultado = 0;

        switch (operador) {
            case '+':
                resultado = operando1 + operando2;
                break;
            case '-':
                resultado = operando1 - operando2;
                break;
            case '*':
                resultado = operando1 * operando2;
                break;
            case '/':
                resultado = operando1 / operando2;
                break;
        }

        return resultado;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Calculadora());
    }
}
