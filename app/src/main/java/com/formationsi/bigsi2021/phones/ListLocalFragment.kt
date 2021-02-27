package com.formationsi.bigsi2021.phones

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.formationsi.bigsi2021.R
import com.formationsi.bigsi2021.adapter.RecycleAdapterFavoris
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class ListLocalFragment : Fragment() {

    companion object {
        fun newInstance() = ListLocalFragment()
    }

    private val viewModel: ListLocalViewModel by lazy {
        ViewModelProvider(this).get(ListLocalViewModel::class.java)
    }

    private lateinit var recycleview:RecyclerView
    private lateinit var  progressBar:ProgressBar
    private lateinit var txt_compteur :TextView
    private lateinit var adapter: RecycleAdapterFavoris

    override fun onAttach(context: Context) {
        super.onAttach(context)
        GlobalScope.launch(Dispatchers.IO) {
            viewModel.prepareLocalContacts(activity?.application)
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.list_local_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recycleview = view.findViewById(R.id.recycle_favoris)
        progressBar = view.findViewById(R.id.progressBar)
        txt_compteur = view.findViewById(R.id.txt_compteur)

        recycleview.layoutManager = LinearLayoutManager(context)
        adapter = RecycleAdapterFavoris(listOf())
        recycleview.adapter = adapter

        viewModel.getLocalContacts().observe(viewLifecycleOwner,{
            adapter = RecycleAdapterFavoris(it)
            recycleview.adapter = adapter
            progressBar.visibility = View.GONE
        })

        viewModel.compteurLocalContact.observe(viewLifecycleOwner,{
            txt_compteur.text = it.toString()
        })

    }



}

