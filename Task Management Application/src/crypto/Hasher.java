package crypto;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


public class Hasher {
	public static String hashString(String str){
		String hashed;
		try {
			MessageDigest md = MessageDigest.getInstance("md5");
			md.reset();
			byte[] bytes = md.digest(str.getBytes());
			hashed = new BigInteger(1, bytes).toString();
		}
		catch(NoSuchAlgorithmException nsae){
			nsae.printStackTrace();
			hashed = "";
		}
		return hashed;
	}
	
}
