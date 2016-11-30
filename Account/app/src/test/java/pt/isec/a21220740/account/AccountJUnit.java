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
    public void testAccountAdd() {
        String str = "If Pickford's packers packed a packet of crisps would the packet of crisps that Pickford's packers packed survive for two and a half years?";

        //Assert.assertEquals(decrypted, str);

        System.out.println("Success!\n");
    }

    @Test
    public void testAccountUpdate() {
        String str = "If Pickford's packers packed a packet of crisps would the packet of crisps that Pickford's packers packed survive for two and a half years?";

        //Assert.assertEquals(decrypted, str);

        System.out.println("Success!\n");
    }

    @Test
    public void testAccountDelete() {
        String str = "If Pickford's packers packed a packet of crisps would the packet of crisps that Pickford's packers packed survive for two and a half years?";

        //Assert.assertEquals(decrypted, str);

        System.out.println("Success!\n");
    }

    @After
    public void tearDown() {

    }
}
