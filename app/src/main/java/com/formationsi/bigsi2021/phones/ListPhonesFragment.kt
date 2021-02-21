package com.formationsi.bigsi2021.phones

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.formationsi.bigsi2021.R
import com.formationsi.bigsi2021.adapter.RecycleAdapterPhones
import com.formationsi.bigsi2021.db.School


class ListPhonesFragment : Fragment() {
    private val myviewmodel: ListPhonesViewModel by activityViewModels()

    companion object {
        fun newInstance() = ListPhonesFragment()
    }

    private var mylist = listOf<School>()
    private lateinit var recycle: RecyclerView
    private lateinit var adapter: RecycleAdapterPhones


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
        mylist = listOf(School("", "", ""))
        adapter = RecycleAdapterPhones(mylist)
        recycle.adapter = adapter
        myviewmodel.getshcools().observe(viewLifecycleOwner, {
            mylist = it
            adapter = RecycleAdapterPhones(mylist)
            recycle.adapter = adapter

        })

    }

}