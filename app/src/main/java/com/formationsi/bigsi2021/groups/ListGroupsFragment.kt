package com.formationsi.bigsi2021.groups

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.formationsi.bigsi2021.R

class ListGroupsFragment : Fragment() {

    companion object {
        fun newInstance() = ListGroupsFragment()
    }

    private lateinit var viewModel: ListGroupsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.list_groups_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(ListGroupsViewModel::class.java)
        // TODO: Use the ViewModel
    }

}