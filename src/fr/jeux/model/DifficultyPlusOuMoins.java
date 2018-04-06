package fr.jeux.model;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import javax.swing.JOptionPane;

public class DifficultyPlusOuMoins {
	
	private int nombreMax = 0;
	private String difficulty = "";
	
	public DifficultyPlusOuMoins() {
initDifficulty();
	}
	

	public void initDifficulty() {
	initProperties();
	int a = Integer.parseInt(difficulty);
		if(a == 1) {
		nombreMax = 100;
		difficulty = "Facile";
		}
		if(a == 2) {
			nombreMax = 1000;
			difficulty = "Moyen";
			}
		if(a == 3) {
			nombreMax = 10000;
			difficulty = "Difficile";
			}
		if(a != 1 && a != 2 && a != 3) {
			JOptionPane jop = new JOptionPane();
			jop.showMessageDialog(null, "Le fichier config.properties à été mal iniatialiser");
		}
	}
	
	
	
	public void initProperties(){
	Properties prop = new Properties();
	try {
	    prop.load(new FileInputStream("file/config.properties"));
	    difficulty = prop.getProperty("Difficulty_Plus_ou_Moins");

	    
	} catch (IOException e) {
	}
}


	public int getNombreMax() {
		return nombreMax;
	}


	public String getDifficulty() {
		return difficulty;
	}
		
	
	
	
}

