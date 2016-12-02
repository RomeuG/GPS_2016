import junit.framework.Assert;
import junit.framework.TestCase;

import org.junit.Test;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class UnitTest extends TestCase {

    @Test
    public void testPasswordGenerator() {

        System.out.println("---- TESTING generateRandomPassword() ----");

        for (int i = 0; i < 1000000; i++) {
            String test = PasswordUtil.generateRandomPassword(10,true);
            Assert.assertNotNull(test);
            Assert.assertTrue(test.length()==10);
        }
        for (int i = 0; i < 1000000; i++) {
            String test = PasswordUtil.generateRandomPassword(10,false);
            Assert.assertNotNull(test);
            Assert.assertTrue(test.length()==10);
            for(char c=' ';c<'/';c++) {
                String s = ""+ c;
                Assert.assertFalse(test.contains(s));
            }
        }

        System.out.println("Success!\n");
    }
}