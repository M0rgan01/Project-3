package fr.jeux.view;

import java.awt.Color;
import java.awt.Font;
import javax.swing.JPanel;

public abstract class Container extends JPanel{
	
	protected JPanel panel;
	protected Font comics30 = new Font("Comics Sans MS", Font.BOLD, 30);
	protected Font comics40 = new Font("Comics Sans MS", Font.BOLD, 40);
	protected Font comics20 = new Font("Comics Sans MS", Font.BOLD, 20);
	
	
	
	public Container() {
		this.panel = new JPanel();
		this.panel.setBackground(Color.white);

	}

	protected JPanel getPanel() {
		return this.panel;
	}

	protected abstract void initPanel();

}
	
	
	

