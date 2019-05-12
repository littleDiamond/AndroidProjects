package com.examples.myreceipts;

/**
 * Create User Class to maintain single contact as an object.
 */
public class User {
    private int mId;
    private String mName;
    private String mEmail;
    private String mPassword;

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        this.mId = id;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        this.mName = name;
    }

    public String getEmail() { return mEmail; }

    public void setEmail(String email) {
        this.mEmail = email;
    }

    public String getPassword() {
        return mPassword;
    }

    public void setPassword(String password) {
        this.mPassword = password;
    }
}
