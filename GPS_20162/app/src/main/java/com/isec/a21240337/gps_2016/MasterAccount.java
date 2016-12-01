package com.isec.a21240337.gps_2016;

/**
 * Created by andre on 25-11-2016.
 */

public class MasterAccount {
    private String userName;
    private String masterPassword;

    public MasterAccount(String userName, String masterPassword) {
        this.userName = userName;
        this.masterPassword = masterPassword;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getMasterPassword() {
        return masterPassword;
    }

    public void setMasterPassword(String masterPassword) {
        this.masterPassword = masterPassword;
    }

    //This method will be responsible for the login of the User,
    // if the login is successfully  done, it will return true if not
    //it will return false
    public boolean login() {


    }

    public boolean register(){

    }

    public boolean updateMasterPassword(String newPassword){

    }

    public boolean deleteMasterAccount(String password){
    }


}
