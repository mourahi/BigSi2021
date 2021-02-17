package com.formationsi.bigsi2021.phones

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.formationsi.bigsi2021.R

class ListFavorisFragment : Fragment() {

    companion object {
        fun newInstance() = ListFavorisFragment()
    }

    private lateinit var viewModel: ListFavorisViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.list_favoris_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(ListFavorisViewModel::class.java)
        // TODO: Use the ViewModel
    }

}