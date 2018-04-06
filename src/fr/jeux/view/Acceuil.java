package fr.jeux.view;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JTextArea;

public class Acceuil extends Container {

	private JLabel titre = new JLabel("Bienvenue dans l'application du project 3 de openclassrooms");
	private JTextArea explication1 = new JTextArea("Dans cette application, vous aurez le choix entre 2 jeux : \n"
												+ " - Le jeux du Plus ou Moins, ou l'on dois trouver le nombre secret à l'aide d'indicateur.\n"
												+ " - Le Mastermind, ou l'on dois trouver la combinaison secrète.");
	private JTextArea explication2 = new JTextArea("Chaque jeux propose 3 modes de jeux :\n"
												+ " - Le mode Challenger, où vous devez trouver la combinaison secrète de l'ordinateur.\n"
												+ " - Le mode Défenseur, où c'est à l'ordinateur de trouver votre combinaison secrète.\n"
												+ " - Le mode Duel, où l'ordinateur et vous jouez tour à tour,\n   le premier à trouver la combinaison secrète de l'autre a gagné.");
	private JTextArea explication3 = new JTextArea("Pour commencer un jeu, entrer dans le menu Fichier,\n"
												+ "choisissez votre jeu et votre mode parmis ceux proposés.");
	
	public Acceuil() {
	super();
	initPanel();
	}
	
	
	protected void initPanel() {
	titre.setBorder(BorderFactory.createEtchedBorder());
	explication1.setBorder(BorderFactory.createEmptyBorder(30, 10, 30, 10));
	explication2.setBorder(BorderFactory.createEmptyBorder(30, 10, 30, 10));
	explication3.setBorder(BorderFactory.createEmptyBorder(30, 10, 30, 10));
	titre.setFont(comics20);
	explication1.setFont(comics15);
	explication2.setFont(comics15);
	explication3.setFont(comics15);
	explication1.setEditable(false);
	explication2.setEditable(false);
	explication3.setEditable(false);
	this.panel.add(titre);
	this.panel.add(explication1);
	this.panel.add(explication2);
	this.panel.add(explication3);
	}

}
