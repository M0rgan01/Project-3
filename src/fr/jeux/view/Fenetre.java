package fr.jeux.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

public class Fenetre extends JFrame {

	private JMenuBar menuBar = new JMenuBar();
	private JMenu menuFichier = new JMenu("Fichier");
	private JMenu menuJeu = new JMenu("Jeux");
	private JMenu menuJeuPlusOuMoins = new JMenu("Plus ou moins");
	private JMenu menuJeuMastermind = new JMenu("Mastermind");
	private JMenu menuApropos = new JMenu("A propos");
	private JMenuItem itemModeChallenger = new JMenuItem("Mode Challenger");
	private JMenuItem itemModeDefenseur = new JMenuItem("Mode Défenseur");
	private JMenuItem itemModeDuel = new JMenuItem("Mode duel");
	private JMenuItem itemModeChallenger2 = new JMenuItem("Mode Challenger");
	private JMenuItem itemModeDefenseur2 = new JMenuItem("Mode Défenseur");
	private JMenuItem itemModeDuel2 = new JMenuItem("Mode duel");
	private JMenuItem itemQuitter = new JMenuItem("Quitter");
	private JMenuItem itemApropos = new JMenuItem("?");
	private boolean AcceuilB = true, PlusoumoinsChallengerB = false, PlusoumoinsDefenseurB = false,
			PlusoumoinsDuelB = false, MastermindChallengerB = false, MastermindDefenseurB = false;

	public Fenetre() {

		this.setTitle("Project 3");
		this.setSize(700, 500);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.setResizable(false);

		itemModeChallenger.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				AcceuilB = false;
				PlusoumoinsChallengerB = true;
				PlusoumoinsDefenseurB = false;
				PlusoumoinsDuelB = false;
				MastermindChallengerB = false;
				MastermindDefenseurB = false;
				initContainer();
			}
		});

		itemModeDefenseur.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				AcceuilB = false;
				PlusoumoinsChallengerB = false;
				PlusoumoinsDefenseurB = true;
				PlusoumoinsDuelB = false;
				MastermindChallengerB = false;
				MastermindDefenseurB = false;
				initContainer();

			}
		});

		itemModeDuel.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				AcceuilB = false;
				PlusoumoinsChallengerB = false;
				PlusoumoinsDefenseurB = false;
				PlusoumoinsDuelB = true;
				MastermindChallengerB = false;
				MastermindDefenseurB = false;
				initContainer();
			}
		});

		itemModeChallenger2.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				AcceuilB = false;
				PlusoumoinsChallengerB = false;
				PlusoumoinsDefenseurB = false;
				PlusoumoinsDuelB = false;
				MastermindChallengerB = true;
				MastermindDefenseurB = false;
				initContainer();

			}
		});

		itemModeDefenseur2.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				AcceuilB = false;
				PlusoumoinsChallengerB = false;
				PlusoumoinsDefenseurB = false;
				PlusoumoinsDuelB = false;
				MastermindChallengerB = false;
				MastermindDefenseurB = true;
				initContainer();

			}
		});

		itemModeDuel2.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				AcceuilB = false;
				PlusoumoinsChallengerB = false;
				PlusoumoinsDefenseurB = false;
				PlusoumoinsDuelB = false;
				MastermindChallengerB = false;
				MastermindDefenseurB = false;
				initContainer();

			}
		});

		itemApropos.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				JOptionPane.showMessageDialog(null, "Project 3 - Java\n" + "Par Pichat Morgan", "A propos",
						JOptionPane.INFORMATION_MESSAGE);

			}
		});
		menuJeuPlusOuMoins.add(itemModeChallenger);
		menuJeuPlusOuMoins.add(itemModeDefenseur);
		menuJeuPlusOuMoins.add(itemModeDuel);
		menuJeu.add(menuJeuPlusOuMoins);
		menuJeuMastermind.add(itemModeChallenger2);
		menuJeuMastermind.add(itemModeDefenseur2);
		menuJeuMastermind.add(itemModeDuel2);
		menuJeu.add(menuJeuMastermind);
		menuFichier.add(menuJeu);
		itemQuitter.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				System.exit(0);

			}
		});
		menuFichier.add(itemQuitter);
		menuApropos.add(itemApropos);
		menuBar.add(menuFichier);
		menuBar.add(menuApropos);
		this.setJMenuBar(menuBar);
		initContainer();
	}

	// méthode pour définir le panel
	public void initContainer() {

		if (AcceuilB) {
			Acceuil acceuil = new Acceuil();
			this.setSize(700, 500);
			this.setContentPane(acceuil.getPanel());
		}

		else if (PlusoumoinsChallengerB) {
			PlusOuMoinsChallenger POMC = new PlusOuMoinsChallenger();
			this.setSize(600, 500);
			this.setContentPane(POMC.getPanel());
			repaint();
		}

		else if (PlusoumoinsDefenseurB) {
			PlusOuMoinsDefenseur POMD = new PlusOuMoinsDefenseur();
			this.setSize(600, 500);
			this.setContentPane(POMD.getPanel());
			repaint();
		}

		else if (PlusoumoinsDuelB) {
			PlusOuMoinsDuel POMDuel = new PlusOuMoinsDuel();
			this.setSize(700, 600);
			this.setContentPane(POMDuel.getPanel());
			repaint();

		} else if (MastermindChallengerB) {
			MastermindChallenger MMC = new MastermindChallenger();
			this.setSize(700, 550);
			this.setContentPane(MMC.getPanel());
			repaint();

		} else if (MastermindDefenseurB) {
			MastermindDefenseur MMD = new MastermindDefenseur();
			this.setSize(700, 550);
			this.setContentPane(MMD.getPanel());
			repaint();

		} else {
			MastermindDuel MMDuel = new MastermindDuel();
			this.setSize(700, 600);
			this.setContentPane(MMDuel.getPanel());
			repaint();
		}

		this.setVisible(true);
	}
}