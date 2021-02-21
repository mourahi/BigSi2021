package com.formationsi.bigsi2021.phones

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.formationsi.bigsi2021.R
import com.formationsi.bigsi2021.adapter.RecycleAdapterFavoris
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class ListFavorisFragment : Fragment() {


    companion object {
        fun newInstance() = ListFavorisFragment()
    }

    private lateinit var viewModel: ListFavorisViewModel
    lateinit var recycleview:RecyclerView
    lateinit var adapter: RecycleAdapterFavoris


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.list_favoris_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        recycleview = requireActivity().findViewById(R.id.recycle_favoris)
        recycleview.layoutManager = LinearLayoutManager(requireContext())
        adapter = RecycleAdapterFavoris(listOf(NumerPhone("","")))
        recycleview.adapter = adapter


            viewModel = ViewModelProvider(this).get(ListFavorisViewModel::class.java)
            val x = viewModel.getLocalContacts(activity?.application)
            x.observe(viewLifecycleOwner,{
                    adapter = RecycleAdapterFavoris(it)
                    recycleview.adapter = adapter
            })



    }


}

data class NumerPhone( val name:String, val phoneNo:String)
