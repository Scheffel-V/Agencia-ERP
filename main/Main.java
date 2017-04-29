package main;

import dataBank.BancoDeDados;
import userInterface.Principal;

public class Main {

	public static void main(String[] args) {
		BancoDeDados banco =  new BancoDeDados();
		Principal principal = new Principal(banco);
		principal.mostrarInterface();
	}

}
