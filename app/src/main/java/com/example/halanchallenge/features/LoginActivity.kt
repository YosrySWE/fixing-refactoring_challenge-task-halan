package com.example.halanchallenge.features

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.example.halanchallenge.features.login.Login
import com.example.halanchallenge.R

class LoginActivity : AppCompatActivity() {
    var userNameEt: EditText? = null
    var passwordEt: EditText? = null
    var loginBtn: Button? = null
    var login: Login? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        userNameEt = findViewById(R.id.username_et)
        passwordEt = findViewById(R.id.password_et)
        loginBtn = findViewById(R.id.login_button)
        login = Login()
        loginBtn?.setOnClickListener {
            login!!.login(
                userNameEt?.text.toString(), passwordEt?.text.toString(), applicationContext
            )
        }
    }
}