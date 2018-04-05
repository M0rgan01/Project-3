package fr.jeux.view;

import java.awt.BorderLayout;

import javax.swing.JLabel;
import javax.swing.JTextArea;

public class Acceuil extends Container {

	private JLabel titre = new JLabel("Bienvenue dans l'application du project 3 de openclassrooms");
	private JTextArea explication1 = new JTextArea("Dans cette application vous aurez le choix entre 2 jeux : \n"
												+ " - Le jeux du Plus ou Moins, ou l'on dois trouver le nombre secret à l'aide d'indicateur.\n"
												+ " - Le Mastermind, ou l'on dois trouver la combinaison secrète.");
	
	private JTextArea explication2 = new JTextArea("Chaque jeux propose 3 mode de jeux :\n"
												+ " - Le mode Challenger, où vous devez trouver la combinaison secrète de l'ordinateur.\n"
												+ " - Le mode Défenseur, où c'est à l'ordinateur de trouver votre combinaison secrète.\n"
												+ " - Le mode Duel, où l'ordinateur et vous jouez tour à tour, le premier à trouver la combinaison secrète de l'autre a gagné.");
	
	
	
	public Acceuil() {
	super();
	initPanel();
	}
	
	
	protected void initPanel() {
	this.panel.setLayout(new BorderLayout());
	this.panel.add(comp)
		
	}

}
