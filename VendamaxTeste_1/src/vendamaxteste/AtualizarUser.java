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

public class AtualizarUser extends JFrame {
    private DefaultTableModel modelo = new DefaultTableModel();
    private JPanel painelFundo;
    private JButton btSalvar;
    private JButton btLimpar;
    private JLabel lbId;
    private JLabel lbNome;
    private JLabel lbCod;
    private JLabel lbCpf;
    private JTextField txId;
    private JTextField txNome;
    private JTextField txCod;
    private JTextField txCpf;
    UserPrincipal users;
    private int linhaselecionada;

    public AtualizarUser(DefaultTableModel md, int id, int linha) {
        super("User");
        criarJanela();
        modelo = md;
        UserDAO dao = new UserDAO();
        users = dao.getUserByID(id);
        txId.setText(String.valueOf(users.getId()));
        txNome.setText(users.getNome());
        txCod.setText(users.getCod());
        txCpf.setText(String.valueOf(users.getCpf()));
        linhaselecionada = linha;
    }

    public void criarJanela() {
        btSalvar = new JButton("Salvar");
        btLimpar = new JButton("Limpar");
        lbId = new JLabel("Id:");
        lbNome = new JLabel("Nome:");
        lbCod = new JLabel("Cod do User:");
        lbCpf = new JLabel("Cpf do User");
        txId = new JTextField();
        txId.setEditable(false);
        txNome = new JTextField();
        txCod = new JTextField();
        txCpf = new JTextField();

        painelFundo = new JPanel();
        painelFundo.setLayout(new GridLayout(5, 2, 2, 4));
        painelFundo.add(lbId);
        painelFundo.add(txId);
        painelFundo.add(lbNome);
        painelFundo.add(txNome);
        painelFundo.add(lbCod);
        painelFundo.add(txCod);
        painelFundo.add(lbCpf);
        painelFundo.add(txCpf);
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
            UserPrincipal c = new UserPrincipal();
            c.setId(Integer.parseInt(txId.getText()));
            c.setNome(txNome.getText());
            c.setCod(txCod.getText());
            c.setCpf(txCpf.getText());
            
            
            UserDAO dao = new UserDAO();
            dao.atualizar(c);
            modelo.removeRow(linhaselecionada);
            modelo.addRow(new Object[]{c.getId(), c.getNome(), c.getCod(),c.getCpf()});
            setVisible(false);
        }
    }

    private class BtLimparListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent ae) {
            txNome.setText("");
            txCod.setText("");
            txCpf.setText("");
        }

    }
}
