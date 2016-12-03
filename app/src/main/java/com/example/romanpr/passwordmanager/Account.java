package com.example.romanpr.passwordmanager;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by romanpr on 11/25/2016.
 */

public class Account {
    String service, username, password, lastUpdated, iv, salt;

    public Account() {

    }

    public Account(String service, String username, String password) {
        this.service = service;
        this.username = username;
        this.iv = PMCrypto.generateIV();
        this.salt = new String(PMCrypto.generateSalt());
        this.password = PMCrypto.AESEncryptPBKDF2(password, salt.getBytes(), iv);


        DateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        lastUpdated = sdf.format(new Date());
    }

    public String getService() {
        return service;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getLastUpdated() {
        return lastUpdated;
    }

    public String getIv() {
        return iv;
    }

    public String getSalt() {
        return salt;
    }

    public String getDecryptedPassword() {
        return  PMCrypto.AESDecryptPBKDF2(password, "TODO", salt.getBytes(), iv);
    }

    public String toString() {
        return
                "\nService:\t" + this.service
                + "\nUsername:\t" + this.username
                + "\nPassword:\t" + this.password
                + "\nLast updated:\t" + this.lastUpdated;
    }
}
