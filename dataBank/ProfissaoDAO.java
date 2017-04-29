package dataBank;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import domain.Profissao;

public class ProfissaoDAO {
	private Connection con = null;
	
	public ProfissaoDAO() {
		con = ConnectionFactory.getConnection();
	}
	
	public boolean create(Profissao prof){
		
		String sql = "INSERT INTO profissoes (nome) VALUES (?)";
		PreparedStatement stmt = null;
		
		try {
			stmt = con.prepareStatement(sql);
			stmt.setString(1, prof.getNome());
			stmt.executeUpdate();
			return true;
		} catch (SQLException e) {
			System.err.println("Erro " + e);
			if(e.getErrorCode() == 1062){
				JOptionPane.showMessageDialog(null, "Profissão " + prof.getNome() + " já existente!");
			}
			return false;
		}finally{
			ConnectionFactory.closeConnection(con, stmt);
		}
	}

	public boolean delete(Profissao prof){
		
		CandidatoDAO candDAO = new CandidatoDAO();
		if(candDAO.findAll(prof).isEmpty()){
			String sql = "DELETE FROM profissoes WHERE nome = ?";
			
			PreparedStatement stmt = null;
			
			try {
				stmt = con.prepareStatement(sql);
				stmt.setString(1, prof.getNome());
				stmt.executeUpdate();
				return true;
			} catch (SQLException e) {
				System.err.println("Erro 1 " + e);
				JOptionPane.showMessageDialog(null, "Profissão não pode ser deletada!");
				return false;
			}finally{
				ConnectionFactory.closeConnection(con, stmt);
			}
		}else{
			JOptionPane.showMessageDialog(null, "Existe algum candidato nesta profissão!");
			return false;
		}

		
	}
	
	public boolean update(Profissao profVelha, Profissao profNova){
		String sql2 = "SET foreign_key_checks = 0";
		PreparedStatement stmt2 = null;
		
		try {
			stmt2 = con.prepareStatement(sql2);
			stmt2.execute();
		} catch (SQLException e2) {
			// TODO Auto-generated catch block
			System.err.println("Erro 3" + e2);
			return false;
		}
		
		String sql1 = "UPDATE candidatos SET profissao = ? WHERE profissao = ?";
		PreparedStatement stmt1 = null;
		
		try {
			stmt1 = con.prepareStatement(sql1);
			stmt1.setString(1, profNova.getNome());
			stmt1.setString(2, profVelha.getNome());
			stmt1.executeUpdate();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			System.err.println("Erro 2 " + e1);
			ConnectionFactory.closeConnection(con, stmt1);
			return false;
		}
		
		String sql = "UPDATE profissoes SET nome = ? WHERE nome = ?";
		PreparedStatement stmt = null;
		
		try {
			stmt = con.prepareStatement(sql);
			stmt.setString(1, profNova.getNome());
			stmt.setString(2, profVelha.getNome());
			stmt.executeUpdate();
		} catch (SQLException e) {
			System.err.println("Erro 1 " + e);
			if(e.getErrorCode() == 1062){
				JOptionPane.showMessageDialog(null, "Já existe uma profissão com esse nome!");
			}
			return false;
		}
		
		String sql4 = "SET foreign_key_checks = 1";
		PreparedStatement stmt4 = null;
		
		try {
			stmt4 = con.prepareStatement(sql4);
			stmt4.execute();
			return true;
		} catch (SQLException e2) {
			// TODO Auto-generated catch block
			System.err.println("Erro 4" + e2);
			return false;
		}finally{
			ConnectionFactory.closeConnection(con, stmt);
		}
	}
	
	public List<Profissao> findAll(){
		
		String sql = "SELECT * FROM profissoes";
		
		PreparedStatement stmt = null;
		ResultSet rs = null;
		List<Profissao> profissoes = new ArrayList<>();
		
		try {
			stmt = con.prepareStatement(sql);
			rs = stmt.executeQuery();
			
			while(rs.next()){
				Profissao profissao = new Profissao(rs.getString("nome"));
				profissoes.add(profissao);
			}
			
		} catch (SQLException e) {
			System.err.println("Erro " + e);
		}finally{
			ConnectionFactory.closeConnection(con, stmt, rs);
		}
		
		return profissoes;
	}
}
