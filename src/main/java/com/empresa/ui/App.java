package com.empresa.ui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;
import com.empresa.dao.FuncionarioDAO;

import com.empresa.model.Funcionario;

public class App {
    private static FuncionarioDAO funcionarioDAO = new FuncionarioDAO();

    public static void main(String[] args) {
        JFrame frame = new JFrame("Sistema de Ponto");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);

        JPanel panel = new JPanel(new GridBagLayout()); // Usando GridBagLayout para mais controle de espaçamento
        panel.setBorder(new EmptyBorder(10, 10, 10, 10)); // Adiciona padding ao redor do painel

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 10, 10, 10); // Espaçamento de 10 pixels em todos os lados dos componentes

        JLabel nomeLabel = new JLabel("Nome:");
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(nomeLabel, gbc);

        JTextField nomeField = new JTextField();
        gbc.gridx = 1;
        gbc.gridy = 0;
        panel.add(nomeField, gbc);

        JLabel cargoLabel = new JLabel("Cargo:");
        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(cargoLabel, gbc);

        JTextField cargoField = new JTextField();
        gbc.gridx = 1;
        gbc.gridy = 1;
        panel.add(cargoField, gbc);

        JButton adicionarButton = new JButton("Adicionar");
        adicionarButton.addActionListener(e -> {
            try {
                Funcionario funcionario = new Funcionario(0, nomeField.getText(), cargoField.getText());
                funcionario.setHorarioEntrada(LocalDateTime.now());
                funcionarioDAO.adicionarFuncionario(funcionario);
                JOptionPane.showMessageDialog(frame, "Funcionário adicionado com sucesso!");
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        });
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        panel.add(adicionarButton, gbc);

        JButton listarButton = new JButton("Listar Funcionários");
        listarButton.addActionListener(e -> {
            try {
                List<Funcionario> funcionarios = funcionarioDAO.listarFuncionarios();
                StringBuilder lista = new StringBuilder();
                for (Funcionario f : funcionarios) {
                    lista.append(f.getId()).append(" - ").append(f.getNome()).append(" - ").append(f.getCargo())
                            .append("\n");
                }
                JOptionPane.showMessageDialog(frame, lista.toString());
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        });
        gbc.gridy = 3;
        panel.add(listarButton, gbc);

        frame.add(panel);
        frame.setVisible(true);
    }
}
