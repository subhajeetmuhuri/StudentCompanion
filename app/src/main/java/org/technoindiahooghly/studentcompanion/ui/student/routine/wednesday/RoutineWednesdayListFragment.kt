package org.technoindiahooghly.studentcompanion.ui.student.routine.wednesday

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
import org.technoindiahooghly.studentcompanion.adapter.student.routine.wednesday.RoutineWednesdayListAdapter
import org.technoindiahooghly.studentcompanion.alarm.student.alarmHandler
import org.technoindiahooghly.studentcompanion.data.student.WednesdayData
import org.technoindiahooghly.studentcompanion.databinding.FragmentRoutineWednesdayListBinding
import org.technoindiahooghly.studentcompanion.ui.student.routine.RoutineViewPagerDirections
import org.technoindiahooghly.studentcompanion.viewmodel.student.StudentViewModel
import org.technoindiahooghly.studentcompanion.viewmodel.student.StudentViewModelFactory

class RoutineWednesdayListFragment :
    Fragment(),
    RoutineWednesdayListAdapter.WednesdayDeleteClickInterface,
    RoutineWednesdayListAdapter.WednesdayNotifyClickInterface {
    private val viewModel: StudentViewModel by activityViewModels {
        StudentViewModelFactory(
            (activity?.application as StudentApplication).studentDatabase.studentDao())
    }

    private var _binding: FragmentRoutineWednesdayListBinding? = null
    private val binding
        get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRoutineWednesdayListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter =
            RoutineWednesdayListAdapter(this, this) {
                val action =
                    RoutineViewPagerDirections
                        .actionRoutineViewPager2ToRoutineWednesdayAddUpdateFragment(it.id)
                this.findNavController().navigate(action)
            }

        binding.wednesdayListRecyclerView.layoutManager = LinearLayoutManager(this.context)
        binding.wednesdayListRecyclerView.adapter = adapter

        viewModel.getWednesday.observe(this.viewLifecycleOwner) {
            it.let { adapter.submitList(it) }
        }

        binding.addWednesdayRoutineFAB.setOnClickListener {
            val action =
                RoutineViewPagerDirections
                    .actionRoutineViewPager2ToRoutineWednesdayAddUpdateFragment()
            this.findNavController().navigate(action)
        }
    }

    override fun onDeleteImageViewClick(entry: WednesdayData) {
        val builder = MaterialAlertDialogBuilder(requireContext())
        builder.setPositiveButton(requireContext().getString(R.string.yes_alert_dialog)) { _, _ ->
            alarmHandler(
                requireContext(),
                entry.id,
                entry.subjectName,
                entry.wednesdayStartTime,
                CANCEL_ALARM)

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

    override fun onNotifyImageViewClick(entry: WednesdayData) {
        if (!entry.wednesdayNotification) {
            alarmHandler(
                requireContext(), entry.id, entry.subjectName, entry.wednesdayStartTime, SET_ALARM)

            viewModel.setNotify(SET_ALARM, entry.id, DAY)

            Toast.makeText(
                    requireContext(),
                    requireContext().getString(R.string.class_notify_on, entry.subjectName, DAY),
                    Toast.LENGTH_SHORT)
                .show()
        } else {
            alarmHandler(
                requireContext(),
                entry.id,
                entry.subjectName,
                entry.wednesdayStartTime,
                CANCEL_ALARM)

            viewModel.setNotify(CANCEL_ALARM, entry.id, DAY)

            Toast.makeText(
                    requireContext(),
                    requireContext().getString(R.string.class_notify_off, entry.subjectName, DAY),
                    Toast.LENGTH_SHORT)
                .show()
        }
    }

    companion object {
        private const val DAY = "Wednesday"
        private const val SET_ALARM = true
        private const val CANCEL_ALARM = !SET_ALARM
    }
}
