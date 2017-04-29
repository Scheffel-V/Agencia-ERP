package dataBank;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import domain.Candidato;
import domain.Profissao;

public class BancoDeDados {
	
	public BancoDeDados() {
		
	}
	
	public boolean adcionarCandidato(Candidato candidato){
		CandidatoDAO candDAO = new CandidatoDAO();
		
		if(candDAO.create(candidato)){
			return true;
		}else{
			return false;
		}
	}
	
	public boolean deletarCandidato(Candidato candidato){
		CandidatoDAO candDAO = new CandidatoDAO();
		
		if(candDAO.delete(candidato)){
			JOptionPane.showMessageDialog(null, "Candidato deletado!");
			return true;
		}else{
			return false;
		}
	}
	
	public boolean editarCandidato(Candidato candVelho, Candidato candNovo){
		CandidatoDAO candDAO = new CandidatoDAO();
		
		if(candDAO.update(candVelho, candNovo)){
			JOptionPane.showMessageDialog(null, "Candidato atualizado!");
			return true;
		}else{
			return false;
		}
	}
	
	public Candidato getCandidato(String candidato){
		CandidatoDAO candDAO = new CandidatoDAO();
		
		for(Candidato cand : candDAO.findAll()){
			if(cand.getNome().equals(candidato)){
				return cand;
			}
		}
		
		return null;
	}

	public boolean adcionarProfissao(Profissao profissao){
		ProfissaoDAO profDAO = new ProfissaoDAO();
		
		if(profDAO.create(profissao)){
			return true;
		}else{
			return false;
		}
	}
	
	public boolean deletarProfissao(Profissao profissao){
		ProfissaoDAO profDAO = new ProfissaoDAO();
		
		if(profDAO.delete(profissao)){
			JOptionPane.showMessageDialog(null, "Profissão deletada!");
			return true;
		}else{
			return false;
		}
		
	}
	
	public boolean editarProfissao(Profissao profVelha, Profissao profNova){
		ProfissaoDAO profDAO = new ProfissaoDAO();
		
		if(profDAO.update(profVelha, profNova)){
			JOptionPane.showMessageDialog(null, "Profissão atualizada!");
			return true;
		}else{
			return false;
		}
	}
	
	public Profissao getProfissao(String nome){
		ProfissaoDAO profDAO = new ProfissaoDAO();
		
		for(Profissao obj : profDAO.findAll()){
			if(obj.getNome().equals(nome)){
				return obj;
			}
		}
		return null;
	}
	
	public ArrayList<Profissao> getListaProfissoes(){
		ProfissaoDAO profDAO = new ProfissaoDAO();
		return (ArrayList<Profissao>) profDAO.findAll();
	}
	
	public ArrayList<Candidato> getListaCandidatos(){
		CandidatoDAO candDAO = new CandidatoDAO();
		return (ArrayList<Candidato>) candDAO.findAll();
	}
	
	public ArrayList<Candidato> getListaCandidatos(Profissao profissao){
		CandidatoDAO candDAO = new CandidatoDAO();
		return (ArrayList<Candidato>) candDAO.findAll(profissao);
	}
	
	public ArrayList<Candidato> getListaCandidatos(String primeiroNome){
		CandidatoDAO candDAO = new CandidatoDAO();
		return (ArrayList<Candidato>) candDAO.findAll(primeiroNome);
	}
	
	public ArrayList<Candidato> getListaCandidatos(Profissao profissao, String primeiroNome){
		CandidatoDAO candDAO = new CandidatoDAO();
		return (ArrayList<Candidato>) candDAO.findAll(profissao, primeiroNome);
	}
	
	public ArrayList<Candidato> getListaCandidatos(Profissao profissao, int idadeMinima, int idadeMaxima){
		CandidatoDAO candDAO = new CandidatoDAO();
		return (ArrayList<Candidato>) candDAO.findAll(profissao, idadeMinima, idadeMaxima);
	}
	
	public ArrayList<Candidato> getListaCandidatos(Profissao profissao, int avaliacao){
		CandidatoDAO candDAO = new CandidatoDAO();
		return (ArrayList<Candidato>) candDAO.findAll(profissao, avaliacao);
	}
	
