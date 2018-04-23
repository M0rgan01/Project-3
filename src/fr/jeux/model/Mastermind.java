package fr.jeux.model;

public class Mastermind {
	
	//Renvoie le nombre de chiffres communs entre deux combinaisons(tableau)
	public int nbCommuns(int[] tab1, int[] tab2, int k) {
   
        int[] t1 = new int[k];
        int[] t2 = new int[k];
        for (int i = 0; i < k; i++) {
            t1[i] = tab1[i];
            t2[i] = tab2[i];
        }
        
        int cpt = 0;
        boolean trouve = false;
        
        for (int i = 0; i < k; i++) {
            int j = 0;
            trouve = false;
            do {
                if (t1[i] == t2[j]) {
                    cpt++;
                    t2[j] = 0;
                    trouve = true;
                }
                j++;
            } while (j < k && !trouve);
        }        
        return cpt;
    }
	
	//Renvoie le nombre de chiffres bien placé entre deux combinaisons(tableau)
		 public int nbBienPlace(int[] tab1, int[] tab2, int k) {
		        int nb_bien_place = 0;
		        for (int i = 0; i < k; i++) {
		            if (tab1[i] == tab2[i]) {
		                nb_bien_place++;
		            }
		        }
		        return nb_bien_place;
		    }
		
}
