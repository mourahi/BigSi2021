package com.formationsi.bigsi2021.phones

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.GridLayout
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.formationsi.bigsi2021.R
import com.formationsi.bigsi2021.adapter.RecycleAdapterPhones

class ListPhonesFragment : Fragment() {

    companion object {
        fun newInstance() = ListPhonesFragment()
    }

    private lateinit var viewModel: ListPhonesViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.list_phones_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(ListPhonesViewModel::class.java)
        viewModel.getData().observe(this, {
            Log.d("adil","it = $it")
        })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val recycle = view.findViewById<RecyclerView>(R.id.recycle_phones)
        recycle.layoutManager = LinearLayoutManager(view.context)
        val mylist = listOf(Phone("adil",40), Phone("saida", 30))
        val adapter = RecycleAdapterPhones(mylist)
        recycle.adapter = adapter

    }

}