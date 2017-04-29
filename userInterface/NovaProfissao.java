package userInterface;

import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import dataBank.BancoDeDados;
import domain.Profissao;
import java.awt.Toolkit;

public class NovaProfissao {
	private JFrame framePrincipal;
	private JPanel panelPrincipal;
	private JTextField textFieldNome;
	
	public NovaProfissao(BancoDeDados banco) {
		framePrincipal = new JFrame();
		framePrincipal.setIconImage(Toolkit.getDefaultToolkit().getImage(NovaProfissao.class.getResource("/util/scheffelrhicone.png")));
		framePrincipal.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		framePrincipal.setTitle("Nova Profissao");
		framePrincipal.setSize(289, 185);
		framePrincipal.setResizable(false);
		
		panelPrincipal = new JPanel();
		panelPrincipal.setSize(100, 100);
		framePrincipal.getContentPane().add(panelPrincipal);
		panelPrincipal.setLayout(null);
		
		JLabel lblNome = new JLabel("Nome");
		lblNome.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNome.setBounds(10, 26, 56, 21);
		panelPrincipal.add(lblNome);
		
		textFieldNome = new JTextField();
		textFieldNome.setBounds(59, 28, 197, 20);
		panelPrincipal.add(textFieldNome);
		textFieldNome.setColumns(10);
		
		JButton button = new JButton("");
		button.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent arg0) {
				button.setIcon(new ImageIcon(NovaProfissao.class.getResource("/util/add-group-button(1).png")));
			}
			@Override
			public void mouseExited(MouseEvent arg0) {
				button.setIcon(new ImageIcon(NovaProfissao.class.getResource("/util/add-group-button.png")));
			}
			@Override
			public void mousePressed(MouseEvent arg0) {
				button.setIcon(new ImageIcon(NovaProfissao.class.getResource("/util/add-group-button(2).png")));
			}
			@Override
			public void mouseReleased(MouseEvent arg0) {
				button.setIcon(new ImageIcon(NovaProfissao.class.getResource("/util/add-group-button(1).png")));
				adcionarProfissao(banco);
			}
		});
		button.setBorder(null);
		button.setContentAreaFilled(false);
		button.setBorderPainted(false);
		button.setFocusPainted(false);
		button.setIcon(new ImageIcon(NovaProfissao.class.getResource("/util/add-group-button.png")));
		button.setBounds(100, 75, 74, 73);
		panelPrincipal.add(button);
	}
	
	public void mostrarInterface(){
		framePrincipal.setVisible(true);
	}
	
	private void adcionarProfissao(BancoDeDados banco){
		String nome = getNome();
		if(nome.trim().isEmpty()){
			JOptionPane.showMessageDialog(null, "Nome Inválido!");
		}else{
			Profissao profissao = new Profissao(nome);
			if(banco.adcionarProfissao(profissao)){
				JOptionPane.showMessageDialog(null, "Profissão Adcionada!");
				dispose();
			}
		}
	}
	
	private String getNome(){
		return textFieldNome.getText();
	}
	
	private void dispose(){
		framePrincipal.dispose();
	}
}
