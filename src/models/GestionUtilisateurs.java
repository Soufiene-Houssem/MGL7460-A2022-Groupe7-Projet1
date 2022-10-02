package models;

import java.util.List;

import application.Utilisateur;

public interface GestionUtilisateurs {
	public boolean ajouterUtilisateur();
	public boolean modifierUtilisateur(Utilisateur user);
	public boolean supprimerUtilisateur(int id);
	public List<Utilisateur> consulterUtilisateurs();
	public List<Utilisateur> chercherUtilisateurByNomPrenom(String nomPrenom);
	public Utilisateur chercherUtilisateurById(int id);
}
