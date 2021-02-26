package com.formationsi.bigsi2021.phones

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.formationsi.bigsi2021.R
import com.formationsi.bigsi2021.adapter.RecycleAdapterFavoris


class ListLocalFragment : Fragment() {

    companion object {
        fun newInstance() = ListLocalFragment()
    }

    private val viewModel: ListFavorisViewModel by lazy {
        ViewModelProvider(this).get(ListFavorisViewModel::class.java)
    }

    private lateinit var recycleview:RecyclerView
    private lateinit var  progressBar:ProgressBar
    private lateinit var adapter: RecycleAdapterFavoris

    override fun onAttach(context: Context) {
        super.onAttach(context)
        viewModel.prepareLocalContacts(activity?.application)
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
        recycleview.layoutManager = LinearLayoutManager(context)
        adapter = RecycleAdapterFavoris(listOf())
        recycleview.adapter = adapter

        viewModel.mylistphone.observe(viewLifecycleOwner,{
            adapter = RecycleAdapterFavoris(it)
            recycleview.adapter = adapter
            progressBar.visibility = View.GONE
        })
    }



}

data class NumerPhone( val name:String, val phoneNo:String)
