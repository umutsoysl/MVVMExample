package com.umutsoysl.mvvmexample.view.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.umutsoysl.mvvmexample.R
import com.umutsoysl.mvvmexample.model.Person
import com.umutsoysl.mvvmexample.util.Constants
import com.umutsoysl.mvvmexample.view.adapter.PersonListAdapter
import com.umutsoysl.mvvmexample.viewmodel.PersonViewModel

class MainActivity : AppCompatActivity() {

    var personListView : RecyclerView ? = null
    var addButton : FloatingActionButton ? = null
    private lateinit var personViewModel: PersonViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        init()

        val adapter = PersonListAdapter(this)
        personListView!!.adapter = adapter
        personListView!!.layoutManager = LinearLayoutManager(this)

        personViewModel = ViewModelProviders.of(this)[PersonViewModel::class.java]


        personViewModel.allPersons.observe(this, Observer { person ->
            person?.let { adapter.setPerson(it) }
        })

        addButton!!.setOnClickListener {
            val intent = Intent(this@MainActivity, AddPersonActivity::class.java)
            startActivity(intent)
        }

        personListView!!.addOnItemClickListener(object : OnItemClickListener {
            override fun onItemClicked(position: Int, view: View) {

                val itemPosition = personListView!!.getChildLayoutPosition(view)

                val person : Person = personViewModel.allPersons.value!![itemPosition]

                val intent = Intent(applicationContext, ShowPersonDetailsActivity::class.java)
                intent.putExtra(Constants.PUT_EXTRA_PERSON, person)
                startActivity(intent)

            }
        })

    }

    fun init(){
        personListView = findViewById(R.id.personListView)
        addButton = findViewById(R.id.addPersonButton)
    }



    interface OnItemClickListener {
        fun onItemClicked(position: Int, view: View)
    }

    fun RecyclerView.addOnItemClickListener(onClickListener: OnItemClickListener) {
        this.addOnChildAttachStateChangeListener(object : RecyclerView.OnChildAttachStateChangeListener {

            override fun onChildViewAttachedToWindow(view: View) = view?.setOnClickListener {
                val holder = getChildViewHolder(view)
                onClickListener.onItemClicked(holder.adapterPosition, view)
            }

            override fun onChildViewDetachedFromWindow(view: View) {
                view?.setOnClickListener(null)
            }
        })
    }
}
