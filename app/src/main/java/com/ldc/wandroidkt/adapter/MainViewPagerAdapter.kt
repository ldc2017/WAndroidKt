package com.ldc.wandroidkt.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import me.yokeyword.fragmentation.SupportFragment

class MainViewPagerAdapter(fm: FragmentManager) :
    FragmentStatePagerAdapter(fm, FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
    lateinit var fragments: Array<SupportFragment?>
    lateinit var tabs: Array<String>

    fun setNewData(fragments: Array<SupportFragment?>, tabs: Array<String>) {
        this.fragments = fragments
        this.tabs = tabs

    }

    override fun getItem(position: Int): Fragment {

        return fragments.get(position)!!

    }

    override fun getCount(): Int {
        return fragments.size
    }


    override fun getPageTitle(position: Int): CharSequence? {
        return tabs.get(position)
    }
}