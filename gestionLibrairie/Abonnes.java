/**
 * 
 */
package gestionLibrairie;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import gestionLibrairie.PwdEncrypt;

/**
 * @author KARIM
 * classe pour la manipulation des utilisateurs.
 * ajouter un utilisateur/lister les livres/Chercher un livre
 *
 */

public class Abonnes {
	
	public static final String DBNAME="Librairie.db";
	private int id_util;
	private String nom,prenom,phone, addresse, email, password,role;
	//private String dbName="Librairie.db";
	
//Declaration des getteurs et setteurs
	public int getId_util () {
		return this.id_util;
	}
	public void setId_util(int id_util) {
		this.id_util=id_util;
	}
	
	public String getNom () {
		return this.nom;
	}
	public void setNom(String nom) {
		this.nom=nom;
	}
	
	public String getPrenom () {
		return this.prenom;
	}
	public void setPrenom(String prenom) {
		this.prenom=prenom;
	}

	public String getPhone () {
		return this.phone;
	}
	public void setPhone(String phone) {
		this.phone=phone;
	}
	
	public String getAddresse () {
		return this.addresse;
	}
	public void setAddresse(String addresse) {
		this.addresse=addresse;
	}
	
	public String getEmail () {
		return this.email;
	}
	public void setEmail(String email) {
		this.email=email;
	}
	
	public String getPassword () {
		return this.password;
	}
	public void setPassword(String password) {
		this.password=password;
	}
	
	public String getRole () {
		return this.role;
	}
	public void setRole(String role) {
		this.role=role;
	}
	
//constructeur.
	public Abonnes(String nom,  String prenom,String phone,String addresse, String email,String password) {	
		this.nom=nom;
		this.prenom=prenom;
		this.phone=phone;
		this.addresse=addresse;
		this.email=email;
		this.password=password;
	}
//consstructeur#2

	public Abonnes() {	
		
	}
	
/*Methode pour la connection a la BD	**/
	
	public Connection connect (String dbName) {
		Connection c =null;
		String url="jdbc:sqlite:C:\\sqlite\\"+dbName;
		try {
			c = DriverManager.getConnection(url);
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		return c;
	}
	
	/* Methode pour ajouter un utilisateur*/
	
	public Boolean ajout(Abonnes abonne) {
		String sql="insert into utilisateurs (nom_util,prenom_util,phone,addresse,email,password,salt,role) values(?,?,?,?,?,?,?,?)";
		String lerole="Normal";
		boolean added=false;
		/* GENERER le Salt. */  
		PwdEncrypt pwdEncrypt= new PwdEncrypt();
        String saltvalue = PwdEncrypt.getSaltvalue(30);  
        
        /* GENERER un mot de passe crypt� pour l'enregister a la base de donn�es */
        String encryptedpassword = PwdEncrypt.generateSecurePassword(password, saltvalue);  
		try (Connection conn=this.connect(DBNAME);
				PreparedStatement pstmt= conn.prepareStatement(sql);
				){
			pstmt.setString(1,nom);
			pstmt.setString(2,prenom);
			pstmt.setString(3,phone);
			pstmt.setString(4,addresse);
			pstmt.setString(5,email);
			pstmt.setString(6,encryptedpassword);
			pstmt.setString(7,saltvalue);
			pstmt.setString(8,lerole);
			pstmt.executeUpdate();
			added=true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return added;
	}
	
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Abonnes user =new Abonnes("Diallo","karim","438460696","Brossard","karim01@gmail.com","karim");
		user.ajout(user);

	}

}
