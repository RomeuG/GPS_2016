package pt.a21240332;

import junit.framework.TestCase;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import javax.crypto.SecretKey;

public class CryptoJUnit extends TestCase {

    @Before
    public void setUp() throws Exception {
        super.setUp();
        System.out.println("Starting tests...");
    }

    @Test
    public void testWhirlpoolHashVeryShort() {
        String str = "If";
        String expected = "0e8f0872fc85b6ac60a9915de65b911ec48c76932d11ea235bab8e6e0035e998564ef815cb241240daa673e3576ca237cbebd3be41e4f4aaef7f349ade3507e1".toUpperCase();

        System.out.println("---- TESTING testWhirlpoolHashVeryShort() ----");
        System.out.println("String: " + str);
        System.out.println("Hash: " + expected);

        Assert.assertEquals(PMCrypto.whirlpoolDigest(str.getBytes()), expected);

        System.out.println("Success!\n");
    }

    @Test
    public void testWhirlpoolHashShort() {
        String str = "If Pickford's packers";
        String expected = "b73d38e35ab95e3716b3b3710f49d717e658a365bf371e2c23b36b1ffd998a76242ca812310f831f61d550995dd9a1f8017444b0b43fc0eb26ec24af2e1f8ecc".toUpperCase();

        System.out.println("---- TESTING testWhirlpoolHashShort() ----");
        System.out.println("String: " + str);
        System.out.println("Hash: " + expected);

        Assert.assertEquals(PMCrypto.whirlpoolDigest(str.getBytes()), expected);

        System.out.println("Success!\n");
    }

    @Test
    public void testWhirlpoolHashMedium() {
        String str = "If Pickford's packers packed a packet";
        String expected = "bbbe58672da767b54d455085384ef0d9d302f6838e06bfbcc54823a05f40917979b0c0625a858274851245206fe000d98c9d39ada617589bdd2d42ffa630c6fb".toUpperCase();

        System.out.println("---- TESTING testWhirlpoolHashMedium() ----");
        System.out.println("String: " + str);
        System.out.println("Hash: " + expected);

        Assert.assertEquals(PMCrypto.whirlpoolDigest(str.getBytes()), expected);

        System.out.println("Success!\n");
    }

    @Test
    public void testWhirlpoolHashLarge() {
        String str = "If Pickford's packers packed a packet of crisps would the packet of crisps";
        String expected = "4fcc15d1e767f6bf4563ad78d1bb4ec15f49a113a3d5017a08c38666b0c29cba463ece963f5e5ecfb5e46a51a1989a3b2163a28ccad1c832e28788f9edf90e41".toUpperCase();

        System.out.println("---- TESTING testWhirlpoolHashLarge() ----");
        System.out.println("String: " + str);
        System.out.println("Hash: " + expected);

        Assert.assertEquals(PMCrypto.whirlpoolDigest(str.getBytes()), expected);

        System.out.println("Success!\n");
    }

    @Test
    public void testWhirlpoolHashVeryLarge() {
        String str = "If Pickford's packers packed a packet of crisps would the packet of crisps that Pickford's packers packed survive for two and a half years?";
        String expected = "f55a3a1f3319c57cbaaf213e5d0390e9f8fe0e3f8e0cfd74c76e99f9b8e3d2020b2ea68c9cea76833ec92e567c4e358d9ae1bc3d1a4facf0eb115497439e5d58".toUpperCase();

        System.out.println("---- TESTING testWhirlpoolHashVeryLarge() ----");
        System.out.println("String: " + str);
        System.out.println("Hash: " + expected);

        Assert.assertEquals(PMCrypto.whirlpoolDigest(str.getBytes()), expected);

        System.out.println("Success!\n");
    }

