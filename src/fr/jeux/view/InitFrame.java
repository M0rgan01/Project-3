package fr.jeux.view;

import javax.swing.JFrame;

public class InitFrame extends JFrame{

	private boolean AcceuilB = false,
			PlusoumoinsChallengerB = true,
			PlusoumoinsDefenseurB = false,
			PlusoumoinsDuelB = false,
			MastermindChallengerB = false,
			MastermindDefenseurB = false,
			MastermindDuelB = false;
	
	public void initContainer() {
		if(AcceuilB) {
			Acceuil acceuil = new Acceuil();
			this.setContentPane(acceuil.getPanel());
		}
		
		if(PlusoumoinsChallengerB) {
			PlusOuMoinsChallenger POMC = new PlusOuMoinsChallenger();
			this.setContentPane(POMC.getPanel());
		}
	}
	
	
	
}
