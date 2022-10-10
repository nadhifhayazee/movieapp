package com.nadhif.hayazee.favorite

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter


class TabAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {

    private val fragmentList = arrayListOf<Fragment>()
    fun addFragment(fragment: Fragment) {
        fragmentList.add(fragment)
    }

    override fun getItemCount(): Int {
        return fragmentList.size
    }

    override fun createFragment(position: Int): Fragment {
        return fragmentList[position]
    }


}