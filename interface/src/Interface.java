import java.awt.BorderLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.filechooser.FileNameExtensionFilter;

import types.TONrosSorteados;
import utilidades.ExtrairResultadosURL;
import br.com.megasena.sb.SBMegaSena;

public class Interface {
	
	JFrame frame = new JFrame("Gerador Mega sena");
	
	///panels	
	JPanel panelHome = new JPanel();
	// panels da barra panelSorteios	
	JPanel panelSorteiosItemListar = new JPanel();
	JPanel panelSorteiosItemExibir = new JPanel();
	JPanel panelSorteiosItemNovoSorteio = new JPanel();
	JPanel panelSorteiosItemEstatisticas = new JPanel();
	JPanel panelSorteiosItemFechar = new JPanel();
	//panels da barra panelMinhasApostas
	JPanel panelMinhasApostasItemCadastrarAposta = new JPanel();
	JPanel panelMinhasApostasItemVerificarNumerosAcertados = new JPanel();
	JPanel panelMinhasApostasItemListarNrosApostados = new JPanel();
	//panels da barra panelGerador
	JPanel panelGeradorItemGerador = new JPanel();
	JPanel panelGeradorItemCombinacaoDez = new JPanel();
	//panels da barra panelImportarDados
	JPanel panelImportarDadosItem = new JPanel();
	JPanel panelImportarDadoG1 = new JPanel();
	//panels da barra panelSair
	JPanel panelItemSair = new JPanel();
	
	//toolbar
	JToolBar toolbar = new JToolBar();
	//barra
	JMenuBar menuBar = new JMenuBar();
	//itens da barra
	JMenu menuSorteios = new JMenu("Sorteios");
	JMenu menuMinhasApostas = new JMenu("Minhas apostas");	
	JMenu menuGerador = new JMenu("Gerador de Números");
	JMenu menuImportarDados = new JMenu("Importar Dados");
	JMenu menuSair = new JMenu("Sair");	
	//itens da opção da barra menuSorteios	
	JMenuItem menuSorteiosItemListar = new JMenuItem("Listar Sorteios");
	JMenuItem menuSorteiosItemExibir = new JMenuItem("Exibir Sorteio (por nº sorteio)");
	JMenuItem menuSorteiosItemNovoSorteio = new JMenuItem("Cadastrar Novo Sorteio");
	JMenuItem menuSorteiosItemEstatisticas = new JMenuItem("Listar Estatísticas de Números");
	JMenuItem menuSorteiosItemFechar = new JMenuItem("Fechar");
	//itens da opção da barra menuMinhasApostas	
	JMenuItem menuMinhasApostasItemCadastrarAposta = new JMenuItem("Cadastrar Nova Aposta");
	JMenuItem menuMinhasApostasItemVerificarNumerosAcertados = new JMenuItem("Verificar Números Acertados");
	JMenuItem menuMinhasApostasItemListarNrosApostados = new JMenuItem("Listar Números Apostados");
	//itens da opção da barra menuGerador	
	JMenuItem menuGeradorItemGerador = new JMenuItem("Gerar");
	JMenuItem menuGeradorItemCombinacaoDez = new JMenuItem("Cria combinação com 10 dezenas");
	//itens da opção da barra menuImportarDados	
	JMenuItem menuImportarDadosItem = new JMenuItem("Importar Dados (.txt)");
	JMenuItem menuImportarDadoG1 = new JMenuItem("Importar último sorteio (G1)");
	//itens da opção da barra menuSair	
	JMenuItem menuItemSair = new JMenuItem("Sair");

	//Label
	JLabel labelSequenciaGerado = new JLabel("00-00-00-00-00-00");

	FileReader leitor = null;
	
