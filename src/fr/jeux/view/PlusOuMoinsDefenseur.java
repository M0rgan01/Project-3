package fr.jeux.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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


public class PlusOuMoinsDefenseur extends Container{
	
	FichierConfig FC = new FichierConfig();
	private JPanel panelNord = new JPanel(), panelCentre = new JPanel(), panelAnnonce = new JPanel();
	private int Min = 0,
			Max = FC.getNombreMax(),
			nombre_secret = 0,
			nombre_user = 0,
			essai = FC.getNombreEssai();
	private String difficulty = FC.getDifficulty();
	private JLabel titre = new JLabel("Plus ou moins : Mode Défenseur"),
				   difficultyLabel = new JLabel("Difficulté : " + difficulty),
				   nombreEssai = new JLabel("Nombre d'essaie restant : " + essai),
				   annonceLabel = new JLabel("Saisir un nombre :"),
				   devLabel = new JLabel("Mode développeur activé, réponse : " + nombre_secret);
	private JTextArea explication = new JTextArea("Saissisez un nombre entre " + Min + " et " + (Max)
			+ ", \nl'ordinateur devra trouver le nombre secret.");
	private JTextArea défilement = new JTextArea();
	private JScrollPane scroll = new JScrollPane(défilement);
	private JTextField annonce = new JTextField();
	private JButton button = new JButton("Ok");
	private Thread T;
	
	public PlusOuMoinsDefenseur() {
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
		
		if(FC.getModeDev())
			this.panel.add(devLabel);
		
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
	
	
	public void startIA() {
		nombre_user = Max / 2;
		do {
			
			if (nombre_secret < nombre_user) {
				défilement.append("L'ordinateur donne la valeur " + nombre_user +"\n");
				défilement.append("Le nombre trouver par l'ordinateur est trop grand\n");
				défilement.append("-----------------------------\n");
				essai--;
				Max = nombre_user;
				nombre_user = (Max + Min) / 2;
			}
			if (nombre_secret > nombre_user) {
				défilement.append("L'ordinateur donne la valeur " + nombre_user +"\n");
				défilement.append("Le nombre trouver par l'ordinateur est trop petit\n");
				défilement.append("-----------------------------\n");
				essai--;
				Min = nombre_user;
				nombre_user = (Min + Max) / 2;
			}
			if (essai == 0) 
				nombre_secret = nombre_user;
				
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		} while ((nombre_secret != nombre_user));
		
		if( essai == 0) {
		perdu();
		}
		else {
		défilement.append("L'ordinateur donne la valeur " + nombre_user +"\n");
		gagné();
		}
		}
	
	
	
	public void perdu() {
		JOptionPane.showMessageDialog(null,
				"L'ordinateur a perdu ! La réponse étais " + nombre_secret + "\n" + "Vous pouvez changer de jeu ou recommencer depuis le menu fichier");
		annonce.setEditable(false);
		button.setEnabled(false);
		défilement.append("Le nombre d'essai de l'ordinateur est a 0.");
	}

	public void gagné() {
		JOptionPane.showMessageDialog(null, "L'ordinateur à trouver le nombre secret !\n"
				+ "Vous pouvez changer de jeu ou recommencer depuis le menu fichier");
		annonce.setEditable(false);
		button.setEnabled(false);
		défilement.append("L'ordinateur à gagné ! Il lui restait " + essai + " essai(s).");
	}
	
	public class Listener implements ActionListener {

		public void actionPerformed(ActionEvent arg0) {

			if (getAnnonce() < 1 || getAnnonce() > (Max-1)) {
				JOptionPane.showMessageDialog(null, "Le nombre doit être compris entre 0 et " + (Max) + ".");
				annonce.setText("");
			} else {
				nombre_secret = getAnnonce();
				T = new Thread(new IA());
				T.start();
				défilement.append("L'ordinateur commence à chercher la bonne réponse :\n");
				annonce.setText("");
				devLabel.setText("Mode développeur activé, réponse : " + nombre_secret);
			}
		}
	}
	
	 class IA implements Runnable{
		    public void run() {
		      startIA();                 
		    }               
		  }     
}
	
	

