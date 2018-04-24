package fr.jeux.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
import fr.jeux.model.TexteFieldControler;

public class PlusOuMoinsDuel extends Container {

	FichierConfig FC = new FichierConfig();
	Random random = new Random();
	private JPanel panelNord = new JPanel(), 
				   panelCentre = new JPanel(), 
				   panelAnnonce = new JPanel(),
				   panelScroll = new JPanel();
	private int Min = 0, 
			    Max = FC.getNombreMax(), 
			    MaxIA = FC.getNombreMax(),
			    nombre_secret = random.nextInt(Max - 1) + 1, 
			    nombre_secret2 = 0, nombre_user = 0, 
			    nombre_user2 = 0;
	private String difficulty = FC.getDifficulty();
	private JLabel titre = new JLabel("Plus ou moins : Mode Duel"),
			difficultyLabel = new JLabel("Difficulté : " + difficulty), annonceLabel = new JLabel("Saisir un nombre :");
	private JTextArea devArea = new JTextArea(
			"\tMode développeur activé !\n Réponse du joueur:  " + nombre_secret + "\tRéponse de l'ordinateur : ");
	private JTextArea explication = new JTextArea("Saissisez un nombre entre " + Min + " et " + Max
			+ ", \nl'ordinateur devra trouver le nombre secret, et vous devrez trouver le sien !\n"
			+ "\nLe premier à trouver le bon nombre à gagné !\n"
			+ "Si l'un des deux joueurs n'a plus d'essais, son opposent gagne la partie.");
	private JTextArea défilement = new JTextArea();
	private JTextArea défilement2 = new JTextArea();
	private JScrollPane scroll = new JScrollPane(défilement);
	private JScrollPane scroll2 = new JScrollPane(défilement2);
	private JTextField annonce = new JTextField();
	private JButton button = new JButton("Ok");
	private boolean b = true, b2 = true, restartB = false;

	public PlusOuMoinsDuel() {
		super();
		initPanel();
		Logging.logger.info("Initialisation PlusOuMoinsDuel");
	}

	protected void initPanel() {

		titre.setFont(comics30);
		titre.setHorizontalAlignment(JLabel.CENTER);
		difficultyLabel.setFont(comics20);
		TexteFieldControler TFLC = new TexteFieldControler(annonce);
		annonce.addKeyListener(TFLC);
		annonce.setPreferredSize(new Dimension(60, 25));
		annonceLabel.setBorder(BorderFactory.createEmptyBorder(10, 300, 10, 0));
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
		panelAnnonce.add(annonce);
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
		défilement.append(" Entrer le nombre secret\n que devra trouver l'ordinateur.");
	}

	// Nous reprenons la méthode start du mode challenger
	public void start() {
		Logging.logger.info("Vérification du nombre joueur");
		défilement.append(" Vous avez saisi " + annonce.getText() + "\n");
		nombre_user = getAnnonce();

		if (nombre_secret == nombre_user) {
			défilement.append(" Vous avez gagnez !");
			gagné();
		} else if (nombre_user > nombre_secret) {
			défilement.append(" Votre nombre est trop grand\n" + "   -------------------------------\n");
		} else {
			défilement.append(" Votre nombre est trop petit\n" + "   -------------------------------\n");
		}
	}