    @Test
    public void testAesEncryptionVeryShort() {
        String str = "If";

        String iv = PMCrypto.generateIV();
        byte[] salt = PMCrypto.generateSalt();

        String strKey = "this_is_the_key";
        SecretKey key = PMCrypto.AESDeriveKey(strKey, salt);

        String encrypted = PMCrypto.AESEncryptPBKDF2(str, key, iv);
        String decrypted = PMCrypto.AESDecryptPBKDF2(encrypted, key, iv);

        System.out.println("---- TESTING testAesEncryptionVeryLarge() ----");
        System.out.println("String: " + str);
        System.out.println("IV: " + iv);
        System.out.println("Encrypted: " + encrypted);
        System.out.println("Decrypted: " + decrypted);

        Assert.assertEquals(decrypted, str);

        System.out.println("Success!\n");
    }

    @Test
    public void testAesEncryptionShort() {
        String str = "If Pickford's packers";

        String iv = PMCrypto.generateIV();
        byte[] salt = PMCrypto.generateSalt();

        String strKey = "this_is_the_key";
        SecretKey key = PMCrypto.AESDeriveKey(strKey, salt);

        String encrypted = PMCrypto.AESEncryptPBKDF2(str, key, iv);
        String decrypted = PMCrypto.AESDecryptPBKDF2(encrypted, key, iv);

        System.out.println("---- TESTING testAesEncryptionVeryLarge() ----");
        System.out.println("String: " + str);
        System.out.println("IV: " + iv);
        System.out.println("Encrypted: " + encrypted);
        System.out.println("Decrypted: " + decrypted);

        Assert.assertEquals(decrypted, str);

        System.out.println("Success!\n");
    }

    @Test
    public void testAesEncryptionMedium() {
        String str = "If Pickford's packers packed a packet";

        String iv = PMCrypto.generateIV();
        byte[] salt = PMCrypto.generateSalt();

        String strKey = "this_is_the_key";
        SecretKey key = PMCrypto.AESDeriveKey(strKey, salt);

        String encrypted = PMCrypto.AESEncryptPBKDF2(str, key, iv);
        String decrypted = PMCrypto.AESDecryptPBKDF2(encrypted, key, iv);

        System.out.println("---- TESTING testAesEncryptionVeryLarge() ----");
        System.out.println("String: " + str);
        System.out.println("IV: " + iv);
        System.out.println("Encrypted: " + encrypted);
        System.out.println("Decrypted: " + decrypted);

        Assert.assertEquals(decrypted, str);

        System.out.println("Success!\n");
    }

    @Test
    public void testAesEncryptionLarge() {
        String str = "If Pickford's packers packed a packet of crisps would the packet of crisps";

        String iv = PMCrypto.generateIV();
        byte[] salt = PMCrypto.generateSalt();

        String strKey = "this_is_the_key";
        SecretKey key = PMCrypto.AESDeriveKey(strKey, salt);

        String encrypted = PMCrypto.AESEncryptPBKDF2(str, key, iv);
        String decrypted = PMCrypto.AESDecryptPBKDF2(encrypted, key, iv);

        System.out.println("---- TESTING testAesEncryptionVeryLarge() ----");
        System.out.println("String: " + str);
        System.out.println("IV: " + iv);
        System.out.println("Encrypted: " + encrypted);
        System.out.println("Decrypted: " + decrypted);

        Assert.assertEquals(decrypted, str);

        System.out.println("Success!\n");
    }

    @Test
    public void testAesEncryptionVeryLarge() {
        String str = "If Pickford's packers packed a packet of crisps would the packet of crisps that Pickford's packers packed survive for two and a half years?";

        String iv = PMCrypto.generateIV();
        byte[] salt = PMCrypto.generateSalt();

        String strKey = "this_is_the_key";
        SecretKey key = PMCrypto.AESDeriveKey(strKey, salt);

        String encrypted = PMCrypto.AESEncryptPBKDF2(str, key, iv);
        String decrypted = PMCrypto.AESDecryptPBKDF2(encrypted, key, iv);

        System.out.println("---- TESTING testAesEncryptionVeryLarge() ----");
        System.out.println("String: " + str);
        System.out.println("IV: " + iv);
        System.out.println("Encrypted: " + encrypted);
        System.out.println("Decrypted: " + decrypted);

        Assert.assertEquals(decrypted, str);

        System.out.println("Success!\n");
    }

    @After
    public void tearDown() {

    }
}
