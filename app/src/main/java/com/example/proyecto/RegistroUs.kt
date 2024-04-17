package com.example.proyecto

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import java.io.OutputStreamWriter
import java.net.HttpURLConnection
import java.net.URL
import java.net.URLEncoder

class RegistroUs : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.registro_usuario)

        val nombreEditText = findViewById<EditText>(R.id.Nombre)
        val primerApellidoEditText = findViewById<EditText>(R.id.PrimerApellido)
        val segundoApellidoEditText = findViewById<EditText>(R.id.SegudoAp)
        val matriculaEditText = findViewById<EditText>(R.id.Matricula)
        val tarjetaEditText = findViewById<EditText>(R.id.editTextText4)
        val contrasenaEditText = findViewById<EditText>(R.id.editTextTextPassword2)
        val repetirContrasenaEditText = findViewById<EditText>(R.id.editTextTextPassword3)
        val registrarButton = findViewById<Button>(R.id.button5)

        registrarButton.setOnClickListener {
            val nombre = nombreEditText.text.toString()
            val primerApellido = primerApellidoEditText.text.toString()
            val segundoApellido = segundoApellidoEditText.text.toString()
            val matricula = matriculaEditText.text.toString()
            val tarjeta = tarjetaEditText.text.toString()
            val contrasena = contrasenaEditText.text.toString()
            val repetirContrasena = repetirContrasenaEditText.text.toString()

            // Verifica que las contraseñas coincidan
            if (contrasena != repetirContrasena) {
                Toast.makeText(this, "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Realiza la solicitud HTTP POST al servidor PHP
            Thread {
                val url = URL("https://cofeecard.proyectos-idgs-23s.com/apiR.php")
                val urlConnection = url.openConnection() as HttpURLConnection
                try {
                    urlConnection.requestMethod = "POST"
                    urlConnection.doOutput = true
                    val postData = "nombre=${URLEncoder.encode(nombre, "UTF-8")}" +
                            "&primer_ap=${URLEncoder.encode(primerApellido, "UTF-8")}" +
                            "&segundo_ap=${URLEncoder.encode(segundoApellido, "UTF-8")}" +
                            "&matricula=${URLEncoder.encode(matricula, "UTF-8")}" +
                            "&tarjeta=${URLEncoder.encode(tarjeta, "UTF-8")}" +
                            "&contrasena=${URLEncoder.encode(contrasena, "UTF-8")}"
                    val outputStreamWriter = OutputStreamWriter(urlConnection.outputStream)
                    outputStreamWriter.write(postData)
                    outputStreamWriter.flush()

                    // Manejar la respuesta del servidor
                    if (urlConnection.responseCode == HttpURLConnection.HTTP_OK) {
                        // Lógica para manejar la respuesta exitosa
                        runOnUiThread {
                            Toast.makeText(this, "Registro exitoso", Toast.LENGTH_SHORT).show()
                        }
                    } else {
                        // Lógica para manejar la respuesta de error
                        runOnUiThread {
                            Toast.makeText(this, "Error en el registro, verifica que tus datos coincidan con los que te registraste en Administracion", Toast.LENGTH_SHORT).show()
                        }
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                } finally {
                    urlConnection.disconnect()
                }
            }.start()
        }
    }
}
