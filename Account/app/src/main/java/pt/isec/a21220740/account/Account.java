package pt.isec.a21220740.account;

import java.util.Date;

/**
 * Created by João on 30/11/2016.
 */

public class Account
{
    private String userName;
    private String password;
    private String service;
    private Date lastUpdated;

    public Account(String userName, String password, String service) {
        this.userName = userName;
        this.password = password;
        this.service = service;
        this.lastUpdated = PasswordUtil.getTime();
    }

    public String getUserName() {
        return userName;
    }
    public String getPassword() {
        return password;
    }
    public String getService() {
        return service;
    }

    /* This method adds an account into the database */
    public boolean add(Account acc) {
        // MUST HAVE DATABASE METHODS
        if(acc != null) {
            acc = new Account(acc.getUserName(), PasswordUtil.generateRandomPassword(8, true), acc.getService());

            return true;
        }

        return false;
    }

    /* This method updates an account into the database */
    public boolean update(Account acc) {
        // MUST HAVE DATABASE METHODS
        if(acc != null) {
            String user = acc.getUserName();
            String pw = acc.getPassword();
            String service = acc.getService();

            new Account(user, pw, service);
            return true;
        }

        return false;
    }

    /* This method deletes an account from the database */
    public boolean delete(Account acc) {
        // MUST HAVE DATABASE METHODS
        if(acc != null) {
            return true;
        }

        return false;
    }

    /* This method retrieves the number of days before a password expires */
    public int getDaysBeforeExpiration() {
        return (PasswordUtil.getTime().getDay() - lastUpdated.getDay());
    }
}
