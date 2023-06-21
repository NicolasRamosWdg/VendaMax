package vendamaxteste;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import vendamaxteste.ListarItens;

public class PrincipalMenu {
    private JFrame frame;
    private JButton cadastrarItensButton;
    private JButton cadastrarUsuariosButton;
    private JButton realizarVendaButton;

    public PrincipalMenu() {
        // Cria a tela principal
        frame = new JFrame("VendaMax");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 400);

        // Cria o painel para os botões com fundo degradê
        JPanel panel = new JPanel(new GridBagLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                // Cria o objeto GradientPaint para o fundo degradê
                GradientPaint gradient = new GradientPaint(0, 0, Color.BLUE, getWidth(), getHeight(), Color.GREEN);
                
                // Pinta o fundo com o degradê
                Graphics2D g2d = (Graphics2D) g;
                g2d.setPaint(gradient);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };

        // Cria os botões
        cadastrarItensButton = new JButton("Cadastrar Itens");
        cadastrarUsuariosButton = new JButton("Cadastrar Usuários");
        realizarVendaButton = new JButton("Realizar Venda");

        // Configuração do GridBagConstraints para centralizar os botões
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 10, 10, 10);

        // Adiciona os botões ao painel
        panel.add(cadastrarItensButton, gbc);
        panel.add(cadastrarUsuariosButton, gbc);
        panel.add(realizarVendaButton, gbc);

        // Adiciona o painel ao centro do frame
        frame.getContentPane().add(panel, BorderLayout.CENTER);

        // Centraliza o frame na tela
        frame.setLocationRelativeTo(null);

        // Torna o frame visível
        frame.setVisible(true);

        // Adiciona os ActionListeners aos botões
        cadastrarItensButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ListarItens listarItens = new ListarItens();
                listarItens.setVisible(true);
            }
        });

        cadastrarUsuariosButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ListarUser listarUser = new ListarUser();
                listarUser.setVisible(true);
            }
        });

        realizarVendaButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                VendaGUI vendaGUI = null;
                try {
                    vendaGUI = new VendaGUI(); // Corrigido aqui
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(PrincipalMenu.class.getName()).log(Level.SEVERE, null, ex);
                }
                vendaGUI.setVisible(true);
            }
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new PrincipalMenu();
            }
        });
    }
}
