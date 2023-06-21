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

public class AtualizarItem extends JFrame {
    private DefaultTableModel modelo = new DefaultTableModel();
    private JPanel painelFundo;
    private JButton btSalvar;
    private JButton btLimpar;
    private JLabel lbId;
    private JLabel lbNome;
    private JLabel lbCod;
    private JLabel lbValor;
    private JTextField txId;
    private JTextField txNome;
    private JTextField txCod;
    private JTextField txValor;
    ItemPrincipal itens;
    private int linhaselecionada;

    public AtualizarItem(DefaultTableModel md, int id, int linha) {
        super("Item");
        criarJanela();
        modelo = md;
        ItemDAO dao = new ItemDAO();
        itens = dao.getItemByID(id);
        txId.setText(String.valueOf(itens.getId()));
        txNome.setText(itens.getNome());
        txCod.setText(itens.getCod());
        txValor.setText(String.valueOf(itens.getValor()));
        linhaselecionada = linha;
    }

    public void criarJanela() {
        btSalvar = new JButton("Salvar");
        btLimpar = new JButton("Limpar");
        lbId = new JLabel("Id:");
        lbNome = new JLabel("Nome:");
        lbCod = new JLabel("Cod do Produto:");
        lbValor = new JLabel("Valor do item");
        txId = new JTextField();
        txId.setEditable(false);
        txNome = new JTextField();
        txCod = new JTextField();
        txValor = new JTextField();

        painelFundo = new JPanel();
        painelFundo.setLayout(new GridLayout(5, 2, 2, 4));
        painelFundo.add(lbId);
        painelFundo.add(txId);
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
        setSize(300, 150);

        btSalvar.addActionListener(new BtSalvarListener());
        btLimpar.addActionListener(new BtLimparListener());
    }

    private class BtSalvarListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            ItemPrincipal c = new ItemPrincipal();
            c.setId(Integer.parseInt(txId.getText()));
            c.setNome(txNome.getText());
            c.setCod(txCod.getText());
            c.setValor(txValor.getText());
            
            
            ItemDAO dao = new ItemDAO();
            dao.atualizar(c);
            modelo.removeRow(linhaselecionada);
            modelo.addRow(new Object[]{c.getId(), c.getNome(), c.getCod(),c.getValor()});
            setVisible(false);
        }
    }

    private class BtLimparListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent ae) {
            txNome.setText("");
            txCod.setText("");
            txValor.setText("");
        }

    }
}
