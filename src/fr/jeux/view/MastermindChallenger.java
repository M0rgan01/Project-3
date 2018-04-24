package fr.jeux.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.Random;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import fr.jeux.model.FichierConfig;
import fr.jeux.model.Logging;
import fr.jeux.model.Mastermind;
import fr.jeux.model.TexteFieldControler;

public class MastermindChallenger extends Container {

	FichierConfig FC = new FichierConfig();
	Random random = new Random();
	private JPanel panelNord = new JPanel(), 
				   panelCentre = new JPanel(), 
				   panelAnnonce = new JPanel();
	private final int NB_CHIFFRES = FC.getNombre_chiffre(), 
					  MAX = FC.getNombreMaxMastermind();
	private int essais = FC.getNombreEssai();
	private final int[] solution = new int[NB_CHIFFRES];
	private String difficulty = FC.getDifficulty();
	private JLabel titre = new JLabel("Mastermind : Mode Challenger"),
			difficultyLabel = new JLabel("Difficulté : " + difficulty),
			nombreEssai = new JLabel("Nombre d'essai restant : " + essais),
			annonceLabel = new JLabel("Saisir un nombre :"), devLabel = new JLabel();
	private JTextArea explication = new JTextArea("Trouver la combinaison secrete à " + FC.getNombre_chiffre()
			+ " chiffres de l'ordinateur,\n" + "pour vous aidez, l'ordinateur vous indiquera :\n"
			+ "- Combien de chiffres sont bien placés.\n" + "- Combien de chiffres sont bons mais mal placés.\n"
			+ "Les chiffres de la combinaison vont de 1 à " + FC.getNombreMaxMastermind());
	private JTextArea défilement = new JTextArea();
	private JScrollPane scroll = new JScrollPane(défilement);
	private JTextField[] annonce = new JTextField[NB_CHIFFRES];
	private JButton button = new JButton("Ok");
	private boolean restartB = false;

	public MastermindChallenger() {
		super();
		initSolution();
		initPanel();
		Logging.logger.info("Initialisation MastermindChallenger");
	}

	protected void initPanel() {

		titre.setFont(comics30);
		titre.setHorizontalAlignment(JLabel.CENTER);
		difficultyLabel.setFont(comics20);

		annonceLabel.setBorder(BorderFactory.createEmptyBorder(10, 50, 10, 0));
		button.addActionListener(new Listener());
		button.setPreferredSize(new Dimension(130, 25));

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
		for (int i = 0; i < NB_CHIFFRES; i++) {
			annonce[i] = new JTextField();
			TexteFieldControler TFLC = new TexteFieldControler(annonce[i]);
			annonce[i].addKeyListener(TFLC);
			annonce[i].setPreferredSize(new Dimension(20, 25));
			panelAnnonce.add(annonce[i]);
		}
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

		if (FC.getModeDev())
			this.panel.add(devLabel);
	}

	// méthode pour initialiser et écrire la solution
	public void initSolution() {
		for (int i = 0; i < NB_CHIFFRES; i++) {
			solution[i] = random.nextInt(MAX) + 1;
		}
		devLabel.setText("Mode développeur activé, réponse : " + Arrays.toString(solution));
	}

	public void start() {
		Mastermind m = new Mastermind();
		int[] chiffres = getAnnonce();
		boolean victoire = true;
		int malplacé = 0;
		int bienplacé = 0;

		défilement.append(" Vous avez saisi ");

		// Boucle pour écrire notre saisie dans la texteArea
		for (int i = 0; i < NB_CHIFFRES; i++)
			défilement.append(chiffres[i] + " ");
		défilement.append("\n");
		// nous vérifions le nombre de chiffre bien placé
		bienplacé = m.nbBienPlace(solution, chiffres, NB_CHIFFRES);
		// ainsi que le nombre de mal placé, en soustrayant les chiffres communs des
		// bien placé, pour ne pas qu'il soit compris
		malplacé = m.nbCommuns(solution, chiffres, NB_CHIFFRES) - m.nbBienPlace(solution, chiffres, NB_CHIFFRES);
		victoire = bienplacé == NB_CHIFFRES;

		if (victoire) {
			défilement.append(" Félicitation !!!\n");
			gagné();
		} else {
			essais--;
			nombreEssai.setText("Nombre d'essai restant : " + essais);
			if (essais == 0) {
				perdu();
			}
		}
		défilement.append(" " + malplacé + " chiffre(s) mal placé(s)\n");
		défilement.append(" " + bienplacé + " chiffre(s) bien placé(s)\n\n");
	}

	public void perdu() {
		JOptionPane.showMessageDialog(null, "Vous avez perdu ! La réponse était " + Arrays.toString(solution) + "\n"
				+ "Vous pouvez changer de jeu ou recommencer depuis le menu fichier");
		restart();
	}

	public void gagné() {
		JOptionPane.showMessageDialog(null, "Vous avez trouvé le nombre secret !\n"
				+ "Vous pouvez changer de jeu ou recommencer depuis le menu fichier");
		restart();
	}

	public void restart() {
		button.setText("Recommencer ?");
		for (int i = 0; i < NB_CHIFFRES; i++) {
			annonce[i].setEditable(false);
		}
		restartB = true;
	}

	// méthode pour convertir le texteField en tableau de int
	public int[] getAnnonce() {
		int[] a = new int[NB_CHIFFRES];
		try {
			for (int i = 0; i < NB_CHIFFRES; i++) {
				String str = annonce[i].getText();
				a[i] = Integer.parseInt(str);
			}
			// s'il n'est pas convertissable
		} catch (NumberFormatException e) {
			Logging.logger.error("Saisie incorrect");
			JOptionPane.showMessageDialog(null,"Attention, une ou plusieurs donnée(s) saisie ne sont pas des chiffres");
		}
		return a;
	}

	class Listener implements ActionListener {

		public void actionPerformed(ActionEvent arg0) {

			if (restartB) {
				Logging.logger.info("Restart : génération d'une nouvelle partie");
				défilement.setText("");
				button.setText("OK");
				for (int i = 0; i < NB_CHIFFRES; i++) {
					annonce[i].setEditable(true);
				}
				initSolution();
				essais = FC.getNombreEssai();
				nombreEssai.setText("Nombre d'essai restant : " + essais);
				restartB = false;
			} else {
				start();
				for (int i = 0; i < NB_CHIFFRES; i++) {
					annonce[i].setText("");
				}
			}
		}
	}
}
