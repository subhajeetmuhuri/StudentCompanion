package org.technoindiahooghly.studentcompanion.adapter.student.routine

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class RoutineViewPagerAdapter(list: ArrayList<Fragment>, fm: Fragment) : FragmentStateAdapter(fm) {

    private val fragmentList = list

    override fun getItemCount(): Int {
        return fragmentList.size
    }

    override fun createFragment(position: Int): Fragment {
        return fragmentList[position]
    }
}
