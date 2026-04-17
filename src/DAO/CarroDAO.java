
package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import locadoradecarros.ConexaoBanco;
import model.Carro;
import model.Emprestimo;
import model.Modelo;


public class CarroDAO {

private Connection conn;


public CarroDAO() {
        this.conn = new ConexaoBanco().getConnection();
    }






    public void cadastrar(Carro c, Modelo m) {
        String sql = "INSERT INTO carros (ano, status, modelo_id, valor_carro) VALUES (?, ?, ?, ?)";
        
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, c.getAno());
            stmt.setString(2, c.getStatus());
            stmt.setInt(3, m.getId_modelo());
            stmt.setDouble(4, c.valor_carro);
            
            
            
            stmt.execute();
            System.out.println("Carro cadastrado com sucesso!");
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao cadastrar: " + e.getMessage());
        }
    }



public List<Carro> listar() {
    
    String sql = "SELECT carros.*, modelos.nome_modelo "
               + "FROM carros LEFT JOIN modelos ON carros.modelo_id = modelos.id_modelo";
    
    List<Carro> lista = new ArrayList<>();
    
   
    try (PreparedStatement stmt = conn.prepareStatement(sql);
         ResultSet rs = stmt.executeQuery()) {

        while (rs.next()) {
            Carro carro = new Carro();
            carro.setId_carro(rs.getInt("id_carro"));
            carro.setAno(rs.getInt("ano"));
            carro.setStatus(rs.getString("status"));
            
            
            int idModelo = rs.getInt("modelo_id");
            
            carro.setValor_carro(rs.getDouble("valor_carro"));
            
            if (!rs.wasNull()) {
                Modelo m = new Modelo();
                m.setId_modelo(idModelo);
                m.setNome_modelo(rs.getString("nome_modelo"));
                
               
                carro.setModelo(m); 
            }

            lista.add(carro);
        }
    } catch (SQLException e) {
        throw new RuntimeException("Erro ao listar: " + e.getMessage());
    }
    
    return lista;
}




public void deletar(int id_carro) {
    String sqlEmprestimos = "DELETE FROM emprestimos WHERE carro_id = ?";
    String sqlCarro = "DELETE FROM carros WHERE id_carro = ?";
    
    try {
        conn.setAutoCommit(false);

        try (PreparedStatement stmt1 = conn.prepareStatement(sqlEmprestimos)) {
            stmt1.setInt(1, id_carro);
            stmt1.execute();
        }

        try (PreparedStatement stmt2 = conn.prepareStatement(sqlCarro)) {
            stmt2.setInt(1, id_carro);
            stmt2.execute();
        }

        conn.commit();
        System.out.println("Carro e seus históricos de empréstimo foram excluídos!");
        
    } catch (SQLException e) {
        try { conn.rollback(); } catch (SQLException ex) { }
        throw new RuntimeException("Erro ao excluir: " + e.getMessage());
    }
}



public Carro buscarPorId(int id_carro) {
    String sql = "SELECT * FROM carros WHERE id_carro = ?"; 
    
    try (PreparedStatement stmt = conn.prepareStatement(sql)) {
        stmt.setInt(1, id_carro);
        
        try (ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                Carro carro = new Carro();
                carro.setId_carro(rs.getInt("id_carro"));
                carro.setAno(rs.getInt("ano"));
                
                carro.setStatus(rs.getString("status")); 
                
                Modelo modelo = new Modelo();
                modelo.setId_modelo(rs.getInt("modelo_id")); 
                carro.setModelo(modelo);
                
                carro.setValor_carro(rs.getDouble("valor_carro"));
                
                return carro; 
            }
        }
    } catch (SQLException e) {
        throw new RuntimeException("Erro ao buscar por ID: " + e.getMessage());
    }
    return null;
}



public Double verificarValor(int id) throws SQLException {
    String sql = "SELECT valor_carro FROM carros WHERE id_carro = ?";
    Double Valor = null;

    try (PreparedStatement stmt = conn.prepareStatement(sql)) {
         
        
        stmt.setInt(1, id); 

        try (ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                
                Valor = rs.getDouble("valor_carro");
            }
        }
    } catch (SQLException e) {
        throw new RuntimeException("Erro ao verificar o status: " + e.getMessage());
    }

    return Valor;
}







public String verificarStatus(int id) throws SQLException {
    String sql = "SELECT status FROM carros WHERE id_carro = ?";
    String status = null;

    try (PreparedStatement stmt = conn.prepareStatement(sql)) {
         
        
        stmt.setInt(1, id); 

        try (ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                
                status = rs.getString("status");
            }
        }
    } catch (SQLException e) {
        throw new RuntimeException("Erro ao verificar o status: " + e.getMessage());
    }

    return status;
}




public void devolverCarro(int id_carro) throws SQLException {
    String sqlUpdateCarro = "UPDATE carros SET status = 'Disponivel' WHERE id_carro = ?";
    
    String sqlDeleteEmprestimo = "DELETE FROM emprestimos WHERE carro_id = ? LIMIT 1";

    try {
        conn.setAutoCommit(false);

        try (PreparedStatement stmt1 = conn.prepareStatement(sqlUpdateCarro)) {
            stmt1.setInt(1, id_carro);
            int atualizouCarro = stmt1.executeUpdate();

            if (atualizouCarro > 0) {
                try (PreparedStatement stmt2 = conn.prepareStatement(sqlDeleteEmprestimo)) {
                    stmt2.setInt(1, id_carro);
                    stmt2.executeUpdate();
                }
                
                conn.commit();
                System.out.println("-------------------------------------------");
                System.out.println("SUCESSO: Veículo devolvido e registro baixado!");
                System.out.println("-------------------------------------------");
            } else {
                System.out.println("Erro: Carro não encontrado.");
                conn.rollback();
            }
        }
    } catch (SQLException e) {
        conn.rollback();
        throw new RuntimeException("Erro ao devolver: " + e.getMessage());
    } finally {
        conn.setAutoCommit(true);
    }
}




public Emprestimo alugarCarro(int idUsuario, int idCarro) throws SQLException {
    String sqlUpdate = "UPDATE carros SET status = 'Alugado' WHERE id_carro = ?";
    try (PreparedStatement stmt1 = conn.prepareStatement(sqlUpdate)) {
        stmt1.setInt(1, idCarro);
        stmt1.executeUpdate();
    }

    String sqlInsert = "INSERT INTO emprestimos (usuario_id, carro_id, valor) VALUES (?, ?, ?)";
    
    try (PreparedStatement stmt2 = conn.prepareStatement(sqlInsert)) {
        stmt2.setInt(1, idUsuario);
        stmt2.setInt(2, idCarro);
        stmt2.setDouble(3, 0.0);
        stmt2.executeUpdate();
    } catch (SQLException e) {
        System.out.println("Erro no INSERT de empréstimo: " + e.getMessage());
        throw e;
    }
    
    return new Emprestimo();
}
        



}



