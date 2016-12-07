package pt.a21240332;

import org.bouncycastle.crypto.digests.WhirlpoolDigest;
import org.bouncycastle.util.encoders.Base64;

import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Random;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;


/**
 * The PMCrypto class is a module of Password Manager
 * that is used for the encryption and decryption of
 * password and the hashing of the master password.
 */
public class PMCrypto {

    // IV Size
    final private static int IV_BLOCK_SIZE = 16;
    // Whirlpool size in bytes
    final private static int WHIRLPOOL_BLOCK_SIZE = 64;

    /**
     * This static method generates an Initialization Value
     * with 16 bytes of size. It is used in the AES encryption
     * of the passwords.
     *
     * @return String The IV to be used in encryption.
     */
    public static String generateIV() {
        String alphanumeric = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
        SecureRandom rnd = new SecureRandom();

        StringBuilder sb = new StringBuilder(IV_BLOCK_SIZE);
        for (int i = 0; i < IV_BLOCK_SIZE; i++)
            sb.append(alphanumeric.charAt(rnd.nextInt(alphanumeric.length())));
        return sb.toString();
    }

    /**
     * This static method generates a salt with 8 bytes
     * of size. It will be used in the AES encryption of
     * the passwords.
     *
     * @return byte[] The salt to be used in encryption.
     */
    public static byte[] generateSalt() {
        byte[] bytes = new byte[8];

        new Random().nextBytes(bytes);

        return bytes;
    }

    /**
     * This static method converts bytes
     * to a string of hexadecimals.
     *
     * @param bytes Bytes to be converted.
     * @return String Hexadecimal string.
     */
    public static String bytesToHexStr(byte[] bytes) {
        StringBuilder sb = new StringBuilder();

        for(byte b : bytes) {
            sb.append(String.format("%02x", b));
        }

        return sb.toString();
    }

    /**
     * This static method originates
     * a key given a string and a salt.
     * It will be used in the encryption
     * and decryption.
     *
     * @param text Plain text.
     * @param salt Salt to be added to plain text.
     * @return SecretKey Object that represents the final key.
     */
    public static SecretKey AESDeriveKey(String text, byte[] salt) {
        try {
            // Creation of the key
            SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
            KeySpec spec = new PBEKeySpec(text.toCharArray(), salt, 65536, 128);
            SecretKey secret = new SecretKeySpec(factory.generateSecret(spec).getEncoded(), "AES");

            return secret;
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            return null;
        }
    }

    /**
     * This static method encrypts the text together
     * with the salt, with the help of IV. After the
     * encryption, it encodes the bytes in Base64 to
     * get a readable string.
     *
     * @param text Plain text password.
     * @param key Key to be used.
     * @param aesiv IV to be used.
     * @return String
     */
    public static String AESEncryptPBKDF2(String text, SecretKey key, String aesiv) {
        try {
            // IV construction
            IvParameterSpec iv = new IvParameterSpec(aesiv.getBytes("UTF-8"));

            // Actual encryption
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
            cipher.init(Cipher.ENCRYPT_MODE, key, iv);

            byte[] encrypted = cipher.doFinal(text.getBytes("UTF-8"));

            return Base64.toBase64String(encrypted);

        } catch (NoSuchAlgorithmException |
                InvalidKeyException |
                NoSuchPaddingException |
                BadPaddingException |
                UnsupportedEncodingException |
                InvalidAlgorithmParameterException |
                IllegalBlockSizeException e) {
            return null;
        }
    }

    /**
     * This static method decrypts the encrypted
     * password encoded in Base64. Like the encryption
     * it needs the IV, salt and the original text.
     *
     * @param encrypted Encrypted password encoded in Base64.
     * @param key Key generated in the encryption.
     * @param aesiv IV used in the encryption.
     * @return String Decrypted, plain text password.
     */
    public static String AESDecryptPBKDF2(String encrypted, SecretKey key, String aesiv) {
        try {
            // IV construction
            IvParameterSpec iv = new IvParameterSpec(aesiv.getBytes("UTF-8"));

            // Actual encryption
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
            cipher.init(Cipher.DECRYPT_MODE, key, iv);

            byte[] decrypted = cipher.doFinal(Base64.decode(encrypted));

            return new String(decrypted);

        } catch (NoSuchAlgorithmException |
                InvalidKeyException |
                NoSuchPaddingException |
                BadPaddingException |
                InvalidAlgorithmParameterException |
                UnsupportedEncodingException |
                IllegalBlockSizeException e) {
            return null;
        }
    }

    /**
     * This static method hashes the given
     * password using the Whirlpool Hashing
     * Algorithm. This algorithm is 512 bits
     * long.
     *
     * @param password Password to hash.
     * @return String Hexadecimal string representing Whirlpool Hash.
     */
    public static String whirlpoolDigest(byte[] password) {
        byte result[] = new byte[WHIRLPOOL_BLOCK_SIZE];

        // Hashing
        WhirlpoolDigest wpHash = new WhirlpoolDigest();
        wpHash.update(password, 0, password.length);
        wpHash.doFinal(result, 0);

        return bytesToHexStr(result).toUpperCase();
    }
}
 
