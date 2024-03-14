package com.example.proyecto

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.sql.*


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val loginButton = findViewById<Button>(R.id.button)
        loginButton.setOnClickListener {
            val matriculaEditText = findViewById<EditText>(R.id.editTextText)
            val passwordEditText = findViewById<EditText>(R.id.editTextTextPassword)

            val matricula = matriculaEditText.text.toString()
            val password = passwordEditText.text.toString()

            if (validateLogin(matricula, password)) {
                val intent = Intent(this, PIncio::class.java)
                startActivity(intent)
            } else {
                Toast.makeText(this, "Usuario o contraseña incorrectos", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun validateLogin(matricula: String, contrasena: String) {
        val databaseConnection = DatabaseConnection()
        databaseConnection.execute()
    }

    // Dentro de DatabaseConnection, modifica el método onPostExecute() para que llame a validateLogin() pasando la conexión como parámetro
    override fun onPostExecute(connection: Connection?) {
        if (connection != null) {
            validateLogin(connection, matricula, contrasena)
        } else {
            Log.e(TAG, "Connection failed")
        }
    }

    // Modifica validateLogin() para que acepte una conexión como parámetro
    private fun validateLogin(connection: Connection, matricula: String, contrasena: String): Boolean {
        val query = "SELECT * FROM usuarios WHERE matricula = ? AND contrasena = ?"
        try {
            val statement = connection.prepareStatement(query)
            statement.setString(1, matricula)
            statement.setString(2, contrasena)
            val resultSet = statement.executeQuery()
            if (resultSet.next()) {
                // Las credenciales son válidas
                return true
            }
        } catch (e: SQLException) {
            Log.e(TAG, "Error al ejecutar la consulta SQL", e)
        } finally {
            try {
                connection.close()
            } catch (e: SQLException) {
                Log.e(TAG, "Error al cerrar la conexión", e)
            }
        }
        // Las credenciales no son válidas o hubo un error
        return false
    }


    public fun reg(view: View){
        val intent = Intent(this, RegistroUs::class.java)
        startActivity(intent)
    }
}
