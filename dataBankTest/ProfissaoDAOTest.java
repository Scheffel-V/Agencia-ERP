package dataBankTest;

import static org.junit.Assert.*;

import org.junit.Test;

import dataBank.ProfissaoDAO;
import domain.Profissao;

public class ProfissaoDAOTest {

	@Test
	public void testCreate() {
		Profissao profissao = new Profissao("Financeiro");
		ProfissaoDAO profDAO = new ProfissaoDAO();
		
		if(profDAO.create(profissao)){
			System.out.println("Salvo!");
		}else{
			fail("Erro!");
		}
	}

}
