package application;

public interface GestionLibraires {
	public void consulterLibraires();
	public void ajouterLibraire(Libraire libraire);
	public void supprimerLibraire(int id);
	public void modifierLibraire(Libraire libraire);
	public void rechercherLibraireById(int id);
	public void rechercherLibraireByNomPrenom(String nomPrenom);
}
