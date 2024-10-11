package com.sorveteria.gelattobom

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_register.*

class RegisterActivity : AppCompatActivity() {

    private lateinit var dbHelper: DatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        dbHelper = DatabaseHelper(this)

        registerButton.setOnClickListener {
            val username = registerUsername.text.toString()
            val password = registerPassword.text.toString()

            if (dbHelper.registerUser(username, password)) {
                Toast.makeText(this, "Registro bem-sucedido", Toast.LENGTH_SHORT).show()
                finish()
            } else {
                Toast.makeText(this, "Erro ao registrar usuário", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
