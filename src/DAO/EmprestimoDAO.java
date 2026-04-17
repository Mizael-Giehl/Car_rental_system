
package DAO;

import java.sql.Connection;
import locadoradecarros.ConexaoBanco;
import model.Emprestimo;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;
import model.Carro;
import java.sql.ResultSet;

public class EmprestimoDAO {
    
    
    private Connection conn;


public EmprestimoDAO() {
    this.conn = new ConexaoBanco().getConnection();
}
    
    
public void salvarEmprestimo(Emprestimo emprestimo) {
        String sql = "INSERT INTO emprestimos (usuario_id, carro_id, valor) VALUES (?, ?, ?)";
        
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, emprestimo.getUsuario_id());
            stmt.setInt(2, emprestimo.getCarro_id());
            stmt.setDouble(3, emprestimo.getValor());
            
            stmt.execute();
            System.out.println("Emprestimo registrado com sucesso!");
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao salvar emprestimo: " + e.getMessage());
        }
    }


public List<Carro> CarrosPorUsuarios(int idUsuario) throws SQLException {
    List<Carro> alugados = new ArrayList<>();
    
    String sql = "SELECT * FROM carros " +
                 "INNER JOIN emprestimos ON carros.id_carro = emprestimos.carro_id " +
                 "WHERE emprestimos.usuario_id = ? AND carros.status = 'Alugado'";

    try (PreparedStatement stmt = conn.prepareStatement(sql)) {
        
        stmt.setInt(1, idUsuario);
        
        try (ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                
                Carro c = new Carro();
                c.setId_carro(rs.getInt("id_carro"));
                c.setAno(rs.getInt("ano"));
                c.setStatus(rs.getString("status"));
                c.setValor_carro(rs.getDouble("valor_carro"));
                
                alugados.add(c);
            }
        }
    }
    return alugados;
}


    
}
