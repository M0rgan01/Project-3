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

public class PlusOuMoinsChallenger extends Container {

	FichierConfig FC = new FichierConfig();
	Random random = new Random();
	private JPanel panelNord = new JPanel(), panelCentre = new JPanel(), panelAnnonce = new JPanel();
	private int Max = FC.getNombreMax(), 
				nombre_secret = random.nextInt(Max - 1) + 1, 
				nombre_user = 0,
				essai = FC.getNombreEssai();
	private String difficulty = FC.getDifficulty();
	private JLabel titre = new JLabel("Plus ou moins : Mode Challenger"),
			difficultyLabel = new JLabel("Difficulté : " + difficulty),
			nombreEssai = new JLabel("Nombre d'essai restant : " + essai),
			annonceLabel = new JLabel("Saisir un nombre :"),
			devLabel = new JLabel("Mode développeur activé, réponse : " + nombre_secret);
	private JTextArea explication = new JTextArea(
			"Saissisez un nombre entre 0 et " + Max + ", \nl'application vous guidera pour trouver le nombre secret.");
	private JTextArea défilement = new JTextArea();
	private JScrollPane scroll = new JScrollPane(défilement);
	private JTextField annonce = new JTextField();
	private JButton button = new JButton("Ok");
	private boolean restartB = false;

	public PlusOuMoinsChallenger() {
		super();
		initPanel();
		Logging.logger.info("Initialisation PlusOuMoinsChallenger");
	}

	// méthode pour placé les différents composants
	protected void initPanel() {

		titre.setFont(comics30);
		titre.setHorizontalAlignment(JLabel.CENTER);
		difficultyLabel.setFont(comics20);
		TexteFieldControler TFLC = new TexteFieldControler(annonce);
		annonce.addKeyListener(TFLC);
		annonce.setPreferredSize(new Dimension(60, 25));
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

		if (FC.getModeDev())
			this.panel.add(devLabel);
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

	// méthode pour vérifier les données saisies et les comparées
	public void start() {
		Logging.logger.info("Vérification du nombre saisi");
		défilement.append(" Vous avez saisi " + annonce.getText() + "\n");
		nombre_user = getAnnonce();

		// si les données correspondent
		if (nombre_secret == nombre_user) {
			défilement.append(" Vous avez gagnez !");
			gagné();

			// si elles sont supérieurs
		} else if (nombre_user > nombre_secret) {
			défilement.append(" votre nombre est trop grand\n" + "   -------------------------------\n");
			essai--;
			nombreEssai.setText("Nombre d'essaie restant : " + essai);
			if (essai == 0)
				perdu();

			// alors elles sont inférieurs
		} else {
			défilement.append(" votre nombre est trop petit\n" + "   -------------------------------\n");
			essai--;
			nombreEssai.setText("Nombre d'essaie restant : " + essai);
			if (essai == 0)
				perdu();
		}
	}

	public void perdu() {
		JOptionPane.showMessageDialog(null, "Vous avez perdu ! La réponse était " + nombre_secret + "\n"
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
		annonce.setEditable(false);
		restartB = true;
	}

	class Listener implements ActionListener {

		public void actionPerformed(ActionEvent arg0) {

			// nous regardons si la partie est terminer
			if (restartB) {
				Logging.logger.info("Restart : génération d'une nouvelle partie");
				nombre_secret = random.nextInt(Max - 1) + 1;
				devLabel.setText("Mode développeur activé, réponse : " + nombre_secret);
				défilement.setText("");
				annonce.setEditable(true);
				restartB = false;
				button.setText("OK");
				essai = FC.getNombreEssai();
				nombreEssai.setText("Nombre d'essai restant : " + essai);

				// nous vérifions que les données saisies sont conforment
			} else if (getAnnonce() < 1 || getAnnonce() > (Max - 1)) {
				Logging.logger.error("Saisie incorrect");
				JOptionPane.showMessageDialog(null, "Le nombre doit être compris entre 0 et " + Max + ".");
				annonce.setText("");

			} else {
				start();
				annonce.setText("");
			}
		}
	}
}
