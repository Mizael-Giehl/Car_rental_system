/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package util;

import DAO.CarroDAO;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import model.Carro;
import model.Modelo;


public class Exportador {



    
    public void exportarInventario(String caminhoArquivo) throws IOException, SQLException {
        
        CarroDAO dao = new CarroDAO(); 
        List<Carro> lista = dao.listar();
        
        if (lista.isEmpty()) {
            System.out.println("A lista está vazia. Nada para exportar.");
            return;
        }
        
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(caminhoArquivo))) {
            bw.write("ID;Ano;Status;Modelo;Valor\n");
            
            for (Carro carro : lista) {
                
        String nomeModelo = (carro.getModelo() != null) ? carro.getModelo().getNome_modelo() : "Sem Categoria";

        String linha = carro.getId_carro() + ";" +
                   carro.getAno() + ";" +
                   carro.getStatus() + ";" +
                   nomeModelo + ";" + 
                   carro.getValor_carro() + ";\n";

    bw.write(linha);
}
            
            System.out.println("Relatorio exportado com sucesso para: " + caminhoArquivo);
             
        } catch (IOException e) {
             System.out.println("Erro ao gravar arquivo: " + e.getMessage());
        }
    }
}


