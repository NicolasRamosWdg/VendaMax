package vendamaxteste;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

public class ItemDAO {
    private final String INSERT = "INSERT INTO ITEM (COD_ITEM, NOME, VALOR) VALUES (?,?,?)";
    private final String UPDATE = "UPDATE ITEM SET COD_ITEM=?, NOME=?, VALOR=? WHERE ID=?";
    private final String DELETE = "DELETE FROM ITEM WHERE ID=?";
    private final String LIST = "SELECT * FROM ITEM";
    private final String LISTBYID = "SELECT * FROM ITEM WHERE ID=?";
    
    public List<ItemPrincipal> getItens(){
        Connection con = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        ArrayList<ItemPrincipal> itens = new ArrayList<>();
        try {
            con = FabricaConexao.getConexao();
            pstm = con.prepareStatement(LIST);
            rs = pstm.executeQuery();
            while (rs.next()) {
                ItemPrincipal item = new ItemPrincipal();
                item.setId(rs.getInt("id"));
                item.setCod(rs.getString("cod_item"));
                item.setNome(rs.getString("nome"));
                item.setValor(rs.getString("valor"));
                itens.add(item);
            }
            FabricaConexao.fechaConexao(con, pstm, rs);
        } catch(Exception e){
            JOptionPane.showMessageDialog(null, "Erro ao listar Itens!\n" + e.getMessage());
        }
        return itens;
    }
    
    public void inserirItem(ItemPrincipal item){
        if(item != null){
            Connection con = null;
            try{
                con = FabricaConexao.getConexao();
                PreparedStatement pstm = con.prepareStatement(INSERT);
                pstm.setString(1, item.getCod());
                pstm.setString(2, item.getNome());
                pstm.setString(3, item.getValor());
                pstm.execute();
                JOptionPane.showMessageDialog(null, "Item cadastrado com sucesso!");
                FabricaConexao.fechaConexao(con, pstm);
                
            } catch(Exception e){
                JOptionPane.showMessageDialog(null, "Erro ao inserir Item no banco de dados:\n"
                        + e.getMessage());
            }
        } else{
            JOptionPane.showMessageDialog(null, "Dados inválidos para Item!");
        }
    }
    
    public void atualizar(ItemPrincipal item) {
    if (item != null) {
        Connection con = null;
        try {
            con = FabricaConexao.getConexao();
            PreparedStatement pstm = con.prepareStatement(UPDATE);
            pstm.setString(1, item.getCod());
            pstm.setString(2, item.getNome());
            pstm.setString(3, item.getValor());
            pstm.setInt(4, item.getId());
            
            pstm.executeUpdate();
            JOptionPane.showMessageDialog(null, "Item atualizado com sucesso!");
            FabricaConexao.fechaConexao(con, pstm);
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao atualizar Item!\n" + e.getMessage());
        }
    } else {
        JOptionPane.showMessageDialog(null, "O Item está vazio!");
    }
}





    
    public ItemPrincipal getItemByID(int id){
        Connection con = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        ItemPrincipal item = new ItemPrincipal();
        try{
            con = FabricaConexao.getConexao();
            pstm = con.prepareStatement(LISTBYID);
            pstm.setInt(1, id);
            rs = pstm.executeQuery();
            while(rs.next()){
                item.setId(rs.getInt("id"));
                item.setCod(rs.getString("cod_item"));
                item.setNome(rs.getString("nome"));
                item.setValor(rs.getString("valor"));
            }
            FabricaConexao.fechaConexao(con, pstm, rs);
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, "Erro ao listar Item!\n" + e.getMessage());
        }
        return item;
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
            JOptionPane.showMessageDialog(null, "Erro ao excluir Item!\n" + e.getMessage());
        }
    }
}
