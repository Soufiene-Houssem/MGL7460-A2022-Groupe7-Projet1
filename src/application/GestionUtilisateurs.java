package application;

public interface GestionUtilisateurs {
	public void consulterUtilisateurs();
	public boolean ajouterUtilisateur();
	public void supprimerUtilisateur(int id);
	public void modifierUtilisateur(Utilisateur user);
	public void rechercherUtilisateurById(int id);
	public void rechercherUtilisateurByNomPrenom(String nomPrenom);
}
