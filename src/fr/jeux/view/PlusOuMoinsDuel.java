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
import fr.jeux.model.TexteFieldControler;

public class PlusOuMoinsDuel extends Container {

	FichierConfig FC = new FichierConfig();
	Random random = new Random();
	private JPanel panelNord = new JPanel(), panelCentre = new JPanel(), panelAnnonce = new JPanel(),
			panelEssai = new JPanel(), panelScroll = new JPanel();
	private int Min = 0, Max = FC.getNombreMax(), MaxIA = FC.getNombreMax(), nombre_secret = random.nextInt(Max),
			nombre_secret2 = 0, nombre_user = 0, nombre_user2 = 0, essai = FC.getNombreEssai(),
			essai2 = FC.getNombreEssai();
	private String difficulty = FC.getDifficulty();
	private JLabel titre = new JLabel("Plus ou moins : Mode Duel"),
			difficultyLabel = new JLabel("Difficulté : " + difficulty),
			nombreEssai = new JLabel("Nombre d'essaie restant du joueur : " + essai),
			nombreEssai2 = new JLabel("Nombre d'essaie restant de l'ordinateur : " + essai2),
			annonceLabel = new JLabel("Saisir un nombre :");
	private JTextArea devArea = new JTextArea(
			"\tMode développeur activé !\n Réponse du joueur:  " + nombre_secret + "\tRéponse de l'ordinateur :");
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
	private boolean b = true, b2 = true;

	public PlusOuMoinsDuel() {
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
		button.addActionListener(new Listener());

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

		panelEssai.setBackground(Color.white);
		panelEssai.setLayout(new BorderLayout());
		panelEssai.add(nombreEssai);
		panelEssai.add(nombreEssai2, BorderLayout.SOUTH);

		panelAnnonce.setBackground(Color.white);
		panelAnnonce.add(panelEssai);
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
		défilement.append("Entrer le nombre secret\nque devra trouver l'ordinateur.");
	}

	public void start() {
		défilement.append(" Vous avez saisi " + annonce.getText() + "\n");
		nombre_user = getAnnonce();
		if (nombre_secret == nombre_user) {
			défilement.append(" Félicitation !!!\n");
			défilement.append(" Il vous restait " + essai + " essaie(s)\n avant de trouver le bon nombre\n");
			gagné();
		} else if (nombre_user > nombre_secret) {
			défilement.append(" Votre nombre est trop grand\n" + "   -------------------------------\n");
			essai--;
			nombreEssai.setText("Nombre d'essaie restant du joueur : " + essai);
			if (essai == 0)
				perdu();
		} else {
			défilement.append(" Votre nombre est trop petit\n" + "   -------------------------------\n");
			essai--;
			nombreEssai.setText("Nombre d'essaie restant du joueur : " + essai);
			if (essai == 0)
				perdu();
		}
	}

	public void startIA() {

		if (b2) {
			b2 = false;
			nombre_user2 = MaxIA / 2;
			défilement2.append("L'ordinateur donne la valeur " + nombre_user2 + "\n");
			if (nombre_secret2 == nombre_user2) {
				gagné();
				défilement2.append("L'ordinateur à gagné !\n");
				défilement2.append(" -----------------------------\n");
			}
			if (nombre_secret2 < nombre_user2) {
				défilement2.append("Le nombre trouver par l'ordinateur est trop grand\n");
				défilement2.append(" -----------------------------\n");
				essai2--;
				nombreEssai2.setText("Nombre d'essaie restant de l'ordinateur : " + essai2);
				if (essai2 == 0)
					perdu();
			}
			if (nombre_secret2 > nombre_user2) {
				défilement2.append("Le nombre trouver par l'ordinateur est trop petit\n");
				défilement2.append(" -----------------------------\n");
				essai2--;
				nombreEssai2.setText("Nombre d'essaie restant de l'ordinateur : " + essai2);
				if (essai2 == 0)
					perdu();
			}
		}

		else if (nombre_secret2 < nombre_user2) {

			MaxIA = nombre_user2;
			nombre_user2 = (MaxIA + Min) / 2;

			défilement2.append("L'ordinateur donne la valeur " + nombre_user2 + "\n");

			if (nombre_secret2 == nombre_user2) {
				gagné();
				défilement2.append("L'ordinateur à gagné !\n");
				défilement2.append(" -----------------------------\n");
			} else {
				défilement2.append("Le nombre trouver par l'ordinateur est trop grand\n");
				défilement2.append(" -----------------------------\n");
				essai2--;
				nombreEssai2.setText("Nombre d'essaie restant de l'ordinateur : " + essai2);
			}
			if (essai2 == 0)
				perdu();
			
		} else {

			Min = nombre_user2;
			nombre_user2 = (Min + MaxIA) / 2;

			défilement2.append("L'ordinateur donne la valeur " + nombre_user2 + "\n");

			if (nombre_secret2 == nombre_user2) {
				gagné();
				défilement2.append("L'ordinateur à gagné !\n");
				défilement2.append(" -----------------------------\n");
			} else {
				défilement2.append("Le nombre trouver par l'ordinateur est trop petit\n");
				défilement2.append(" -----------------------------\n");
				essai2--;
				nombreEssai2.setText("Nombre d'essaie restant de l'ordinateur : " + essai2);
			}
			if (essai2 == 0)
				perdu();
		}

	}

	public void perdu() {
		if (essai == 0) {
			JOptionPane.showMessageDialog(null,
					"Vous avez perdu ! La réponse étais "+ nombre_secret + "\n" + "Vous pouvez changer de jeu ou recommencer depuis le menu fichier");
			annonce.setEditable(false);
			button.setEnabled(false);
		} else {
			JOptionPane.showMessageDialog(null,
					"L'ordinateur à perdu ! La réponse étais "+ nombre_secret2 + "\n" + "Vous pouvez changer de jeu ou recommencer depuis le menu fichier");
			annonce.setEditable(false);
			button.setEnabled(false);
		}
	}

	public void gagné() {
		if (nombre_secret == nombre_user) {
			JOptionPane.showMessageDialog(null, "Vous avez trouvé le nombre secret !\n"
					+ "Vous pouvez changer de jeu ou recommencer depuis le menu fichier");
			annonce.setEditable(false);
			button.setEnabled(false);
		} else {
			JOptionPane.showMessageDialog(null, "L'ordinateur a trouvé le nombre secret !\n"
					+ "Vous pouvez changer de jeu ou recommencer depuis le menu fichier");
			annonce.setEditable(false);
			button.setEnabled(false);

		}
	}

	public int getAnnonce() {
		int a = 0;
		try {
			String str = annonce.getText();
			a = Integer.parseInt(str);
		} catch (NumberFormatException e) {
		}
		return a;
	}

	class Listener implements ActionListener {

		public void actionPerformed(ActionEvent arg0) {

			if (getAnnonce() < 1 || getAnnonce() > (Max - 1)) {
				JOptionPane.showMessageDialog(null, "Le nombre doit être compris entre 0 et " + Max + ".");
				annonce.setText("");
			} else if (b) {
				nombre_secret2 = getAnnonce();
				annonce.setText("");
				défilement.setText("");
				JOptionPane.showMessageDialog(null, "Vous devez maintenant trouver le nombre de l'ordinateur.");
				devArea.setText("\tMode développeur activé !\n Réponse du joueur:  " + nombre_secret
						+ "\tRéponse de l'ordinateur :" + nombre_secret2);
				b = false;
			} else {
				start();
				startIA();
				annonce.setText("");
			}
		}

	}
}
