package com.umutsoysl.mvvmexample.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.umutsoysl.mvvmexample.model.Person
import com.umutsoysl.mvvmexample.model.room.PersonRepository
import com.umutsoysl.mvvmexample.model.room.db.PersonDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext


class PersonViewModel (application: Application) : AndroidViewModel(application) {

    private var parentJob = Job()
    private val coroutineContext: CoroutineContext
        get() = parentJob + Dispatchers.Main
    private val scope = CoroutineScope(coroutineContext)
    private val repository: PersonRepository
    val allPersons: LiveData<List<Person>>

    init {
        val wordsDao = PersonDatabase.getDatabase(application,scope).personDao()
        repository = PersonRepository(wordsDao)
        allPersons = repository.allPersons
    }

    fun insert(person: Person) = viewModelScope.launch {
        repository.insert(person)
    }
}