	public ArrayList<Candidato> getListaCandidatos(Profissao profissao, int idadeMinima, int idadeMaxima, int avaliacao){
		CandidatoDAO candDAO = new CandidatoDAO();
		return (ArrayList<Candidato>) candDAO.findAll(profissao, idadeMinima, idadeMaxima, avaliacao);
	}
	
	public boolean exportarProfissoes(){
		List<Profissao> profissoes = new ArrayList<Profissao>();
		ProfissaoDAO profDAO = new ProfissaoDAO();
		profissoes = profDAO.findAll();
		String timeStamp = new SimpleDateFormat("yyyyMMdd").format(Calendar.getInstance().getTime());
		
		try {
			FileWriter fileWriter = new FileWriter("profissoes" + timeStamp + ".txt");
			BufferedWriter buff = new BufferedWriter(fileWriter);
			
			for(Profissao prof : profissoes){
				buff.write(prof.getNome());
				buff.newLine();
			}
			buff.close();
			fileWriter.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return true;
	}
	
	public boolean exportarCandidatos(){
		List<Candidato> candidatos = new ArrayList<Candidato>();
		CandidatoDAO candDAO = new CandidatoDAO();
		candidatos = candDAO.findAll();
		String timeStamp = new SimpleDateFormat("yyyyMMdd").format(Calendar.getInstance().getTime());
		
		try {
			FileWriter fileWriter = new FileWriter("candidatos" + timeStamp + ".txt");
			BufferedWriter buff = new BufferedWriter(fileWriter);
			
			for(Candidato cand : candidatos){
				buff.write(cand.getNome());
				buff.newLine();
				buff.write(cand.getDataDeCadastro());
				buff.newLine();
				buff.write(cand.getDataDeNascimento());
				buff.newLine();
				buff.write(cand.getArquivo());
				buff.newLine();
				buff.write(cand.getProfissao().getNome());
				buff.newLine();
				buff.write(String.valueOf(cand.getAvaliacao()));
				buff.newLine();
			}
			buff.close();
			fileWriter.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return true;
	}

	@SuppressWarnings("static-access")
	public boolean importarProfissoes(){
		JFileChooser arquivo = new JFileChooser();
		int result = arquivo.showOpenDialog(null);
		List<Profissao> listaProfissoes = new ArrayList<Profissao>();
		String caminhoArquivo;
		String linha;
		
		if(result == arquivo.APPROVE_OPTION){
			caminhoArquivo = arquivo.getSelectedFile().getAbsolutePath();
			
			try {
				FileReader fileReader = new FileReader(caminhoArquivo);
				@SuppressWarnings("resource")
				BufferedReader buff = new BufferedReader(fileReader);
				
				linha = buff.readLine();
				while(linha != null){
					Profissao prof = new Profissao(linha);
					listaProfissoes.add(prof);
					linha = buff.readLine();
				}
				
				for(Profissao prof : listaProfissoes)
					this.adcionarProfissao(prof);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return false;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return false;
			}
		}
		
		return true;
	}
	@SuppressWarnings("static-access")
	public boolean importarCandidatos(){
		JFileChooser arquivo = new JFileChooser();
		int result = arquivo.showOpenDialog(null);
		List<Candidato> listaCandidatos = new ArrayList<Candidato>();
		String caminhoArquivo;
		String nome, dataCadastro, dataNascimento, arquivoDoCandidato;
		int avaliacao;
		Profissao profissao;
		
		if(result == arquivo.APPROVE_OPTION){
			caminhoArquivo = arquivo.getSelectedFile().getAbsolutePath();
			
			try {
				FileReader fileReader = new FileReader(caminhoArquivo);
				@SuppressWarnings("resource")
				BufferedReader buff = new BufferedReader(fileReader);
				
				nome = buff.readLine();
				while(nome != null){
					
					dataCadastro = buff.readLine();
					dataNascimento = buff.readLine();
					arquivoDoCandidato = buff.readLine();
					profissao = new Profissao(buff.readLine());
					avaliacao = Integer.valueOf(buff.readLine());
					Candidato cand = new Candidato(nome, dataCadastro, dataNascimento, arquivoDoCandidato, profissao, avaliacao);;
					listaCandidatos.add(cand);
					nome = buff.readLine();
				}
				
				for(Candidato cand : listaCandidatos)
					this.adcionarCandidato(cand);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return false;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return false;
			}
		}
		
		return true;
	}
}
