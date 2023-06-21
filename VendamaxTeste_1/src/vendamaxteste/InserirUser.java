package vendamaxteste;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.regex.Pattern;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

public class InserirUser extends JFrame {
    private DefaultTableModel modelo = new DefaultTableModel();
    private JPanel painelFundo;
    private JButton btSalvar;
    private JButton btLimpar;
    private JLabel lbNome;
    private JLabel lbCod;
    private JLabel lbCpf;
    private JTextField txNome;
    private JTextField txCod;
    private JTextField txCpf;

    public InserirUser(DefaultTableModel md) {
        super("User");
        criarJanela();
        modelo = md;
    }

    public void criarJanela() {
        btSalvar = new JButton("Salvar");
        btLimpar = new JButton("Limpar");
        lbNome = new JLabel("Nome:");
        lbCod = new JLabel("Codigo:");
        lbCpf = new JLabel("CPF");
        txNome = new JTextField();
        txCod = new JTextField();
        txCpf = new JTextField();

        painelFundo = new JPanel();
        painelFundo.setLayout(new GridLayout(4, 2, 2, 4));
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
        setSize(400, 150);
        btSalvar.addActionListener(new btSalvarListener());
        btLimpar.addActionListener(new btLimparListener());
    }

    private class btSalvarListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent ae) {
            if (txNome.getText().isEmpty() || txCod.getText().isEmpty() || txCpf.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Dados não preenchidos para User!");
            } else if (!isValidCPF(txCpf.getText())) {
                JOptionPane.showMessageDialog(null, "CPF inválido!");
            } else {
                UserPrincipal c = new UserPrincipal();
                c.setNome(txNome.getText());
                c.setCod(txCod.getText());
                c.setCpf(txCpf.getText());
                UserDAO dao = new UserDAO();
                dao.inserirUser(c);
                ListarItens.pesquisar(modelo);
                setVisible(false);
            }
        }
    }

    private class btLimparListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent ae) {
            txNome.setText("");
            txCpf.setText("");
            txCod.setText("");
        }
    }

    private boolean isValidCPF(String cpf) {
        // Remove caracteres não numéricos
        cpf = cpf.replaceAll("\\D", "");

        // Verifica se possui 11 dígitos
        if (cpf.length() != 11) {
            return false;
        }

        // Verifica se todos os dígitos são iguais
        if (cpf.matches("(\\d)\\1{10}")) {
            return false;
        }

        // Calcula o primeiro dígito verificador
        int sum = 0;
        for (int i = 0; i < 9; i++) {
            sum += Character.getNumericValue(cpf.charAt(i)) * (10 - i);
        }
        int digit1 = 11 - (sum % 11);
        if (digit1 >= 10) {
            digit1 = 0;
        }

        // Verifica o primeiro dígito verificador
        if (Character.getNumericValue(cpf.charAt(9)) != digit1) {
            return false;
        }

        // Calcula o segundo dígito verificador
        sum = 0;
        for (int i = 0; i < 10; i++) {
            sum += Character.getNumericValue(cpf.charAt(i)) * (11 - i);
        }
        int digit2 = 11 - (sum % 11);
        if (digit2 >= 10) {
            digit2 = 0;
        }

        // Verifica o segundo dígito verificador
        if (Character.getNumericValue(cpf.charAt(10)) != digit2) {
            return false;
        }

        return true;
    }
}
