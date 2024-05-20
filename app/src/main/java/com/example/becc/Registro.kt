package com.example.becc

import android.os.Bundle
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText
import java.sql.PreparedStatement
import java.sql.SQLException
class Registro : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registro)

        val etName = findViewById<TextInputEditText>(R.id.etName)
        val etSurname = findViewById<TextInputEditText>(R.id.etSurname)
        val etAge = findViewById<TextInputEditText>(R.id.etAge)
        val etEmail = findViewById<TextInputEditText>(R.id.etEmail)
        val etPassword = findViewById<TextInputEditText>(R.id.etPassword)
        val btnRegister = findViewById<Button>(R.id.btnRegister)

        btnRegister.setOnClickListener {
            val animation = AnimationUtils.loadAnimation(this, R.anim.scale)
            it.startAnimation(animation)

            val name = etName.text.toString()
            val surname = etSurname.text.toString()
            val age = etAge.text.toString().toIntOrNull()
            val email = etEmail.text.toString()
            val password = etPassword.text.toString()

            if (name.isNotEmpty() && surname.isNotEmpty() && age != null && email.isNotEmpty() && password.isNotEmpty()) {
                val connection = MainActivity.DatabaseConnection().getConnection()
                if (connection != null) {
                    try {
                        val query = "INSERT INTO users (nombre, apellido, edad, email, password) VALUES (?, ?, ?, ?, ?)"
                        val preparedStatement: PreparedStatement = connection.prepareStatement(query)
                        preparedStatement.setString(1, name)
                        preparedStatement.setString(2, surname)
                        preparedStatement.setInt(3, age)
                        preparedStatement.setString(4, email)
                        preparedStatement.setString(5, password)

                        val rowsInserted = preparedStatement.executeUpdate()
                        if (rowsInserted > 0) {
                            Toast.makeText(this, "Registro exitoso", Toast.LENGTH_LONG).show()
                        } else {
                            Toast.makeText(this, "Error al registrar", Toast.LENGTH_LONG).show()
                        }

                        preparedStatement.close()
                        connection.close()
                    } catch (e: SQLException) {
                        e.printStackTrace()
                        Toast.makeText(this, "Error al registrar: ${e.message}", Toast.LENGTH_LONG).show()
                    }
                } else {
                    Toast.makeText(this, "Error al conectar con la base de datos", Toast.LENGTH_LONG).show()
                }
            } else {
                Toast.makeText(this, "Por favor, completa todos los campos", Toast.LENGTH_LONG).show()
            }
        }
    }
}