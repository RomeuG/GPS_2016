package pt.isec.a21220740.account;

import junit.framework.TestCase;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by João on 30/11/2016.
 */

public class AccountJUnit extends TestCase
{
    @Before
    public void setUp() throws Exception {
        super.setUp();
        System.out.println("Starting tests...");
    }

    @Test
    public void testAesEncryptionVeryLarge() {
        String str = "If Pickford's packers packed a packet of crisps would the packet of crisps that Pickford's packers packed survive for two and a half years?";

        /* String iv = PMCrypto.generateIV();
        byte[] salt = PMCrypto.generateSalt();

        String encrypted = PMCrypto.AESEncryptPBKDF2(str, salt, iv);
        String decrypted = PMCrypto.AESDecryptPBKDF2(encrypted, str, salt, iv);

        System.out.println("---- TESTING testAesEncryptionVeryLarge() ----");
        System.out.println("String: " + str);
        System.out.println("IV: " + iv);
        System.out.println("Salt: " + salt.toString());
        System.out.println("Encrypted: " + encrypted);
        System.out.println("Decrypted: " + decrypted);

        Assert.assertEquals(decrypted, str);

        System.out.println("Success!\n"); */
    }

    @After
    public void tearDown() {

    }
}
