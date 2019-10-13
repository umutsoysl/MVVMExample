package com.umutsoysl.mvvmexample.model.room.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.umutsoysl.mvvmexample.model.Person


@Dao
interface  PersonDao {

    @Insert
    fun insert(person: Person)

    @Query("DELETE FROM PersonTable")
    fun deleteAllPerson()

    @Query("SELECT * FROM PersonTable")
    fun getAllPerson(): LiveData<List<Person>>

    @Query("DELETE FROM PersonTable WHERE id = :personID")
    fun deleteByPersonId(personID:Int)

}