package com.masai.sqlitecreateevents

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHandler(context:Context):
    SQLiteOpenHelper(context, DATABASE_NAME,null, DATABASE_VERSION) {
    companion object{
        private val DATABASE_VERSION =1
        private val DATABASE_NAME ="EmployeeDatabase"
        private val TABLE_CONTACTS ="EmployeeTable"

        private  val KEY_ID ="_id"
        private val KEY_NAME="name"
        private val KEY_EMAIL="email"
        private val KEY_DATE="date"
        private val KEY_PRICE="price"
    }
    override fun onCreate(db: SQLiteDatabase?) {
        //creating table with database fields
        val CREATE_CONTACTS_TABLE =("CREATE TABLE" + TABLE_CONTACTS +"(" + KEY_ID +"INTEGER PRIMARY KEY,"+ KEY_NAME +"TEXT,"
        +KEY_EMAIL + "TEXT," + KEY_DATE+"TEXT,"+ KEY_PRICE+"TEXT"+ ")")
        db?.execSQL(CREATE_CONTACTS_TABLE)

    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db!!.execSQL("DROP TABLE IF EXISTS $TABLE_CONTACTS")
        onCreate(db)


    }
    //Insert Record in Database

    fun addEmployee(emp : EventModelClass): Long{
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(KEY_NAME,emp.name) //EventModelClass Name
        contentValues.put(KEY_EMAIL,emp.email)//EventModelClass email
        contentValues.put(KEY_DATE,emp.date)//EventModelClass date
        contentValues.put(KEY_PRICE,emp.price)//EventModelClass price

        //Inserting event details using insert query
        val success =db.insert(TABLE_CONTACTS,null,contentValues)

        //2nd argument is String containing nullColumnHack

        db.close() // Closing database connection
        return success

    }
}