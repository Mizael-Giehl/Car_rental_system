/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author MIZAELDAROSAGIEHL
 */
public class Usuario {
    
     private int id;
    private String login, senhaHash;
    private double Saldo;

    public Usuario(String login) {
        this.login = login;
    }
    
    
    
    public Usuario() {
    
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getSenhaHash() {
        return senhaHash;
    }

    public void setSenhaHash(String senhaHash) {
        this.senhaHash = senhaHash;
    }

    @Override
    public String toString() {
        return "Usuario: " + "Id: " + id + " - login: " + login + " - Saldo: " + Saldo;
    }

    

    public double getSaldo() {
        return Saldo;
    }

    public void setSaldo(double Saldo) {
        this.Saldo = Saldo;
    }
    
    
    
    
}
