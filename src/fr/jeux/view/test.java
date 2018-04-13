package fr.jeux.view;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagLayout;
 
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
 
public class test {
 
	public static void main(String[] args) {
 
		JFrame frame = new JFrame("Exemple");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
 
		// un panneau pour y mettre les différents composants à afficher, avec un cardlayout
		CardLayout cardLayout = new CardLayout();
		JPanel panel = new JPanel(cardLayout);
		frame.getContentPane().add(panel); // on le met au centre du content pane de la fenêtre pour qu'il soit affiché
 
		// un panneau pour les boutons
		JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
 
		// je créé x boutons avec x panels
 
		// je créé un panel (juste avec un texte centré dedans et une couleur pour qu'on le voit bien, pour l'exemple, mais tu fais ce que tu veux)
		JPanel panelOrange = createPanel("Orange", Color.ORANGE);
 
		// j'en créé un deuxième
		JPanel panelBleu = createPanel("Bleu", Color.CYAN);
 
		// j'en créé un troisième
		JPanel panelVert = createPanel("Vert", Color.GREEN);
 
		// j'en créé un de plus
		JPanel panelJaune = createPanel("Jaune", Color.YELLOW);
 
		creerBouton(panel, cardLayout, buttonPanel, "Afficher le panel orange", "ORANGE", panelOrange);
		creerBouton(panel, cardLayout, buttonPanel, "Afficher le panel bleu", "BLEU", panelBleu);
		creerBouton(panel, cardLayout, buttonPanel, "Afficher le panel vert", "VERT", panelVert);
		creerBouton(panel, cardLayout, buttonPanel, "Afficher le panel jaune", "JAUNE", panelJaune);
 
		buttonPanel.doLayout();
		buttonPanel.setPreferredSize(new Dimension(0,buttonPanel.getPreferredSize().height*2));
 
		// je mets mes boutons en bas
		frame.getContentPane().add(buttonPanel, BorderLayout.SOUTH);
 
		frame.setSize(600, 400);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true); 
 
	}
 
	private static JPanel createPanel(String name, Color color) {
		JPanel panel = new JPanel(new GridBagLayout()); // le gridbaglayout c'est juste pour centrer le texte
		panel.setBackground(color);
		panel.add(new JLabel(name));
		return panel;
	}
 
	private static JButton creerBouton(
			JPanel cardLayoutPanel, // le conteneur avec son layout cardlayout
			CardLayout cardLayout, // la cardlayout
			JPanel buttonPanel,  // le panel des boutons pour pouvoir mettre le bouton dedans
			String texteDuBouton, // le texte du bouton
			String identifiantPanel, // il faut un identifiant de panel pour pouvoir le sélectionner dans le cardlayout
			JPanel panel // le panneau à afficher quand on clique sur le bouton
			) {
 
		// je créé le bouton
		JButton button = new JButton(texteDuBouton);
 
		// je mets le bouton dans le panneau des boutons
		buttonPanel.add(button);
 
		// j'ajoute le panel au cardLayoutPanel :
		cardLayoutPanel.add(panel, identifiantPanel);
 
		// ajoute un action listener pour afficher le panel quand on clique sur le bouton
		button.addActionListener(e-> 
		cardLayout.show(cardLayoutPanel, identifiantPanel) // au clic, on demande d'afficher le composant qui correspond à l'identifiant
		);
 
		return button;
 
	}
 
}