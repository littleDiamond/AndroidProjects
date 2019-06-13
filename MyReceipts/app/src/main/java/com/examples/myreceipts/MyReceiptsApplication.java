package com.examples.myreceipts;

import android.app.Application;

public final class MyReceiptsApplication extends Application {
    private String currentUserName;

    public String getCurrentUser() {return currentUserName;}
    public void setCurretUser(String newUserName)
    {
        currentUserName = newUserName;
    }
}
