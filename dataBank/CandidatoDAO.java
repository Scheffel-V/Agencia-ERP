package dataBank;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.swing.JOptionPane;

import domain.Candidato;
import domain.Profissao;

public class CandidatoDAO {

	private Connection con = null;
	
	public CandidatoDAO() {
		con = ConnectionFactory.getConnection();
	}
	
	public boolean create(Candidato cand){
		
		String sql = "INSERT INTO candidatos (nome,dataDeCadastro,dataDeNascimento,arquivo,profissao,avaliacao) VALUES (?,?,?,?,?,?)";
		
		PreparedStatement stmt = null;
		
		try {
			stmt = con.prepareStatement(sql);
			stmt.setString(1, cand.getNome());
			stmt.setString(2, cand.getDataDeCadastro());
			stmt.setString(3, cand.getDataDeNascimento());
			stmt.setString(4, cand.getArquivo());
			stmt.setString(5, cand.getProfissao().getNome());
			stmt.setInt(6, cand.getAvaliacao());
			stmt.executeUpdate();
			return true;
		} catch (SQLException e) {
			System.err.println("Erro 1 " + e);
			if(e.getErrorCode() == 1062){
				JOptionPane.showMessageDialog(null, "Candidato " + cand.getNome() + " já cadastrado!");
			}
			return false;
		}finally{
			ConnectionFactory.closeConnection(con, stmt);
		}
	}
	
	public boolean delete(Candidato cand){
		
		String sql = "DELETE FROM candidatos WHERE nome = ?";
		
		PreparedStatement stmt = null;
		
		try {
			stmt = con.prepareStatement(sql);
			stmt.setString(1, cand.getNome());
			stmt.executeUpdate();
			return true;
		} catch (SQLException e) {
			System.err.println("Erro 1 " + e);
			JOptionPane.showMessageDialog(null, "Candidato não pode ser deletado!");
			return false;
		}finally{
			ConnectionFactory.closeConnection(con, stmt);
		}
		
	}
	
	public boolean update(Candidato candVelho, Candidato candNovo){
		String sql = "UPDATE candidatos SET nome = ?, dataDeCadastro = ?, dataDeNascimento = ?, arquivo = ?, profissao = ?, avaliacao = ? WHERE nome = ?";
		
		PreparedStatement stmt = null;
		
		try {
			stmt = con.prepareStatement(sql);
			stmt.setString(1, candNovo.getNome());
			stmt.setString(2, candNovo.getDataDeCadastro());
			stmt.setString(3, candNovo.getDataDeNascimento());
			stmt.setString(4, candNovo.getArquivo());
			stmt.setString(5, candNovo.getProfissao().getNome());
			stmt.setInt(6, candNovo.getAvaliacao());
			stmt.setString(7, candVelho.getNome());
			stmt.executeUpdate();
			return true;
		} catch (SQLException e) {
			System.err.println("Erro 1 " + e);
			if(e.getErrorCode() == 1062){
				JOptionPane.showMessageDialog(null, "Já existe um candidato com esse nome!");
			}
			return false;
		}finally{
			ConnectionFactory.closeConnection(con, stmt);
		}
	}
	
	public List<Candidato> findAll(){
		
		String sql = "SELECT * FROM candidatos";
		
		PreparedStatement stmt = null;
		ResultSet rs = null;
		List<Candidato> candidatos = new ArrayList<>();
		
		try {
			stmt = con.prepareStatement(sql);
			rs = stmt.executeQuery();
			
			while(rs.next()){
				Profissao profissao = new Profissao(rs.getString("profissao"));
				Candidato candidato = new Candidato(rs.getString("nome"), rs.getString("dataDeCadastro"), rs.getString("dataDeNascimento"), rs.getString("arquivo"), profissao, rs.getInt("avaliacao"));
				candidatos.add(candidato);
			}
			
		} catch (SQLException e) {
			System.err.println("Erro " + e);
		}finally{
			ConnectionFactory.closeConnection(con, stmt, rs);
		}
		
		return candidatos;
	}
	
	public List<Candidato> findAll(Profissao prof){
		
		String sql = "SELECT * FROM candidatos WHERE profissao = ?";
		
		PreparedStatement stmt = null;
		ResultSet rs = null;
		List<Candidato> candidatos = new ArrayList<>();
		
		try {
			stmt = con.prepareStatement(sql);
			stmt.setString(1, prof.getNome());
			rs = stmt.executeQuery();
			
			while(rs.next()){
				Profissao profissao = new Profissao(rs.getString("profissao"));
				Candidato candidato = new Candidato(rs.getString("nome"), rs.getString("dataDeCadastro"), rs.getString("dataDeNascimento"),  rs.getString("arquivo"), profissao, rs.getInt("avaliacao"));
				candidatos.add(candidato);
			}
			
		} catch (SQLException e) {
			System.err.println("Erro " + e);
		}finally{
			ConnectionFactory.closeConnection(con, stmt, rs);
		}
		
		return candidatos;
	}
	
public List<Candidato> findAll(String primeiroNome){
		
		String sql = "SELECT * FROM candidatos";
		
		PreparedStatement stmt = null;
		ResultSet rs = null;
		List<Candidato> candidatos = new ArrayList<>();
		
		try {
			stmt = con.prepareStatement(sql);
			rs = stmt.executeQuery();
			
			while(rs.next()){
				Profissao profissao = new Profissao(rs.getString("profissao"));
				Candidato candidato = new Candidato(rs.getString("nome"), rs.getString("dataDeCadastro"), rs.getString("dataDeNascimento"),  rs.getString("arquivo"), profissao, rs.getInt("avaliacao"));
				if(candidato.getNome().startsWith(primeiroNome)){
					candidatos.add(candidato);
				}
			}
			
		} catch (SQLException e) {
			System.err.println("Erro " + e);
		}finally{
			ConnectionFactory.closeConnection(con, stmt, rs);
		}
		
		return candidatos;
	}
	