	public void startIA() {
		Logging.logger.info("Vérification du nombre IA");
		// si c'est le 1er essais
		if (b2) {
			b2 = false;
			nombre_user2 = MaxIA / 2;
			défilement2.append(" L'ordinateur donne la valeur " + nombre_user2 + "\n");
			if (nombre_secret2 == nombre_user2) {
				gagné();
				TexteGagnéIA();
			}
			if (nombre_secret2 < nombre_user2) {
				TexteSupérieurIA();
			}
			if (nombre_secret2 > nombre_user2) {
				TexteInférieurIA();
			}
		}
		// si le montant est supérieurs
		else if (nombre_secret2 < nombre_user2) {
			// Nous attribuons le chiffre trouver par l'ia au montant maximal
			MaxIA = nombre_user2;
			nombre_user2 = (MaxIA + Min) / 2;
			défilement2.append(" L'ordinateur donne la valeur " + nombre_user2 + "\n");
			if (nombre_secret2 == nombre_user2) {
				gagné();
				TexteGagnéIA();
			} else if (nombre_secret2 < nombre_user2) {
				TexteSupérieurIA();
			} else
				TexteInférieurIA();
			// sinon il est inférieur
		} else {
			// Nous attribuons le chiffre trouver par l'ia au montant minimal
			Min = nombre_user2;
			nombre_user2 = (Min + MaxIA) / 2;
			défilement2.append(" L'ordinateur donne la valeur " + nombre_user2 + "\n");
			if (nombre_secret2 == nombre_user2) {
				gagné();
				TexteGagnéIA();
			} else if (nombre_secret2 < nombre_user2) {
				TexteSupérieurIA();
			} else
				TexteInférieurIA();
		}
	}

	public void TexteSupérieurIA() {
		défilement2.append(" Le nombre trouver par l'ordinateur est trop grand\n");
		défilement2.append(" -----------------------------\n");
	}

	public void TexteInférieurIA() {
		défilement2.append(" Le nombre trouver par l'ordinateur est trop petit\n");
		défilement2.append(" -----------------------------\n");
	}

	public void TexteGagnéIA() {
		défilement2.append(" L'ordinateur à gagné !\n");
		défilement2.append(" -----------------------------\n");
	}

	public void gagné() {
		if (nombre_secret == nombre_user) {
			JOptionPane.showMessageDialog(null, "Vous avez trouvé le nombre secret !\n"
					+ "Vous pouvez changer de jeu ou recommencer depuis le menu fichier");
			restart();
		} else {
			JOptionPane.showMessageDialog(null, "L'ordinateur a trouvé le nombre secret !\n" + "La réponse étais "
					+ nombre_secret + "\n" + "Vous pouvez changer de jeu ou recommencer depuis le menu fichier");
			restart();
		}
	}

	public void restart() {
		button.setText("Recommencer ?");
		annonce.setEditable(false);
		restartB = true;
	}

	// méthode pour convertir le texteField en int
	public int getAnnonce() {
		int a = 0;
		try {
			String str = annonce.getText();
			a = Integer.parseInt(str);

			// si n'est pas convertissable
		} catch (NumberFormatException e) {
			Logging.logger.error("Saisie incorrect");
			JOptionPane.showMessageDialog(null,
					"Attention, une ou plusieurs donnée(s) saisie ne sont pas des chiffres");
		}
		return a;
	}

	class Listener implements ActionListener {

		public void actionPerformed(ActionEvent arg0) {

			// nous regardons si la partie est terminer
			if (restartB) {
				Logging.logger.info("Restart : génération d'une nouvelle partie");
				restartB = false;
				défilement.setText(" Entrer le nombre secret\n que devra trouver l'ordinateur.");
				défilement2.setText("");
				button.setText("OK");
				annonce.setEditable(true);
				nombre_secret = random.nextInt(Max - 1) + 1;
				MaxIA = FC.getNombreMax();
				Min = 0;
				b = true;
				b2 = true;
			}

			// nous vérifions que les données saisies sont conforment
			else if (getAnnonce() < 1 || getAnnonce() > (Max - 1)) {
				JOptionPane.showMessageDialog(null, "Le nombre doit être compris entre 0 et " + Max + ".");
				annonce.setText("");

				// nous regardons si le nombre que l'ordinateur doit deviner à été rentrer
			} else if (b) {
				nombre_secret2 = getAnnonce();
				annonce.setText("");
				défilement.setText("");
				JOptionPane.showMessageDialog(null, "Vous devez maintenant trouver le nombre de l'ordinateur.");
				devArea.setText("\tMode développeur activé !\n Réponse du joueur:  " + nombre_secret
						+ "\tRéponse de l'ordinateur : " + nombre_secret2);
				b = false;
				// si les données ont été rentrer
			} else {
				startIA();
				start();
				annonce.setText("");
			}
		}
	}
}
