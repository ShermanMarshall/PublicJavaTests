package crypto;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @class:       Hashes
 * @description: Class containing some of the JCE utilities implementing contemporary crypto standards (SHA-1 & SHA-256)
 */
public class Hashes {
    
    public static void testHash(String userName, String password, String hashType) throws NoSuchAlgorithmException, UnsupportedEncodingException  {
        MessageDigest messageDigest = MessageDigest.getInstance(hashType);
        
        byte[] input = (userName + "&" + password).getBytes();//"UTF-8");
        byte[] digest = messageDigest.digest(input);
	String base64EncodedPassword = new String(Base64Converter.convertBinaryToBase64(digest));
	
	System.out.println(base64EncodedPassword.trim());
	System.out.println(base64EncodedPassword.length());

        System.out.println("Testing: " + hashType);
        System.out.println(new String(input));
        System.out.println(new String(digest));
        System.out.println("Length: " + digest.length);
    }

    public static void main (String...args) {
        try {
            //testHash("SHA-1");
            testHash(args[0], args[1], "SHA-256");
        } catch (NoSuchAlgorithmException nsae) {
            System.out.println(nsae);
        } catch (UnsupportedEncodingException uee) {
            System.out.println(uee);
        }
    }
    
}
