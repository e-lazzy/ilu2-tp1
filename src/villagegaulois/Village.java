package villagegaulois;

import personnages.Chef;
import personnages.Gaulois;

public class Village {
	private String nom;
	private Chef chef;
	private Gaulois[] villageois;
	private int nbVillageois = 0;
	private Marche marche;
	public Village(String nom, int nbVillageoisMaximum,int nbEtal) {
		this.nom = nom;
		villageois = new Gaulois[nbVillageoisMaximum];
		marche=new Marche(nbEtal);
	}

	public String getNom() {
		return nom;
	}

	public void setChef(Chef chef) {
		this.chef = chef;
	}

	public void ajouterHabitant(Gaulois gaulois) {
		if (nbVillageois < villageois.length) {
			villageois[nbVillageois] = gaulois;
			nbVillageois++;
		}
	}

	public Gaulois trouverHabitant(String nomGaulois) {
		if (nomGaulois.equals(chef.getNom())) {
			return chef;
		}
		for (int i = 0; i < nbVillageois; i++) {
			Gaulois gaulois = villageois[i];
			if (gaulois.getNom().equals(nomGaulois)) {
				return gaulois;
			}
		}
		return null;
	}

	public String afficherVillageois() {
		StringBuilder chaine = new StringBuilder();
		if (nbVillageois < 1) {
			chaine.append("Il n'y a encore aucun habitant au village du chef "
					+ chef.getNom() + ".\n");
		} else {
			chaine.append("Au village du chef " + chef.getNom()
					+ " vivent les légendaires gaulois :\n");
			for (int i = 0; i < nbVillageois; i++) {
				chaine.append("- " + villageois[i].getNom() + "\n");
			}
		}
		return chaine.toString();
	}
	private static class Marche{
		private Etal[] etals;
		private int nbEtals;
		public Marche(int nbEtals) {
			this.nbEtals=nbEtals;
			etals= new Etal[nbEtals];
			for(int i=0;i<nbEtals;i++) {
				etals[i]=new Etal();
			}
		}
		
		public void utiliserEtal(int indiceEtal, Gaulois vendeur, String produit, int nbProduit) {
			etals[indiceEtal].occuperEtal(vendeur, produit, nbProduit);
		}
		
		public int trouverEtalLibre() {
			for(int i=0;i<nbEtals;i++) {
				if(!etals[i].isEtalOccupe()) {
					return i;
				}
			}
			return -1;
		}
		public Etal[] trouverEtals(String produit) {
			int nbEtalsAvecProduit=0;
			for(int i=0;i<nbEtals;i++) {
				if(etals[i].contientProduit(produit)) {
					nbEtalsAvecProduit++;
				}
			}
			if (nbEtalsAvecProduit==0) return null;
			
			Etal[] etalsAvecProduit = new Etal[nbEtalsAvecProduit];
			for(int i=0;i<nbEtals;i++) {
				if(etals[i].contientProduit(produit)) {
					etalsAvecProduit[nbEtalsAvecProduit]=etals[i];
				}
			}
			return etalsAvecProduit;
		}
		public Etal trouverVendeur(Gaulois gaulois) {
			for(int i=0;i<nbEtals;i++) {
				if(etals[i].getVendeur()==gaulois) {
					return etals[i];
				}
			}
			return null;
		}
		public void afficherMarche() {
			int nbEtalVide=0;
			
			for(int i=0;i<nbEtals;i++) {
				if(etals[i].isEtalOccupe()) etals[i].afficherEtal();
				else nbEtalVide++;
			}
			
			System.out.println("Il reste" + nbEtalVide + "étals non utilisés dans le marché.");
		}
		}
	//Methode Village+Marche
	
	public String installerVendeur(Gaulois gaulois, String produit, int nbProduit) {
		int nEtal=marche.trouverEtalLibre();
		StringBuilder text= new StringBuilder();
		marche.utiliserEtal(nEtal, gaulois, produit, nbProduit);
		text.append("Bonemine cherche un endroit pour vendre "+ nbProduit +" "+ produit +".\r\n"
				+ "Le vendeur Bonemine vend "+ produit +" à l'étal n°"+ nEtal +".");
		return text.toString();
	}

	public String rechercherVendeursProduit(String produit){
		StringBuilder text= new StringBuilder();
		Etal[] etalsAvecProduit;
		int nVendeurParProduit=marche.trouverEtals(produit).length;
		if(nVendeurParProduit>0) {
			etalsAvecProduit=new Etal[nVendeurParProduit];
			etalsAvecProduit=marche.trouverEtals(produit);
			text.append("Les vendeurs qui proposent des fleurs sont :\n");
			for(int i=0;i<nVendeurParProduit;i++) {
				text.append("-"+etalsAvecProduit[i]+"\n");
			}
			
		}
		else {
			text.append("Il n'y a pas de vendeur avec ce produit sur le marche");
		}
		return text.toString();
		}

	public Etal rechercherEtal(Gaulois gaulois) {
			
		return marche.trouverVendeur(gaulois);
	}

	public String partirVendeur(Gaulois gaulois) {
		Etal etal =rechercherEtal(gaulois);		
		return etal.libererEtal();
	}
		
	
	
}