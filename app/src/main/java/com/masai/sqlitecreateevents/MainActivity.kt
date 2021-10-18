package com.masai.sqlitecreateevents

import android.app.Dialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.dialog_update.*

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
    private  fun getItemsList(): ArrayList<EventModelClass>{
        val databaseHandler: DatabaseHandler = DatabaseHandler(this)
        val empList: ArrayList<EventModelClass> = databaseHandler.viewEmployee()

        return empList
    }
    private fun setUpListofDataIntoRecyclerView(){
        if(getItemsList().size>0){
            rvItemList.layoutManager =LinearLayoutManager(this)
            val itemAdapter =ItemAdapter(this,getItemsList())
            rvItemList.adapter =itemAdapter
        } else{
            rvItemList.visibility = View.GONE
            tvNoRecordsAvailable.visibility =View.VISIBLE
        }

    }
    fun updateRecordDialog(empModelClass: EventModelClass) {
        val updateDialog = Dialog(this, R.style.Theme_Dialog)
        updateDialog.setCancelable(false)
        /*Set the screen content from a layout resource.
         The resource will be inflated, adding all top-level views to the screen.*/
        updateDialog.setContentView(R.layout.dialog_update)

        updateDialog.etUpdateName.setText(empModelClass.name)
        updateDialog.etUpdateEmailId.setText(empModelClass.email)

        updateDialog.tvUpdate.setOnClickListener(View.OnClickListener {

            val name = updateDialog.etUpdateName.text.toString()
            val email = updateDialog.etUpdateEmailId.text.toString()
            val date = updateDialog.etUpdateEmailId.text.toString()
            val price = updateDialog.etUpdateEmailId.text.toString()

            val databaseHandler: DatabaseHandler = DatabaseHandler(this)

            if (!name.isEmpty() && !email.isEmpty()) {
                val status =
                    databaseHandler.updateEmployee(EventModelClass(empModelClass.id, name, email,date,price))
                if (status > -1) {
                    Toast.makeText(applicationContext, "Record Updated.", Toast.LENGTH_LONG).show()

                    setUpListofDataIntoRecyclerView()

                    updateDialog.dismiss() // Dialog will be dismissed
                }
            } else {
                Toast.makeText(
                    applicationContext,
                    "Name or Email cannot be blank",
                    Toast.LENGTH_LONG
                ).show()
            }
        })
        updateDialog.tvCancel.setOnClickListener(View.OnClickListener {
            updateDialog.dismiss()
        })
        //Start the dialog and display it on screen.
        updateDialog.show()
    }

}