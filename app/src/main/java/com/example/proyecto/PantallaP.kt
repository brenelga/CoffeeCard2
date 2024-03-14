package com.example.proyecto

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class PantallaP : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.pantalla_principal)
    }

    fun inicios(view: View){
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }
    fun regus(view: View){
        val intent = Intent(this, RegistroUs::class.java)
        startActivity(intent)
    }
}