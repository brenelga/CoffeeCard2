package com.example.proyecto

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.AbsListView
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.OutputStreamWriter
import java.net.HttpURLConnection
import java.net.URL

class MainActivity : AppCompatActivity() {

    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        sharedPreferences = getSharedPreferences("my_preferences", Context.MODE_PRIVATE)

        val matriculaEditText = findViewById<EditText>(R.id.editTextText)
        val contrasenaEditText = findViewById<EditText>(R.id.editTextTextPassword)
        val iniciarSesionButton = findViewById<Button>(R.id.button)

        iniciarSesionButton.setOnClickListener {
            val matricula = matriculaEditText.text.toString().trim()
            val contrasena = contrasenaEditText.text.toString().trim()

            if (matricula.isNotEmpty() && contrasena.isNotEmpty()) {
                // Realizar la solicitud HTTP POST en un hilo de fondo usando Coroutines
                GlobalScope.launch(Dispatchers.IO) {
                    val response = iniciarSesion(matricula, contrasena)

                    // Manejar la respuesta en el hilo principal
                    launch(Dispatchers.Main) {
                        if (response != null) {
                            val token = response.optString("token", null)
                            if (token != null) {
                                // El inicio de sesión fue exitoso
                                Toast.makeText(this@MainActivity, "Inicio de sesión exitoso", Toast.LENGTH_SHORT).show()
                                // Guardar el token en SharedPreferences
                                guardarToken(token)
                                exito(iniciarSesionButton)
                            } else {
                                // Las credenciales son incorrectas
                                Toast.makeText(this@MainActivity, "Credenciales incorrectas", Toast.LENGTH_SHORT).show()
                            }
                        } else {
                            // Error al conectar con el servidor
                            Toast.makeText(this@MainActivity, "Error de conexión", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            } else {
                Toast.makeText(this@MainActivity, "Por favor ingrese matrícula y contraseña", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun iniciarSesion(matricula: String, contrasena: String): JSONObject? {
        val url = URL("https://cofeecard.proyectos-idgs-23s.com/apiL.php")
        val connection = url.openConnection() as HttpURLConnection
        connection.requestMethod = "POST"
        connection.doOutput = true

        try {
            val writer = OutputStreamWriter(connection.outputStream)
            val postData = "matricula=$matricula&contrasena=$contrasena"
            writer.write(postData)
            writer.flush()

            val response = StringBuilder()
            BufferedReader(InputStreamReader(connection.inputStream)).use { reader ->
                var inputLine: String?
                while (reader.readLine().also { inputLine = it } != null) {
                    response.append(inputLine)
                }
            }
            return JSONObject(response.toString())
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            connection.disconnect()
        }
        return null
    }

    private fun guardarToken(token: String) {
        val editor = sharedPreferences.edit()
        editor.putString("token", token)
        editor.apply()
    }

    fun exito(view: View){
        val intent = Intent(this, PIncio::class.java)
        startActivity(intent)
    }
}
