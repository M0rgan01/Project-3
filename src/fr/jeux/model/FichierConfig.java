package fr.jeux.model;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import javax.swing.JOptionPane;

public class FichierConfig {

	private int nombreMax = 0, nombreEssai = 0;
	private String difficulty = "";
	private boolean modeDevB;

	public FichierConfig() {
		initDifficulty();
	}

	public void initDifficulty() {
		initProperties();
		try {
			int a = Integer.parseInt(difficulty);
			if (a == 1) {
				nombreMax = 100;
				nombreEssai = 20;
				difficulty = "Facile";
			}
			if (a == 2) {
				nombreMax = 1000;
				nombreEssai = 15;
				difficulty = "Moyen";
			}
			if (a == 3) {
				nombreMax = 10000;
				nombreEssai = 10;
				difficulty = "Difficile";
			}
			if (a != 1 && a != 2 && a != 3) {
				JOptionPane.showMessageDialog(null, "Le fichier config.properties à été mal iniatialiser");
				System.exit(0);
			}
		} catch (NumberFormatException e) {
			JOptionPane.showMessageDialog(null, "Le fichier config.properties à été mal iniatialiser");
			System.exit(0);
		}
	}

	public void initProperties() {
		Properties prop = new Properties();
		try {
			prop.load(new FileInputStream("file/config.properties"));
			difficulty = prop.getProperty("Difficulty_Plus_ou_Moins");
			modeDevB = Boolean.parseBoolean(prop.getProperty("Mode_développeur"));
		} catch (IOException e) {
		}
	}

	public int getNombreMax() {
		return nombreMax;
	}

	public int getNombreEssai() {
		return nombreEssai;
	}

	public String getDifficulty() {
		return difficulty;
	}

	public boolean getModeDev() {
		return modeDevB;
	}
}
