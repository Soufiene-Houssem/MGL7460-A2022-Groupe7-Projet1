package models;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.util.Arrays;
import java.util.Base64;
import java.util.Random;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

public class PwdEncrypt { 
    /* Declaration of variables */   
    private static final Random RANDOM= new SecureRandom();  
    private static final String CHARACTERS= "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";  
    private static final int ITERATIONS = 10000;  
    private static final int KEYLENGTH = 256;  
      
    /* Méthode de génération de salt. */  
    public static String getSaltvalue(final int length)   
    {  
        final StringBuilder finalval = new StringBuilder(length);  
  
        for (int i = 0; i < length; i++)   
        {  
            finalval.append(CHARACTERS.charAt(RANDOM.nextInt(CHARACTERS.length())));  
        }  
  
        return new String(finalval);  
    }     
  
    /* Méthode de génération de hash. */  
    public static byte[] hash(final char[] password,final byte[] salt)   
    {  
        final PBEKeySpec spec = new PBEKeySpec(password, salt, ITERATIONS, KEYLENGTH);  
        Arrays.fill(password, Character.MIN_VALUE);  
        try   
        {  
            SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");  
            return skf.generateSecret(spec).getEncoded();  
        }   
        catch (NoSuchAlgorithmException | InvalidKeySpecException e)   
        {  
            throw new AssertionError("Error while hashing a password: " + e.getMessage(), e);  
        }   
        finally   
        {  
            spec.clearPassword();  
        }  
    }  
  
    /* Méthode pour encrypter le mot de passe avec le salt. */  
    public static String generateSecurePassword(final String password,final String salt)   
    {  
        final byte[] securePassword = hash(password.toCharArray(), salt.getBytes());  
        return Base64.getEncoder().encodeToString(securePassword);  
    }  
      
    /* Méthode pour vérifier la conformité des deux mots de passe. */  
    public static boolean verifyUserPassword(String providedPassword,  
            String securedPassword, String salt)  
    {  
        boolean finalval = false;  
          
        /* Générer un mot de passe securisé en utilisant le même salt. */  
        String newSecurePassword = generateSecurePassword(providedPassword, salt);  
          
        /* Vérifier la conformité des deux mots de passe. */  
        finalval = newSecurePassword.equalsIgnoreCase(securedPassword);  
          
        return finalval;  
    }  

}
