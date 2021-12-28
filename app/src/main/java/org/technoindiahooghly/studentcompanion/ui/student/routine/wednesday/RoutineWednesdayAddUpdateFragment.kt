package org.technoindiahooghly.studentcompanion.ui.student.routine.wednesday

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.textfield.TextInputLayout.END_ICON_NONE
import org.technoindiahooghly.studentcompanion.R
import org.technoindiahooghly.studentcompanion.StudentApplication
import org.technoindiahooghly.studentcompanion.alarm.student.alarmHandler
import org.technoindiahooghly.studentcompanion.data.student.Student
import org.technoindiahooghly.studentcompanion.databinding.FragmentRoutineWednesdayAddUpdateBinding
import org.technoindiahooghly.studentcompanion.utils.student.getFormattedTime
import org.technoindiahooghly.studentcompanion.utils.student.timePickerEndTime
import org.technoindiahooghly.studentcompanion.utils.student.timePickerStartTime
import org.technoindiahooghly.studentcompanion.viewmodel.student.RoutineSharedViewModel
import org.technoindiahooghly.studentcompanion.viewmodel.student.StudentViewModel
import org.technoindiahooghly.studentcompanion.viewmodel.student.StudentViewModelFactory

class RoutineWednesdayAddUpdateFragment : Fragment() {
    private val viewModel: StudentViewModel by activityViewModels {
        StudentViewModelFactory(
            (activity?.application as StudentApplication).studentDatabase.studentDao())
    }

    private val sharedViewModel: RoutineSharedViewModel by activityViewModels()
    private lateinit var startTime: String
    private lateinit var endTime: String

    private val navigationArgs: RoutineWednesdayAddUpdateFragmentArgs by navArgs()

    private var _binding: FragmentRoutineWednesdayAddUpdateBinding? = null
    private val binding
        get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRoutineWednesdayAddUpdateBinding.inflate(inflater, container, false)
        return binding.root
    }

    private fun isEntryValid(): Boolean {
        return viewModel.isEntryValid(binding.subjectSelection.text.toString(), startTime, endTime)
    }

    private fun bind(entry: Student) {
        binding.apply {
            binding.subjectDropdown.endIconMode = END_ICON_NONE
            binding.subjectSelection.setText(entry.subjectName, TextView.BufferType.SPANNABLE)

            binding.wednesdayTimeStartText.setText(
                getFormattedTime(requireContext(), entry.wednesdayStartTime),
                TextView.BufferType.SPANNABLE)
            binding.wednesdayTimeEndText.setText(
                getFormattedTime(requireContext(), entry.wednesdayEndTime),
                TextView.BufferType.SPANNABLE)

            sharedViewModel.setStartTime(entry.wednesdayStartTime)
            sharedViewModel.setEndTime(entry.wednesdayEndTime)

            chooseStartEndTime(entry.wednesdayStartTime, entry.wednesdayEndTime)
            wednesdaySaveButton.setOnClickListener { updateNewEntry(entry) }
        }
    }

    private fun addNewEntry() {
        val subject = binding.subjectSelection.text.toString()

        if (isEntryValid()) {
            viewModel.addUpdateNewEntry(subject, startTime, endTime, DAY)

            Toast.makeText(
                    requireContext(),
                    requireContext().getString(R.string.class_add_success_toast, subject, DAY),
                    Toast.LENGTH_SHORT)
                .show()

            sharedViewModel.setStartTime("")
            sharedViewModel.setEndTime("")

            val action =
                RoutineWednesdayAddUpdateFragmentDirections
                    .actionRoutineWednesdayAddUpdateFragmentToRoutineViewPager2()
            this.findNavController().navigate(action)
        } else {
            Toast.makeText(
                    requireContext(),
                    requireContext().getString(R.string.class_add_update_empty_warning_toast),
                    Toast.LENGTH_SHORT)
                .show()
        }
    }

    private fun updateNewEntry(entry: Student) {
        val subject = binding.subjectSelection.text.toString()

        if (isEntryValid()) {
            viewModel.addUpdateNewEntry(subject, startTime, endTime, DAY)

            if (entry.wednesdayNotification) {
                alarmHandler(requireContext(), entry.id, subject, startTime, SET_ALARM)
            }

            Toast.makeText(
                    requireContext(),
                    requireContext().getString(R.string.class_update_success_toast, subject, DAY),
                    Toast.LENGTH_SHORT)
                .show()

            sharedViewModel.setStartTime("")
            sharedViewModel.setEndTime("")

            val action =
                RoutineWednesdayAddUpdateFragmentDirections
                    .actionRoutineWednesdayAddUpdateFragmentToRoutineViewPager2()
            this.findNavController().navigate(action)
        } else {
            Toast.makeText(
                    requireContext(),
                    requireContext().getString(R.string.class_add_update_empty_warning_toast),
                    Toast.LENGTH_SHORT)
                .show()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val id = navigationArgs.id
        if (id > 0) {
            viewModel.retrieveEntry(id).observe(this.viewLifecycleOwner) { bind(it) }
        } else {
            viewModel.getSubjects.observe(this.viewLifecycleOwner) {
                val adapter = ArrayAdapter(requireContext(), R.layout.dropdown_menu, it.distinct())
                binding.subjectSelection.setAdapter(adapter)
            }

            chooseStartEndTime()
            binding.wednesdaySaveButton.setOnClickListener { addNewEntry() }
        }
    }

    private fun chooseStartEndTime(startTimeInMills: String = "", endTimeInMills: String = "") {
        binding.wednesdayTimeStartText.setOnClickListener {
            timePickerStartTime(
                binding.wednesdayTimeStartText,
                requireContext(),
                childFragmentManager,
                sharedViewModel,
                DAY_OF_WEEK,
                startTimeInMills)
        }

        sharedViewModel.startTime.observe(this.viewLifecycleOwner) { startTime = it }

        binding.wednesdayTimeEndText.setOnClickListener {
            timePickerEndTime(
                binding.wednesdayTimeEndText,
                requireContext(),
                childFragmentManager,
                sharedViewModel,
                DAY_OF_WEEK,
                endTimeInMills)
        }

        sharedViewModel.endTime.observe(this.viewLifecycleOwner) { endTime = it }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private const val DAY_OF_WEEK = 4
        private const val DAY = "Wednesday"
        private const val SET_ALARM = true
        private const val CANCEL_ALARM = !SET_ALARM
    }
}
