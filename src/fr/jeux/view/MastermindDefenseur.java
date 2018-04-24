package fr.jeux.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;

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

public class MastermindDefenseur extends Container {

	FichierConfig FC = new FichierConfig();
	private JPanel panelNord = new JPanel(), panelCentre = new JPanel(), panelAnnonce = new JPanel();
	private final int NB_CHIFFRES = FC.getNombre_chiffre(), 
					  MAX = FC.getNombreMaxMastermind();
	private int essais = FC.getNombreEssai();
	private int[] solution = new int[NB_CHIFFRES];
	private String difficulty = FC.getDifficulty();
	private JLabel titre = new JLabel("Mastermind : Mode Defenseur"),
			difficultyLabel = new JLabel("Difficulté : " + difficulty),
			nombreEssai = new JLabel("Nombre d'essai restant : " + essais),
			annonceLabel = new JLabel("Saisir un nombre :"), devLabel = new JLabel();
	private JTextArea explication = new JTextArea(
			"L'ordinateur doit trouver votre combinaison secrete à " + FC.getNombre_chiffre() + " chiffres,\n"
					+ "pour l'aidez, des indicateurs guideront l'ordinateur de manière automatique :\n"
					+ "- Combien de chiffres sont bien placés.\n" + "- Combien de chiffres sont bons mais mal placés.\n"
					+ "Les chiffres de la combinaison vont de 1 à " + FC.getNombreMaxMastermind());
	private JTextArea défilement = new JTextArea();
	private JScrollPane scroll = new JScrollPane(défilement);
	private JTextField[] annonce = new JTextField[NB_CHIFFRES];
	private JButton button = new JButton("Ok");
	private boolean restartB = false;
	private Thread T;

