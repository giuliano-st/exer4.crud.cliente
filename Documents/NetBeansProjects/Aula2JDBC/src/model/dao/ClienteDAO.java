/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.dao;

import connection.ConnectionFactory;
import model.bean.Cliente;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author Trevisan
 */
public class ClienteDAO {

    public void create(Cliente cliente) { //Inserção de Dados no Banco de Dados
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        try {
            con.setAutoCommit(false); // Para o Auto-Commit
            stmt = con.prepareStatement("INSERT INTO cliente (nome, email, CPF, telefone, dataAniversario) VALUES (?, ?, ?, ?, ?) ");
            stmt.setString(1, cliente.getNome());
            stmt.setString(2, cliente.getEmail());
            stmt.setString(3, cliente.getCPF());
            stmt.setString(4, cliente.getTelefone());
            stmt.setString(5, cliente.getDataAniversario());
          
            stmt.executeUpdate();
            
            con.commit(); // Faz o commit
            JOptionPane.showMessageDialog(null, "Salvo com sucesso!");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao salvar: " + ex);
            Logger.getLogger(ClienteDAO.class.getName()).log(Level.SEVERE, null, ex);
            try {
                con.rollback(); //Desfaz as alterações em caso de erros
            } catch (SQLException ex1) {
                Logger.getLogger(ClienteDAO.class.getName()).log(Level.SEVERE, null, ex1);
            }
        } finally {
            ConnectionFactory.closeConnection(con, stmt);
        }

    }

    public ArrayList<Cliente> read() {
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;

        ArrayList<Cliente> listaClientes = new ArrayList<>();
        try {
            stmt = con.prepareStatement("SELECT * FROM cliente ORDER BY idcliente");
            rs = stmt.executeQuery();

            while (rs.next()) {
                Cliente cliente = new Cliente();
                cliente.setIdcliente(rs.getInt("idcliente"));
                cliente.setNome(rs.getString("nome"));
                cliente.setEmail(rs.getString("email"));
                cliente.setCPF(rs.getString("cpf"));
                cliente.setTelefone(rs.getString("telefone"));
                cliente.setDataAniversario(rs.getString("dataaniversario"));
                listaClientes.add(cliente);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao ler o Cliente!", "Erro", JOptionPane.ERROR_MESSAGE);
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
        return listaClientes;
    }

    public void update(Cliente cliente) {
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;

        try {
            stmt = con.prepareStatement("UPDATE cliente SET nome = ?, email = ?, "
                    + "cpf = ?, telefone = ?, dataaniversario = ? WHERE idcliente = ?");
            stmt.setString(1, cliente.getNome());
            stmt.setString(2, cliente.getEmail());
            stmt.setString(3, cliente.getCPF());
            stmt.setString(4, cliente.getTelefone());
            stmt.setString(5, cliente.getDataAniversario());
            stmt.setInt(6, cliente.getIdcliente());

            stmt.executeUpdate();

            JOptionPane.showMessageDialog(null, "Atualizado com sucesso!");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao atualizar: " + ex);
        } finally {

        }
    }

    public void delete(Cliente cliente) {
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;

        try {
            stmt = con.prepareStatement("DELETE FROM cliente WHERE idcliente = ?");
            stmt.setInt(1, cliente.getIdcliente());

            stmt.executeUpdate();

            JOptionPane.showMessageDialog(null, "Excluído com sucesso!");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao excluir: " + ex);
        } finally {
            ConnectionFactory.closeConnection(con, stmt);
        }
    }
}
