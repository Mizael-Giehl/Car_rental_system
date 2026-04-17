
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
import model.Modelo;

public class ModeloDAO {

    private Connection conn;

    
    public ModeloDAO() {
        this.conn = new ConexaoBanco().getConnection();
    }
    
public List<Modelo> listarModelos() {
    String sql = "SELECT * FROM modelos";
    List<Modelo> lista = new ArrayList<>();
    try (PreparedStatement stmt = conn.prepareStatement(sql);
         ResultSet rs = stmt.executeQuery()) {
        while (rs.next()) {
            Modelo m = new Modelo();
            m.setId_modelo(rs.getInt("id_modelo"));
            m.setNome_modelo(rs.getString("nome_modelo"));
            lista.add(m);
        }
    } catch (SQLException e) {
        throw new RuntimeException("Erro ao listar modelos: " + e.getMessage());
    }
    return lista;
}



public void adicionarModelo(Modelo modelo) throws SQLException {
        
    String sql = "insert into modelos (nome_modelo) values (?)";
    
    try (PreparedStatement stmt = conn.prepareStatement(sql)) {
        
        stmt.setString(1, modelo.getNome_modelo());
        
        stmt.execute();
    
    System.out.println("Novo modelo: " + modelo.getNome_modelo() + " foi cadastrado!");

    } catch (SQLException e) {
        throw new RuntimeException("Erro ao cadastrar modelo: " + e.getMessage());
    }


}

public Modelo buscarPorNome(String nome_modelo) {

    String sql = "SELECT * FROM modelos WHERE nome_modelo = ?";
    Modelo modelo = null;

    try (PreparedStatement stmt = conn.prepareStatement(sql)) {

        stmt.setString(1, nome_modelo);

        try (ResultSet rs = stmt.executeQuery()) {

            if (rs.next()) {
                modelo = new Modelo();
                modelo.setId_modelo(rs.getInt("id_modelo"));
                modelo.setNome_modelo(rs.getString("nome_modelo"));
            }
        }

    } catch (SQLException e) {
        throw new RuntimeException("Erro ao buscar modelo por nome: " + e.getMessage());
    }

    return modelo;
}

}
