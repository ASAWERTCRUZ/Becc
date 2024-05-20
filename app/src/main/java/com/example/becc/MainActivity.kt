package com.example.becc

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.sql.Connection
import java.sql.DriverManager
import java.sql.SQLException

class MainActivity : AppCompatActivity() {

    private lateinit var nombreEditText: EditText
    private lateinit var apellidoEditText: EditText
    private lateinit var edadEditText: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Inicializar vistas
        nombreEditText = findViewById(R.id.txtnombre)
        apellidoEditText = findViewById(R.id.txtapellido)
        edadEditText = findViewById(R.id.txtedad)

        val btnSiguiente = findViewById<Button>(R.id.datosenviar)
        btnSiguiente.setOnClickListener {
            // Obtener los valores de los EditText
            val nombre = nombreEditText.text.toString()
            val apellido = apellidoEditText.text.toString()
            val edad = edadEditText.text.toString().toIntOrNull()

            // Verificar si los campos están completos
            if (nombre.isNotEmpty() && apellido.isNotEmpty() && edad != null) {
                // Crear un Intent para ir a beck1 y agregar los datos extras
                val intentBeck1 = Intent(this, beck1::class.java)
                intentBeck1.putExtra("nombre", nombre)
                intentBeck1.putExtra("apellido", apellido)
                intentBeck1.putExtra("edad", edad)
                // Iniciar la actividad beck1
                startActivity(intentBeck1)
            } else {
                // Mostrar un mensaje de error si los campos no están completos
                Toast.makeText(this, "Por favor, completa todos los campos.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    class DatabaseConnection {
        fun getConnection(): Connection? {
            var connection: Connection? = null
            try {
                Class.forName("com.mysql.jdbc.Driver") // Usar la clase del driver antigua
                connection = DriverManager.getConnection(
                    "jdbc:mysql://154.12.254.242:3306/ratiosof74bo_uv_bd",
                    "ratiosof74bo_uv_bd_user",
                    "Estudiante@123"
                )
            } catch (e: ClassNotFoundException) {
                e.printStackTrace()
            } catch (e: SQLException) {
                e.printStackTrace()
            }
            return connection
        }
    }
}
