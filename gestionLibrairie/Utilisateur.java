/**
 * 
 */
package gestionLibrairie;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Random;

/**
 * Classe Utilisateur: ajouter/Consulter Livre/Cherche livre
 * e.printStackTrace();
 *
 */
public class Utilisateur {
	String dbName = "Librairie.db";
	public Connection connect (String dbName) {
		Connection c =null;
		String url="jdbc:sqlite:C:\\sqlite\\"+dbName;
		try {
			c = DriverManager.getConnection(url);
		} catch (SQLException e) {
			
			
		}
		return c;
	}
	int id_util;
	String nom,prenom, email, password;
	public void ajouter(int id_util,String nom, String prenom,String email,String password,String role) {
		String sql="insert into utilisateurs (id_util,nom_util,prenom_util,email,password,role) values(?,?,?,?,?)";
		
		try (Connection conn=this.connect(this.dbName);
				PreparedStatement pstmt= conn.prepareStatement(sql);
				){
			pstmt.setInt(1,id_util);
			pstmt.setString(2,nom);
			pstmt.setString(3,prenom);
			pstmt.setString(4,email);
			pstmt.setString(5,password);
			pstmt.setString(6,role);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	public Utilisateur() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		
	}

}
