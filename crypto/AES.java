package crypto;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKeySpec;
import javax.crypto.spec.IvParameterSpec;

import sun.misc.BASE64Encoder;

/**
 * @class:       AES
 * @description: Class containing an encryption example using JCE
 */
public class AES {
    private static final int KEY_SIZE_128 = 128;
    
    public static void main(String[] args) {

        String strDataToEncrypt;
        String strCipherText;
        String strDecryptedText;
        
        if (args.length == 0) {
            System.out.println("Must input an argument");
            return;
        }

        try {
            SecretKey secretKey = new SecretKeySpec("PASSWORD", "AES");

            final int AES_KEYLENGTH = 128;	
            byte[] iv = new byte[AES_KEYLENGTH / 8];

            iv = "16character55555".getBytes();
            
            Cipher aesCipherForEncryption = Cipher.getInstance("AES/CBC/PKCS5Padding"); 

            aesCipherForEncryption.init(Cipher.ENCRYPT_MODE, secretKey, new IvParameterSpec(iv));

            strDataToEncrypt = args[0];
            byte[] byteDataToEncrypt = strDataToEncrypt.getBytes();
            byte[] byteCipherText = aesCipherForEncryption.doFinal(byteDataToEncrypt);

            System.out.println("Cipher Text generated using AES is " + new String(strCipherText));

            Cipher aesCipherForDecryption = Cipher.getInstance("AES/CBC/PKCS5Padding"); 

            aesCipherForDecryption.init(Cipher.DECRYPT_MODE, secretKey, new IvParameterSpec(iv));
            byte[] byteDecryptedText = aesCipherForDecryption.doFinal(byteCipherText);
            strDecryptedText = new String(byteDecryptedText);
            System.out.println("Decrypted Text message is " + strDecryptedText);
        }
        catch (NoSuchAlgorithmException noSuchAlgo) {
            System.out.println("No Such Algorithm exists " + noSuchAlgo);
        }
        catch (NoSuchPaddingException noSuchPad) {
            System.out.println("No Such Padding exists " + noSuchPad);
        }
        catch (InvalidKeyException invalidKey) {
            System.out.println("Invalid Key " + invalidKey);
        }
        catch (BadPaddingException badPadding) {
            System.out.println("Bad Padding " + badPadding);
        }
        catch (IllegalBlockSizeException illegalBlockSize) {
            System.out.println("Illegal Block Size " + illegalBlockSize);
        }
        catch (InvalidAlgorithmParameterException invalidParam) {
            System.out.println("Invalid Parameter " + invalidParam);
        }
    }
}
