package com.formationsi.bigsi2021.phones

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.formationsi.bigsi2021.R
import com.formationsi.bigsi2021.adapter.RecycleAdapterPhones
import com.formationsi.bigsi2021.db.School


class ListPhonesFragment : Fragment() {
    private val myviewmodel: ListPhonesViewModel by lazy {
        ViewModelProvider(this).get(ListPhonesViewModel::class.java)
    }

    companion object {
        fun newInstance() = ListPhonesFragment()
    }

    private var mylist:List<School> =  listOf()
    private lateinit var recycle: RecyclerView
    private lateinit var adapter: RecycleAdapterPhones

    override fun onAttach(context: Context) {
        super.onAttach(context)
        myviewmodel.getDataSheet()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.list_phones_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recycle = view.findViewById(R.id.recycle_phones)
        recycle.layoutManager = LinearLayoutManager(view.context)
        adapter = RecycleAdapterPhones(mylist)
        recycle.adapter = adapter
        myviewmodel.getDataSheet().observe(viewLifecycleOwner, {
            mylist = if(!it.isNullOrEmpty()) it else mylist
            adapter = RecycleAdapterPhones(mylist)
            recycle.adapter = adapter

        })





    }

}