	public List<Candidato> findAll(Profissao prof, String primeiroNome){
		
		String sql = "SELECT * FROM candidatos WHERE profissao = ?";
		
		PreparedStatement stmt = null;
		ResultSet rs = null;
		List<Candidato> candidatos = new ArrayList<>();
		
		try {
			stmt = con.prepareStatement(sql);
			stmt.setString(1, prof.getNome());
			rs = stmt.executeQuery();
			
			while(rs.next()){
				Profissao profissao = new Profissao(rs.getString("profissao"));
				Candidato candidato = new Candidato(rs.getString("nome"), rs.getString("dataDeCadastro"), rs.getString("dataDeNascimento"),  rs.getString("arquivo"), profissao, rs.getInt("avaliacao"));
				if(candidato.getNome().startsWith(primeiroNome)){
					candidatos.add(candidato);
				}
			}
			
		} catch (SQLException e) {
			System.err.println("Erro " + e);
		}finally{
			ConnectionFactory.closeConnection(con, stmt, rs);
		}
		
		return candidatos;
	}
	
	public List<Candidato> findAll(Profissao prof, int idadeMinima, int idadeMaxima){
		
		String sql = "SELECT * FROM candidatos WHERE profissao = ?";
		
		PreparedStatement stmt = null;
		ResultSet rs = null;
		List<Candidato> candidatos = new ArrayList<>();
		int idade;
		
		try {
			stmt = con.prepareStatement(sql);
			stmt.setString(1, prof.getNome());
			rs = stmt.executeQuery();
			
			while(rs.next()){
				Profissao profissao = new Profissao(rs.getString("profissao"));
				Candidato candidato = new Candidato(rs.getString("nome"), rs.getString("dataDeCadastro"), rs.getString("dataDeNascimento"), rs.getString("arquivo"), profissao, rs.getInt("avaliacao"));
				idade = calcularIdade(candidato.getDataDeNascimento());
				if(idade >= idadeMinima && idade <= idadeMaxima){
					candidatos.add(candidato);
				}
			}
			
		} catch (SQLException e) {
			System.err.println("Erro " + e);
		}finally{
			ConnectionFactory.closeConnection(con, stmt, rs);
		}
		
		return candidatos;
	}
	
	public List<Candidato> findAll(Profissao prof, int avaliacao){
		
		String sql = "SELECT * FROM candidatos WHERE profissao = ? AND avaliacao >= ?";
		
		PreparedStatement stmt = null;
		ResultSet rs = null;
		List<Candidato> candidatos = new ArrayList<>();
		
		try {
			stmt = con.prepareStatement(sql);
			stmt.setString(1, prof.getNome());
			stmt.setInt(2, avaliacao);
			rs = stmt.executeQuery();
			
			while(rs.next()){
				Profissao profissao = new Profissao(rs.getString("profissao"));
				Candidato candidato = new Candidato(rs.getString("nome"), rs.getString("dataDeCadastro"), rs.getString("dataDeNascimento"), rs.getString("arquivo"), profissao, rs.getInt("avaliacao"));
				candidatos.add(candidato);
			}
			
		} catch (SQLException e) {
			System.err.println("Erro " + e);
		}finally{
			ConnectionFactory.closeConnection(con, stmt, rs);
		}
		
		return candidatos;
	}
	
	public List<Candidato> findAll(Profissao prof, int idadeMinima, int idadeMaxima, int avaliacao ){
		
		String sql = "SELECT * FROM candidatos WHERE profissao = ? AND avaliacao >= ?";
		
		PreparedStatement stmt = null;
		ResultSet rs = null;
		List<Candidato> candidatos = new ArrayList<>();
		int idade;
		
		try {
			stmt = con.prepareStatement(sql);
			stmt.setString(1, prof.getNome());
			stmt.setInt(2, avaliacao);
			rs = stmt.executeQuery();
			
			while(rs.next()){
				Profissao profissao = new Profissao(rs.getString("profissao"));
				Candidato candidato = new Candidato(rs.getString("nome"), rs.getString("dataDeCadastro"), rs.getString("dataDeNascimento"), rs.getString("arquivo"), profissao, rs.getInt("avaliacao"));
				idade = calcularIdade(candidato.getDataDeNascimento());
				if(idade >= idadeMinima && idade <= idadeMaxima){
					candidatos.add(candidato);
				}
			}
			
		} catch (SQLException e) {
			System.err.println("Erro " + e);
		}finally{
			ConnectionFactory.closeConnection(con, stmt, rs);
		}
		
		return candidatos;
	}
	
	private int calcularIdade(String nascimento){
		String timeStamp = new SimpleDateFormat("yyyyMMdd").format(Calendar.getInstance().getTime());
		String strAnoAtual = timeStamp.substring(0, 4);
		String strAnoNascimento = nascimento.substring(6, 10);
		int anoAtual = Integer.valueOf(strAnoAtual);
		int anoNascimento = Integer.valueOf(strAnoNascimento);
	
		return (anoAtual - anoNascimento);
	}
}
