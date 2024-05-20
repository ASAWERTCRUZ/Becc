package com.example.becc

import android.app.Dialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class beck3 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_beck3)

        val nombre = intent.getStringExtra("nombre")
        val apellido = intent.getStringExtra("apellido")
        val edad = intent.getIntExtra("edad", 0)

        val btnEnviar = findViewById<Button>(R.id.btnAceptar)
        btnEnviar.setOnClickListener {
            // Aquí puedes llamar a la función para analizar las respuestas del formulario Beck
            val puntaje = analizarRespuestas()

            // Mostrar el diálogo con los resultados
            mostrarDialogoResultado(nombre, puntaje)
        }
    }

    private fun mostrarDialogoResultado(nombre: String?, puntaje: Int) {
        val dialog = Dialog(this)
        dialog.setContentView(R.layout.dialog_result)
        dialog.setCancelable(false)

        val txtNombre = dialog.findViewById<TextView>(R.id.txtNombre)
        val txtPuntuacion = dialog.findViewById<TextView>(R.id.txtPuntuacion)
        val txtGradoDepresion = dialog.findViewById<TextView>(R.id.txtGradoDepresion)
        val txtConsejo = dialog.findViewById<TextView>(R.id.txtConsejo)

        txtNombre.text = "Nombre del paciente: $nombre"
        txtPuntuacion.text = "Puntuación: $puntaje"

        // Implementa la lógica para determinar el grado de depresión
        val gradoDepresion = determinarGradoDepresion(puntaje)
        txtGradoDepresion.text = "Grado de depresión: $gradoDepresion"

        // Implementa la lógica para mostrar un consejo según el grado de depresión
        val consejo = obtenerConsejo(gradoDepresion)
        txtConsejo.text = "Consejo: $consejo"

        val btnAceptar = dialog.findViewById<Button>(R.id.btnAceptar)
        btnAceptar.setOnClickListener {
            dialog.dismiss()
            volverAMainActivity()
        }

        dialog.show()
    }

    private fun volverAMainActivity() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun determinarGradoDepresion(puntaje: Int): String {
        return when {
            puntaje >= 0 && puntaje < 10 -> "Leve"
            puntaje >= 10 && puntaje < 20 -> "Moderado"
            puntaje >= 20 -> "Severo"
            else -> "Desconocido"
        }
    }

    private fun obtenerConsejo(gradoDepresion: String): String {
        return when (gradoDepresion) {
            "Leve" -> "Busca actividades que disfrutes y te relajen."
            "Moderado" -> "Considera buscar ayuda profesional y hablar con amigos o familiares cercanos."
            "Severo" -> "Es importante buscar ayuda profesional de inmediato. No dudes en comunicarte con un terapeuta o médico."
            else -> "Buscar apoyo emocional y profesional."
        }
    }

    private fun analizarRespuestas(): Int {
        var puntajeTotal = 0

        // Puntajes obtenidos en cada formulario Beck
        val puntajeBeck1 = intent.getIntExtra("puntajeBeck1", 0)
        val puntajeBeck2 = intent.getIntExtra("puntajeBeck2", 0)
        val puntajeBeck3 = intent.getIntExtra("puntajeBeck3", 0)

        // Sumar los puntajes obtenidos en cada formulario
        puntajeTotal += puntajeBeck1
        puntajeTotal += puntajeBeck2
        puntajeTotal += puntajeBeck3

        return puntajeTotal
    }
}
