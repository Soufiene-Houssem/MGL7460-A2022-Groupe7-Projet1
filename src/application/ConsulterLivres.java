package application;

public interface ConsulterLivres {
	public void consulterLivres();
	public void rechercherLivreByIsbn(int id);
	public void rechercherLivreByTitre(String title);
	public void rechercherLivreByAuteur(String auteur);
}
