package com.masai.sqlitecreateevents

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
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
    //Method to read the records from database in form of ArrayList
    @SuppressLint("Range")
    fun viewEmployee(): ArrayList<EventModelClass> {
        val empList: ArrayList<EventModelClass> = ArrayList<EventModelClass>()
        //Query to select all the records from the table
        val selectQuery = "SELECT * FROM $TABLE_CONTACTS"
        val db = this.readableDatabase
        //Cursor is used to read the record one by one. ADD them to data model class
        var cursor: Cursor? = null
        try {
            cursor = db.rawQuery(selectQuery, null)

        } catch (e: SQLiteException) {
            db.execSQL(selectQuery)
            return ArrayList()
        }
        var id: Int
        var name: String
        var email: String
        var date: String
        var price: String
        if (cursor.moveToFirst())  {

            do {
               id = cursor.getInt(cursor.getColumnIndex(KEY_ID))
                name = cursor.getString(cursor.getColumnIndex(KEY_NAME))
                email = cursor.getString(cursor.getColumnIndex(KEY_EMAIL))
                date = cursor.getString(cursor.getColumnIndex(KEY_DATE))
                price = cursor.getString(cursor.getColumnIndex(KEY_PRICE))
            } while (cursor.moveToNext())

        }

        return empList
    }
    fun updateEmployee(emp: EventModelClass): Int{
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(KEY_NAME,emp.name)
        contentValues.put(KEY_EMAIL,emp.email)
        contentValues.put(KEY_DATE,emp.date)
        contentValues.put(KEY_PRICE,emp.price)
       val success = db.update(TABLE_CONTACTS,contentValues, KEY_ID + "="+emp.id,null)
        db.close()
        return success

    }

}