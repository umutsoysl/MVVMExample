package com.umutsoysl.mvvmexample.view.activity

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.umutsoysl.mvvmexample.R
import com.umutsoysl.mvvmexample.model.Person
import com.umutsoysl.mvvmexample.util.Constants

class ShowPersonDetailsActivity : AppCompatActivity() {

    var nameTextView : TextView ? = null
    var birthdateTextView : TextView ? = null
    var addressTextView : TextView ? = null
    var hobbyTextView : TextView ? = null
    var person:Person ? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_person_details)

        getPersonInfo()

        init()

        setPersonInfo(person!!)
    }

    private fun init(){
        nameTextView = findViewById(R.id.name)
        birthdateTextView = findViewById(R.id.birthDate)
        addressTextView = findViewById(R.id.address)
        hobbyTextView = findViewById(R.id.hobby)
    }

    private fun getPersonInfo(){
        person = intent.getParcelableExtra(Constants.PUT_EXTRA_PERSON)
    }

    private fun setPersonInfo(person:Person){
        nameTextView!!.text = "${person.name} ${person.surname}"
        birthdateTextView!!.text = person.birthDate
        addressTextView!!.text = person.address
        hobbyTextView!!.text = person.hobby
    }
}
