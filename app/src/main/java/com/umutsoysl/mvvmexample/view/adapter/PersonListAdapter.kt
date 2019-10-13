package com.umutsoysl.mvvmexample.view.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.umutsoysl.mvvmexample.R
import com.umutsoysl.mvvmexample.model.Person
import androidx.recyclerview.widget.RecyclerView


class PersonListAdapter internal constructor (context: Context) : RecyclerView.Adapter<PersonListAdapter.MyViewHolder>()  {

    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private var person = emptyList<Person>()

    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var title: TextView = view.findViewById(R.id.name)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = inflater.inflate(R.layout.item_person, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        val person = person[position]
        holder.title!!.text= "${person.name} ${person.surname}"
    }

    override fun getItemCount(): Int {
        return person!!.size
    }

    internal fun setPerson(person: List<Person>) {
        this.person = person
        notifyDataSetChanged()
    }

}