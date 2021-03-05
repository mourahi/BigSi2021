package com.formationsi.bigsi2021.local

import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.formationsi.bigsi2021.R
import com.formationsi.bigsi2021.adapter.RecycleAdapterLocalPhones
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class LocalPhonesActivity : AppCompatActivity() {
    private lateinit var recycleview: RecyclerView
    private lateinit var progressBar: ProgressBar
    private lateinit var txt_compteur: TextView
    private val viewModel: ListLocalPhonesViewModel by lazy {
     ViewModelProvider(this).get(ListLocalPhonesViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_local_phones)
        recycleview = findViewById(R.id.recycle_local_phones)
        progressBar = findViewById(R.id.progressBar)
        txt_compteur = findViewById(R.id.txt_compteur)

        GlobalScope.launch(Dispatchers.IO) {
            viewModel.prepareLocalContacts(application)
        }

        recycleview.layoutManager =LinearLayoutManager(this)
        recycleview.adapter = RecycleAdapterLocalPhones(listOf())

        viewModel.listLocalPhones.observe(this, {
            recycleview.adapter  = RecycleAdapterLocalPhones(it)
            progressBar.visibility = View.GONE
        })

        viewModel.compteurLocalContact.observe(this, {
            txt_compteur.text = it.toString()
        })
    }
}