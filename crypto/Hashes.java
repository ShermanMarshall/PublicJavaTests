package crypto;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @class:       Hashes
 * @description: Class containing some of the JCE utilities implementing contemporary crypto standards
 */
public class Hashes {
    
    public static void testHash(String hashType) throws NoSuchAlgorithmException, UnsupportedEncodingException  {
        MessageDigest messageDigest = MessageDigest.getInstance(hashType);
        
        byte[] input = "username1&password1".getBytes("UTF-8");
        byte[] output;
        
        byte[] digest = messageDigest.digest(input);
        
        System.out.println(new String(input));
        System.out.println(new String(digest));
        System.out.println("Length: " + digest.length);
    }
    
    public static void main (String...args) {
        try {
            testHash("SHA-1");
            testHash("SHA-256");
        } catch (NoSuchAlgorithmException nsae) {
            System.out.println(nsae);
        } catch (UnsupportedEncodingException uee) {
            System.out.println(uee);
        }
    }
    
}