	public MastermindDefenseur() {
		super();
		initPanel();
		Logging.logger.info("Initialisation MastermindDefenseur");
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
			JOptionPane.showMessageDialog(null,
					"Attention, une ou plusieurs donnée(s) saisie ne sont pas des chiffres");
		}
		return a;
	}

	public void startIA() {

		Mastermind m = new Mastermind();
		int[] chiffresTenté = new int[NB_CHIFFRES]; // Tableau contenant la combinaison tenté par l'ia
		int[] chiffresTrouvé = new int[NB_CHIFFRES];// Tableau contenant les couleurs trouvées
		int malplacé = 0;
		int bienplacé = 0;
		int nbTrouve = 0; // Nombre de boules de couleurs dont ont a trouvé la position
		int couleur = 1; // Couleur testé, on commence avec la 1ier couleur : 1
		int pos = 0;

		// Nous récupérons les données saisies et les assignont à la solution
		solution = getAnnonce();
		devLabel.setText("Mode développeur activé, réponse : " + Arrays.toString(solution));

		// tant que le nb de bien placé est inférieur aux nb de cases et qu'il reste des
		// essais
		while (m.nbBienPlace(solution, chiffresTenté, NB_CHIFFRES) < NB_CHIFFRES && essais > 0) {

			try {
				Thread.sleep(2000);
			} catch (Exception e) {
			}

			// On crée la nouvelle combinaison à tenté, qui determine la
			// presence ou non d'une couleur. Tout en tenant compte des
			// positions des boules de couleurs deja trouvées
			for (int i = 0; i < NB_CHIFFRES; i++) {
				if (chiffresTrouvé[i] == 0)
					chiffresTenté[i] = couleur;
				else
					chiffresTenté[i] = chiffresTrouvé[i];
			}

			essais--;
			nombreEssai.setText("Nombre d'essai restant : " + essais);

			// On détermine le nombre de bien placés et mal placés
			bienplacé = m.nbBienPlace(solution, chiffresTenté, NB_CHIFFRES);
			malplacé = m.nbCommuns(solution, chiffresTenté, NB_CHIFFRES)
					- m.nbBienPlace(solution, chiffresTenté, NB_CHIFFRES);

			// Boucle pour écrire la saisie dans la texteArea
			for (int i = 0; i < NB_CHIFFRES; i++)
				défilement.append(" " + chiffresTenté[i] + " ");
			défilement.append("\n");
			défilement.append(" " + malplacé + " chiffre(s) mal placé(s)\n");
			défilement.append(" " + bienplacé + " chiffre(s) bien placé(s)\n\n");

			int nbBoules = bienplacé - nbTrouve;

			// Si le chiffre tester est présent et que couleur est supérieur ou égal au max
			if (nbBoules >= 1 && couleur <= (MAX + 1) && bienplacé != NB_CHIFFRES) {
				for (int x = 1; x <= nbBoules; x++) {
					malplacé = 1;
					// indice de la position testé pour trouver l'emplacement de
					// la couleur coul. On ne test pas une position dont on
					// connais deja la
					// position. Donc on crée une boucle qui cherche une
					// position possible.
					pos = 0;
					// tant que malPlace != 0 faire
					while (malplacé > 0 && essais > 0) {
						// tant que pos est inférieur à NB_Chiffres et que l'index ChiffresTrouvé est
						// différent de 0
						while ((pos < NB_CHIFFRES) && chiffresTrouvé[pos] != 0)
							pos++;
						try {
							Thread.sleep(2000);
						} catch (Exception e) {
						}
						// On crée la nouvelle combinaison à tenté, qui cherche
						// la position exacte du chiffre
						// en cour. Tout en tenant compte des positions des chiffres deja trouvées
						for (int i = 0; i < NB_CHIFFRES; i++) {
							if (chiffresTrouvé[i] == 0) {
								if (i != pos)
									chiffresTenté[i] = couleur + 1;
								else
									chiffresTenté[i] = couleur;
							} else
								chiffresTenté[i] = chiffresTrouvé[i];
						}
						essais--;
						nombreEssai.setText("Nombre d'essai restant : " + essais);

						bienplacé = m.nbBienPlace(solution, chiffresTenté, NB_CHIFFRES);
						malplacé = m.nbCommuns(solution, chiffresTenté, NB_CHIFFRES) - bienplacé;
						pos++;
						// Boucle pour écrire la saisie dans la texteArea
						for (int i = 0; i < NB_CHIFFRES; i++)
							défilement.append(" " + chiffresTenté[i] + " ");
						défilement.append("\n");
						défilement.append(" " + malplacé + " chiffre(s) mal placé(s)\n");
						défilement.append(" " + bienplacé + " chiffre(s) bien placé(s)\n\n");
					}
					// A la sortie de la boucle, on a la position du chiffre -> pos - 1
					// On ajoute donc cette boule à la combinaison contenant les
					// chiffres Trouvées
					try {
						chiffresTrouvé[pos - 1] = couleur;
					} catch (ArrayIndexOutOfBoundsException e) {
						// on utilise try catch car si le compteur d'essais arrive à 0,
						// on sort de la boucle et ce code peut lever Exception
					}
					nbTrouve++;
				}
			}
			couleur++;
		}
		if (m.nbBienPlace(solution, chiffresTenté, NB_CHIFFRES) == NB_CHIFFRES) {
			gagné();
		} else
			perdu();
	}

	public void perdu() {
		JOptionPane.showMessageDialog(null, "L'ordinateur a perdu ! La réponse était " + Arrays.toString(solution)
				+ "\n" + "Vous pouvez changer de jeu ou recommencer depuis le menu fichier");
		restart();
	}

	public void gagné() {
		JOptionPane.showMessageDialog(null, "L'ordinateur a trouvé le nombre secret !\n"
				+ "Vous pouvez changer de jeu ou recommencer depuis le menu fichier");
		restart();
	}

	public void restart() {
		button.setText("Recommencer ?");
		restartB = true;
		button.setEnabled(true);
		for (int i = 0; i < NB_CHIFFRES; i++) {
			annonce[i].setText("");
		}
	}

	public class Listener implements ActionListener {

		public void actionPerformed(ActionEvent arg0) {
			if (restartB) {
				Logging.logger.info("Restart : génération d'une nouvelle partie");
				défilement.setText("");
				button.setText("OK");
				for (int i = 0; i < NB_CHIFFRES; i++) {
					annonce[i].setEditable(true);
				}
				essais = FC.getNombreEssai();
				nombreEssai.setText("Nombre d'essai restant : " + essais);
				restartB = false;
			} else {
				T = new Thread(new IA());
				T.start();
				défilement.append(" L'ordinateur commence à chercher la bonne réponse :\n");
				for (int i = 0; i < NB_CHIFFRES; i++) {
					annonce[i].setEditable(false);
				}
				button.setEnabled(false);
			}
		}
	}

	class IA implements Runnable {
		public void run() {
			startIA();
		}
	}
}
