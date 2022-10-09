package services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import entities.Livre;
import utils.DbConnection;

public class LivreService{
	
    private transient  PreparedStatement preparedStatement;
    private transient ResultSet resultSet;
    private final transient Connection connexion;
    
    
    
	public LivreService() {
		super();
		this.connexion = DbConnection.getInstance().getConnexion();
	}
	
	public List<Livre> getAllLivres() {
		List<Livre> livres = new ArrayList<>();
		try {
			preparedStatement = connexion.prepareStatement("select * from livre");
			resultSet = preparedStatement.executeQuery();
			while(resultSet.next()) {
				livres.add(new Livre(resultSet.getInt("isbn"), resultSet.getString("title"), resultSet.getString("author"), resultSet.getInt("year"), resultSet.getString("category")));
			}
			resultSet.close();
			preparedStatement.close();
		} catch(SQLException e) {
			System.out.println(e.getMessage());
		}
		return livres;
	}
	 
	public boolean deleteLivre(int isbn) {
		boolean isDeleted = false;
		try {
			preparedStatement = connexion.prepareStatement("delete from livre where isbn = ?");
			preparedStatement.setInt(1, isbn);
			isDeleted = preparedStatement.executeUpdate() > 0;
		} catch(SQLException e) {
			System.out.println(e.getMessage());
		}
		return isDeleted;
	}
	 
	public List<Livre> findLivreByTitle(String searchTitle) {
		List<Livre> livres = new ArrayList<>();
		try {
			String sqlQuery = "select * from livre where title like ?";
			preparedStatement = connexion.prepareStatement(sqlQuery);
			final String search = "%"+searchTitle+"%";
			preparedStatement.setString(1, search);
			resultSet = preparedStatement.executeQuery();
			while(resultSet.next()) {
				livres.add(new Livre(resultSet.getInt("isbn"), resultSet.getString("title"), resultSet.getString("author"), resultSet.getInt("year"), resultSet.getString("category")));
			}
		} catch ( SQLException e) {
			System.out.println(e.getMessage());
		}
		return livres;
	}
	
	public Livre findLivreByIsbn(int isbn) {
		Livre livre = null;
		try {
			String sqlQuery = "select * from livre where isbn = ?";
			preparedStatement = connexion.prepareStatement(sqlQuery);
			preparedStatement.setInt(1, isbn);
			resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				livre =new Livre(resultSet.getInt("isbn"), resultSet.getString("title"), resultSet.getString("author"), resultSet.getInt("year"), resultSet.getString("category"));
			}
			resultSet.close();
			preparedStatement.close();
		} catch ( SQLException e) {
			System.out.println(e.getMessage());
		}
		return livre;
	}
	
	
	public boolean addLivre(Livre livre) {
		boolean isAdded = false;  
		try{
			preparedStatement = connexion.prepareStatement("insert into livre(isbn, title, author, year, category) values (?, ?, ?, ?, ?)");
			preparedStatement.setInt(1, livre.getIsbn());
			preparedStatement.setString(2, livre.getTitle());
			preparedStatement.setString(3, livre.getAuthor());
			preparedStatement.setInt(4, livre.getYear());
			preparedStatement.setString(5,livre.getCategory());
			isAdded = preparedStatement.executeUpdate() > 0;
		} catch ( SQLException e) {
			System.out.println(e.getMessage());
		}
		return isAdded;
	}
	
	public boolean updateLivre(Livre livreMods) {
		boolean isModified = false;
		try {
			preparedStatement = connexion.prepareStatement("update livre set isbn = ?, title = ?, author = ?, year = ?, category = ? where isbn = ?");
			preparedStatement.setInt(1, livreMods.getIsbn());
			preparedStatement.setString(2, livreMods.getTitle());
			preparedStatement.setString(3, livreMods.getAuthor());
			preparedStatement.setInt(4, livreMods.getYear());
			preparedStatement.setString(5, livreMods.getCategory());
			preparedStatement.setInt(6, livreMods.getIsbn());
			isModified = preparedStatement.executeUpdate() > 0 ;
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return isModified;
	}

}
