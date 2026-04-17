
package locadoradecarros;

import java.sql.Connection; // Conexao SQL para JAVA
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexaoBanco {

private static Connection conexaoUnica;


public Connection getConnection(){
    
    try{
        
        // 2. se a conexao for null (nimguem abriu ainda) ou estiver fechada, nos abrimos.
        
        if(conexaoUnica == null || conexaoUnica.isClosed()) {
        
        conexaoUnica = DriverManager.getConnection(
        "jdbc:mysql://localhost:3306/locadoradecarros", // URL: Onde esta o banco?
        "root", // Usuario
        "root" // Senha
        );

            System.out.println("\n[LOG] Nova conexão aberta com o banco.");        

        }
        
         // 3. se estiver aberta, vamos devolver a que ja existe
        return conexaoUnica;
        
        
    }catch(SQLException e){
        throw new RuntimeException(e);
    }
}
    
}
