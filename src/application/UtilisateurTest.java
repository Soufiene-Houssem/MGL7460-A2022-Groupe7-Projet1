package application;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Test;

import entities.Livre;

class UtilisateurTest {

	@Test
	void testConsulterLivres() {
		Utilisateur user = new Utilisateur("Houssem","Soufiene","fjdonf@gmail.com","123456","sdfsdfsd",2564184,1);
		List<Livre> livres = user.consulterLivres();
		assertEquals(1, livres.size());
	}

	@Test
	void testRechercherLivre() {
		Utilisateur user = new Utilisateur("Houssem","Soufiene","fjdonf@gmail.com","123456","sdfsdfsd",2564184,1);
		Livre livre = user.rechercherLivre(1);
		int annee = livre.getAnnee();
		assertEquals(2019, annee);
	}

	@Test
	void testRechercherLivresByTitre() {
		Utilisateur user = new Utilisateur("Houssem","Soufiene","fjdonf@gmail.com","123456","sdfsdfsd",2564184,1);
		List<Livre> livres = user.rechercherLivresByTitre("programmer");
		assertEquals(1, livres.size());
	}

	@Test
	void testAuthentification() {
		String email = "godkh@gmail.com";
		String password = "123456";
		Utilisateur user = new Utilisateur();
		boolean connected = user.authentification(email, password);
		assertEquals(true, connected);
		
	}

}
