package fr.jeux.model;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import javax.swing.JOptionPane;

public class FichierConfig {

	private int nombreMax = 0, nombreEssai = 0, nombre_chiffre, NombreMaxMastermind;
	private String difficulty = "";
	private boolean modeDevB;

	public FichierConfig() {
		initDifficulty();
	}

	// Méthode pour initialiser la difficulté
	public void initDifficulty() {
		initProperties();
		Logging.logger.info("Initialisation difficulté");
		try {
			int a = Integer.parseInt(difficulty);
			if (a == 1) {
				nombreMax = 100;
				nombreEssai = 20;
				difficulty = "Facile";
				nombre_chiffre = 3;
			}
			if (a == 2) {
				nombreMax = 1000;
				nombreEssai = 15;
				difficulty = "Moyen";
				nombre_chiffre = 4;
			}
			if (a == 3) {
				nombreMax = 10000;
				nombreEssai = 10;
				difficulty = "Difficile";
				nombre_chiffre = 5;
			}
			// Si Difficulty_Plus_ou_Moins n'est pas égal à 1, 2 ou 3, ce bloc est utilisé
			if (a != 1 && a != 2 && a != 3) {
				Logging.logger.fatal("Fichier config mal initialiser");
				JOptionPane.showMessageDialog(null, "Le fichier config.properties à été mal iniatialiser");
				System.exit(0);
			}
			// Si Difficulty_Plus_ou_Moins n'a aucune valeur, ce bloc est utilisé
		} catch (NumberFormatException e) {
			Logging.logger.fatal("Fichier config mal initialiser");
			JOptionPane.showMessageDialog(null, "Le fichier config.properties à été mal iniatialiser");
			System.exit(0);
		}
	}

	// Méthode de lecture du fichier properties
	public void initProperties() {
		Properties prop = new Properties();
		try {
			Logging.logger.info("Chargement fichier config.properties");

			prop.load(new FileInputStream("file/config.properties"));

			difficulty = prop.getProperty("Difficulty_Plus_ou_Moins");
			modeDevB = Boolean.parseBoolean(prop.getProperty("Mode_développeur"));
			NombreMaxMastermind = Integer.parseInt(prop.getProperty("Nombre_Chiffre_Mastermind"));

			try {
				if (NombreMaxMastermind < 4 || NombreMaxMastermind > 10) {
					Logging.logger.fatal("Fichier config mal initialiser");
					JOptionPane.showMessageDialog(null, "Le fichier config.properties à été mal iniatialiser");
					System.exit(0);
				}
				// si NombreMaxMastermind n'est pas un chiffre ou nul, ce bloc est utilisé
			} catch (NumberFormatException e) {
				Logging.logger.fatal("Fichier config mal initialiser");
				JOptionPane.showMessageDialog(null, "Le fichier config.properties à été mal iniatialiser");
				System.exit(0);
			}

			// si le fichier est inexistant
		} catch (IOException e) {
			Logging.logger.fatal("Fichier config introuvable");
			JOptionPane.showMessageDialog(null, "Le fichier config.properties est introuvable");
			System.exit(0);
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
	
	public void setModeDev(boolean modeDevB) {
		this.modeDevB = modeDevB;
	}

	public int getNombre_chiffre() {
		return nombre_chiffre;
	}

	public int getNombreMaxMastermind() {
		return NombreMaxMastermind;
	}

}
