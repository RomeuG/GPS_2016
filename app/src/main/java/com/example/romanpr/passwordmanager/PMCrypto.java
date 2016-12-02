package com.example.romanpr.passwordmanager;

import org.bouncycastle.crypto.digests.WhirlpoolDigest;
import org.bouncycastle.util.encoders.Base64;

import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

//import static javax.xml.bind.DatatypeConverter.printHexBinary;


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
        SecureRandom rnd = new SecureRandom();

        rnd.nextBytes(bytes);

        return bytes;
    }

    /**
     * This static method encrypts the text together
     * with the salt, with the help of IV. After the
     * encryption, it encodes the bytes in Base64 to
     * get a readable string.
     *
     * @param text Plain text password.
     * @param salt Salt to be used.
     * @param aesiv IV to be used.
     * @return String
     */
    public static String AESEncryptPBKDF2(String text, byte[] salt, String aesiv) {
        try {
            // IV construction
            IvParameterSpec iv = new IvParameterSpec(aesiv.getBytes("UTF-8"));

            // Creation of the key
            SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
            KeySpec spec = new PBEKeySpec(text.toCharArray(), salt, 65536, 256);
            SecretKey secret = new SecretKeySpec(factory.generateSecret(spec).getEncoded(), "AES");

            // Actual encryption
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
            cipher.init(Cipher.ENCRYPT_MODE, secret, iv);

            byte[] encrypted = cipher.doFinal(text.getBytes("UTF-8"));

            return Base64.toBase64String(encrypted);

        } catch (NoSuchAlgorithmException |
                InvalidKeyException |
                NoSuchPaddingException |
                BadPaddingException |
                UnsupportedEncodingException |
                InvalidKeySpecException |
                InvalidAlgorithmParameterException |
                IllegalBlockSizeException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * This static method decrypts the encrypted
     * password encoded in Base64. Like the encryption
     * it needs the IV, salt and the original text.
     *
     * @param encrypted Encrypted password encoded in Base64.
     * @param original Original plain text password.
     * @param salt Salt used in the encryption.
     * @param aesiv IV used in the encryption.
     * @return String
     */
    public static String AESDecryptPBKDF2(String encrypted, String original, byte[] salt, String aesiv) {
        try {
            // IV construction
            IvParameterSpec iv = new IvParameterSpec(aesiv.getBytes("UTF-8"));

            // Creation of the key
            SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
            KeySpec spec = new PBEKeySpec(original.toCharArray(), salt, 65536, 256);
            SecretKey secret = new SecretKeySpec(factory.generateSecret(spec).getEncoded(), "AES");

            // Actual encryption
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
            cipher.init(Cipher.DECRYPT_MODE, secret, iv);

            byte[] decrypted = cipher.doFinal(Base64.decode(encrypted));

            return new String(decrypted);

        } catch (NoSuchAlgorithmException |
                InvalidKeyException |
                NoSuchPaddingException |
                BadPaddingException |
                InvalidKeySpecException |
                InvalidAlgorithmParameterException |
                UnsupportedEncodingException |
                IllegalBlockSizeException e) {
            e.printStackTrace();
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
     * @return String
     */
    /*
    public static String whirlpoolDigest(byte[] password) {
        byte result[] = new byte[WHIRLPOOL_BLOCK_SIZE];

        // Hashing
        WhirlpoolDigest wpHash = new WhirlpoolDigest();
        wpHash.update(password, 0, password.length);
        wpHash.doFinal(result, 0);

        return printHexBinary(result);
    }*/
}
