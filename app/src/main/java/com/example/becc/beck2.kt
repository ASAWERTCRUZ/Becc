package com.example.becc

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.RadioButton
import android.widget.RadioGroup

class beck2 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_beck2)

        val nombre = intent.getStringExtra("nombre")
        val apellido = intent.getStringExtra("apellido")
        val edad = intent.getIntExtra("edad", 0)

        val btnSig = findViewById<Button>(R.id.btnsiguiete2)
        btnSig.setOnClickListener {
            // Calcular puntaje Beck2
            val puntajeBeck2 = calcularPuntajeBeck2()

            // Crear intent para pasar a la siguiente actividad (Beck3)
            val intent = Intent(this, beck3::class.java)
            // Pasar datos extras y puntaje Beck2 al nuevo intent
            intent.putExtra("nombre", nombre)
            intent.putExtra("apellido", apellido)
            intent.putExtra("edad", edad)
            intent.putExtra("puntajeBeck2", puntajeBeck2)
            startActivity(intent)
        }

        val btnRes = findViewById<Button>(R.id.btnAnterior)
        btnRes.setOnClickListener {
            // Crear intent para volver a la actividad anterior (Beck1)
            val intent = Intent(this, beck1::class.java)
            // Pasar datos extras al nuevo intent
            intent.putExtra("nombre", nombre)
            intent.putExtra("apellido", apellido)
            intent.putExtra("edad", edad)
            startActivity(intent)
        }
    }

    private fun calcularPuntajeBeck2(): Int {
        var puntaje = 0

        // Puntajes asignados a cada opción de respuesta
        val puntajesRespuestas = arrayOf(0, 1, 2, 3)

        // RadioGroups del formulario Beck2
        val radioGroups = arrayOf(
            R.id.radioGroupFatigabilidad,
            R.id.radioGroupPerdidaApetito,
            R.id.radioGroupPerdidaPeso,
            R.id.radioGroupGananciaPeso,
            R.id.radioGroupSuicidio,
            R.id.radioGroupAgitacion,
            R.id.radioGroupConcentracion
        )

        // Calcular puntaje del formulario Beck2
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
            // Verificar si el índice está dentro del rango del array
            if (indiceRespuesta >= 0 && indiceRespuesta < puntajesRespuestas.size) {
                // Obtener el puntaje correspondiente al índice
                return puntajesRespuestas[indiceRespuesta]
            }
        }
        // Si no se seleccionó ninguna respuesta válida, retornar 0
        return 0
    }

}
