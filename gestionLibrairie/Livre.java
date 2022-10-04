/**
 * 
 */
package gestionLibrairie;

import java.sql.Statement;
import java.util.Scanner;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @Ajouter et consulter des livres
 *@author KARIM
 
 */
public class Livre {
	int id_livre;
	String titre, auteur,date;
	String  dbName="Librairie.db";
	Utilisateur user= new Utilisateur();
	public void consulterLivres() {
		String sql="Select * from Livres";
		try {
			Connection connect= user.connect(dbName);
			Statement statement= connect.createStatement();
			ResultSet result=statement.executeQuery(sql);
			while (result.next()) {
				id_livre=result.getInt("id_livre");
				titre=result.getString("titre");
				auteur=result.getString("auteur");
				date=result.getString("date");
				System.out.println(id_livre+"|"+titre+"|"+auteur+"|"+date);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void checherLivreParId_livre(int id_livre) {
		String sql1="Select * from Livres Where id_livre='"+id_livre+"'";
		try {
			Connection connect = user.connect(dbName);
			Statement statement=connect.createStatement();
			ResultSet result=statement.executeQuery(sql1);
			while (result.next()) {
				id_livre=result.getInt("id_livre");
				titre=result.getString("titre");
				auteur=result.getString("auteur");
				date=result.getString("date");
				System.out.println(id_livre+"|"+titre+"|"+auteur+"|"+date);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void chercherLivreParTitre(String tritre) {
		String sql2="Select * from Livres Where titre='"+titre+"'OR auteur='"+auteur+"'";
		try {
			Connection connect = user.connect(dbName);
			Statement statement=connect.createStatement();
			ResultSet result=statement.executeQuery(sql2);
			while (result.next()) {
				id_livre=result.getInt("id_livre");
				titre=result.getString("titre");
				auteur=result.getString("auteur");
				date=result.getString("date");
				System.out.println(id_livre+"|"+titre+"|"+auteur+"|"+date);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	/**
	 * 
	 */
	public Livre() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Livre livre= new Livre();
		System.out.println("Afficher la liste des livres:");
		livre.consulterLivres();
		Scanner scan= new Scanner (System.in);
		if (scan.hasNextInt()) {
			int saisi=scan.nextInt();
			System.out.println("Resultat de recherche par ID_livre:");
			livre.checherLivreParId_livre(saisi);
		}
		String saisi=scan.nextLine();
		
		System.out.println("Resultat de recherche par titre:");
		livre.chercherLivreParTitre(saisi);
		

	}

}
