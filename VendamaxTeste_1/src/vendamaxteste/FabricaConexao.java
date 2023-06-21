package vendamaxteste;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class FabricaConexao {
    private static final String USUARIO = "root";
    private static final String SENHA = "";
    private static final String DATABASE = "vendamax";
    private static final String DRIVER_CONEXAO = "com.mysql.jdbc.Driver";
    private static final String STR_CONEXAO = "jdbc:mysql://localhost:3306/";

    public static Connection getConexao() throws ClassNotFoundException, SQLException {
        Connection conn = null;
        try{
            Class.forName(DRIVER_CONEXAO);
            conn = DriverManager.getConnection(STR_CONEXAO+DATABASE,USUARIO,SENHA);
            System.out.println("Conexão com o banco de dados feita com sucesso!");
            return conn;
       
            
        } catch(ClassNotFoundException e1){
            throw new ClassNotFoundException(
            "Driver MySQL não foi encontrado" + e1.getMessage());
        } catch(SQLException e2){
            throw new SQLException("Erro ao conectar "
            + " com a base de dados " + e2.getMessage());
        }
        
    }
    
    public static void fechaConexao(Connection con) throws SQLException{
        try{
            if (con != null){
                con.close();
                System.out.println("Conexão fechada com o banco de dados");
            }
        } catch (SQLException e){
            System.out.println("Não foi possível fechar a conexão com o BD");
        }
    }
    
    public static void fechaConexao(Connection con, Statement st) throws SQLException{
        try{
            //Tenho uma conexão
            if(con != null){
                fechaConexao(con);
            }
            //Tenho um statement
            if(st != null){
                st.close();
                System.out.println("Statement fechado com sucesso");
            }
        } catch( SQLException e){
            System.out.println("Não foi possível fechar o statement " + e.getMessage());
        }
    }
    
     public static void fechaConexao(Connection con, Statement st, ResultSet rs) throws SQLException{
        try{
            //Tenho uma conexão e um statement
            if(con != null || st != null){
                fechaConexao(con, st);
            }
            //Tenho um resultset
            if(rs != null){
                rs.close();
                System.out.println("ResultSet fechado com sucesso");
            }
        } catch( SQLException e){
            System.out.println("Não foi possível fechar o ResultSet " + e.getMessage());
        }
    }
    
}
