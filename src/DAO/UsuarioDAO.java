


package DAO;

import locadoradecarros.ConexaoBanco;
import java.sql.Connection;
import util.Seguranca;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import model.Usuario;


public class UsuarioDAO {

private Connection conn;


public UsuarioDAO() {
    this.conn = new ConexaoBanco().getConnection();
}












public Usuario buscarPorLogin(String login) {
    String sql = "SELECT id_usuario, login, saldo, senha_hash FROM usuarios WHERE login = ?";
    
    try (PreparedStatement stmt = conn.prepareStatement(sql)) {
        stmt.setString(1, login);
        
                try (ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                Usuario user = new Usuario();
                user.setId(rs.getInt("id_usuario"));
                user.setLogin(rs.getString("login"));
                user.setSaldo(rs.getDouble("saldo"));
                user.setSenhaHash(rs.getString("senha_hash"));
                
                return user; 
            }
        }
    } catch (SQLException e) {
        throw new RuntimeException("Erro ao buscar usuário: " + e.getMessage());
    }
    return null; 
}



public boolean autenticarSenha(String senhaDigitada) throws SQLException {
    String sql = "select * from usuarios where senha_hash = ?";
    try {
       PreparedStatement stmt = conn.prepareStatement(sql);
       
       stmt.setString(1, Seguranca.gerarHash(senhaDigitada));
       
       ResultSet rs = stmt.executeQuery();
       
       boolean acessoPermitido = rs.next();
       
       stmt.close();
       return acessoPermitido;
       
     } catch (Exception e ) {
            throw new RuntimeException("Erro ao autenticar: " + e.getMessage());
        }
    
}






public void addSaldo(int userId, double saldoAdicionado) {
    String sql = "UPDATE usuarios SET saldo = saldo + ? WHERE id_usuario = ?";

    try {
        PreparedStatement stmt = conn.prepareStatement(sql);
        
        stmt.setDouble(1, saldoAdicionado);
        stmt.setInt(2, userId);
        
        stmt.executeUpdate();
        
        stmt.close();
        
        System.out.println("Saldo atualizado com sucesso!");
        
    } catch (SQLException e) {
        System.out.println("Erro ao atualizar saldo: " + e.getMessage());
    }
}



public void cadastrarUsuario(String LoginNovo, String senhaDigitada) {
    String sql = "insert into usuarios (login, senha_hash) values (?, ?)";
    try {
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1, LoginNovo);
        
       stmt.setString(2, Seguranca.gerarHash(senhaDigitada)); 
        
        stmt.execute();
        
        stmt.close();
        
     } catch (Exception e ) {
            throw new RuntimeException("Erro ao cadastrar utilizador: " + e.getMessage());
        }
}

public boolean autenticar(String login, String senhaDigitada) throws SQLException {
    String sql = "select * from usuarios where login = ? and senha_hash = ?";
    try {
       PreparedStatement stmt = conn.prepareStatement(sql);
       stmt.setString(1, login);
       
       stmt.setString(2, Seguranca.gerarHash(senhaDigitada));
       
       ResultSet rs = stmt.executeQuery();
       
       boolean acessoPermitido = rs.next();
       
       stmt.close();
       return acessoPermitido;
       
     } catch (Exception e ) {
            throw new RuntimeException("Erro ao autenticar: " + e.getMessage());
        }
    
}









}


