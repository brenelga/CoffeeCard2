package com.example.proyecto

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText

class PIncio : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.pantalla_inicio)

        val sharedPreferences = getSharedPreferences("my_preferences", Context.MODE_PRIVATE)
        val nombreUsuario = sharedPreferences.getString("nombreUsuario", "")

        // Mostrar el nombre de usuario en el EditText
        val editText = findViewById<EditText>(R.id.editTextText2)
        editText.setText(nombreUsuario)
    }

    fun saldo(view: View){
        val intent = Intent(this, CSaldo::class.java)
        startActivity(intent)
    }
    fun cuenta(view: View){
        val intent = Intent(this, CMovimiento::class.java)
        startActivity(intent)
    }
}