import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JButton;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.border.TitledBorder;

import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.InvalidRemoteException;
import org.eclipse.jgit.api.errors.TransportException;

import javax.swing.UIManager;
import java.awt.Color;
import javax.swing.JProgressBar;

public class Main {

	private JFrame frmCriadorDeEntidades;
	private JTextField pathProjeto;
	private Creator criadorEntidade;
	private Config config;
	private Util util =  new Util();
	private JTextField nomeEntidadeDeletar;
	private JTextArea log = new JTextArea();
	private JTextField moduloAngular;
	private JTextField nomeEntidade;
	private JTextField menuLabel;
	private JTextField principalAttr;
	private JTextField principalLabel;
	private JTextField principalIcon;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {




		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Main window = new Main();
					window.criadorEntidade = new Creator();
					window.criadorEntidade.define(window.pathProjeto.getText(), window.moduloAngular.getText());
					window.frmCriadorDeEntidades.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Main() {
		initialize();

		try {
			config = util.lerConfig();
			pathProjeto.setText(config.getPathProject());
			moduloAngular.setText(config.getModuloAngular());
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}


	private void salvarConfiguracoes() {

		try {
			util.saveConfig(pathProjeto.getText(), moduloAngular.getText());
			criadorEntidade.define(pathProjeto.getText(), moduloAngular.getText());
			log.append("Salvo com sucesso!\n");
		} catch (IOException e1) {
			log.append("Ocorreu um erro ao salvar as configura��es\n");
			log.append(e1.getMessage()+"\n");
		}
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {




		frmCriadorDeEntidades = new JFrame();
		frmCriadorDeEntidades.setTitle("Criador de Entidades - Saat's Framework");
		frmCriadorDeEntidades.setBounds(100, 100, 926, 527);
		frmCriadorDeEntidades.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JPanel panelHeader = new JPanel();
		panelHeader.setBorder(new TitledBorder(null, "Configura\u00E7\u00F5es", TitledBorder.LEADING, TitledBorder.TOP, null, null));

		JScrollPane scrollFooter = new JScrollPane();

		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "Deletar Entidade", TitledBorder.LEADING, TitledBorder.TOP, null, null));

		JLabel label = new JLabel("Nome da entidade (Ex: Produto)");

		nomeEntidadeDeletar = new JTextField();
		nomeEntidadeDeletar.setColumns(10);

		JButton btnDeletarEntidade = new JButton("Deletar Entidade");
		btnDeletarEntidade.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				try {
					criadorEntidade.deleteEntidade(nomeEntidadeDeletar.getText());
					log.append("Entidade  "+nomeEntidadeDeletar.getText()+" deletada com sucesso!\n");

				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					log.append("Ocorreu um erro ao deletar a entidade  "+nomeEntidade.getText()+"\n");
					log.append(e.getMessage());
					log.append("-------------------------------------------------------------------------------\n");
				}
			}
		});
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(
				gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
						.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
								.addComponent(label, GroupLayout.PREFERRED_SIZE, 180, GroupLayout.PREFERRED_SIZE)
								.addComponent(nomeEntidadeDeletar, GroupLayout.PREFERRED_SIZE, 159, GroupLayout.PREFERRED_SIZE)
								.addComponent(btnDeletarEntidade))
						.addContainerGap(350, Short.MAX_VALUE))
				);
		gl_panel.setVerticalGroup(
				gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
						.addContainerGap()
						.addComponent(label)
						.addPreferredGap(ComponentPlacement.RELATED)
						.addComponent(nomeEntidadeDeletar, GroupLayout.PREFERRED_SIZE, 29, GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(ComponentPlacement.RELATED)
						.addComponent(btnDeletarEntidade)
						.addContainerGap(12, Short.MAX_VALUE))
				);
		panel.setLayout(gl_panel);

		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new TitledBorder(null, "Front-End", TitledBorder.LEADING, TitledBorder.TOP, null, null));

		JPanel panel_2 = new JPanel();
		panel_2.setBorder(new TitledBorder(null, "Criar Entidade", TitledBorder.LEADING, TitledBorder.TOP, null, null));

		JLabel label_1 = new JLabel("Nome da entidade (Ex: Produto)");

		nomeEntidade = new JTextField();
		nomeEntidade.setColumns(10);

		JCheckBox criarJava = new JCheckBox("JAVA");
		criarJava.setSelected(true);

		JCheckBox criarWeb = new JCheckBox("WEB");
		criarWeb.setSelected(true);

		JCheckBox criarArquivoTesteUnitarioFrontEnd = new JCheckBox("Criar arquivo de testes unitários no front-end");
		criarArquivoTesteUnitarioFrontEnd.setSelected(true);

		JButton btnCriarEntidade = new JButton("Criar entidade");
		btnCriarEntidade.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				try {

					if(criarJava.isSelected())
						criadorEntidade.criarJava(nomeEntidade.getText(), principalAttr.getText());

					if(criarWeb.isSelected())
						criadorEntidade.criarWeb(nomeEntidade.getText(),
								menuLabel.getText(), 
								principalAttr.getText(),  
								principalLabel.getText(),  
								principalIcon.getText()
								);

					if(criarArquivoTesteUnitarioFrontEnd.isSelected()) {

						criadorEntidade.criarArquivoTesteUnitarioFrontEnd(nomeEntidade.getText());
					}


					log.append("Entidade "+nomeEntidade.getText()+" criada com sucesso!\n");
				} catch (IOException e) {
					log.append("Ocorreu um erro ao criar  "+nomeEntidade.getText()+"\n");
					log.append("-------------------------------------------------------------------------------\n");
					log.append(e.getMessage());
					e.printStackTrace();
				}
			}
		});



		JLabel label344 = new JLabel("Label no menu (Ex: Produtos)");

		menuLabel = new JTextField();
		menuLabel.setColumns(10);

		JLabel lblAtributoPrincipalDa = new JLabel("Atributo principal da entidade (Ex: nomeProduto)");

		principalAttr = new JTextField();
		principalAttr.setColumns(10);

		JLabel lblLabelPrincipalDa = new JLabel("Label principal da entidade (Ex: Nome do produto)");

		principalLabel = new JTextField();
		principalLabel.setColumns(10);

		JLabel lblIconePrincipalex = new JLabel("Icone principal (Ex: person)");

		principalIcon = new JTextField();
		principalIcon.setColumns(10);


		GroupLayout gl_panel_2 = new GroupLayout(panel_2);
		gl_panel_2.setHorizontalGroup(
				gl_panel_2.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_2.createSequentialGroup()
						.addGroup(gl_panel_2.createParallelGroup(Alignment.LEADING)
								.addComponent(btnCriarEntidade)
								.addGroup(gl_panel_2.createSequentialGroup()
										.addGroup(gl_panel_2.createParallelGroup(Alignment.LEADING)
												.addGroup(gl_panel_2.createSequentialGroup()
														.addContainerGap()
														.addGroup(gl_panel_2.createParallelGroup(Alignment.LEADING)
																.addComponent(menuLabel, GroupLayout.PREFERRED_SIZE, 159, GroupLayout.PREFERRED_SIZE)
																.addGroup(gl_panel_2.createSequentialGroup()
																		.addComponent(label344, GroupLayout.PREFERRED_SIZE, 180, GroupLayout.PREFERRED_SIZE)
																		.addPreferredGap(ComponentPlacement.UNRELATED)
																		.addComponent(lblLabelPrincipalDa, GroupLayout.DEFAULT_SIZE, 281, Short.MAX_VALUE))))
												.addGroup(gl_panel_2.createParallelGroup(Alignment.LEADING, false)
														.addGroup(gl_panel_2.createSequentialGroup()
																.addComponent(criarJava)
																.addGap(10)
																.addComponent(criarWeb, GroupLayout.PREFERRED_SIZE, 55, GroupLayout.PREFERRED_SIZE)
																.addPreferredGap(ComponentPlacement.RELATED)
																.addComponent(criarArquivoTesteUnitarioFrontEnd, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
														.addGroup(gl_panel_2.createSequentialGroup()
																.addContainerGap()
																.addGroup(gl_panel_2.createParallelGroup(Alignment.LEADING)
																		.addComponent(label_1, GroupLayout.PREFERRED_SIZE, 172, GroupLayout.PREFERRED_SIZE)
																		.addComponent(nomeEntidade, GroupLayout.PREFERRED_SIZE, 160, GroupLayout.PREFERRED_SIZE))
																.addGap(18)
																.addGroup(gl_panel_2.createParallelGroup(Alignment.LEADING)
																		.addComponent(lblAtributoPrincipalDa, GroupLayout.PREFERRED_SIZE, 275, GroupLayout.PREFERRED_SIZE)
																		.addComponent(principalAttr, GroupLayout.PREFERRED_SIZE, 232, GroupLayout.PREFERRED_SIZE)
																		.addComponent(principalLabel, GroupLayout.PREFERRED_SIZE, 233, GroupLayout.PREFERRED_SIZE))
																.addPreferredGap(ComponentPlacement.RELATED))))
										.addPreferredGap(ComponentPlacement.RELATED)
										.addGroup(gl_panel_2.createParallelGroup(Alignment.LEADING)
												.addComponent(lblIconePrincipalex, GroupLayout.PREFERRED_SIZE, 180, GroupLayout.PREFERRED_SIZE)
												.addComponent(principalIcon, GroupLayout.PREFERRED_SIZE, 138, GroupLayout.PREFERRED_SIZE))
										.addGap(266)))
						.addGap(414))
				);
		gl_panel_2.setVerticalGroup(
				gl_panel_2.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_2.createSequentialGroup()
						.addContainerGap()
						.addGroup(gl_panel_2.createParallelGroup(Alignment.BASELINE)
								.addComponent(criarJava)
								.addComponent(criarWeb)
								.addComponent(criarArquivoTesteUnitarioFrontEnd))
						.addGap(14)
						.addGroup(gl_panel_2.createParallelGroup(Alignment.BASELINE)
								.addComponent(label_1)
								.addComponent(lblAtributoPrincipalDa)
								.addComponent(lblIconePrincipalex))
						.addPreferredGap(ComponentPlacement.RELATED)
						.addGroup(gl_panel_2.createParallelGroup(Alignment.BASELINE)
								.addComponent(nomeEntidade, GroupLayout.PREFERRED_SIZE, 29, GroupLayout.PREFERRED_SIZE)
								.addComponent(principalAttr, GroupLayout.PREFERRED_SIZE, 29, GroupLayout.PREFERRED_SIZE)
								.addComponent(principalIcon, GroupLayout.PREFERRED_SIZE, 29, GroupLayout.PREFERRED_SIZE))
						.addGap(18)
						.addGroup(gl_panel_2.createParallelGroup(Alignment.BASELINE)
								.addComponent(label344)
								.addComponent(lblLabelPrincipalDa))
						.addGap(6)
						.addGroup(gl_panel_2.createParallelGroup(Alignment.BASELINE)
								.addComponent(menuLabel, GroupLayout.PREFERRED_SIZE, 29, GroupLayout.PREFERRED_SIZE)
								.addComponent(principalLabel, GroupLayout.PREFERRED_SIZE, 29, GroupLayout.PREFERRED_SIZE))
						.addPreferredGap(ComponentPlacement.RELATED, 27, Short.MAX_VALUE)
						.addComponent(btnCriarEntidade)
						.addContainerGap())
				);
		panel_2.setLayout(gl_panel_2);
		GroupLayout groupLayout = new GroupLayout(frmCriadorDeEntidades.getContentPane());
		groupLayout.setHorizontalGroup(
				groupLayout.createParallelGroup(Alignment.TRAILING)
				.addGroup(groupLayout.createSequentialGroup()
						.addContainerGap()
						.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addGroup(Alignment.TRAILING, groupLayout.createSequentialGroup()
										.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
												.addComponent(scrollFooter, GroupLayout.DEFAULT_SIZE, 672, Short.MAX_VALUE)
												.addComponent(panel_2, GroupLayout.PREFERRED_SIZE, 672, Short.MAX_VALUE))
										.addContainerGap())
								.addGroup(Alignment.TRAILING, groupLayout.createSequentialGroup()
										.addComponent(panelHeader, GroupLayout.DEFAULT_SIZE, 341, Short.MAX_VALUE)
										.addGap(18)
										.addComponent(panel_1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
										.addGap(29)
										.addComponent(panel, GroupLayout.PREFERRED_SIZE, 206, GroupLayout.PREFERRED_SIZE)
										.addGap(114))))
				);
		groupLayout.setVerticalGroup(
				groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
						.addContainerGap()
						.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
								.addComponent(panelHeader, GroupLayout.PREFERRED_SIZE, 120, GroupLayout.PREFERRED_SIZE)
								.addComponent(panel_1, GroupLayout.PREFERRED_SIZE, 120, GroupLayout.PREFERRED_SIZE)
								.addComponent(panel, GroupLayout.PREFERRED_SIZE, 120, GroupLayout.PREFERRED_SIZE))
						.addGap(18)
						.addComponent(panel_2, GroupLayout.PREFERRED_SIZE, 244, GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(ComponentPlacement.RELATED)
						.addComponent(scrollFooter, GroupLayout.DEFAULT_SIZE, 79, Short.MAX_VALUE)
						.addContainerGap())
				);

		JLabel lblMduloAngularjs = new JLabel("Módulo AngularJS ");

		moduloAngular = new JTextField();
		moduloAngular.setColumns(10);
		GroupLayout gl_panel_1 = new GroupLayout(panel_1);
		gl_panel_1.setHorizontalGroup(
				gl_panel_1.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_1.createSequentialGroup()
						.addGroup(gl_panel_1.createParallelGroup(Alignment.LEADING)
								.addComponent(lblMduloAngularjs)
								.addComponent(moduloAngular, GroupLayout.PREFERRED_SIZE, 159, GroupLayout.PREFERRED_SIZE))
						.addContainerGap(21, Short.MAX_VALUE))
				);
		gl_panel_1.setVerticalGroup(
				gl_panel_1.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_1.createSequentialGroup()
						.addContainerGap()
						.addComponent(lblMduloAngularjs)
						.addPreferredGap(ComponentPlacement.RELATED)
						.addComponent(moduloAngular, GroupLayout.PREFERRED_SIZE, 29, GroupLayout.PREFERRED_SIZE)
						.addContainerGap(37, Short.MAX_VALUE))
				);
		panel_1.setLayout(gl_panel_1);


		scrollFooter.setViewportView(log);

		pathProjeto = new JTextField();
		pathProjeto.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent arg0) {


			}
		});
		
		JProgressBar progressBar = new JProgressBar();
		progressBar.setIndeterminate(true);
		progressBar.setVisible(false);


		pathProjeto.setColumns(10);

		JLabel lblPathDoProjeto = new JLabel("Path do projeto");

		JButton btnSalvarConfiguraes = new JButton("Salvar configura\u00E7\u00F5es");
		btnSalvarConfiguraes.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				salvarConfiguracoes();

			}
		});

		JButton btnSelectPathProject = new JButton("New button");
		btnSelectPathProject.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {


				JFileChooser chooser = new JFileChooser();
				chooser.setCurrentDirectory(new java.io.File("."));
				chooser.setDialogTitle("choosertitle");
				chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				chooser.setAcceptAllFileFilterUsed(false);

				if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {

					pathProjeto.setText(chooser.getSelectedFile().toString()+"\\");
					salvarConfiguracoes();

				} else {
				}
			}
		});

		JButton btnCriarProjetoBase = new JButton("Criar projeto base no diretório");
		btnCriarProjetoBase.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				File diretorio = new File(pathProjeto.getText());

				if(diretorio.list()==null || diretorio.list().length==0) {

					log.append("Clonando repositório...\n");
					progressBar.setVisible(true);

					new Thread() {

						@Override
						public void run() {
							try {
								util.cloneBaseProject(pathProjeto.getText());
								log.append("Projeto base criado com sucesso\n");
								progressBar.setVisible(false);
							} catch (Exception e) {

								log.append("Ocorreu u erro ao criar o projeto base\n");
								log.append(e.getMessage()+"\n");
								progressBar.setVisible(false);

							}
						
						}
					}.start();

				}
				else {
					log.append("O diretório não está vazio\n");

				}

			}
		});
		
		
		GroupLayout gl_panelHeader = new GroupLayout(panelHeader);
		gl_panelHeader.setHorizontalGroup(
			gl_panelHeader.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panelHeader.createSequentialGroup()
					.addGroup(gl_panelHeader.createParallelGroup(Alignment.LEADING)
						.addComponent(lblPathDoProjeto)
						.addComponent(pathProjeto, GroupLayout.PREFERRED_SIZE, 138, GroupLayout.PREFERRED_SIZE)
						.addComponent(btnSalvarConfiguraes, GroupLayout.PREFERRED_SIZE, 122, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_panelHeader.createParallelGroup(Alignment.LEADING)
						.addComponent(btnCriarProjetoBase, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addGroup(gl_panelHeader.createSequentialGroup()
							.addComponent(btnSelectPathProject, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(progressBar, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
					.addContainerGap())
		);
		gl_panelHeader.setVerticalGroup(
			gl_panelHeader.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panelHeader.createSequentialGroup()
					.addGap(9)
					.addGroup(gl_panelHeader.createParallelGroup(Alignment.TRAILING)
						.addGroup(gl_panelHeader.createSequentialGroup()
							.addComponent(lblPathDoProjeto)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(gl_panelHeader.createParallelGroup(Alignment.BASELINE)
								.addComponent(pathProjeto, GroupLayout.PREFERRED_SIZE, 29, GroupLayout.PREFERRED_SIZE)
								.addComponent(btnSelectPathProject, GroupLayout.PREFERRED_SIZE, 28, GroupLayout.PREFERRED_SIZE)))
						.addComponent(progressBar, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_panelHeader.createParallelGroup(Alignment.BASELINE)
						.addComponent(btnSalvarConfiguraes)
						.addComponent(btnCriarProjetoBase))
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		panelHeader.setLayout(gl_panelHeader);
		frmCriadorDeEntidades.getContentPane().setLayout(groupLayout);
	}
}
