package fr.jeux.model;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

//Class de vérification la saisie clavier
public class TexteFieldControler implements KeyListener {

	private JTextField texte = new JTextField();

	public TexteFieldControler(JTextField texte) {
		this.texte = texte;
	}

	public void keyPressed(KeyEvent arg0) {}

	public void keyReleased(KeyEvent k) {
		// si le caractère n'est pas un chiffre
		if (!isNumeric(k.getKeyChar())) {
			texte.setText(texte.getText().replace(String.valueOf(k.getKeyChar()), ""));

			// et s'il ne correspond pas à la touche Retour ou entrée
			if (k.getKeyCode() != 8 && k.getKeyCode() != 10) {
				Logging.logger.error("Saisi d'un caractère incorrect");
				JOptionPane.showMessageDialog(null, "Vous devez entrer un chiffre");

			}
		}
	}

	public void keyTyped(KeyEvent arg0) {}

	// méthode de vérification de chiffre
	private boolean isNumeric(char carac) {
		try {
			Integer.parseInt(String.valueOf(carac));
		} catch (NumberFormatException e) {
			// si le caractère n'est pas un chiffre, retourne false
			return false;
		}
		return true;
	}

}
