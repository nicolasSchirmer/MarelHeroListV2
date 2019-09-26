package com.nschirmer.heroes_list.list.viewpager

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.nschirmer.heroes_list.list.HeroListItemFragment
import com.nschirmer.response_objects.Character


internal class HeroListAdapter(fragmentManager: FragmentManager, private var heroesList: MutableList<Character>):
    FragmentStatePagerAdapter(fragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    override fun getItem(position: Int): Fragment {
        heroesList[position].run {
            return HeroListItemFragment.newInstance(this)
        }
    }


    override fun getCount(): Int = heroesList.size

}