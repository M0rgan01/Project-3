package fr.jeux.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.util.Random;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import fr.jeux.model.FichierConfig;
import fr.jeux.model.TexteFieldControler;


public class MastermindChallenger extends Container {
	
	FichierConfig FC = new FichierConfig();
	Random random = new Random();
	private JPanel panelNord = new JPanel(), panelCentre = new JPanel(), panelAnnonce = new JPanel();
	private int Max = FC.getNombreMax(),
				nombre_secret = random.nextInt(Max),
				nombre_user = 0,
				essai = FC.getNombreEssai();
	private String difficulty = FC.getDifficulty();
	private JLabel titre = new JLabel("Mastermind : Mode Challenger"),
				   difficultyLabel = new JLabel("Difficulté : " + difficulty),
				   nombreEssai = new JLabel("Nombre d'essaie restant : " + essai),
				   annonceLabel = new JLabel("Saisir un nombre :"),
				   devLabel = new JLabel("Mode développeur activé, réponse : " + nombre_secret);
	private JTextArea explication = new JTextArea("Trouver la combinaison secret de l'ordinateur,\n"
												+ "pour vous aidez, l'ordinateur utilisera des symboles :\n"
												+ "- 'O' signifie que le chiffre est bien placé.\n"
												+ "- 'X' signifie que ne chiffre n'est pas correct à l'endrois placé.");
	private JTextArea défilement = new JTextArea();
	private JScrollPane scroll = new JScrollPane(défilement);
	private JTextField annonce = new JTextField();
	private JButton button = new JButton("Ok");
	
	public MastermindChallenger() {
		super();
		initPanel();
	}
	
	
	protected void initPanel() {
		
		titre.setFont(comics30);
		titre.setHorizontalAlignment(JLabel.CENTER);
		difficultyLabel.setFont(comics20);
		TexteFieldControler TFLC = new TexteFieldControler(annonce);
		annonce.addKeyListener(TFLC);
		annonce.setPreferredSize(new Dimension(60, 25));
		annonceLabel.setBorder(BorderFactory.createEmptyBorder(10, 100, 10, 0));
		//button.addActionListener(new Listener());

		explication.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10));
		explication.setFont(comics15);
		explication.setEditable(false);

		scroll.setPreferredSize(new Dimension(300, 200));
		scroll.setBorder(BorderFactory.createLineBorder(Color.BLACK));

		défilement.setBackground(Color.LIGHT_GRAY);
		défilement.setEditable(false);

		panelAnnonce.setBackground(Color.white);
		panelAnnonce.add(nombreEssai);
		panelAnnonce.add(annonceLabel);
		panelAnnonce.add(annonce);
		panelAnnonce.add(button);

		panelNord.setBackground(Color.white);
		panelNord.setLayout(new BorderLayout());
		panelNord.add(titre, BorderLayout.NORTH);
		panelNord.add(difficultyLabel);

		panelCentre.setLayout(new BorderLayout());
		panelCentre.add(explication, BorderLayout.NORTH);
		panelCentre.add(panelAnnonce);
		panelCentre.add(scroll, BorderLayout.SOUTH);

		this.panel.add(panelNord);
		this.panel.add(panelCentre);
		
		if(FC.getModeDev())
			this.panel.add(devLabel);
	}

	 
	}



