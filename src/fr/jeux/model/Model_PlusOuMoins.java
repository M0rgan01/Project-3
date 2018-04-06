package fr.jeux.model;

public class Model_PlusOuMoins {

	private int nombre_secret;
	private int nombre_user;
	private int resultat;
	
	
	
	public void start(){
		nombre_secret = (int) (Math.random()*999)+1;
	  do {
		System.out.println("Entrer un nombre entre 0 et 1000");
		//nombre_user = sc.nextInt();
		
		if (nombre_secret == nombre_user) {
			System.out.println("f�licitation !!!");
			System.out.println("Vous avez essay� "+resultat+" fois avant de trouver le bon nombre");
		}
		
		else if (nombre_user > nombre_secret) {
			System.out.println("votre nombre est trop grand");
			resultat++;
		}
		else {
			System.out.println("votre nombre est trop petit");
			resultat++;
		}
		
	}while (nombre_secret != nombre_user);
	
	
	
	}
	
	
	
	
}
