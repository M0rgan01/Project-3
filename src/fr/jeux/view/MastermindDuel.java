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

public class MastermindDuel extends Container {

	Mastermind m = new Mastermind();
	Random random = new Random();
	FichierConfig FC = new FichierConfig();
	private JPanel panelNord = new JPanel(), 
				   panelCentre = new JPanel(), 
				   panelAnnonce = new JPanel(),
				   panelScroll = new JPanel();
	private final int NB_CHIFFRES = FC.getNombre_chiffre(), 
					  MAX = FC.getNombreMaxMastermind();
	private int[] solution = new int[NB_CHIFFRES], 
				  solutionIA = new int[NB_CHIFFRES];
	private String difficulty = FC.getDifficulty();
	private JLabel titre = new JLabel("Mastermind : Mode Duel"),
			difficultyLabel = new JLabel("Difficulté : " + difficulty),			
			annonceLabel = new JLabel("Saisir un nombre :");
	private JTextArea devArea = new JTextArea(
			"\tMode développeur activé !\n Réponse du joueur:  " + NB_CHIFFRES + "\tRéponse de l'ordinateur : ");
	private JTextArea explication = new JTextArea("Saissisez une combinaison secrète de " + NB_CHIFFRES + " chiffres"
			+ ", \nl'ordinateur devra trouver cette combinaison, et vous devrez trouver la sienne !\n"
			+ "\nLe premier à trouver la combinaison de l'autre a gagné,\n"
			+ "si l'un des deux joueurs n'a plus d'essai, son opposant gagne la partie.\n"
			+ "Les chiffres des combinaisons vont de 1 à " + FC.getNombreMaxMastermind());
	private JTextArea défilement = new JTextArea();
	private JTextArea défilement2 = new JTextArea();
	private JScrollPane scroll = new JScrollPane(défilement);
	private JScrollPane scroll2 = new JScrollPane(défilement2);
	private JTextField[] annonce = new JTextField[NB_CHIFFRES];
	private JButton button = new JButton("Ok");
	private boolean b = true, b2 = false, b3 = true, restartB = false;
	private Thread T;

	public MastermindDuel() {
		super();
		initSolution();
		initPanel();
		Logging.logger.info("Initialisation MastermindDuel");
	}

