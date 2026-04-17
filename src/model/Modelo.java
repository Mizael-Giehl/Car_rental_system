
package model;


public class Modelo {

public int id_modelo;
public String nome_modelo;

    public Modelo(int id_modelo, String nome_modelo) {
        this.id_modelo = id_modelo;
        this.nome_modelo = nome_modelo;
    }

    public Modelo() {
    
    }

    @Override
    public String toString() {
        return nome_modelo;
    }

    


    
    

    public int getId_modelo() {
        return id_modelo;
    }

    public void setId_modelo(int id_modelo) {
        this.id_modelo = id_modelo;
    }

    public String getNome_modelo() {
        return nome_modelo;
    }

    public void setNome_modelo(String nome_modelo) {
        this.nome_modelo = nome_modelo;
    }



    
    
    

}
