package com.formationsi.bigsi2021.phones

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.formationsi.bigsi2021.R


class ListLocalFragment : Fragment() {

    companion object {
        fun newInstance() = ListLocalFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.list_local_fragment, container, false)
    }




}

