/**
 * 
 */
package com.sumadhura.sumadhuragateway.util;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

import org.apache.log4j.Logger;

/**
 * AESEncryptDecrypt class provides some  methods for encrypt and decrypt.
 *  
 * @author Venkat_Koniki
 * @since 23.03.2019
 * @time 04:11PM
 */

/*
 * This class is for to encrypt and decrypt the password based on the AES-128 algorithem.
 */
public class AESEncryptDecrypt {

	 private static final String AES = "AES";
	 
	 private static final Logger LOGGER = Logger.getLogger(AESEncryptDecrypt.class);
	 
	 private static String byteArrayToHexString(byte[] b) {
	        StringBuffer sb = new StringBuffer(b.length * 2);
	        for (int i = 0; i < b.length; i++) {
	            int v = b[i] & 0xff;
	            if (v < 16) {
	                sb.append('0');
	            }
	            sb.append(Integer.toHexString(v));
	        }
	        return sb.toString().toUpperCase();
	    }

	    private static byte[] hexStringToByteArray(String s) {
	        byte[] b = new byte[s.length() / 2];
	        for (int i = 0; i < b.length; i++) {
	            int index = i * 2;
	            int v = Integer.parseInt(s.substring(index, index + 2), 16);
	            b[i] = (byte) v;
	        }
	        return b;
	    }

	    public static String  encrypt(String password,String key) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException {
	    	 LOGGER.info("*** The Control is inside the encrypt in AESEncryptDecrypt ***password***"+password+"****key***"+key);
	    	 byte[] bytekey = hexStringToByteArray(key);
	         SecretKeySpec sks = new SecretKeySpec(bytekey, AESEncryptDecrypt.AES);
	         Cipher cipher = Cipher.getInstance(AESEncryptDecrypt.AES);
	         cipher.init(Cipher.ENCRYPT_MODE, sks, cipher.getParameters());
	         byte[] encrypted = cipher.doFinal(password.getBytes());
	         String encryptedpwd = byteArrayToHexString(encrypted);
	         LOGGER.info("*** The encryptedpwd is ***"+encryptedpwd);
	    	return encryptedpwd ;
	    }
	    
	    public static String decrypt(String encryptedPassword,String key) throws IllegalBlockSizeException, BadPaddingException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException {
	    	 LOGGER.info("*** The Control is inside the decrypt in AESEncryptDecrypt ***");
	    	 byte[] bytekey = hexStringToByteArray(key);
	         SecretKeySpec sks = new SecretKeySpec(bytekey, AESEncryptDecrypt.AES);
	         Cipher cipher = Cipher.getInstance(AESEncryptDecrypt.AES);
	         cipher.init(Cipher.DECRYPT_MODE, sks);
	         byte[] decrypted = cipher.doFinal(hexStringToByteArray(encryptedPassword));
	         String OriginalPassword = new String(decrypted);
	    	 return OriginalPassword;
	    }
	    
	    public static String convertKeyToHex(String key) {

		    char[] ch = key.toCharArray();
		    StringBuilder builder = new StringBuilder();
		    for (char c : ch) {
			 // Step-2 Use %H to format character to Hex
			 String hexCode = String.format("%H", c);
			 builder.append(hexCode);
		   }
		   // System.out.println("Hex value is " + builder);
		    return builder.toString();
	     }
	
	   public static void main(String ...strings) throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException {
	    	//String key = "BF241299293FFD4F12CD40DEB5687793";
	    	 String key = "SUAMSCUSTOMERAPP";
	    	//String key = "ABE14B190EC353B8AD34B22B1D23ED2E";
	    	//String key = "4B3C16BFFD1678112BD21C24EAAE2F4A";
	    	
	    	String encryptedPassword = encrypt("naveen@sipl",convertKeyToHex(key));
	    	System.out.println("**** encrypted password ****"+encryptedPassword);
	    	
	    	/*String encryptedPassword = encrypt("47411e-c5fGRP-1586c6-1-1-sducf-3",convertKeyToHex(key));
	    	System.out.println("**** encrypted password ****"+encryptedPassword); */
	    	
	    	String decryptedPassword = decrypt("9CF011DA2FF7DAB07B76A4AC7C4A4DCC",convertKeyToHex(key));
	    	System.out.println("**** decrypted password ****"+decryptedPassword);
	    	
	    }
	
	
}
