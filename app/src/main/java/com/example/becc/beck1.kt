package com.example.becc

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.RadioButton
import android.widget.RadioGroup

class beck1 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_beck1)

        // Obtener datos del intent actual
        val nombre = intent.getStringExtra("nombre")
        val apellido = intent.getStringExtra("apellido")
        val edad = intent.getIntExtra("edad", 0)

        val btnsig = findViewById<Button>(R.id.btnSiguiente)
        btnsig.setOnClickListener {
            val intent = Intent(this, beck2::class.java)
            // Calcular y pasar el puntaje del formulario Beck1
            val puntajeBeck1 = calcularPuntajeBeck1()
            intent.putExtra("nombre", nombre)
            intent.putExtra("apellido", apellido)
            intent.putExtra("edad", edad)
            intent.putExtra("puntajeBeck1", puntajeBeck1)
            startActivity(intent)
        }

        val btnretro = findViewById<Button>(R.id.btnRetroceso)
        btnretro.setOnClickListener{
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }

    private fun calcularPuntajeBeck1(): Int {
        var puntaje = 0

        // Puntajes asignados a cada opción de respuesta
        val puntajesRespuestas = arrayOf(0, 1, 2, 3)

        // RadioGroups del formulario Beck1
        val radioGroups = arrayOf(
            R.id.radioGroupPregunta1,
            R.id.radioGroupPregunta2,
            R.id.radioGroupPregunta3,
            R.id.radioGroupPregunta4,
            R.id.radioGroupPregunta5,
            R.id.radioGroupPregunta6,
            R.id.radioGroupPregunta7
        )

        // Calcular puntaje del formulario Beck1
        for (radioGroupId in radioGroups) {
            val puntajeRespuesta = obtenerPuntajeRespuesta(radioGroupId, puntajesRespuestas)
            puntaje += puntajeRespuesta
        }

        return puntaje
    }

    private fun obtenerPuntajeRespuesta(radioGroupId: Int, puntajesRespuestas: Array<Int>): Int {
        val radioGroup = findViewById<RadioGroup>(radioGroupId)
        val radioButtonId = radioGroup.checkedRadioButtonId
        if (radioButtonId != -1) {
            val radioButton = findViewById<RadioButton>(radioButtonId)
            // Obtener el índice de la opción seleccionada
            val indiceRespuesta = radioGroup.indexOfChild(radioButton)
            // Obtener el puntaje correspondiente al índice
            return puntajesRespuestas[indiceRespuesta]
        }
        // Si no se seleccionó ninguna respuesta, retornar 0
        return 0
    }
    private fun obtenerRespuestasUsuarioBeck1(): Array<String?> {
        val respuestasUsuarioBeck1 = arrayOfNulls<String>(7)

        // Obtener las respuestas seleccionadas por el usuario para cada pregunta
        respuestasUsuarioBeck1[0] = obtenerRespuestaSeleccionada(R.id.radioGroupPregunta1)
        respuestasUsuarioBeck1[1] = obtenerRespuestaSeleccionada(R.id.radioGroupPregunta2)
        respuestasUsuarioBeck1[2] = obtenerRespuestaSeleccionada(R.id.radioGroupPregunta3)
        respuestasUsuarioBeck1[3] = obtenerRespuestaSeleccionada(R.id.radioGroupPregunta4)
        respuestasUsuarioBeck1[4] = obtenerRespuestaSeleccionada(R.id.radioGroupPregunta5)
        respuestasUsuarioBeck1[5] = obtenerRespuestaSeleccionada(R.id.radioGroupPregunta6)
        respuestasUsuarioBeck1[6] = obtenerRespuestaSeleccionada(R.id.radioGroupPregunta7)

        return respuestasUsuarioBeck1
    }

    private fun obtenerRespuestaSeleccionada(radioGroupId: Int): String? {
        val radioGroup = findViewById<RadioGroup>(radioGroupId)
        val radioButtonId = radioGroup.checkedRadioButtonId
        if (radioButtonId != -1) {
            val radioButton = findViewById<RadioButton>(radioButtonId)
            return radioButton.text.toString()
        }
        return null
    }
}