	protected void initPanel() {
		titre.setFont(comics30);
		titre.setHorizontalAlignment(JLabel.CENTER);
		difficultyLabel.setFont(comics20);

		annonceLabel.setBorder(BorderFactory.createEmptyBorder(10, 240, 10, 0));
		button.addActionListener(new Listener());
		button.setPreferredSize(new Dimension(130, 25));

		explication.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10));
		explication.setFont(comics15);
		explication.setEditable(false);

		scroll.setPreferredSize(new Dimension(300, 200));
		scroll.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		scroll2.setPreferredSize(new Dimension(300, 200));
		scroll2.setBorder(BorderFactory.createLineBorder(Color.BLACK));

		défilement.setBackground(Color.LIGHT_GRAY);
		défilement.setEditable(false);
		défilement2.setBackground(Color.LIGHT_GRAY);
		défilement2.setEditable(false);

		panelAnnonce.setBackground(Color.white);
		panelAnnonce.add(annonceLabel);
		for (int i = 0; i < NB_CHIFFRES; i++) {
			annonce[i] = new JTextField();
			TexteFieldControler TFLC = new TexteFieldControler(annonce[i]);
			annonce[i].addKeyListener(TFLC);
			annonce[i].setPreferredSize(new Dimension(20, 25));
			panelAnnonce.add(annonce[i]);
		}
		panelAnnonce.add(button);

		panelScroll.setBackground(Color.white);
		panelScroll.add(scroll);
		panelScroll.add(scroll2);

		panelNord.setBackground(Color.white);
		panelNord.setLayout(new BorderLayout());
		panelNord.add(titre, BorderLayout.NORTH);
		panelNord.add(difficultyLabel);

		panelCentre.setLayout(new BorderLayout());
		panelCentre.add(explication, BorderLayout.NORTH);
		panelCentre.add(panelAnnonce);
		panelCentre.add(panelScroll, BorderLayout.SOUTH);

		this.panel.add(panelNord);
		this.panel.add(panelCentre);

		if (FC.getModeDev()) {
			devArea.setFont(comics15);
			devArea.setEditable(false);
			this.panel.add(devArea);
		}
		défilement.append(" Entrer la combinaison secrete\n que devra trouver l'ordinateur.");
	}

	// méthode pour initialiser et écrire la solution
	public void initSolution() {
		for (int i = 0; i < NB_CHIFFRES; i++) {
			solution[i] = random.nextInt(MAX) + 1;
		}
		devArea.setText("\tMode développeur activé !\n Réponse du joueur:  " + Arrays.toString(solution)
				+ "\tRéponse de l'ordinateur : ");
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

	// nous reprenons la méthode start du mode challenger
	public void start() {
		Logging.logger.info("Vérification de la combinaison Joueur");
		int[] chiffres = getAnnonce();
		boolean victoire = true;
		int malplacé = 0;
		int bienplacé = 0;

		défilement.append(" Vous avez saisi ");

		// Boucle pour écrire notre saisie dans la texteArea
		for (int i = 0; i < NB_CHIFFRES; i++)
			défilement.append(chiffres[i] + " ");
		défilement.append("\n");

		bienplacé = m.nbBienPlace(solution, chiffres, NB_CHIFFRES);
		malplacé = m.nbCommuns(solution, chiffres, NB_CHIFFRES) - m.nbBienPlace(solution, chiffres, NB_CHIFFRES);
		victoire = bienplacé == NB_CHIFFRES;

		if (victoire) {
			défilement.append(" Félicitation !!!\n");
			b2 = true;
			gagné();		
		}
		défilement.append(" " + malplacé + " chiffre(s) mal placé(s)\n");
		défilement.append(" " + bienplacé + " chiffre(s) bien placé(s)\n\n");
	}

	public void startIA() {

		Logging.logger.info("Vérification de la combinaison IA");
		int[] chiffresTenté = new int[NB_CHIFFRES]; // Tableau contenant la combinaison tenté par l'ia
		int[] chiffresTrouvé = new int[NB_CHIFFRES];// Tableau contenant les couleurs trouvées par l'ia
		int malplacé = 0;
		int bienplacé = 0;
		int nbTrouve = 0; // Nombre de boules de couleurs dont ont a trouvé la position
		int couleur = 1; // Couleur testé, on commence avec la 1ier couleur : 1
		int pos = 0;

		// tant que le nb de bien placé est inférieur aux nb de cases et qu'il reste des
		// essais
		while (m.nbBienPlace(solutionIA, chiffresTenté, NB_CHIFFRES) < NB_CHIFFRES) {

			// On crée la nouvelle combinaison à tenté, qui determine la
			// presence ou non d'une couleur. Tout en tenant compte des
			// positions des boules de couleurs deja trouvées
			for (int i = 0; i < NB_CHIFFRES; i++) {
				if (chiffresTrouvé[i] == 0)
					chiffresTenté[i] = couleur;
				else
					chiffresTenté[i] = chiffresTrouvé[i];
			}

			// On détermine le nombre de bien placés et mal placés
			bienplacé = m.nbBienPlace(solutionIA, chiffresTenté, NB_CHIFFRES);
			malplacé = m.nbCommuns(solutionIA, chiffresTenté, NB_CHIFFRES)
					- m.nbBienPlace(solutionIA, chiffresTenté, NB_CHIFFRES);

			// Boucle pour écrire la saisie dans la texteArea
			for (int i = 0; i < NB_CHIFFRES; i++)
				défilement2.append(" " + chiffresTenté[i] + " ");
			défilement2.append("\n");
			défilement2.append(" " + malplacé + " chiffre(s) mal placé(s)\n");
			défilement2.append(" " + bienplacé + " chiffre(s) bien placé(s)\n\n");

			if (m.nbBienPlace(solutionIA, chiffresTenté, NB_CHIFFRES) == NB_CHIFFRES)
				gagné();

			// nous mettons le processus en supsens içi
			try {
				synchronized (T) {
					T.wait();
					Logging.logger.info("Vérification de la combinaison IA");
				}
			} catch (InterruptedException e2) {
				e2.printStackTrace();
			}

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
					while (malplacé > 0) {
						// tant que pos est inférieur à NB_Chiffres et que l'index ChiffresTrouvé est
						// différent de 0
						while ((pos < NB_CHIFFRES) && chiffresTrouvé[pos] != 0)
							pos++;

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
						
						bienplacé = m.nbBienPlace(solutionIA, chiffresTenté, NB_CHIFFRES);
						malplacé = m.nbCommuns(solutionIA, chiffresTenté, NB_CHIFFRES) - bienplacé;
						pos++;

						// Boucle pour écrire la saisie dans la texteArea
						for (int i = 0; i < NB_CHIFFRES; i++)
							défilement2.append(" " + chiffresTenté[i] + " ");
						défilement2.append("\n");
						défilement2.append(" " + malplacé + " chiffre(s) mal placé(s)\n");
						défilement2.append(" " + bienplacé + " chiffre(s) bien placé(s)\n\n");

						if (m.nbBienPlace(solutionIA, chiffresTenté, NB_CHIFFRES) == NB_CHIFFRES)
							gagné();
						// nous mettons le processus en supsens içi
						try {
							synchronized (T) {
								T.wait();
								Logging.logger.info("Vérification de la combinaison IA");
							}
						} catch (InterruptedException e1) {
							e1.printStackTrace();
						}
					}
					// A la sortie de la boucle, on a la position du chiffre -> pos - 1
					// On ajoute donc cette boule à la combinaison contenant les
					// chiffres Trouvées
					try {
						chiffresTrouvé[pos - 1] = couleur;
					} catch (ArrayIndexOutOfBoundsException e) {
						// on utilise try catch car si le compteur d'essais arrive à 0,
						// on sort de le boucle et ce code peut lever Exception
					}
					nbTrouve++;
				}
			}
			couleur++;
		}
	}

	public void gagné() {
		if (b2) {
			JOptionPane.showMessageDialog(null, "Vous avez trouvé le nombre secret !\n"
					+ "Vous pouvez changer de jeu ou recommencer depuis le menu fichier");
			restart();
		} else {
			JOptionPane.showMessageDialog(null, "L'ordinateur a trouvé le nombre secret !\n"
					+ "Vous pouvez changer de jeu ou recommencer depuis le menu fichier");
			restart();
		}
	}

	public void restart() {
		button.setText("Recommencer ?");
		for (int i = 0; i < NB_CHIFFRES; i++) {
			annonce[i].setEditable(false);
		}
		restartB = true;
	}

	class Listener implements ActionListener {

		public void actionPerformed(ActionEvent arg0) {

			// nous regardons si la partie est terminer
			if (restartB) {
				Logging.logger.info("Restart : génération d'une nouvelle partie");
				défilement.setText(" Entrer la combinaison secrete\n que devra trouver l'ordinateur.");
				défilement2.setText("");
				button.setText("OK");
				for (int i = 0; i < NB_CHIFFRES; i++) {
					annonce[i].setEditable(true);
				}
				initSolution();
				b = true;
				b2 = false;
				b3 = true;
				restartB = false;

				// nous regardons si la combinaison que l'ordinateur doit deviner à été rentrer
			} else if (b) {
				// Nous récupérons les données saisies et les assignont à la solution
				solutionIA = getAnnonce();
				défilement.setText("");
				for (int i = 0; i < NB_CHIFFRES; i++) {
					annonce[i].setText("");
				}
				JOptionPane.showMessageDialog(null, "Vous devez maintenant trouver la combinaison de l'ordinateur.");
				devArea.setText("\tMode développeur activé !\n Réponse du joueur:  " + Arrays.toString(solution)
						+ "\tRéponse de l'ordinateur : " + Arrays.toString(solutionIA));
				b = false;

				// sinon nous lançons/continuons le jeu
			} else {
				// si c'est une nouvelle partie
				if (b3) {
					T = new Thread(new IA());
					T.start();
					b3 = false;
					// sinon nous reprenons la ou le processus c'est suspendu
				} else {
					synchronized (T) {
						T.notify();
					}
				}
				start();
				for (int i = 0; i < NB_CHIFFRES; i++) {
					annonce[i].setText("");
				}
			}
		}
	}

	class IA implements Runnable {
		public void run() {
			startIA();
		}
	}
}
