/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package util;

import java.security.MessageDigest;
import java.security.MessageDigest;


public class Seguranca {

    
    public static String gerarHash(String senha) {
        
        try {
         
         MessageDigest digest = MessageDigest.getInstance("SHA-256");
         byte[] hash = digest.digest(senha.getBytes()); // 32bytes (256 bits)
         
         StringBuilder hexString = new StringBuilder();
         for (byte b : hash) {
             String hex = Integer.toHexString(0xff & b); 
             if (hex.length() == 1) {
                 hexString.append('0'); 
             }
             
              hexString.append(hex); 
             
         }
         
         return hexString.toString(); 
         
        } catch (Exception e ) {
            throw new RuntimeException("Erro ao gerar a hash: " + e.getMessage());
        }
        
    }
    
}