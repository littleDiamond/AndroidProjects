package com.examples.myreceipts;

/**
 * Create User Class to maintain single contact as an object.
 */
public class User {
    private int mId;
    private String mName;
    private String mEmail;
    private int mPhoneNumber;
    private String mPassword;
    private String mCompanyName;
    private String mStreetAddress;
    private String mAreaAddress;
    private int mGST;


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

    public String getEmail() {
        return mEmail;
    }

    public void setEmail(String email) {
        this.mEmail = email;
    }

    public int getPhoneNumber() {
        return mPhoneNumber;
    }

    public void setPhoneNumber(int mPhoneNumber) {
        this.mPhoneNumber = mPhoneNumber;
    }

    public String getPassword() {
        return mPassword;
    }

    public void setPassword(String password) {
        this.mPassword = password;
    }

    public String getCompanyName() {
        return mCompanyName;
    }

    public void setCompanyName(String mCompanyName) {
        this.mCompanyName = mCompanyName;
    }

    public String getStreetAddress() {
        return mStreetAddress;
    }

    public void setStreetAddress(String mStreetAddress) {
        this.mStreetAddress = mStreetAddress;
    }

    public String getAreaAddress() {
        return mAreaAddress;
    }

    public void setAreaAddress(String mAreaAddress) {
        this.mAreaAddress = mAreaAddress;
    }

    public int getGST() {
        return mGST;
    }

    public void setGST(int mGST) {
        this.mGST = mGST;
    }


    @Override
    public String toString() {
        return String.format("[User detail] ID : {%d} Name : {%s} Email : {%s} Phone : {%d} " +
                        "Password : {%s} Company : {%s} Street : {%s} Area : {%s} GST : {%d}",
                mId,mName,mEmail,mPhoneNumber,mPassword,mCompanyName,mStreetAddress,mAreaAddress,mGST);
    }
}
