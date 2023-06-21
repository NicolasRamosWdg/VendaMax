package vendamaxteste;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

public class UserDAO {
    private final String INSERT = "INSERT INTO USER (COD_USER, NOME, CPF) VALUES (?,?,?)";
    private final String UPDATE = "UPDATE USER SET COD_USER=?, NOME=?, CPF=? WHERE ID=?";
    private final String DELETE = "DELETE FROM USER WHERE ID=?";
    private final String LIST = "SELECT * FROM USER";
    private final String LISTBYID = "SELECT * FROM USER WHERE ID=?";
    
    public List<UserPrincipal> getUser(){
        Connection con = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        ArrayList<UserPrincipal> itens = new ArrayList<>();
        try {
            con = FabricaConexao.getConexao();
            pstm = con.prepareStatement(LIST);
            rs = pstm.executeQuery();
            while (rs.next()) {
                UserPrincipal user = new UserPrincipal();
                user.setId(rs.getInt("id"));
                user.setCod(rs.getString("cod_user"));
                user.setNome(rs.getString("nome"));
                user.setCpf(rs.getString("Cpf"));
                itens.add(user);
            }
            FabricaConexao.fechaConexao(con, pstm, rs);
        } catch(Exception e){
            JOptionPane.showMessageDialog(null, "Erro ao listar User!\n" + e.getMessage());
        }
        return itens;
    }
    
    public void inserirUser(UserPrincipal user){
        if(user != null){
            Connection con = null;
            try{
                con = FabricaConexao.getConexao();
                PreparedStatement pstm = con.prepareStatement(INSERT);
                pstm.setString(1, user.getCod());
                pstm.setString(2, user.getNome());
                pstm.setString(3, user.getCpf());
                pstm.execute();
                JOptionPane.showMessageDialog(null, "User cadastrado com sucesso!");
                FabricaConexao.fechaConexao(con, pstm);
                
            } catch(Exception e){
                JOptionPane.showMessageDialog(null, "Erro ao inserir User no banco de dados:\n"
                        + e.getMessage());
            }
        } else{
            JOptionPane.showMessageDialog(null, "Dados inválidos para User!");
        }
    }
    
    public void atualizar(UserPrincipal user) {
    if (user != null) {
        Connection con = null;
        try {
            con = FabricaConexao.getConexao();
            PreparedStatement pstm = con.prepareStatement(UPDATE);
            pstm.setString(1, user.getCod());
            pstm.setString(2, user.getNome());
            pstm.setString(3, user.getCpf());
            pstm.setInt(4, user.getId());
            
            pstm.executeUpdate();
            JOptionPane.showMessageDialog(null, "User atualizado com sucesso!");
            FabricaConexao.fechaConexao(con, pstm);
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao atualizar User!\n" + e.getMessage());
        }
    } else {
        JOptionPane.showMessageDialog(null, "O User está vazio!");
    }
}





    
    public UserPrincipal getUserByID(int id){
        Connection con = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        UserPrincipal user = new UserPrincipal();
        try{
            con = FabricaConexao.getConexao();
            pstm = con.prepareStatement(LISTBYID);
            pstm.setInt(1, id);
            rs = pstm.executeQuery();
            while(rs.next()){
                user.setId(rs.getInt("id"));
                user.setCod(rs.getString("cod_user"));
                user.setNome(rs.getString("nome"));
                user.setCpf(rs.getString("Cpf"));
            }
            FabricaConexao.fechaConexao(con, pstm, rs);
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, "Erro ao listar User!\n" + e.getMessage());
        }
        return user;
    }
    
    public void remover(int id){
        Connection con = null;
        try{
            con = FabricaConexao.getConexao();
            PreparedStatement pstm = con.prepareStatement(DELETE);
            pstm.setInt(1, id);
            pstm.execute();
            FabricaConexao.fechaConexao(con, pstm);
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, "Erro ao excluir User!\n" + e.getMessage());
        }
    }
}
