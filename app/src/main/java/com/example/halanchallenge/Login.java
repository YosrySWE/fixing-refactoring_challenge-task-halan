package com.example.halanchallenge;

import android.content.Context;

// Call async LoginTask
public class Login{
    public void login(String username, String password,Context context) {
        LoginTask loginTask = new LoginTask(context);
        loginTask.execute(username, password);
    }
}
