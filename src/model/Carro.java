
package model;

import model.Modelo;

public class Carro {
    
    public int id_carro;
    public int ano;
    public String status;
    private Modelo Modelo;
    public double valor_carro;

    public Carro( int ano, String status, Modelo Modelo, double valor_carro) {
        this.id_carro = id_carro;
        this.ano = ano;
        this.status = status;
        this.Modelo = Modelo;
        this.valor_carro = valor_carro;
    }



    
    

    
    
    public Carro() {
     
    }

    @Override
    public String toString() {
        return "| Id: " + id_carro + " - Modelo: " + Modelo + " - Ano: " + ano + " - Status: " + status + " - Valor do aluguel: " + valor_carro + " |";
    }

    
    
    
    public int getId_carro() {
        return id_carro;
    }

    public void setId_carro(int id_carro) {
        this.id_carro = id_carro;
    }


    public int getAno() {
        return ano;
    }

    public void setAno(int ano) {
        this.ano = ano;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Modelo getModelo() {
        return Modelo;
    }

    public double getValor_carro() {
        return valor_carro;
    }

    public void setValor_carro(double valor_carro) {
        this.valor_carro = valor_carro;
    }

    

    
    
    

    public void setModelo(Modelo Modelo) {
        this.Modelo = Modelo;
    }

    
   
    
    
    
    
    
    
}
