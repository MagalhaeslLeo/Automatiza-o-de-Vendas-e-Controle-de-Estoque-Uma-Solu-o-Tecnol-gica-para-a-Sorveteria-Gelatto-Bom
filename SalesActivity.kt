package com.sorveteria.gelattobom

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_sales.*

class SalesActivity : AppCompatActivity() {

    private lateinit var dbHelper: DatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sales)

        dbHelper = DatabaseHelper(this)

        addSaleButton.setOnClickListener {
            val productName = productNameInput.text.toString()
            val quantity = quantityInput.text.toString().toInt()

            if (dbHelper.addSale(productName, quantity)) {
                Toast.makeText(this, "Venda registrada com sucesso", Toast.LENGTH_SHORT).show()
                clearFields()
            } else {
                Toast.makeText(this, "Erro ao registrar venda", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun clearFields() {
        productNameInput.text.clear()
        quantityInput.text.clear()
    }
}
