package vendamaxteste;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.*;

public class VendaGUI extends JFrame {
    private Connection conexao;
    private PreparedStatement inserirVenda;
    private PreparedStatement buscarUsuario;
    private PreparedStatement buscarItem;
    private DefaultTableModel modelo;
    public JTable tabela;
    public JTextField tfCodigoUsuario;

    public VendaGUI() throws ClassNotFoundException {
        super("Venda de Itens");
        criarJanela();
        conectarBancoDados();
        criarJTable();
    }

    private void criarJanela() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        JPanel painelPrincipal = new JPanel(new BorderLayout());

        // Painel de consulta de usuário
        JPanel painelConsulta = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel lblCodigoUsuario = new JLabel("Código do Usuário:");
        tfCodigoUsuario = new JTextField(10);
        JButton btConsultarUsuario = new JButton("Consultar");
        btConsultarUsuario.addActionListener(new BtConsultarUsuarioListener());
        JButton btCancelar = new JButton("Cancelar");
        btCancelar.addActionListener(new BtCancelarListener());

        // Adicionar o MouseListener ao botão "Cancelar"
        btCancelar.addMouseListener(new BotaoMouseListener(btCancelar));

        painelConsulta.add(lblCodigoUsuario);
        painelConsulta.add(tfCodigoUsuario);
        painelConsulta.add(btConsultarUsuario);
        painelConsulta.add(btCancelar);

        // Painel de venda
        JPanel painelVenda = new JPanel(new BorderLayout());
        JScrollPane scrollPane = new JScrollPane();
        tabela = new JTable();
        scrollPane.setViewportView(tabela);
        painelVenda.add(scrollPane, BorderLayout.CENTER);

        // Painel de botões
        JPanel painelBotoes = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton btVender = new JButton("Vender");
        btVender.addActionListener(new BtVenderListener());
        painelBotoes.add(btVender);

        // Adicionar painéis ao painel principal
        painelPrincipal.add(painelConsulta, BorderLayout.NORTH);
        painelPrincipal.add(painelVenda, BorderLayout.CENTER);
        painelPrincipal.add(painelBotoes, BorderLayout.SOUTH);

        // Definir tamanho da janela e centralizar na tela
        setSize(500, 400);
        setLocationRelativeTo(null);

        // Adicionar painel principal à janela
        add(painelPrincipal);
    }

    private class BtVenderListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent ae) {
            int linhaSelecionada = tabela.getSelectedRow();
            if (linhaSelecionada >= 0) {
                try {
                    String codUsuario = tfCodigoUsuario.getText();

                    buscarUsuario.setString(1, codUsuario);
                    ResultSet resultadosUsers = buscarUsuario.executeQuery();
                    if (resultadosUsers.next()) {
                        String nomeUser = resultadosUsers.getString("nome");

                        int codItem = (int) tabela.getValueAt(linhaSelecionada, 0);
                        double valorItem = (double) tabela.getValueAt(linhaSelecionada, 2);
                        String nomeItem = (String) tabela.getValueAt(linhaSelecionada, 1);

                        inserirVenda.setInt(1, codItem);
                        inserirVenda.setInt(2, Integer.parseInt(codUsuario));
                        inserirVenda.setDouble(3, valorItem);
                        inserirVenda.setString(4, nomeItem);
                        inserirVenda.executeUpdate();

                        JOptionPane.showMessageDialog(null, "Venda realizada:\n\n" +
                                "Código do Usuário: " + codUsuario + "\n" +
                                "Nome do Usuário: " + nomeUser + "\n" +
                                "Código do Item: " + codItem + "\n" +
                                "Nome do Item: " + nomeItem + "\n" +
                                "Valor do Item: R$ " + valorItem);

                        tfCodigoUsuario.setText("");
                    } else {
                        JOptionPane.showMessageDialog(null, "Usuário não encontrado.");
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            } else {
                JOptionPane.showMessageDialog(null, "Selecione um item para venda.");
            }
        }
    }

    private class BtConsultarUsuarioListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent ae) {
            String codUsuario = tfCodigoUsuario.getText();
            try {
                buscarUsuario.setString(1, codUsuario);
                ResultSet resultadosUsers = buscarUsuario.executeQuery();
                if (resultadosUsers.next()) {
                    String nomeUser = resultadosUsers.getString("nome");
                    JOptionPane.showMessageDialog(null, "Usuário encontrado:\n\n" +
                            "Código do Usuário: " + codUsuario + "\n" +
                            "Nome do Usuário: " + nomeUser);
                } else {
                    // Usuário não encontrado, exibir todos os usuários do banco
                    mostrarTodosUsuarios();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    private void mostrarTodosUsuarios() {
        try {
            Statement consulta = conexao.createStatement();
            ResultSet resultado = consulta.executeQuery("SELECT * FROM user");

            StringBuilder usuarios = new StringBuilder();
            while (resultado.next()) {
                int idUsuario = resultado.getInt("id");
                String nomeUsuario = resultado.getString("nome");
                String cpfUsuario = resultado.getString("cpf");
                int codigoUsuario = resultado.getInt("cod_user");

                usuarios.append("ID: ").append(idUsuario).append(", Nome: ").append(nomeUsuario).append(", CPF: ").append(cpfUsuario).append(", Código: ").append(codigoUsuario).append("\n");
            }

            if (usuarios.length() > 0) {
                JOptionPane.showMessageDialog(null, "Usuários encontrados:\n\n" + usuarios.toString());
            } else {
                JOptionPane.showMessageDialog(null, "Nenhum usuário encontrado.");
            }

            resultado.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    private class BtCancelarListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent ae) {
            tfCodigoUsuario.setText("");
            dispose(); // Fecha a janela atual
        }
    }

    private void criarJTable() {
        try {
            modelo = new DefaultTableModel();
            tabela.setModel(modelo);
            modelo.addColumn("Código");
            modelo.addColumn("Nome");
            modelo.addColumn("Valor");

            Statement stmt = conexao.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM item");

            while (rs.next()) {
                int codItem = rs.getInt("id");
                String nomeItem = rs.getString("nome");
                double valorItem = rs.getDouble("valor");

                modelo.addRow(new Object[]{codItem, nomeItem, valorItem});
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    private void conectarBancoDados() throws ClassNotFoundException {
        try {
            conexao = FabricaConexao.getConexao();

            inserirVenda = conexao.prepareStatement("INSERT INTO venda (cod_item, cod_user, valor, nome) VALUES (?, ?, ?, ?)");
            buscarUsuario = conexao.prepareStatement("SELECT * FROM user WHERE cod_user = ?");
            buscarItem = conexao.prepareStatement("SELECT * FROM item WHERE id = ?");
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) throws ClassNotFoundException {
        VendaGUI vendaGUI = new VendaGUI();
        vendaGUI.setVisible(true);
    }
}

class BotaoMouseListener extends MouseAdapter {
    private final JButton botao;

    public BotaoMouseListener(JButton botao) {
        this.botao = botao;
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        botao.setForeground(Color.RED);
    }

    @Override
    public void mouseExited(MouseEvent e) {
        botao.setForeground(UIManager.getColor("Button.foreground"));
    }
}
