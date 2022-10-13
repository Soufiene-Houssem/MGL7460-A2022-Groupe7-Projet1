package application;

public interface GestionLibraires {
	public void consulterLibraires();
	public void ajouterLibraire();
	public void supprimerLibraire(int id);
	public void modifierLibraire(int id);
	public void rechercherLibraireById(int id);
	public void rechercherLibraireByNomPrenom(String nomPrenom);
}