	public void criaJanela() {
		ImageIcon img = new ImageIcon("C:\\img.gif");
		JLabel imgMoney = new JLabel(img, JLabel.CENTER);
		JLabel icon = new JLabel(img);
		
		//adiciona o JMenu no JMenuBar
		menuBar.add(icon);
		menuBar.add(menuSorteios);
		menuBar.add(menuMinhasApostas);
		menuBar.add(menuGerador);
		menuBar.add(menuImportarDados);
		menuBar.add(menuSair);
				
		//adicona os JMenuItem no JMenu
		menuSorteios.add(menuSorteiosItemListar);
		menuSorteios.add(menuSorteiosItemExibir);
		menuSorteios.add(menuSorteiosItemNovoSorteio);
		menuSorteios.add(menuSorteiosItemEstatisticas);
		menuSorteios.addSeparator();
		menuSorteios.add(menuSorteiosItemFechar);

		menuMinhasApostas.add(menuMinhasApostasItemCadastrarAposta );
		menuMinhasApostas.add(menuMinhasApostasItemVerificarNumerosAcertados );
		menuMinhasApostas.add(menuMinhasApostasItemListarNrosApostados );

		menuGerador.add(menuGeradorItemGerador );
		menuGerador.add(menuGeradorItemCombinacaoDez );
				
		menuImportarDados.add(menuImportarDadosItem);
		menuImportarDados.add(menuImportarDadoG1);
		
		menuSair.add(menuItemSair);

		//atribui um JMenuBar para o frame
		frame.setJMenuBar(menuBar); 

		// Itens Painel Gerar
		JButton botaoGerar = new JButton("Gerar sequência");

		
		// adiciona itens ao painel Gerar		
		panelGeradorItemGerador.add(botaoGerar);
		panelGeradorItemGerador.add(labelSequenciaGerado);
		
		// adiciona itens ao painel Home
		final JProgressBar progressBar = new JProgressBar();
		JButton botaoProgressBar = new JButton("Carregar");
		panelHome.add(progressBar);
		panelHome.add(botaoProgressBar);
		panelHome.add(imgMoney);
		
		
	// /menu Sorteio		
		// adiciona itens ao painel panelListarSorteios
		JLabel titulopanelListarSorteios = new JLabel("panelListarSorteios");
		panelSorteiosItemListar.add(titulopanelListarSorteios);
		// adiciona itens ao painel panelExibirSorteio
		JLabel titulopanelExibirSorteio = new JLabel("panelExibirSorteio");
		panelSorteiosItemExibir.add(titulopanelExibirSorteio);
		//Cadastrar novo sorteio
		JLabel nroSorteioLabel = new JLabel("Nro Soteio: ");
		final JTextField nroSorteioTextField = new JTextField(4);
		final JLabel dataLabel = new JLabel("Data: ");
		final JTextField dataTextField = new JTextField(8);
		final JLabel numerosLabel = new JLabel("Números: ");
		final JTextField numerosTextField = new JTextField(17);
		JButton cadastrarButton = new JButton("Cadastrar Sorteio");
		panelSorteiosItemNovoSorteio.add(nroSorteioLabel);
		panelSorteiosItemNovoSorteio.add(nroSorteioTextField);
		panelSorteiosItemNovoSorteio.add(dataLabel);
		panelSorteiosItemNovoSorteio.add(dataTextField);
		panelSorteiosItemNovoSorteio.add(numerosLabel);
		panelSorteiosItemNovoSorteio.add(numerosTextField);
		panelSorteiosItemNovoSorteio.add(cadastrarButton);
		
		//Listar estatisticas

		
	// /menu Minhas Apostas
	// /menu Gerador de números
	// /menu Importar Dados
		// adiciona itens ao painel panelImportarDadosItem
		final JFileChooser dadosChooser = new JFileChooser();
		dadosChooser.setFileFilter(new FileNameExtensionFilter("Apenas *.txt ou *.html", "txt", "html"));
		JLabel arquivoLabel = new JLabel("Escolha o Arquivo: ");
		final JLabel caminhoLabel = new JLabel(" ... ");
		JButton escolherButton = new JButton("Escolher");
		JButton imprimeDadosButton = new JButton("Imprime dados");
		final JTextArea dadosTextArea = new JTextArea("", 30, 50);
		JScrollPane scrollpane = new JScrollPane(dadosTextArea);
		scrollpane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollpane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		panelImportarDadosItem.add(arquivoLabel);
		panelImportarDadosItem.add(caminhoLabel);
		panelImportarDadosItem.add(escolherButton);
		panelImportarDadosItem.add(imprimeDadosButton);
		panelImportarDadosItem.add(scrollpane);

		// JPanel panelImportarDadoG1 = new JPanel();
		JButton botaoMostrarSorteio = new JButton("Mostrar");
		final JButton botaoInserirBanco = new JButton("Inserir sorteio no Banco de dados");
		botaoInserirBanco.setVisible(false);
		final JLabel dadosSorteioLabel = new JLabel("...");
		dadosSorteioLabel.setBounds(20, 50, 300, 300);
		panelImportarDadoG1.add(botaoMostrarSorteio);
		panelImportarDadoG1.add(dadosSorteioLabel);
		panelImportarDadoG1.add(botaoInserirBanco);
	// /Sair
		
		
		// Ações de abetura de tela
		///Menu Sorteios
		menuSorteiosItemListar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.setContentPane(panelSorteiosItemListar);
				frame.validate();
				frame.repaint();
			}
		});
		
		menuSorteiosItemExibir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.setContentPane(panelSorteiosItemExibir);
				frame.validate();
				frame.repaint();
			}
		});
		
		menuSorteiosItemNovoSorteio.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.setContentPane(panelSorteiosItemNovoSorteio);
				frame.validate();
				frame.repaint();				
			}
		});
		cadastrarButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int nro = Integer.parseInt(nroSorteioTextField.getText());
				String data = dataTextField.getText();
				String numeros = numerosTextField.getText();
				TONrosSorteados toNrosSorteados = new TONrosSorteados(nro, data, numeros);
				JOptionPane.showMessageDialog(null, new SBMegaSena().insereNrosSorteados(toNrosSorteados));
			}
		});	
		menuSorteiosItemFechar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		
		
		//menu Gerador de números
		menuGeradorItemGerador.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.setContentPane(panelGeradorItemGerador);
				frame.validate();
				frame.repaint();

			}
		});

		//menu Importar Dados
		menuImportarDadosItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.setContentPane(panelImportarDadosItem);
				frame.validate();
				frame.repaint();
			}
		});
		menuImportarDadoG1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.setContentPane(panelImportarDadoG1);
				frame.validate();
				frame.repaint();
			}
		});
		
		//ações de botoes
		menuItemSair.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		
		// adiciona acoes botoes de telas
		botaoGerar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Random gerador = new Random();
				int n1 = gerador.nextInt(60) + 1;
				int n2 = gerador.nextInt(60) + 1;
				int n3 = gerador.nextInt(60) + 1;
				int n4 = gerador.nextInt(60) + 1;
				int n5 = gerador.nextInt(60) + 1;
				int n6 = gerador.nextInt(60) + 1;
				labelSequenciaGerado.setText(n1 + "-" + n2 + "-" + n3 + "-" + n4 + "-" + n5 + "-" + n6);
			}
		});
		escolherButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int retorno = dadosChooser.showOpenDialog(null);
				leitor = null;
				if(retorno == JFileChooser.APPROVE_OPTION){
					caminhoLabel.setText("<html><font color='red'>"+dadosChooser.getName(dadosChooser.getSelectedFile())+"</font></html>");
					try {
						leitor = new FileReader(dadosChooser.getSelectedFile());
					} catch (FileNotFoundException e1) {
						e1.printStackTrace();
					}
				} else {
					caminhoLabel.setText(" ...");
					leitor = null;
				}
			}
		});
		
		imprimeDadosButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (leitor != null) {
					BufferedReader lerArq = new BufferedReader(leitor);
					StringBuffer linhaBuffer = new StringBuffer();
					String linha = null;
					try {
						// lê a primeira linha
						linha = lerArq.readLine();

						while (linha != null) {
							linhaBuffer.append(linha + "\n");
							linha = lerArq.readLine();
						}

						dadosTextArea.setText(linhaBuffer.toString());

						lerArq.close();

					} catch (IOException e1) {
						e1.printStackTrace();
					}

				}
			}
		});
		
		botaoProgressBar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				final Thread tBarra = new Thread() {
					public void run() {
						for (int j = 0; j <= 100; j++) {
							progressBar.setValue(j);
							try {
								Thread.sleep((int) (100 * Math.random()));
							} catch (InterruptedException e1) {
								e1.printStackTrace();
							}
						}
						JOptionPane.showMessageDialog(null, "Concluído!");
					}
				};

				tBarra.start();
			}
		});
		
		botaoMostrarSorteio.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				botaoInserirBanco.setVisible(true);
				dadosSorteioLabel.setText(
						"<html>Jogo: "+ExtrairResultadosURL.recuperaUltimoJogo(null, 1) + "<br>" +
						"Data: "+ExtrairResultadosURL.recuperaUltimoJogo(null, 2) + "<br>" +
						"Sequencia: <font color='red'>"+ExtrairResultadosURL.recuperaUltimoJogo(null, 3) + "</font></html>"
				);
			}
		});
		botaoInserirBanco.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				TONrosSorteados toNrosSorteados = new TONrosSorteados(Integer.parseInt(ExtrairResultadosURL.recuperaUltimoJogo(null, 1)), ExtrairResultadosURL.recuperaUltimoJogo(null, 2), ExtrairResultadosURL.recuperaUltimoJogo(null, 3)); 
				JOptionPane.showMessageDialog(null, new SBMegaSena().insereNrosSorteados(toNrosSorteados));
			}
		});

		// Configurações Frame
		frame.setResizable(false);
		frame.setSize(800, 600);
		frame.setLocation(Toolkit.getDefaultToolkit().getScreenSize().width / 2 - frame.getSize().width / 2, 
						  Toolkit.getDefaultToolkit().getScreenSize().height / 2 - frame.getSize().height / 2);
		
		frame.getContentPane().add(panelHome, BorderLayout.WEST);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}

	public static void main(String[] args) {
		JOptionPane.showMessageDialog(null, "Bem Vindo!");
		Interface interFace = new Interface();
		interFace.criaJanela();
	}
}