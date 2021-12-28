package org.technoindiahooghly.studentcompanion.ui.student.routine

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayoutMediator
import org.technoindiahooghly.studentcompanion.adapter.student.routine.RoutineViewPagerAdapter
import org.technoindiahooghly.studentcompanion.databinding.FragmentRoutineViewPagerBinding
import org.technoindiahooghly.studentcompanion.ui.student.routine.friday.RoutineFridayListFragment
import org.technoindiahooghly.studentcompanion.ui.student.routine.monday.RoutineMondayListFragment
import org.technoindiahooghly.studentcompanion.ui.student.routine.saturday.RoutineSaturdayListFragment
import org.technoindiahooghly.studentcompanion.ui.student.routine.thursday.RoutineThursdayListFragment
import org.technoindiahooghly.studentcompanion.ui.student.routine.tuesday.RoutineTuesdayListFragment
import org.technoindiahooghly.studentcompanion.ui.student.routine.wednesday.RoutineWednesdayListFragment
import org.technoindiahooghly.studentcompanion.viewmodel.student.RoutineSharedViewModel

class RoutineViewPager : Fragment() {

    private var _binding: FragmentRoutineViewPagerBinding? = null
    private val binding
        get() = _binding!!

    private lateinit var viewPager: ViewPager2
    private val sharedViewModel: RoutineSharedViewModel by activityViewModels()

    private val viewPagerCallback =
        object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                sharedViewModel.updatePageSelected(position)
            }
        }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRoutineViewPagerBinding.inflate(inflater, container, false)
        viewPager = binding.routineViewPager

        val fragmentList =
            arrayListOf(
                RoutineMondayListFragment(),
                RoutineTuesdayListFragment(),
                RoutineWednesdayListFragment(),
                RoutineThursdayListFragment(),
                RoutineFridayListFragment(),
                RoutineSaturdayListFragment())

        @Suppress("UNCHECKED_CAST") val adapter = RoutineViewPagerAdapter(fragmentList, this)

        viewPager.adapter = adapter

        viewPager.registerOnPageChangeCallback(viewPagerCallback)
        sharedViewModel.viewPagerSelected.observe(this.viewLifecycleOwner) {
            viewPager.setCurrentItem(it, false)
        }

        val tabLayout = binding.routineTabLayout
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
                when (position) {
                    0 -> tab.text = "MON"
                    1 -> tab.text = "TUE"
                    2 -> tab.text = "WED"
                    3 -> tab.text = "THU"
                    4 -> tab.text = "FRI"
                    5 -> tab.text = "SAT"
                }
            }
            .attach()

        return binding.root
    }

    //    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    //
    //        sharedViewModel.viewPagerSelected.observe(this.viewLifecycleOwner) { page ->
    //            viewPager.setCurrentItem(page, false)
    //        }
    //
    //        viewPager.registerOnPageChangeCallback(
    //            object : ViewPager2.OnPageChangeCallback() {
    //                override fun onPageSelected(page: Int) {
    //                    sharedViewModel.updatePageSelected(page)
    //                }
    //            })
    //
    //    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewPager.unregisterOnPageChangeCallback(viewPagerCallback)
        _binding = null
    }
}
