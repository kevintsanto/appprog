/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Utilidades;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 *
 * @author gg
 */
public class Encriptador {
     public static String encriptar(String texto) {
        try {
            // Se crea un objeto MessageDigest utilizando el algoritmo SHA-256
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            // Se convierte el texto en bytes y se actualiza el digest
            digest.update(texto.getBytes());
            // Se obtiene la representación hexadecimal del hash
            byte[] hash = digest.digest();
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                String hex = Integer.toHexString(0xFF & b);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }
            // Se retorna el hash encriptado
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }
    
}
