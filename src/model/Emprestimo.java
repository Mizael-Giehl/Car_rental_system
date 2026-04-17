
package model;


public class Emprestimo {
    
    private int id;
    private int usuario_id;
    private int carro_id;
    private double valor;

    public Emprestimo() {
    }

    
    
    
    
    public Emprestimo(int id, int usuario_id, int carro_id, double valor) {
        this.id = id;
        this.usuario_id = usuario_id;
        this.carro_id = carro_id;
        this.valor = valor;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUsuario_id() {
        return usuario_id;
    }

    public void setUsuario_id(int usuario_id) {
        this.usuario_id = usuario_id;
    }

    public int getCarro_id() {
        return carro_id;
    }

    public void setCarro_id(int carro_id) {
        this.carro_id = carro_id;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    @Override
    public String toString() {
        return "| Emprestimo: " + "Id: " + id + " - usuario_id: " + usuario_id + " - carro_id: " + carro_id + " - valor: " + valor + " |";
    }
    
    
    
    
    
}
