package com.umutsoysl.mvvmexample.view.activity


import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import com.umutsoysl.mvvmexample.R
import com.umutsoysl.mvvmexample.model.Person
import com.umutsoysl.mvvmexample.util.Constants
import com.umutsoysl.mvvmexample.util.Exceptions
import com.umutsoysl.mvvmexample.viewmodel.PersonViewModel
import java.util.*


class AddPersonActivity : AppCompatActivity() {

    var nameEdittext : EditText ? = null
    var surnameEdittext : EditText ? = null
    var birthDateEdittext : EditText ? = null
    var addressEdittext : EditText ? = null
    var hobbyEdittext : EditText ? = null
    var saveButton : Button ? = null
    var person : Person ? = null
    lateinit var personViewModel: PersonViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_person)

        personViewModel = ViewModelProviders.of(this)[PersonViewModel::class.java]

        init()

        saveButton!!.setOnClickListener(View.OnClickListener {

            save()

        })
    }

    private fun init(){
        nameEdittext = findViewById(R.id.name)
        surnameEdittext = findViewById(R.id.surname)
        birthDateEdittext = findViewById(R.id.birthDate)
        addressEdittext = findViewById(R.id.address)
        hobbyEdittext = findViewById(R.id.hobby)
        saveButton = findViewById(R.id.saveButton)
    }

    fun clickDataPicker(view: View) {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val dpd = DatePickerDialog(this, DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->

         birthDateEdittext!!.setText("$dayOfMonth-${monthOfYear + 1}-$year")

        }, year, month, day)
        dpd.show()
    }

    private fun setPersonInfo(){
        person = Person(nameEdittext!!.text.toString(), surnameEdittext!!.text.toString(),birthDateEdittext!!.text.toString(),addressEdittext!!.text.toString(),hobbyEdittext!!.text.toString())
    }


    private fun save(){

        setPersonInfo()

        if(!isValidText(nameEdittext!!.text.toString())){
            Toast.makeText(applicationContext,Exceptions.NAME_ENTRY_EXCEPTION ,Toast.LENGTH_SHORT).show()

            return
        }

        if(!isValidText(surnameEdittext!!.text.toString())){
            Toast.makeText(applicationContext,Exceptions.SURNAME_ENTRY_EXCEPTION ,Toast.LENGTH_SHORT).show()

            return
        }

        personViewModel.insert(person!!)

        val intent = Intent(applicationContext, MainActivity::class.java)
        startActivity(intent)
        Toast.makeText(applicationContext,Constants.SUCCESS_SAVE ,Toast.LENGTH_SHORT).show()

    }


    private fun isValidText(text:String): Boolean {
        return text != null && text.length > 3
    }

}
