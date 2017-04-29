package domain;

public class Candidato {

	private String nome;
	private String dataDeCadastro;
	private String dataDeNascimento;
	private String arquivo;
	private Profissao profissao;
	private int avaliacao;
	private int ID;
	
	public Candidato(String nome, String dataDeCadastro, String dataDeNascimento, String arquivo, Profissao profissao, int avaliacao) {
		this.nome = nome;
		this.dataDeCadastro = dataDeCadastro;
		this.dataDeNascimento = dataDeNascimento;
		this.arquivo = arquivo;
		this.profissao = profissao;
		this.avaliacao = avaliacao;
	}
	
	public String getNome() {
		return nome;
	}
	
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public String getDataDeCadastro() {
		return dataDeCadastro;
	}
	
	public void setDataDeCadastro(String dataDeCadastro) {
		this.dataDeCadastro = dataDeCadastro;
	}
	
	public String getDataDeNascimento() {
		return dataDeNascimento;
	}
	
	public void setDataDeNascimento(String dataDeNascimento) {
		this.dataDeNascimento = dataDeNascimento;
	}
	
	public String getArquivo(){
		return arquivo;
	}
	
	public void setArquivo(String arquivo){
		this.arquivo = arquivo;
	}
	
	public Profissao getProfissao() {
		return profissao;
	}
	
	public void setProfissao(Profissao profissao) {
		this.profissao = profissao;
	}
	
	public int getAvaliacao() {
		return avaliacao;
	}
	
	public void setAvaliacao(int avaliacao) {
		this.avaliacao = avaliacao;
	}
	
	public int getID() {
		return ID;
	}
}
