package com.example.halanchallenge.features.login

import android.content.Context
import com.example.halanchallenge.LoginTask

// Call async LoginTask
class Login {
    fun login(username: String?, password: String?, context: Context) {
        val loginTask = LoginTask(context)
        loginTask.execute(username, password)
    }
}