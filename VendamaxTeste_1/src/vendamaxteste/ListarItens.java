package vendamaxteste;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class ListarItens extends JFrame {

    private JPanel painelFundo;
    private JPanel painelBotoes;
    private JTable tabela;
    private JScrollPane barraRolagem;
    private JButton btInserir;
    private JButton btAlterar;
    private JButton btExcluir;
    private JButton btFechar;
    private final DefaultTableModel modelo = new DefaultTableModel();

    public ListarItens() {
        super("Itens");
        criarJTable();
        criarJanela();

    }

    public void criarJanela() {
        btInserir = new JButton("Inserir");
        btAlterar = new JButton("Alterar");
        btExcluir = new JButton("Excluir");
        btFechar = new JButton("Fechar");
        painelBotoes = new JPanel();
        barraRolagem = new JScrollPane(tabela);
        painelFundo = new JPanel();
        painelFundo.setLayout(new BorderLayout());
        painelFundo.add(BorderLayout.CENTER, barraRolagem);
        painelBotoes.add(btInserir);
        painelBotoes.add(btAlterar);
        painelBotoes.add(btExcluir);
        painelBotoes.add(btFechar);
        painelFundo.add(BorderLayout.SOUTH, painelBotoes);
        getContentPane().add(painelFundo);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(500, 320);
        setLocationRelativeTo(null);
        btInserir.addActionListener(new btInserirListener());
        btAlterar.addActionListener(new btAlterarListener());
        btExcluir.addActionListener(new btExcluirListener());
        btFechar.addActionListener(new BtFecharListener());
        
        // Adiciona o MouseListener para os botões
        btInserir.addMouseListener(new BotaoMouseListener());
        btAlterar.addMouseListener(new BotaoMouseListener());
        btExcluir.addMouseListener(new BotaoMouseListener());
        btFechar.addMouseListener(new BotaoMouseListener());
    }

    private class btInserirListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent ae) {
            InserirItem ic = new InserirItem(modelo);
            ic.setVisible(true);
        }
    }

    private class btAlterarListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent ae) {
            int linha = -1;
            int linhaselecionada = tabela.getSelectedRow();
            if (linhaselecionada >= 0) {
                int modeloIndex = tabela.convertRowIndexToModel(linhaselecionada);
                int iditem = (int) modelo.getValueAt(modeloIndex, 0);
                AtualizarItem ac = new AtualizarItem(modelo, iditem, linhaselecionada);
                ac.setVisible(true);
            } else {
                JOptionPane.showMessageDialog(null, "É necessário selecionar uma linha!");
            }
        }
    }

    private class btExcluirListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent ae) {
            int linhaselecionada = tabela.getSelectedRow();
            if (linhaselecionada >= 0) {
                int dialogButton = JOptionPane.YES_NO_OPTION;
                int dialogResult = JOptionPane.showConfirmDialog(null,
                        "Confirma a exclusão do registro?", "Atenção", dialogButton);
                System.out.println("Resultado do dialogo" + dialogResult);
                if (dialogResult == 0) {
                    int iditem = (int) tabela.getValueAt(linhaselecionada, 0);
                    ItemDAO dao = new ItemDAO();
                    dao.remover(iditem);
                    modelo.removeRow(linhaselecionada);
                }
            } else {
                JOptionPane.showMessageDialog(null, "É necessário selecionar uma linha!");
            }
        }
    }

    private class BtFecharListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent ae) {
            dispose();
        }
    }

    public void criarJTable() {
        tabela = new JTable(modelo);
        modelo.addColumn("ID");
        modelo.addColumn("Nome");
        modelo.addColumn("Codigo");
        modelo.addColumn("Valor");
        tabela.getColumnModel().getColumn(0).setPreferredWidth(10);
        tabela.getColumnModel().getColumn(1).setPreferredWidth(120);
        tabela.getColumnModel().getColumn(2).setPreferredWidth(80);
        tabela.getColumnModel().getColumn(3).setPreferredWidth(100);
        tabela.getTableHeader().setReorderingAllowed(false);
        pesquisar(modelo);
    }

    public static void pesquisar(DefaultTableModel model) {
        model.setNumRows(0);
        ItemDAO dao = new ItemDAO();
        for (ItemPrincipal c : dao.getItens()) {
            model.addRow(new Object[]{c.getId(), c.getNome(),
                c.getCod(), c.getValor()});
        }
    }

    private class BotaoMouseListener extends MouseAdapter {
        @Override
        public void mouseEntered(MouseEvent e) {
            JButton button = (JButton) e.getSource();
            button.setBackground(Color.YELLOW);
        }

        @Override
        public void mouseExited(MouseEvent e) {
            JButton button = (JButton) e.getSource();
            button.setBackground(null);
        }
    }

    public static void main(String[] args) {
        ListarItens lc = new ListarItens();
        lc.setVisible(true);
    }
}
