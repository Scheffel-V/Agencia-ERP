package userInterface;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import dataBank.BancoDeDados;
import domain.Profissao;
import javax.swing.JTextField;
import java.awt.Toolkit;

public class GerenciarProfissoes {
	private JFrame framePrincipal;
	private JPanel panelPrincipal;
	private JTable tableProfissoes;
	private JScrollPane scrollPane;
	private JTextField textField;
	private JButton btnEditar;
	private JButton btnSalvar;
	private JButton btnDeletar;
	private JLabel lblNome;
	private Profissao profVelha;
	
	public GerenciarProfissoes(BancoDeDados banco) {
		
		framePrincipal = new JFrame();
		framePrincipal.setIconImage(Toolkit.getDefaultToolkit().getImage(GerenciarProfissoes.class.getResource("/util/scheffelrhicone.png")));
		framePrincipal.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		framePrincipal.setTitle("Gerenciar Profiss\u00F5es");
		framePrincipal.setResizable(false);
		framePrincipal.setSize(530, 249);
		
		panelPrincipal = new JPanel();
		panelPrincipal.setSize(100, 100);
		framePrincipal.getContentPane().add(panelPrincipal);
		panelPrincipal.setLayout(null);
		
		tableProfissoes = new JTable();
		tableProfissoes.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"Profissão",
			}
		));
		tableProfissoes.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);
		scrollPane = new JScrollPane(tableProfissoes);
		panelPrincipal.add(scrollPane);
		tableProfissoes.setFillsViewportHeight(true);
		scrollPane.setBounds(10, 11, 242, 199);
		tableProfissoes.addMouseListener(new java.awt.event.MouseAdapter() {
		    @Override
		    public void mouseClicked(java.awt.event.MouseEvent evt) {
		        int row = tableProfissoes.rowAtPoint(evt.getPoint());
		        if (row >= 0) {
		           btnEditar.setEnabled(true);
		           desabilitarBotoes();
		           textField.setText((String)tableProfissoes.getValueAt(row, 0));
		        }
		    }
		});
		
		lblNome = new JLabel("Nome:");
		lblNome.setEnabled(false);
		lblNome.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNome.setBounds(275, 39, 46, 14);
		panelPrincipal.add(lblNome);
		
		textField = new JTextField();
		textField.setEnabled(false);
		textField.setBounds(275, 64, 234, 20);
		panelPrincipal.add(textField);
		textField.setColumns(10);
		
		btnEditar = new JButton("Editar");
		btnEditar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				editarProfissao(banco);
			}
		});
		btnEditar.setEnabled(false);
		btnEditar.setBounds(275, 153, 89, 23);
		panelPrincipal.add(btnEditar);
		
		btnSalvar = new JButton("Salvar");
		btnSalvar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				atualizarProfissao(banco);
			}
		});
		btnSalvar.setEnabled(false);
		btnSalvar.setBounds(275, 187, 89, 23);
		panelPrincipal.add(btnSalvar);
		
		btnDeletar = new JButton("Deletar");
		btnDeletar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				deletarProfissao(banco);
			}
		});
		btnDeletar.setEnabled(false);
		btnDeletar.setBounds(374, 187, 89, 23);
		panelPrincipal.add(btnDeletar);
		
		atualizarProfissoes(banco);
	}
	
	private void editarProfissao(BancoDeDados banco){
		btnEditar.setEnabled(false);
		btnSalvar.setEnabled(true);
		btnDeletar.setEnabled(true);
		lblNome.setEnabled(true);
		textField.setEnabled(true);
		profVelha = banco.getProfissao(textField.getText());
	}
	
	private void deletarProfissao(BancoDeDados banco){
		Profissao profissao = banco.getProfissao(textField.getText());
	
		if(banco.deletarProfissao(profissao)){
			atualizarProfissoes(banco);
			desabilitarBotoes();
			textField.setText("");
		}
	}
	
	private void atualizarProfissao(BancoDeDados banco){
		Profissao profNova = new Profissao(textField.getText());
		
		if(banco.editarProfissao(profVelha, profNova)){
			atualizarProfissoes(banco);
			desabilitarBotoes();
			textField.setText("");
		}
	}
	
	private void desabilitarBotoes(){
		btnSalvar.setEnabled(false);
		btnDeletar.setEnabled(false);
		lblNome.setEnabled(false);
		textField.setEnabled(false);
	}
	
	public void mostrarInterface(){
		framePrincipal.setVisible(true);
	}
	
	public void atualizarProfissoes(BancoDeDados banco) {
		
		DefaultTableModel model;
		model = (DefaultTableModel) tableProfissoes.getModel();
		int rows = model.getRowCount();
		
		for (int i = rows - 1; i >= 0; i--) {
		    model.removeRow(i);
		}
		
		for(Profissao obj : banco.getListaProfissoes()){
			String[] linha = {obj.getNome()};
			model.addRow(linha);
		}
	}
}
