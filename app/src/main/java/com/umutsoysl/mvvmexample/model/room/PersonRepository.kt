package com.umutsoysl.mvvmexample.model.room

import androidx.lifecycle.LiveData
import com.umutsoysl.mvvmexample.model.Person
import com.umutsoysl.mvvmexample.model.room.dao.PersonDao
import android.os.AsyncTask


class PersonRepository(private val personDao: PersonDao) {


    val allPersons: LiveData<List<Person>> = personDao.getAllPerson()


    fun insert(person: Person) {
        InsertPersonAsyncTask(personDao!!).execute(person)
    }


    private class InsertPersonAsyncTask internal constructor(private val personDao: PersonDao) :
        AsyncTask<Person, Void, Void>() {

        override fun doInBackground(vararg user: Person): Void? {
            personDao.insert(user[0])
            return null
        }
    }
}