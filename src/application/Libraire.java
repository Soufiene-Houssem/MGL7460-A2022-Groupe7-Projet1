package application;


import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

import entities.Livre;
import models.DbConnection;
import models.GestionLivres;

public class Libraire extends Utilisateur implements GestionLivres{
	
	private int numeroBadge;
	
	public Libraire(String nom, String prenom, String email, String password,
			String adresse, int telephone, int role) {
		super(nom, prenom, email, password, adresse, telephone, role);
	}
	
	public Libraire () {};
	
	public int getNumeroBadge() {
		return numeroBadge;
	}



	public void setNumeroBadge(int numeroBadge) {
		this.numeroBadge = numeroBadge;
	}
	
	
	
	@Override
	public String toString() {
		return  "ID "+getId()+" ~~ Nom: "+ getNom() + " || Prenom: "+getPrenom()+ " || Badge: "+numeroBadge+ " || Email: "+getEmail()+" || Adresse: "+getAdresse()+ " || T�l�phone: "+getTelephone();
	}

	
	/*********************************Gestion livres*********************************/
	
	/**
	 * 
	 * @author salim
	 * 
	 */
	
	
	@Override
	public static boolean ajouterLivre() {
		// TODO Auto-generated method stub
		boolean BookAdded = false;
		Scanner scanner = new Scanner(System.in);
		
		System.out.println("Inserer la ref du livre");
		final int id_livre = scanner.nextInt();
		
		System.out.println("Inserer le nom du livre");
		final String name_livre = scanner.nextLine();
		
		System.out.println("Inserer l'auteur du livre");
		final String author_livre = scanner.nextLine();
		
		System.out.println("Inserer la date de publication du livre");
		final String year_livre = scanner.nextLine();
		
		try 
		{
			
			PreparedStatement preparedStatement = DbConnection.connect().prepareStatement("select * from livre where id_livre = ?");
			preparedStatement.setInt(1, id_livre);
			ResultSet resultSet = preparedStatement.executeQuery();
		
			if (resultSet.next())
			{
				System.out.print("Livre deja ajoute");
				preparedStatement.close();
				ajouterLivre();
			}
			else 
			{
				 preparedStatement = DbConnection.connect()
						.prepareStatement("INSERT INTO books (id_livre,nom,auteur,annee) VALUES('"+id_livre+"','"+name_livre+"','"+author_livre+"','"+year_livre+"')");
				 resultSet = preparedStatement.executeQuery();
				 if (resultSet.next())
				 {
					 System.out.print("Livre ajoute avec succes");
					 preparedStatement.close();
					 BookAdded = true;
				 } 
			}	
		}
		catch (SQLException e)
		{ 
			System.out.println(e.getMessage());
		}
		return BookAdded;
	}

	@Override
	public boolean supprimerLivre(int reference) {
		// TODO Auto-generated method stub
		
		
		boolean BookDeleted = false;
		
		try 
		{
			
			PreparedStatement preparedStatement = DbConnection.connect().prepareStatement("select * from livre where id_livre = ?");
			preparedStatement.setInt(1, reference);
			ResultSet resultSet = preparedStatement.executeQuery();
		
		if (resultSet.next())
		{
			 preparedStatement = DbConnection.connect()
						.prepareStatement("DELETE FROM books WHERE id_livre ="+reference+"");
				 resultSet = preparedStatement.executeQuery();
				 if (resultSet.next())
				 {
					 System.out.print("Livre supprime avec succes");
					 preparedStatement.close();
					 BookDeleted = true;
				 } 
		}
		else 
			{
			 System.out.print("Reference non disponible");
			 supprimerLivre(reference);
			}
		}
			catch (SQLException e)
			{ 
				System.out.println(e.getMessage());
			}
		return BookDeleted;
	}

	

	@Override
	public boolean modifierLivre(int reference) {
		// TODO Auto-generated method stub
		
		boolean BookModified = false;
		try 
		{
			PreparedStatement preparedStatement = DbConnection.connect().prepareStatement("select * from books where id_livre = ?");
			preparedStatement.setInt(1, reference);
			ResultSet resultSet = preparedStatement.executeQuery();
		
		if (resultSet.next())
		{
			 Scanner scanner = new Scanner(System.in);
			 System.out.print("Quelle section voulez-vous modifier? \n 1-id livre \t 2-nom \t 3- auteur \t 4- annee");
			 int choix = scanner.nextInt();
			 
			 switch(choix)
			 {
			 case 1 : 
				{
					
					System.out.print("Entrez la nouvelle valeur de la section");
					int newValue = scanner.nextInt();
					preparedStatement = DbConnection.connect()
							.prepareStatement("UPDATE books SET id_livre = ? where id_livre = ?");
					preparedStatement.setInt(1, newValue);
					preparedStatement.setInt(2, reference);
					boolean result = preparedStatement.executeUpdate() >0;
				}
			 case 2 : 
				{
					
					System.out.print("Entrez la nouvelle valeur de la sectiion");
					String newValue = scanner.nextLine();
					preparedStatement = DbConnection.connect()
							.prepareStatement("UPDATE books SET nom = ? where id_livre = ?");
					preparedStatement.setString(1, newValue);
					preparedStatement.setInt(2, reference);
					boolean result = preparedStatement.executeUpdate() >0;
				}
			 case 3 : 
				{
					
					System.out.print("Entrez la nouvelle valeur de la sectiiion");
					String newValue = scanner.nextLine();
					preparedStatement = DbConnection.connect()
							.prepareStatement("UPDATE books SET auteur = ? where id_livre = ?");
					preparedStatement.setString(1, newValue);
					preparedStatement.setInt(2, reference);
					boolean result = preparedStatement.executeUpdate() >0;
				}
			 case 4 : 
				{
					
					System.out.print("Entrez la nouvelle valeur de la sectiiiion");
					String newValue = scanner.next();
					preparedStatement = DbConnection.connect()
							.prepareStatement("UPDATE books SET annee = ? where id_livre = ?");
					preparedStatement.setString(1, newValue);
					preparedStatement.setInt(2, reference);
					boolean result = preparedStatement.executeUpdate() >0;
				}
			default : 
				System.out.print("Erreur");
				modifierLivre(reference);
			 }
		}
		else 
			{
				System.out.print("la reference n'existe pas");
				preparedStatement.close();
				modifierLivre(reference);
			}
		}
		catch (SQLException e)
		{ 
	System.out.println(e.getMessage());
		}
		return BookModified;
	}}
