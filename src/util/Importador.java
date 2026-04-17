package util;

import DAO.CarroDAO;
import DAO.ModeloDAO;
import model.Carro;
import model.Modelo;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Importador {

    public void importarCarros(String caminhoArquivo) {

        CarroDAO carroDAO = new CarroDAO();
        ModeloDAO modeloDAO = new ModeloDAO(); 
        int contador = 0;

        try (BufferedReader br = new BufferedReader(new FileReader(caminhoArquivo))) {

            br.readLine(); 

            String linha;

            while ((linha = br.readLine()) != null) {

                String[] dados = linha.split(";");

                int ano = Integer.parseInt(dados[1].trim());
                String status = dados[2].trim();
                String nome_modelo = dados[3].trim();
                double valor = Double.parseDouble(dados[4].trim());

                Modelo modelo = modeloDAO.buscarPorNome(nome_modelo);

                if (modelo == null) {
                    System.out.println("Modelo não encontrado: " + nome_modelo);
                }

                Carro novoCarro = new Carro(ano, status, modelo, valor);

                carroDAO.cadastrar(novoCarro, modelo);

                contador++;
            }

            System.out.println("Importação concluída! Foram adicionados " + contador + " carros ao sistema.");

        } catch (IOException e) {
            System.err.println("Erro ao ler o arquivo: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.err.println("Erro de formatação numérica: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Erro inesperado: " + e.getMessage());
        }
    }
}