package com.umutsoysl.mvvmexample.model.room.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.umutsoysl.mvvmexample.model.room.dao.PersonDao
import com.umutsoysl.mvvmexample.model.Person
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(entities = [Person::class], version = 1)
abstract class PersonDatabase : RoomDatabase() {

    abstract fun personDao(): PersonDao

    companion object {

        @Volatile
        private var INSTANCE: PersonDatabase? = null

        fun getDatabase(context: Context, scope: CoroutineScope): PersonDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    PersonDatabase::class.java,
                    "person_database"
                )   .fallbackToDestructiveMigration()
                    .addCallback(PersonDatabaseCallback(scope))
                    .build()
                INSTANCE = instance
                return instance
            }
        }

        private class PersonDatabaseCallback(
            private val scope: CoroutineScope
        ) : RoomDatabase.Callback() {

            override fun onOpen(db: SupportSQLiteDatabase) {
                super.onOpen(db)

                INSTANCE?.let { database ->
                    scope.launch(Dispatchers.IO) {
                        populateDatabase(database.personDao())
                    }
                }
            }
        }


        fun populateDatabase(personDao: PersonDao) {

            personDao.deleteAllPerson()

            var person = Person("Umut", "Soysal", "10-07-1985", "İstanbul - Üsküdar", "Gezmek")
            personDao.insert(person)
            var person2 = Person("Selim", "Taşkın", "01-21-1995", "İzmir - Alsancak", "Kitap okumak")
            personDao.insert(person2)

        }

    }

}