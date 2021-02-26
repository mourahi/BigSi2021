package com.formationsi.bigsi2021.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.formationsi.bigsi2021.groups.ListGroupsFragment
import com.formationsi.bigsi2021.phones.ListLocalFragment
import com.formationsi.bigsi2021.phones.ListPhonesFragment


class AdapterTabs(fragmentActivity: FragmentActivity) : FragmentStateAdapter(fragmentActivity) {

    override fun getItemCount(): Int {
        return 3
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            1 -> ListLocalFragment.newInstance()
            2 -> ListGroupsFragment.newInstance()
            else -> ListPhonesFragment.newInstance()
        }
    }
}