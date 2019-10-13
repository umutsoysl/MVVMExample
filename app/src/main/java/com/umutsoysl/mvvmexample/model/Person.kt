package com.umutsoysl.mvvmexample.model

import android.os.Parcel
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "PersonTable")
data class Person(
     val name     : String,
     val surname  : String,
     val birthDate: String,
     val address  : String,
     val hobby    : String
): Parcelable {
     @PrimaryKey(autoGenerate = true)
     var id: Int = 0

     constructor(parcel: Parcel) : this(
          parcel.readString(),
          parcel.readString(),
          parcel.readString(),
          parcel.readString(),
          parcel.readString()
     ) {
          id = parcel.readInt()
     }

     override fun writeToParcel(parcel: Parcel, flags: Int) {
          parcel.writeString(name)
          parcel.writeString(surname)
          parcel.writeString(birthDate)
          parcel.writeString(address)
          parcel.writeString(hobby)
          parcel.writeInt(id)
     }

     override fun describeContents(): Int {
          return 0
     }

     companion object CREATOR : Parcelable.Creator<Person> {
          override fun createFromParcel(parcel: Parcel): Person {
               return Person(parcel)
          }

          override fun newArray(size: Int): Array<Person?> {
               return arrayOfNulls(size)
          }
     }
}
