package userInterface;

import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Color;
import java.awt.Font;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.swing.JTextField;
import javax.swing.text.MaskFormatter;

import dataBank.BancoDeDados;
import domain.Candidato;
import domain.Profissao;
import util.AutoCompletion;
import util.StarRater;
import javax.swing.ImageIcon;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Toolkit;

public class NovoCandidato {

	private JFrame framePrincipal;
	private JPanel panelPrincipal;
	private JTextField textFieldNome;
	private JFormattedTextField textFieldNascimento;
	private JComboBox<String> comboBoxProfissoes;
	private int avaliacao = 0;
	private JLabel lblNovaProfisso;
	private JTextField textFieldArquivo;
	
	public NovoCandidato(BancoDeDados banco) {
		framePrincipal = new JFrame();
		framePrincipal.setIconImage(Toolkit.getDefaultToolkit().getImage(NovoCandidato.class.getResource("/util/scheffelrhicone.png")));
		framePrincipal.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		framePrincipal.setTitle("Novo Candidato");
		framePrincipal.setSize(314, 353);
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
		textFieldNome.setBounds(74, 28, 219, 20);
		panelPrincipal.add(textFieldNome);
		textFieldNome.setColumns(10);
		
		JLabel lblDataDeNascimento = new JLabel("Data de Nascimento");
		lblDataDeNascimento.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblDataDeNascimento.setBounds(164, 182, 144, 21);
		panelPrincipal.add(lblDataDeNascimento);
		
		try {
			textFieldNascimento = new JFormattedTextField(new MaskFormatter("##/##/####"));
			textFieldNascimento.setBounds(162, 207, 86, 20);
			panelPrincipal.add(textFieldNascimento);
			textFieldNascimento.setColumns(10);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		JLabel lblProfisso = new JLabel("Profiss\u00E3o");
		lblProfisso.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblProfisso.setBounds(10, 58, 76, 21);
		panelPrincipal.add(lblProfisso);
		
		comboBoxProfissoes = new JComboBox<String>();
		comboBoxProfissoes.setBounds(74, 59, 219, 21);
		AutoCompletion.enable(comboBoxProfissoes);
		panelPrincipal.add(comboBoxProfissoes);
		atualizarProfissoes(banco);
		
		JLabel lblAvaliao = new JLabel("Avalia\u00E7\u00E3o");
		lblAvaliao.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblAvaliao.setBounds(10, 182, 76, 21);
		panelPrincipal.add(lblAvaliao);
		
		StarRater starRater = new StarRater();
		starRater.setBounds(10, 207, 92, 21);
		starRater.addStarListener(
			new StarRater.StarListener() {
				@Override
				public void handleSelection(int selection) {
					avaliacao = selection;
				}
			});
		panelPrincipal.add(starRater);
		
		JButton button = new JButton("");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		button.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent arg0) {
				button.setIcon(new ImageIcon(NovoCandidato.class.getResource("/util/add-user(2).png")));
			}
			@Override
			public void mouseExited(MouseEvent arg0) {
				button.setIcon(new ImageIcon(NovoCandidato.class.getResource("/util/add-user(1).png")));
			}
			@Override
			public void mousePressed(MouseEvent arg0) {
				button.setIcon(new ImageIcon(NovoCandidato.class.getResource("/util/add-user(3).png")));
			}
			@Override
			public void mouseReleased(MouseEvent arg0) {
				button.setIcon(new ImageIcon(NovoCandidato.class.getResource("/util/add-user(2).png")));
				adcionarCandidato(banco);
			}
		});
		button.setBorder(null);
		button.setContentAreaFilled(false);
		button.setBorderPainted(false);
		button.setFocusPainted(false);
		button.setIcon(new ImageIcon(NovoCandidato.class.getResource("/util/add-user(1).png")));
		button.setBounds(116, 245, 64, 73);
		panelPrincipal.add(button);
		
		lblNovaProfisso = new JLabel("Nova Profiss\u00E3o");
		lblNovaProfisso.setForeground(new Color(0, 0, 0));
		lblNovaProfisso.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent arg0) {
				lblNovaProfisso.setForeground(new Color(255, 215, 0));
			}
			@Override
			public void mouseExited(MouseEvent arg0) {
				lblNovaProfisso.setForeground(Color.BLACK);
			}
			@Override
			public void mouseReleased(MouseEvent arg0) {
				novaProfissao(banco);
			}
		});
		lblNovaProfisso.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblNovaProfisso.setBounds(74, 85, 109, 21);
		panelPrincipal.add(lblNovaProfisso);
		
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
		label.setBounds(179, 87, 16, 17);
		panelPrincipal.add(label);
		
		JButton btnAdcionarArquivo = new JButton("Adcionar Arquivo");
		btnAdcionarArquivo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				adcionarArquivo();
			}
		});
		btnAdcionarArquivo.setBounds(74, 117, 152, 23);
		panelPrincipal.add(btnAdcionarArquivo);
		
		textFieldArquivo = new JTextField();
		textFieldArquivo.setEditable(false);
		textFieldArquivo.setBounds(10, 151, 283, 20);
		panelPrincipal.add(textFieldArquivo);
		textFieldArquivo.setColumns(10);
	}
	
	public void mostrarInterface(){
		framePrincipal.setVisible(true);
	}
	
	private void novaProfissao(BancoDeDados banco){
		NovaProfissao novaProfissao = new NovaProfissao(banco);
		novaProfissao.mostrarInterface();
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
	
	private void adcionarCandidato(BancoDeDados banco){
		String nome = getNome();
		String data = getDataNascimento();
		String arquivo = getArquivo();
		
		if(arquivo.trim().isEmpty())
			arquivo = "Curriculum não informado.";
		
		if(data.equals("  /  /    ")){
			JOptionPane.showMessageDialog(null, "Informe uma data válida!");
		}else{
			if(comboBoxProfissoes.getSelectedItem() == null){
				JOptionPane.showMessageDialog(null, "Informe uma profissão válida!");
			}else{
				Profissao profissao = banco.getProfissao(comboBoxProfissoes.getSelectedItem().toString());
				String timeStamp = new SimpleDateFormat("dd/MM/yyyy").format(Calendar.getInstance().getTime());
				
				if(nome.trim().isEmpty()){
					JOptionPane.showMessageDialog(null, "Informe um nome válido!");
				}else{
					System.out.println(arquivo);
					Candidato candidato = new Candidato(nome, timeStamp , data, arquivo, profissao, avaliacao);
					if(banco.adcionarCandidato(candidato)){
						JOptionPane.showMessageDialog(null, "Adcionado com sucesso!");
						framePrincipal.dispose();
					}
				}
			}	
		}
	}
	
	private String getNome(){
		return textFieldNome.getText();
	}
	
	private String getDataNascimento(){
		return textFieldNascimento.getText();
	}
	
	private String getArquivo(){
		return textFieldArquivo.getText();
	}
	
	public void atualizarProfissoes(BancoDeDados banco) {
		
		comboBoxProfissoes.removeAllItems();
		panelPrincipal.add(comboBoxProfissoes);
		
		for(Profissao obj : banco.getListaProfissoes()){
			comboBoxProfissoes.addItem(obj.getNome());
		}
	}
}
