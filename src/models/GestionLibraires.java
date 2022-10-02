package models;

import java.util.List;

import application.Libraire;

public interface GestionLibraires {
	public List<Libraire> consulterLibraires();
	public boolean ajouterLibraire();
	public boolean supprimerLibraire(int id);
	public Libraire chercherLibraireById(int id);
	public List<Libraire> chercherLibraireByNomPrenom(String nomPrenom);
}
