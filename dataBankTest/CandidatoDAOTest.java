package dataBankTest;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import dataBank.CandidatoDAO;
import domain.Candidato;
import domain.Profissao;

public class CandidatoDAOTest {

	@Test
	public void testCreate() {
		Profissao profissao = new Profissao("Teste");
		Candidato candidato = new Candidato("Vinicius", "22/02/2017", "24/02/1997", null, profissao, 4);
		CandidatoDAO candDAO = new CandidatoDAO();
		
		if(candDAO.create(candidato)){
			System.out.println("Salvo!");
		}else{
			fail("Erro!");
		}
		
	}
	

	@Test
	public void testFindAll(){
		List<Candidato> candidatos = new ArrayList<>();
		CandidatoDAO candDAO = new CandidatoDAO();
		
		candidatos = candDAO.findAll();
		
		if(candidatos == null){
			fail("Erro!");
		}else if(candidatos.isEmpty()){
			fail("Erro!");
		}else{
			System.out.println("Busca correta!");
		}
	}

}
