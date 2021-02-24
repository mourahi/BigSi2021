package com.formationsi.bigsi2021.phones

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.formationsi.bigsi2021.R
import com.formationsi.bigsi2021.adapter.RecycleAdapterPhones


class ListPhonesFragment : Fragment() {
    private lateinit var myviewmodel: ListPhonesViewModel

    companion object {
        fun newInstance() = ListPhonesFragment()
    }

    private lateinit var mylist:ArrayList<MutableMap<String, String>>
    private lateinit var recycle: RecyclerView
    private lateinit var adapter: RecycleAdapterPhones

    override fun onAttach(context: Context) {
        super.onAttach(context)
        myviewmodel = ViewModelProvider(this).get(ListPhonesViewModel::class.java)
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
        myviewmodel.datasheet.observe(viewLifecycleOwner, {
            mylist = it
            Log.d("adil","it de l'adpateur = $it")
            adapter = RecycleAdapterPhones(mylist)
            recycle.adapter = adapter

        })





    }

}