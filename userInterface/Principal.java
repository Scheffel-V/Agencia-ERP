package userInterface;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import util.AutoCompletion;
import util.StarRater;

import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JLabel;

import java.awt.Desktop;
import java.awt.Font;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.MaskFormatter;

import dataBank.BancoDeDados;
import domain.Candidato;
import domain.Profissao;

import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.awt.event.ActionEvent;
import javax.swing.JCheckBox;
import java.io.File;
import java.io.IOException;
import javax.swing.border.BevelBorder;
import java.awt.Color;
import java.awt.Toolkit;

public class Principal {

	private JFrame framePrincipal;
	private JPanel panelPrincipal;
	private JComboBox<String> comboBoxProfissoes;
	private JTable tableCandidatos;
	private JScrollPane scrollPane;
	private JFormattedTextField textFieldIdadeMin;
	private JFormattedTextField textFieldIdadeMax;
	private JLabel lblMinima;
	private JLabel lblMaxima;
	private boolean checkBoxIdade=false;
	private boolean checkBoxAvaliacao=false;
	private int avaliacao;
	private StarRater starRater;
	private JButton btnAbrir;
	
	public Principal(BancoDeDados banco) {
		
		framePrincipal = new JFrame();
		framePrincipal.setIconImage(Toolkit.getDefaultToolkit().getImage(Principal.class.getResource("/util/scheffelrhicone.png")));
		framePrincipal.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		framePrincipal.setTitle("SCHEFFEL RH");
		framePrincipal.setResizable(false);
		framePrincipal.setSize(655, 493);
		
		panelPrincipal = new JPanel();
		panelPrincipal.setSize(100, 100);
		framePrincipal.getContentPane().add(panelPrincipal);
		panelPrincipal.setLayout(null);
		
		comboBoxProfissoes = new JComboBox<String>();
		comboBoxProfissoes.setBounds(10, 83, 219, 21);
		AutoCompletion.enable(comboBoxProfissoes);
		panelPrincipal.add(comboBoxProfissoes);
		atualizarProfissoes(banco);
		
		tableCandidatos = new JTable();
		tableCandidatos.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"Candidato", "Profissão", "Avaliação" ,"Idade", "Cadastro"
			}
		));
		tableCandidatos.getColumnModel().getColumn(0).setPreferredWidth(100);
		tableCandidatos.getColumnModel().getColumn(1).setPreferredWidth(100);
		tableCandidatos.getColumnModel().getColumn(2).setPreferredWidth(40);
		tableCandidatos.getColumnModel().getColumn(3).setPreferredWidth(40);
		tableCandidatos.getColumnModel().getColumn(4).setPreferredWidth(40);
		tableCandidatos.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);
		tableCandidatos.addMouseListener(new java.awt.event.MouseAdapter(){
			public void mouseClicked(java.awt.event.MouseEvent evt) {
				btnAbrir.setEnabled(true);
			}
		});
		scrollPane = new JScrollPane(tableCandidatos);
		panelPrincipal.add(scrollPane);
		tableCandidatos.setFillsViewportHeight(true);
		scrollPane.setBounds(10, 166, 627, 262);		
		
		
		JLabel lblSelecionarAProfisso = new JLabel("Selecionar a profiss\u00E3o");
		lblSelecionarAProfisso.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblSelecionarAProfisso.setBounds(10, 55, 149, 17);
		panelPrincipal.add(lblSelecionarAProfisso);
		
		JButton btnProcurar = new JButton("Procurar");
		btnProcurar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				procurarCandidatos(banco);
			}
		});
		btnProcurar.setBounds(10, 132, 89, 23);
		panelPrincipal.add(btnProcurar);
		
		JLabel lblIdade = new JLabel("Faixa de Idade");
		lblIdade.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblIdade.setBounds(260, 58, 104, 17);
		panelPrincipal.add(lblIdade);
		
		JCheckBox checkBox = new JCheckBox("");
		checkBox.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					if(checkBoxIdade == false){
						checkBoxIdade = true;
						lblMinima.setEnabled(true);
						lblMaxima.setEnabled(true);
						textFieldIdadeMax.setEnabled(true);
						textFieldIdadeMin.setEnabled(true);
						}else{
							checkBoxIdade = false;
							lblMinima.setEnabled(false);
							lblMaxima.setEnabled(false);
							textFieldIdadeMax.setEnabled(false);
							textFieldIdadeMin.setEnabled(false);		
							}
					}
			});
		checkBox.setBounds(356, 57, 21, 21);
		panelPrincipal.add(checkBox);
			
		try {
			textFieldIdadeMin = new JFormattedTextField(new MaskFormatter("##"));
			textFieldIdadeMin.setEnabled(false);
			textFieldIdadeMin.setBounds(314, 87, 31, 20);
			panelPrincipal.add(textFieldIdadeMin);
			textFieldIdadeMin.setColumns(10);
			textFieldIdadeMax = new JFormattedTextField(new MaskFormatter("##"));
			textFieldIdadeMax.setEnabled(false);
			textFieldIdadeMax.setColumns(10);
			textFieldIdadeMax.setBounds(314, 118, 31, 20);
			panelPrincipal.add(textFieldIdadeMax);
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		lblMinima = new JLabel("M\u00EDnima");
		lblMinima.setEnabled(false);
		lblMinima.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblMinima.setBounds(260, 89, 104, 17);
		panelPrincipal.add(lblMinima);
		
		lblMaxima = new JLabel("M\u00E1xima");
		lblMaxima.setEnabled(false);
		lblMaxima.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblMaxima.setBounds(260, 118, 104, 17);
		panelPrincipal.add(lblMaxima);
		
		JLabel lblAvaliao = new JLabel("Avalia\u00E7\u00E3o");
		lblAvaliao.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblAvaliao.setBounds(396, 58, 76, 17);
		panelPrincipal.add(lblAvaliao);
		
		JCheckBox checkBox_1 = new JCheckBox("");
		checkBox_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(checkBoxAvaliacao == false){
					checkBoxAvaliacao = true;
					starRater.setEnabled(true);
					}else{
						checkBoxAvaliacao = false;
						starRater.setEnabled(false);
					}
			}
		});
		checkBox_1.setBounds(460, 57, 21, 21);
		panelPrincipal.add(checkBox_1);
		
		JMenuBar menuBar = new JMenuBar();
		menuBar.setBackground(new Color(204, 204, 255));
		framePrincipal.setJMenuBar(menuBar);
		
		JMenu mnNewMenu = new JMenu("Arquivo");
		menuBar.add(mnNewMenu);
		
		
		JMenu mnNewMenu_2 = new JMenu("Exportar");
		mnNewMenu_2.setIcon(new ImageIcon(Principal.class.getResource("/util/export.png")));
		mnNewMenu.add(mnNewMenu_2);
		
		JMenuItem mntmNewMenuItem = new JMenuItem("Candidatos");
		mntmNewMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				banco.exportarCandidatos();
			}
		});
		mnNewMenu_2.add(mntmNewMenuItem);
		
		JMenuItem mntmPrifisses = new JMenuItem("Profiss\u00F5es");
		mntmPrifisses.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				banco.exportarProfissoes();
			}
		});
		mnNewMenu_2.add(mntmPrifisses);
		
		JMenu mnImportar = new JMenu("Importar");
		mnImportar.setIcon(new ImageIcon(Principal.class.getResource("/util/import.png")));
		mnNewMenu.add(mnImportar);
		mnNewMenu.addSeparator();
		
		JMenuItem mntmNewMenuItem_1 = new JMenuItem("Candidatos");
		mntmNewMenuItem_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				banco.importarCandidatos();
			}
		});
		mnImportar.add(mntmNewMenuItem_1);
		
		JMenuItem mntmNewMenuItem_2 = new JMenuItem("Profiss\u00F5es");
		mntmNewMenuItem_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				banco.importarProfissoes();
			}
		});
		mnImportar.add(mntmNewMenuItem_2);
		
		JMenuItem mntmSair = new JMenuItem("Sair");
		mntmSair.setIcon(new ImageIcon(Principal.class.getResource("/util/logout.png")));
		mntmSair.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				framePrincipal.dispose();
			}
		});
		mnNewMenu.add(mntmSair);
		
		JMenu mnNewMenu_1 = new JMenu("Candidatos");
		menuBar.add(mnNewMenu_1);
		
		JMenuItem mntmNovoCandidato = new JMenuItem("Novo Candidato");
		mntmNovoCandidato.setIcon(new ImageIcon(Principal.class.getResource("/util/add-user-button(8).png")));
		mntmNovoCandidato.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				NovoCandidato novoCandidato = new NovoCandidato(banco);
				novoCandidato.mostrarInterface();
			}
		});
		mnNewMenu_1.add(mntmNovoCandidato);
		
		JMenuItem mntmGerenciarCandidatos = new JMenuItem("Gerenciar Candidatos");
		mntmGerenciarCandidatos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				GerenciarCandidato gerenciarCandidato = new GerenciarCandidato(banco);
				gerenciarCandidato.mostrarInterface();
			}
		});
		mntmGerenciarCandidatos.setIcon(new ImageIcon(Principal.class.getResource("/util/user-edit-.png")));
		mnNewMenu_1.add(mntmGerenciarCandidatos);
		
		JMenu mnProfisses = new JMenu("Profiss\u00F5es");
		menuBar.add(mnProfisses);
		
		JMenuItem mntmNovaProfisso = new JMenuItem("Nova Profiss\u00E3o");
		mntmNovaProfisso.setIcon(new ImageIcon(Principal.class.getResource("/util/add-group-button(8).png")));
		mntmNovaProfisso.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				NovaProfissao novaProfissao = new NovaProfissao(banco);
				novaProfissao.mostrarInterface();
			}
		});
		mnProfisses.add(mntmNovaProfisso);
		
		JMenuItem mntmGerenciarProfisses = new JMenuItem("Gerenciar Profiss\u00F5es");
		mntmGerenciarProfisses.setIcon(new ImageIcon(Principal.class.getResource("/util/edit-group-button.png")));
		mntmGerenciarProfisses.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				GerenciarProfissoes gerProf = new GerenciarProfissoes(banco);
				gerProf.mostrarInterface();
			}
		});
		mnProfisses.add(mntmGerenciarProfisses);
		
		JMenu mnAjuda = new JMenu("Ajuda");
		menuBar.add(mnAjuda);
		
		JMenuItem mntmSobre = new JMenuItem("Sobre");
		mntmSobre.setIcon(new ImageIcon(Principal.class.getResource("/util/icon.png")));
		mntmSobre.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(null, "Vinícius Scheffel\nvbscheffel@inf.ufrgs.br\n18/02/2017\nVersão 1.5.0");
			}
		});
		mnAjuda.add(mntmSobre);
		
		starRater = new StarRater();
		starRater.setEnabled(false);
		starRater.setBounds(396, 83, 92, 21);
		starRater.addStarListener(
			new StarRater.StarListener() {
				@Override
				public void handleSelection(int selection) {
					avaliacao = selection;
				}
			});
		panelPrincipal.add(starRater);
		
		btnAbrir = new JButton("Abrir");
		btnAbrir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				abrirArquivo(banco);
			}
		});
		btnAbrir.setEnabled(false);
		btnAbrir.setBounds(109, 132, 89, 23);
		panelPrincipal.add(btnAbrir);
		
		JLabel label = new JLabel("");
		label.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent arg0) {
				label.setIcon(new ImageIcon(NovoCandidato.class.getResource("/util/refresh-page-option(2).png")));
			}
			@Override
			public void mouseExited(MouseEvent e) {
				label.setIcon(new ImageIcon(NovoCandidato.class.getResource("/util/refresh-page-option.png")));
			}
			@Override
			public void mouseReleased(MouseEvent e) {
				atualizarProfissoes(banco);
			}
		});
		label.setIcon(new ImageIcon(NovoCandidato.class.getResource("/util/refresh-page-option.png")));
		label.setBounds(10, 107, 16, 17);
		panelPrincipal.add(label);
		
		JPanel panel = new JPanel();
		panel.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		panel.setBounds(0, 0, 649, 44);
		panelPrincipal.add(panel);
		panel.setLayout(null);
		
		JLabel label_1 = new JLabel("");
		label_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent arg0) {
				label_1.setIcon(new ImageIcon(Principal.class.getResource("/util/add-user-button(7).png")));
			}
			@Override
			public void mouseExited(MouseEvent e) {
				label_1.setIcon(new ImageIcon(Principal.class.getResource("/util/add-user-button(6).png")));
			}
			@Override
			public void mouseReleased(MouseEvent e) {
				NovoCandidato novoCandidato = new NovoCandidato(banco);
				novoCandidato.mostrarInterface();
			}
		});
		label_1.setBounds(10, 4, 35, 33);
		panel.add(label_1);
		label_1.setIcon(new ImageIcon(Principal.class.getResource("/util/add-user-button(6).png")));
		
		JLabel label_2 = new JLabel("");
		label_2.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				label_2.setIcon(new ImageIcon(Principal.class.getResource("/util/add-group-button(5).png")));
			}
			@Override
			public void mouseExited(MouseEvent e) {
				label_2.setIcon(new ImageIcon(Principal.class.getResource("/util/add-group-button(4).png")));
			}
			@Override
			public void mouseReleased(MouseEvent e) {
				NovaProfissao novaProfissao = new NovaProfissao(banco);
				novaProfissao.mostrarInterface();
			}
		});
		label_2.setBounds(48, 6, 35, 33);
		panel.add(label_2);
		label_2.setIcon(new ImageIcon(Principal.class.getResource("/util/add-group-button(4).png")));
		
		
	}
	
	public void atualizarProfissoes(BancoDeDados banco) {
		
		comboBoxProfissoes.removeAllItems();
		panelPrincipal.add(comboBoxProfissoes);
		
		for(Profissao obj : banco.getListaProfissoes()){
			comboBoxProfissoes.addItem(obj.getNome());
		}
	}
	
	public void atualizarCandidatos(ArrayList<Candidato> candidatos) {
		
		DefaultTableModel model;
		model = (DefaultTableModel) tableCandidatos.getModel();
		int rows = model.getRowCount();
		
		for (int i = rows - 1; i >= 0; i--) {
		    model.removeRow(i);
		}
		
		for(Candidato obj : candidatos){
			String[] linha = {obj.getNome(), obj.getProfissao().getNome(), String.valueOf(obj.getAvaliacao()),
					String.valueOf(calcularIdade(obj.getDataDeNascimento())), obj.getDataDeCadastro()};
			model.addRow(linha);
		}
	}
	
	private void procurarCandidatos(BancoDeDados banco) {
		ArrayList<Candidato> candidatos = new ArrayList<Candidato>();
		Profissao prof = new Profissao(comboBoxProfissoes.getSelectedItem().toString());
		if(checkBoxIdade == true){
			if(textFieldIdadeMin.getText().trim().isEmpty() || textFieldIdadeMax.getText().trim().isEmpty()){
				JOptionPane.showMessageDialog(null, "Informe o intervalo de idade!");
			}else{
				if(checkBoxAvaliacao == true){
					candidatos = banco.getListaCandidatos(prof, Integer.valueOf(textFieldIdadeMin.getText()), Integer.valueOf(textFieldIdadeMax.getText()), avaliacao);
					atualizarCandidatos(candidatos);
				}else{
					candidatos = banco.getListaCandidatos(prof, Integer.valueOf(textFieldIdadeMin.getText()), Integer.valueOf(textFieldIdadeMax.getText()));
					atualizarCandidatos(candidatos);
				}
			}
		}else{
			if(checkBoxAvaliacao == true){
				candidatos = banco.getListaCandidatos(prof, avaliacao);
				atualizarCandidatos(candidatos);
			}else{
				candidatos = banco.getListaCandidatos(prof);
				atualizarCandidatos(candidatos);
			}
		}
	}
	
	private int calcularIdade(String nascimento){
		String timeStamp = new SimpleDateFormat("yyyyMMdd").format(Calendar.getInstance().getTime());
		String strAnoAtual = timeStamp.substring(0, 4);
		String strAnoNascimento = nascimento.substring(6, 10);
		int anoAtual = Integer.valueOf(strAnoAtual);
		int anoNascimento = Integer.valueOf(strAnoNascimento);
	
		return (anoAtual - anoNascimento);
	}
	
	private void abrirArquivo(BancoDeDados banco){
		int row = tableCandidatos.getSelectedRow();
		String candidatoNome = (String)tableCandidatos.getValueAt(row, 0);
		String profissao = (String)tableCandidatos.getValueAt(row, 1);
		String arquivo = null;
		
		for(Candidato c : banco.getListaCandidatos(new Profissao(profissao))){
			if(c.getNome().equals(candidatoNome)){
				arquivo = c.getArquivo();
				break;
			}
		}
		
		if(arquivo != null && !arquivo.trim().isEmpty() && !arquivo.equals("Curriculum não informado.")){
			 try {
			     if (Desktop.isDesktopSupported()) {
			       Desktop.getDesktop().open(new File(arquivo));
			     }
			   } catch (IOException ioe) {
			     ioe.printStackTrace();
			  }	
		}else{
			JOptionPane.showMessageDialog(null, "Candidato não possui curriculum cadastrado!");
		}
	}
	
	public void mostrarInterface(){
		framePrincipal.setVisible(true);
	}
}
