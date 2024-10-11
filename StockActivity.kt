package com.sorveteria.gelattobom

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_stock.*

class StockActivity : AppCompatActivity() {

    private lateinit var dbHelper: DatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_stock)

        dbHelper = DatabaseHelper(this)

        updateStockButton.setOnClickListener {
            val productName = productNameInput.text.toString()
            val quantity = quantityInput.text.toString().toInt()

            if (dbHelper.updateStock(productName, quantity)) {
                Toast.makeText(this, "Estoque atualizado com sucesso", Toast.LENGTH_SHORT).show()
                clearFields()
            } else {
                Toast.makeText(this, "Erro ao atualizar estoque", Toast.LENGTH_SHORT).show()
            }
        }

        viewStockButton.setOnClickListener {
            val stockList = dbHelper.getStockList()
            stockTextView.text = stockList.joinToString(separator = "\n")
        }
    }

    private fun clearFields() {
        productNameInput.text.clear()
        quantityInput.text.clear()
    }
}
