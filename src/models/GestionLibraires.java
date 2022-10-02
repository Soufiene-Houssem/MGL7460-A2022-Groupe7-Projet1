package models;

import java.util.List;

import application.Libraire;

public interface GestionLibraires {
	public List<Libraire> consulterLibraires();
	public boolean supprimerLibraire(int id);
}
