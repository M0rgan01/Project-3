package fr.jeux.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.PrimitiveIterator;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import fr.jeux.model.DifficultyPlusOuMoins;
import fr.jeux.model.TexteFieldControler;

public class PlusOuMoinsChallenger extends Container {
	
	DifficultyPlusOuMoins DPM = new DifficultyPlusOuMoins();
	private JPanel panelNord = new JPanel(),
				   panelCentre = new JPanel(),
				   panelAnnonce = new JPanel();
	private int essai;
	private int Min = 0;
	private int Max = DPM.getNombreMax();;
	private String difficulty = DPM.getDifficulty();
	private JLabel titre = new JLabel("Plus ou moins : Mode Challenger");
	private JLabel difficultyLabel = new JLabel("Difficulté : "+ difficulty);
	private JLabel nombreEssai = new JLabel("Nombre d'essaie restant : " + essai );
	private JLabel annonceLabel = new JLabel("Saisir un nombre :");
	private JTextArea explication = new JTextArea("Saissisez un nombre entre " + Min + " et " + Max + ", \nl'ordinateur vous guidera pour trouver le nombre secret.");
	private JTextArea défilement = new JTextArea();
	private JScrollPane scroll = new JScrollPane(défilement);
	private JTextField annonce = new JTextField();
	private JButton button = new JButton("Ok");
	
	public PlusOuMoinsChallenger() {
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
	button.addActionListener(new ButtonListener());
	
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
	panelNord.add(titre,BorderLayout.NORTH);
	panelNord.add(difficultyLabel);
	
	panelCentre.setLayout(new BorderLayout());
	panelCentre.add(explication, BorderLayout.NORTH);
	panelCentre.add(panelAnnonce);
	panelCentre.add(scroll, BorderLayout.SOUTH);
	
	this.panel.add(panelNord);
	this.panel.add(panelCentre);
		
	}

	
public void initGame() {
		
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

class ButtonListener implements ActionListener{


	public void actionPerformed(ActionEvent arg0) {
		
	if(getAnnonce() < 1 || getAnnonce() > Max) {
		JOptionPane jop = new JOptionPane();
		jop.showMessageDialog(null, "Le nombre doit être compris entre 0 et " + Max + ".");
		annonce.setText("");
	}
	else {	
	System.out.println(getAnnonce());
	}
	}
	
}

}

	


