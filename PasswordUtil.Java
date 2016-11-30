/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package passwordutil;

import java.util.Random;
import java.lang.Character;
import java.util.Date;

/**
 *
 * @author ruife
 */
public class PasswordUtil {

    //this method simply returns a java.util.date object with the time of now
    public static Date getTime(){
        Date d = new Date();
        return d;

    }

    //This method generates a random password with or without special characters according to the variable sc
    public static String generateRandomPassword(int length, boolean sc) {

        Random random = new Random();
        char[] buf;
        char[] symbols;
        StringBuilder tmp = new StringBuilder();

        for (char ch = '0'; ch <= '9'; ++ch) {
            tmp.append(ch);
        }
        for (char ch = 'a'; ch <= 'z'; ++ch) {
            tmp.append(ch);
        }
        if (sc == true) {
            for (char ch = ' '; ch < '/'; ch++) {
                tmp.append(ch);
            }
        }
        symbols = tmp.toString().toCharArray();

        if (length < 1) {
            throw new IllegalArgumentException("length < 1: " + length);
        }
        buf = new char[length];

        for (int idx = 0; idx < buf.length; ++idx) {
            buf[idx] = symbols[random.nextInt(symbols.length)];
        }
        return new String(buf);
    }

    public static void main(String[] args) {
        // TODO code application logic here
        System.out.println(PasswordUtil.generateRandomPassword(10, false));
    }

}
