package application;

public interface GestionUtilisateurs {
	public void consulterUtilisateurs();
	public void ajouterUtilisateur();
	public void supprimerUtilisateur(int id);
	public void modifierUtilisateur(int id);
	public void rechercherUtilisateurById(int id);
	public void rechercherUtilisateurByNomPrenom(String nomPrenom);
}
