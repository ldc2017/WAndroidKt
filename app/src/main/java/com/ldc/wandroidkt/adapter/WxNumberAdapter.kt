package com.ldc.wandroidkt.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import me.yokeyword.fragmentation.SupportFragment

class WxNumberAdapter(fm: FragmentManager) :
    FragmentStatePagerAdapter(fm, FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
    lateinit var fragments: MutableList<SupportFragment>
    lateinit var tabs: MutableList<String>

    fun setNewData(fragments: MutableList<SupportFragment>, tabs: MutableList<String>) {
        this.fragments = fragments
        this.tabs = tabs

    }

    override fun getItem(position: Int): Fragment {

        return fragments[position]

    }

    override fun getCount(): Int {
        return fragments.size
    }


    override fun getPageTitle(position: Int): CharSequence? {
        return tabs[position]
    }
}