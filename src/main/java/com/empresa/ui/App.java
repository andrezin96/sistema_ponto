package com.empresa.ui;

import com.empresa.dao.FuncionarioDAO;
import com.empresa.model.Funcionario;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

public class App {
    private static FuncionarioDAO funcionarioDAO = new FuncionarioDAO();

    public static void main(String[] args) {
        JFrame frame = new JFrame("Sistema de Ponto");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);

        JPanel panel = new JPanel(new GridLayout(6, 2));

        JLabel nomeLabel = new JLabel("Nome:");
        JTextField nomeField = new JTextField();
        JLabel cargoLabel = new JLabel("Cargo:");
        JTextField cargoField = new JTextField();

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

        panel.add(nomeLabel);
        panel.add(nomeField);
        panel.add(cargoLabel);
        panel.add(cargoField);
        panel.add(adicionarButton);
        panel.add(listarButton);

        frame.add(panel);
        frame.setVisible(true);
    }
}
