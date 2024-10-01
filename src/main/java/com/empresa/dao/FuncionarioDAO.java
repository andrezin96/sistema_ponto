package com.empresa.dao;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.empresa.model.Funcionario;

public class FuncionarioDAO {
    private static final String URL = "jdbc:mysql://localhost:3306/empresa";
    private static final String USER = "root";
    private static final String PASSWORD = "@nDr3";

    public void adicionarFuncionario(Funcionario funcionario) throws SQLException {
        String sql = "INSERT INTO funcionarios (nome, cargo, horarioEntrada, horarioSaida) VALUES (?, ?, ?, ?)";
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
                PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, funcionario.getNome());
            stmt.setString(2, funcionario.getCargo());
            stmt.setObject(3, funcionario.getHorarioEntrada());
            stmt.setObject(4, funcionario.getHorarioSaida());
            stmt.executeUpdate();
        }
    }

    public void atualizarFuncionario(Funcionario funcionario) throws SQLException {
        String sql = "UPDATE funcionarios SET nome = ?, cargo = ?, horarioEntrada = ?, horarioSaida = ? WHERE id = ?";
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
                PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, funcionario.getNome());
            stmt.setString(2, funcionario.getCargo());
            stmt.setObject(3, funcionario.getHorarioEntrada());
            stmt.setObject(4, funcionario.getHorarioSaida());
            stmt.setInt(5, funcionario.getId());
            stmt.executeUpdate();
        }
    }

    public void deletarFuncionario(int id) throws SQLException {
        String sql = "DELETE FROM funcionarios WHERE id = ?";
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
                PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }

    public Funcionario buscarFuncionario(int id) throws SQLException {
        String sql = "SELECT * FROM funcionarios WHERE id = ?";
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
                PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Funcionario funcionario = new Funcionario(rs.getInt("id"), rs.getString("nome"), rs.getString("cargo"));
                funcionario.setHorarioEntrada(rs.getObject("horarioEntrada", LocalDateTime.class));
                funcionario.setHorarioSaida(rs.getObject("horarioSaida", LocalDateTime.class));
                return funcionario;
            }
        }
        return null;
    }

    public List<Funcionario> listarFuncionarios() throws SQLException {
        String sql = "SELECT * FROM funcionarios";
        List<Funcionario> funcionarios = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
                PreparedStatement stmt = connection.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Funcionario funcionario = new Funcionario(rs.getInt("id"), rs.getString("nome"), rs.getString("cargo"));
                funcionario.setHorarioEntrada(rs.getObject("horarioEntrada", LocalDateTime.class));
                funcionario.setHorarioSaida(rs.getObject("horarioSaida", LocalDateTime.class));
                funcionarios.add(funcionario);
            }
        }
        return funcionarios;
    }
}
