package vendamaxteste;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

public class InserirItem extends JFrame{
    private DefaultTableModel modelo = new DefaultTableModel();
    private JPanel painelFundo;
    private JButton btSalvar;
    private JButton btLimpar;
    private JLabel lbNome;
    private JLabel lbCod;
    private JLabel lbValor;
    private JTextField txNome;
    private JTextField txCod;
    private JTextField txValor;
    
    public InserirItem(DefaultTableModel md){
        super("Item");
        criarJanela();
        modelo = md;
    }
    
    public void criarJanela(){
        btSalvar = new JButton("Salvar");
        btLimpar = new JButton("Limpar");
        lbNome = new JLabel("Nome:");
        lbCod = new JLabel("Codigo:");
        lbValor = new JLabel("valor");
        txNome = new JTextField();
        txCod = new JTextField();
        txValor = new JTextField();
        
        painelFundo = new JPanel();
        painelFundo.setLayout(new GridLayout(4, 2, 2, 4));
        painelFundo.add(lbNome);
        painelFundo.add(txNome);
        painelFundo.add(lbCod);
        painelFundo.add(txCod);
        painelFundo.add(lbValor);
        painelFundo.add(txValor);
        painelFundo.add(btLimpar);
        painelFundo.add(btSalvar);
        
        getContentPane().add(painelFundo);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(400,150);
        btSalvar.addActionListener(new btSalvarListener());
        btLimpar.addActionListener(new btLimparListener());
        
    }
    
    private class btSalvarListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent ae) {
            if (txNome.getText().isEmpty() || txCod.getText().isEmpty() || txValor.getText().isEmpty()){
                JOptionPane.showMessageDialog(null, "Dados não preenchidos para Item!");
            } else{
            ItemPrincipal c = new ItemPrincipal();
            c.setNome(txNome.getText());
            c.setCod(txCod.getText());
            c.setValor(txValor.getText());
            ItemDAO dao = new ItemDAO();
            dao.inserirItem(c);
            ListarItens.pesquisar(modelo);
            setVisible(false);
            }
        }
    }
    
    private class btLimparListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent ae) {
            txNome.setText("");
            txValor.setText("");
            txCod.setText("");
        }
        
    }
    
    
}
