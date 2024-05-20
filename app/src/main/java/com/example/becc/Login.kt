package com.example.becc

import android.content.Intent
import android.os.Bundle
import android.transition.AutoTransition
import android.transition.TransitionManager
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText
import java.sql.PreparedStatement
import java.sql.SQLException

class Login : AppCompatActivity() {
    private var isLoginLayoutVisible = true

    private lateinit var etEmail: TextInputEditText
    private lateinit var etPassword: TextInputEditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        etEmail = findViewById(R.id.etEmail)
        etPassword = findViewById(R.id.etPassword)

        val loginButton = findViewById<View>(R.id.btnLogin)
        val registerButton = findViewById<View>(R.id.registerLink)

        loginButton.setOnClickListener {
            if (!isLoginLayoutVisible) {
                performTransition()
                isLoginLayoutVisible = true
            }
            onLoginClick(it)
        }

        registerButton.setOnClickListener {
            if (isLoginLayoutVisible) {
                performTransition()
                isLoginLayoutVisible = false
            }
        }
    }

    private fun performTransition() {
        TransitionManager.beginDelayedTransition(findViewById(R.id.login_container), AutoTransition())
        findViewById<View>(R.id.login_layout).visibility = if (isLoginLayoutVisible) View.VISIBLE else View.GONE
        findViewById<View>(R.id.register_layout).visibility = if (isLoginLayoutVisible) View.GONE else View.VISIBLE
    }

    fun onLoginClick(view: View) {
        view.startAnimation(AnimationUtils.loadAnimation(this, R.anim.scale))

        val email = etEmail.text.toString()
        val password = etPassword.text.toString()

        if (email.isNotEmpty() && password.isNotEmpty()) {
            val connection = MainActivity.DatabaseConnection().getConnection()
            if (connection != null) {
                try {
                    val query = "SELECT * FROM users WHERE email = ? AND password = ?"
                    val preparedStatement: PreparedStatement = connection.prepareStatement(query)
                    preparedStatement.setString(1, email)
                    preparedStatement.setString(2, password)
                    val resultSet = preparedStatement.executeQuery()

                    if (resultSet.next()) {
                        val intent = Intent(this, MainActivity::class.java)
                        startActivity(intent)
                    } else {
                        Toast.makeText(this, "Credenciales incorrectas", Toast.LENGTH_SHORT).show()
                    }

                    resultSet.close()
                    preparedStatement.close()
                    connection.close()
                } catch (e: SQLException) {
                    e.printStackTrace()
                    Toast.makeText(this, "Error en la base de datos", Toast.LENGTH_SHORT).show()
                }
            }
        } else {
            Toast.makeText(this, "Ingrese correo electrónico y contraseña", Toast.LENGTH_SHORT).show()
        }
    }
}
