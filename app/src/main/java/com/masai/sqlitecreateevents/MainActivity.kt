package com.masai.sqlitecreateevents

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnAdd.setOnClickListener{
            addRecord()

        }
    }

    //Method for saving event records in database
    private fun addRecord() {
        val name = etName.text.toString()
        val email = etEmail.text.toString()
        val date = etDate.text.toString()
        val price = etPrice.text.toString()
        val databaseHandler: DatabaseHandler = DatabaseHandler(this)
        if (!name.isEmpty() && !email.isEmpty() && !date.isEmpty() && !price.isEmpty()) {
            val status = databaseHandler.addEmployee(EventModelClass(0, name, email, date, price))
            if (status > -1) {
                Toast.makeText(applicationContext, "Record saved", Toast.LENGTH_LONG).show()
                etName.text.clear()
                etEmail.text.clear()
            }

        } else {
            Toast.makeText(applicationContext,"fields Can not be Empty",Toast.LENGTH_LONG).show()

        }
    }
    //int the above code, the event name,date,price,des,is captured and the details inserted into the database.
}