package com.sorveteria.gelattobom

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "sorveteria.db"
        private const val DATABASE_VERSION = 1

        private const val USERS_TABLE = "Users"
        private const val SALES_TABLE = "Sales"
        private const val STOCK_TABLE = "Stock"

        private const val COL_ID = "id"
        private const val COL_USERNAME = "username"
        private const val COL_PASSWORD = "password"
        private const val COL_PRODUCT_NAME = "product_name"
        private const val COL_QUANTITY = "quantity"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val createUsersTable = "CREATE TABLE $USERS_TABLE ($COL_ID INTEGER PRIMARY KEY AUTOINCREMENT, $COL_USERNAME TEXT, $COL_PASSWORD TEXT)"
        val createSalesTable = "CREATE TABLE $SALES_TABLE ($COL_ID INTEGER PRIMARY KEY AUTOINCREMENT, $COL_PRODUCT_NAME TEXT, $COL_QUANTITY INTEGER)"
        val createStockTable = "CREATE TABLE $STOCK_TABLE ($COL_ID INTEGER PRIMARY KEY AUTOINCREMENT, $COL_PRODUCT_NAME TEXT, $COL_QUANTITY INTEGER)"
        
        db?.execSQL(createUsersTable)
        db?.execSQL(createSalesTable)
        db?.execSQL(createStockTable)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $USERS_TABLE")
        db?.execSQL("DROP TABLE IF EXISTS $SALES_TABLE")
        db?.execSQL("DROP TABLE IF EXISTS $STOCK_TABLE")
        onCreate(db)
    }

    fun registerUser(username: String, password: String): Boolean {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(COL_USERNAME, username)
        values.put(COL_PASSWORD, password)
        val result = db.insert(USERS_TABLE, null, values)
        return result != -1L
    }

    fun validateUser(username: String, password: String): Boolean {
        val db = this.readableDatabase
        val query = "SELECT * FROM $USERS_TABLE WHERE $COL_USERNAME = ? AND $COL_PASSWORD = ?"
        val cursor = db.rawQuery(query, arrayOf(username, password))
        return cursor.count > 0
    }

    fun addSale(productName: String, quantity: Int): Boolean {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(COL_PRODUCT_NAME, productName)
        values.put(COL_QUANTITY, quantity)
        val result = db.insert(SALES_TABLE, null, values)
        return result != -1L
    }

    fun updateStock(productName: String, quantity: Int): Boolean {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(COL_PRODUCT_NAME, productName)
        values.put(COL_QUANTITY, quantity)
        val result = db.update(STOCK_TABLE, values, "$COL_PRODUCT_NAME = ?", arrayOf(productName))
        return result > 0
    }

    fun getStockList(): List<String> {
        val db = this.readableDatabase
        val stockList = mutableListOf<String>()
        val cursor = db.rawQuery("SELECT * FROM $STOCK_TABLE", null)

        if (cursor.moveToFirst()) {
            do {
                val productName = cursor.getString(cursor.getColumnIndexOrThrow(COL_PRODUCT_NAME))
                val quantity = cursor.getInt(cursor.getColumnIndexOrThrow(COL_QUANTITY))
                stockList.add("$productName - Quantidade: $quantity")
            } while (cursor.moveToNext())
        }
        cursor.close()
        return stockList
    }
}
