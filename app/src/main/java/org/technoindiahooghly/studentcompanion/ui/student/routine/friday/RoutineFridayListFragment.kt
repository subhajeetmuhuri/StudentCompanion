package org.technoindiahooghly.studentcompanion.ui.student.routine.friday

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import org.technoindiahooghly.studentcompanion.R
import org.technoindiahooghly.studentcompanion.StudentApplication
import org.technoindiahooghly.studentcompanion.adapter.student.routine.friday.RoutineFridayListAdapter
import org.technoindiahooghly.studentcompanion.alarm.student.alarmHandler
import org.technoindiahooghly.studentcompanion.data.student.FridayData
import org.technoindiahooghly.studentcompanion.databinding.FragmentRoutineFridayListBinding
import org.technoindiahooghly.studentcompanion.ui.student.routine.RoutineViewPagerDirections
import org.technoindiahooghly.studentcompanion.viewmodel.student.StudentViewModel
import org.technoindiahooghly.studentcompanion.viewmodel.student.StudentViewModelFactory

class RoutineFridayListFragment :
    Fragment(),
    RoutineFridayListAdapter.FridayDeleteClickInterface,
    RoutineFridayListAdapter.FridayNotifyClickInterface {
    private val viewModel: StudentViewModel by activityViewModels {
        StudentViewModelFactory(
            (activity?.application as StudentApplication).studentDatabase.studentDao())
    }

    private var _binding: FragmentRoutineFridayListBinding? = null
    private val binding
        get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRoutineFridayListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter =
            RoutineFridayListAdapter(this, this) {
                val action =
                    RoutineViewPagerDirections
                        .actionRoutineViewPager2ToRoutineFridayAddUpdateFragment(it.id)
                this.findNavController().navigate(action)
            }

        binding.fridayListRecyclerView.layoutManager = LinearLayoutManager(this.context)
        binding.fridayListRecyclerView.adapter = adapter

        viewModel.getFriday.observe(this.viewLifecycleOwner) { it.let { adapter.submitList(it) } }

        binding.addFridayRoutineFAB.setOnClickListener {
            val action =
                RoutineViewPagerDirections.actionRoutineViewPager2ToRoutineFridayAddUpdateFragment()
            this.findNavController().navigate(action)
        }
    }

    override fun onDeleteImageViewClick(entry: FridayData) {
        val builder = MaterialAlertDialogBuilder(requireContext())
        builder.setPositiveButton(requireContext().getString(R.string.yes_alert_dialog)) { _, _ ->
            alarmHandler(
                requireContext(), entry.id, entry.subjectName, entry.fridayStartTime, CANCEL_ALARM)

            viewModel.setNotify(CANCEL_ALARM, entry.id, DAY)
            viewModel.deleteEntry(entry.id, DAY)

            Toast.makeText(
                    requireContext(),
                    requireContext()
                        .getString(R.string.class_remove_success_toast, entry.subjectName, DAY),
                    Toast.LENGTH_SHORT)
                .show()
        }

        builder.setNegativeButton(requireContext().getString(R.string.no_alert_dialog)) { _, _ -> }
        builder.setTitle(
            requireContext().getString(R.string.class_remove_prompt_title, entry.subjectName, DAY))
        builder.setMessage(requireContext().getString(R.string.class_remove_prompt_message, DAY))
        builder.create().show()
    }

    override fun onNotifyImageViewClick(entry: FridayData) {
        if (!entry.fridayNotification) {
            alarmHandler(
                requireContext(), entry.id, entry.subjectName, entry.fridayStartTime, SET_ALARM)

            viewModel.setNotify(SET_ALARM, entry.id, DAY)

            Toast.makeText(
                    requireContext(),
                    requireContext().getString(R.string.class_notify_on, entry.subjectName, DAY),
                    Toast.LENGTH_SHORT)
                .show()
        } else {
            alarmHandler(
                requireContext(), entry.id, entry.subjectName, entry.fridayStartTime, CANCEL_ALARM)

            viewModel.setNotify(CANCEL_ALARM, entry.id, DAY)

            Toast.makeText(
                    requireContext(),
                    requireContext().getString(R.string.class_notify_off, entry.subjectName, DAY),
                    Toast.LENGTH_SHORT)
                .show()
        }
    }

    companion object {
        private const val DAY = "Friday"
        private const val SET_ALARM = true
        private const val CANCEL_ALARM = !SET_ALARM
    }
}
