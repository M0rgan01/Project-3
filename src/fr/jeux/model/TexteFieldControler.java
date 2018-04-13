package fr.jeux.model;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JOptionPane;
import javax.swing.JTextField;


public class TexteFieldControler implements KeyListener{

	private JTextField texte = new JTextField();


	
	public TexteFieldControler(JTextField texte) {
	this.texte = texte;	
	}
	
	
	
	public void keyPressed(KeyEvent arg0) {}


	public void keyReleased(KeyEvent k) {
	
		 if(!isNumeric(k.getKeyChar())) {
			    texte.setText(texte.getText().replace(String.valueOf(k.getKeyChar()), ""));  
			   if( k.getKeyCode() != 8 &&  k.getKeyCode() != 10) {
				JOptionPane.showMessageDialog(null, "Vous devez entrer un chiffre");
			   }
			    }	    
		 }
	
	public void keyTyped(KeyEvent arg0) {}
	
	 private boolean isNumeric(char carac){
		    try {
		      Integer.parseInt(String.valueOf(carac));
		    }
		    catch (NumberFormatException e) {
		 	
		      return false;    
		    }
		    return true;
		  }
	 
	 
		}


