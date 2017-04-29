package userInterface;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.text.ParseException;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.MaskFormatter;

import dataBank.BancoDeDados;
import domain.Candidato;
import domain.Profissao;
import javax.swing.JFormattedTextField;
import util.AutoCompletion;
import util.StarRater;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JCheckBox;
import java.awt.Toolkit;

public class GerenciarCandidato {
	
	private JFrame framePrincipal;
	private JPanel panelPrincipal;
	private JTable tableCandidatos;
	private JScrollPane scrollPane;
	private JTextField textField;
	private JButton btnEditar;
	private JButton btnSalvar;
	private JButton btnDeletar;
	private Candidato candVelho;
	private JFormattedTextField formattedTextFieldData;
	private JComboBox<String> comboBox;
	private StarRater starRater;
	private int indiceProfissao;
	private JTextField textFieldArquivo;
	private JButton btnArquivo;
	private int avaliacao;
	private JTextField textFieldBuscarNome;
	private JCheckBox checkBox;
	private JCheckBox checkBox_1;
	private JComboBox<String> comboBox_1;
	private int checkBoxFlag=0;
	private int checkBoxFlag_1=0;
	
	public GerenciarCandidato(BancoDeDados banco) {
		framePrincipal = new JFrame();
		framePrincipal.setIconImage(Toolkit.getDefaultToolkit().getImage(GerenciarCandidato.class.getResource("/util/scheffelrhicone.png")));
		framePrincipal.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		framePrincipal.setTitle("Gerenciar Candidatos");
		framePrincipal.setResizable(false);
		framePrincipal.setSize(620, 483);
		
		panelPrincipal = new JPanel();
		panelPrincipal.setSize(100, 100);
		framePrincipal.getContentPane().add(panelPrincipal);
		panelPrincipal.setLayout(null);
		
		tableCandidatos = new JTable();
		tableCandidatos.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"Nome", "Data de Nascimento", "Profissão", "Avaliação"
			}
		));
		tableCandidatos.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);
		scrollPane = new JScrollPane(tableCandidatos);
		panelPrincipal.add(scrollPane);
		tableCandidatos.setFillsViewportHeight(true);
		scrollPane.setBounds(10, 107, 592, 188);
		tableCandidatos.addMouseListener(new java.awt.event.MouseAdapter() {

			@Override
		    public void mouseClicked(java.awt.event.MouseEvent evt) {
		        int row = tableCandidatos.rowAtPoint(evt.getPoint());
		        if (row >= 0) {
		           btnEditar.setEnabled(true);
		           desabilitarBotoes();
		           panelPrincipal.remove(starRater);
		           textField.setText((String)tableCandidatos.getValueAt(row, 0));
		           candVelho = banco.getCandidato(textField.getText());
		           formattedTextFieldData.setText((String)tableCandidatos.getValueAt(row, 1));
		           atualizarProfissoes(banco, (String)tableCandidatos.getValueAt(row, 2));
		           comboBox.setSelectedIndex(indiceProfissao);
		           textFieldArquivo.setText(candVelho.getArquivo());
		           starRater = new StarRater(5, 0, Integer.valueOf((String)tableCandidatos.getValueAt(row, 3)));
		           starRater.setBounds(384, 354, 92, 21);
		           avaliacao = candVelho.getAvaliacao();
		           starRater.addStarListener(
		       			new StarRater.StarListener() {
		       				@Override
		       				public void handleSelection(int selection) {
		       					avaliacao = selection;
		       				}
		       			});
		   		   panelPrincipal.add(starRater);
		   		   starRater.setEnabled(false);
		        }
		    }
		});
		
		textField = new JTextField();
		textField.setEnabled(false);
		textField.setBounds(10, 313, 234, 20);
		panelPrincipal.add(textField);
		textField.setColumns(10);
		
		btnEditar = new JButton("Editar");
		btnEditar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				editarCandidato(banco);
			}
		});
		btnEditar.setEnabled(false);
		btnEditar.setBounds(10, 387, 89, 23);
		panelPrincipal.add(btnEditar);
		
		btnSalvar = new JButton("Salvar");
		btnSalvar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				atualizarCandidato(banco);
			}
		});
		btnSalvar.setEnabled(false);
		btnSalvar.setBounds(114, 387, 89, 23);
		panelPrincipal.add(btnSalvar);
		
		btnDeletar = new JButton("Deletar");
		btnDeletar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				deletarCandidato(banco);
			}
		});
		btnDeletar.setEnabled(false);
		btnDeletar.setBounds(114, 420, 89, 23);
		panelPrincipal.add(btnDeletar);
		
		try {
			formattedTextFieldData = new JFormattedTextField(new MaskFormatter("##/##/####"));
			formattedTextFieldData.setEnabled(false);
			formattedTextFieldData.setColumns(10);
			formattedTextFieldData.setBounds(269, 354, 89, 20);
			panelPrincipal.add(formattedTextFieldData);
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		starRater = new StarRater();
		starRater.setBounds(384, 354, 92, 21);
		starRater.setEnabled(false);
		panelPrincipal.add(starRater);
		
		comboBox = new JComboBox<String>();
		comboBox.setEnabled(false);
		comboBox.setBounds(10, 355, 234, 21);
		AutoCompletion.enable(comboBox);
		panelPrincipal.add(comboBox);
		
		textFieldArquivo = new JTextField();
		textFieldArquivo.setEnabled(false);
		textFieldArquivo.setColumns(10);
		textFieldArquivo.setBounds(269, 313, 234, 20);
		panelPrincipal.add(textFieldArquivo);
		
		btnArquivo = new JButton("Arquivo");
		btnArquivo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				adcionarArquivo();
			}
		});
		btnArquivo.setEnabled(false);
		btnArquivo.setBounds(513, 312, 89, 23);
		panelPrincipal.add(btnArquivo);
		
		JLabel lblPrimeiroNome = new JLabel("Primeiro nome");
		lblPrimeiroNome.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblPrimeiroNome.setBounds(10, 21, 104, 17);
		panelPrincipal.add(lblPrimeiroNome);
		
		checkBox = new JCheckBox("");
		checkBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(checkBoxFlag == 0){
					checkBoxFlag = 1;
					textFieldBuscarNome.setEnabled(true);
				}else{
					checkBoxFlag = 0;
					textFieldBuscarNome.setEnabled(false);
				}
			}
		});
		checkBox.setBounds(106, 20, 21, 21);
		panelPrincipal.add(checkBox);
		
		JLabel lblProfisso = new JLabel("Profiss\u00E3o");
		lblProfisso.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblProfisso.setBounds(269, 17, 76, 17);
		panelPrincipal.add(lblProfisso);
		
		checkBox_1 = new JCheckBox("");
		checkBox_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(checkBoxFlag_1 == 0){
					checkBoxFlag_1 = 1;
					comboBox_1.setEnabled(true);
				}else{
					checkBoxFlag_1 = 0;
					checkBox_1.setSelected(false);
				}
			}
		});
		checkBox_1.setBounds(333, 16, 21, 21);
		panelPrincipal.add(checkBox_1);
		
		comboBox_1 = new JComboBox<String>();
		for(Profissao prof : banco.getListaProfissoes())
			comboBox_1.addItem(prof.getNome());
		comboBox_1.setEnabled(false);
		comboBox_1.setBounds(269, 45, 234, 21);
		AutoCompletion.enable(comboBox_1);
		panelPrincipal.add(comboBox_1);
		
		textFieldBuscarNome = new JTextField();
		textFieldBuscarNome.setBounds(10, 46, 234, 20);
		panelPrincipal.add(textFieldBuscarNome);
		textFieldBuscarNome.setColumns(10);
		textFieldBuscarNome.setEnabled(false);
		textFieldBuscarNome.addKeyListener(new KeyAdapter() {
			   public void keyTyped(KeyEvent e) {
				      char c = e.getKeyChar();
				      if ( c == KeyEvent.VK_SPACE) {
				         e.consume();  // ignore event
				      }
				   }
				});
		
		JButton btnBuscar = new JButton("Buscar");
		btnBuscar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				buscarCandidatos(banco);
			}
		});
		btnBuscar.setBounds(10, 77, 89, 23);
		panelPrincipal.add(btnBuscar);
		
		atualizarCandidatos(banco);
	}
	
	private void editarCandidato(BancoDeDados banco){
		btnEditar.setEnabled(false);
		btnSalvar.setEnabled(true);
		btnDeletar.setEnabled(true);
		btnArquivo.setEnabled(true);
		textField.setEnabled(true);
		textFieldArquivo.setEnabled(true);
		formattedTextFieldData.setEnabled(true);
		comboBox.setEnabled(true);
		starRater.setEnabled(true);
	}
	
	private void deletarCandidato(BancoDeDados banco){
		Candidato candidato = banco.getCandidato(textField.getText());
	
		if(banco.deletarCandidato(candidato)){
			atualizarCandidatos(banco);
			desabilitarBotoes();
			textField.setText("");
			textFieldArquivo.setText("");
			formattedTextFieldData.setText("");
			panelPrincipal.remove(starRater);
			starRater = new StarRater();
			starRater.setBounds(384, 354, 92, 21);
			panelPrincipal.add(starRater);
			starRater.setEnabled(false);
			comboBox.removeAllItems();
		}
	}
	
	private void atualizarCandidato(BancoDeDados banco){
		Candidato candNovo = new Candidato(textField.getText(), candVelho.getDataDeCadastro(), formattedTextFieldData.getText(), textFieldArquivo.getText(), new Profissao(comboBox.getSelectedItem().toString()), avaliacao); // NOVO - EDITAR AQUI
		
		if(banco.editarCandidato(candVelho, candNovo)){
			atualizarCandidatos(banco);
			desabilitarBotoes();
			textField.setText("");
			textFieldArquivo.setText("");
			formattedTextFieldData.setText("");
			panelPrincipal.remove(starRater);
			starRater = new StarRater();
			starRater.setBounds(384, 354, 92, 21);
			panelPrincipal.add(starRater);
			starRater.setEnabled(false);
			comboBox.removeAllItems();
		}
	}
	
	private void desabilitarBotoes(){
		btnSalvar.setEnabled(false);
		btnDeletar.setEnabled(false);
		btnArquivo.setEnabled(false);
		textField.setEnabled(false);
		formattedTextFieldData.setEnabled(false);
		comboBox.setEnabled(false);
		textFieldArquivo.setEnabled(false);
		starRater.setEnabled(false);
	}
	
	public void mostrarInterface(){
		framePrincipal.setVisible(true);
	}
	
	public void atualizarProfissoes(BancoDeDados banco, String profissao) {
		
		comboBox.removeAllItems();
		panelPrincipal.add(comboBox);
		int indice=0;
		
		for(Profissao obj : banco.getListaProfissoes()){
			comboBox.addItem(obj.getNome());
			if(obj.getNome().equals(profissao))
				indiceProfissao = indice;
			indice++;
		}
	}
	
	@SuppressWarnings("static-access")
	private void adcionarArquivo(){
		JFileChooser arquivo = new JFileChooser();
		int result = arquivo.showOpenDialog(null);
		String caminhoArquivo;
		
		if(result == arquivo.APPROVE_OPTION){
			caminhoArquivo = arquivo.getSelectedFile().getAbsolutePath();
			textFieldArquivo.setText(caminhoArquivo);
		}
	}
	
	public void atualizarCandidatos(BancoDeDados banco) {
		
		DefaultTableModel model;
		model = (DefaultTableModel) tableCandidatos.getModel();
		int rows = model.getRowCount();
		
		for (int i = rows - 1; i >= 0; i--) {
		    model.removeRow(i);
		}

		for(Candidato obj : banco.getListaCandidatos()){
			String[] linha = {obj.getNome(), obj.getDataDeNascimento(), obj.getProfissao().getNome(), String.valueOf(obj.getAvaliacao())};
			model.addRow(linha);
		}
	}
	
	private void atualizarTable(ArrayList<Candidato>candidatos){
		DefaultTableModel model;
		model = (DefaultTableModel) tableCandidatos.getModel();
		int rows = model.getRowCount();
		
		for (int i = rows - 1; i >= 0; i--) {
		    model.removeRow(i);
		}

		for(Candidato obj : candidatos){
			String[] linha = {obj.getNome(), obj.getDataDeNascimento(), obj.getProfissao().getNome(), String.valueOf(obj.getAvaliacao())};
			model.addRow(linha);
		}
	}
	
	private void buscarCandidatos(BancoDeDados banco){
		ArrayList<Candidato>candidatos = new ArrayList<Candidato>();
		
		if(checkBox.isSelected()){
			if(checkBox_1.isSelected()){
				candidatos = banco.getListaCandidatos(new Profissao((String)comboBox_1.getSelectedItem()), textFieldBuscarNome.getText());
				atualizarTable(candidatos);
			}else{
				candidatos = banco.getListaCandidatos(textFieldBuscarNome.getText());
				atualizarTable(candidatos);
			}
		}else{
			if(checkBox_1.isSelected()){
				candidatos = banco.getListaCandidatos(new Profissao((String)comboBox_1.getSelectedItem()));
				atualizarTable(candidatos);
			}else{
				candidatos = banco.getListaCandidatos();
				atualizarTable(candidatos);
			}
		}
	}
}
