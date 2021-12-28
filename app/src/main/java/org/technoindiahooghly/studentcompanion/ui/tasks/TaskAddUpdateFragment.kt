package org.technoindiahooghly.studentcompanion.ui.tasks

import android.content.Context.INPUT_METHOD_SERVICE
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.DateValidatorPointForward
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.datepicker.MaterialDatePicker.todayInUtcMilliseconds
import java.util.*
import org.technoindiahooghly.studentcompanion.StudentApplication
import org.technoindiahooghly.studentcompanion.alarm.student.alarmHandler
import org.technoindiahooghly.studentcompanion.data.tasks.Task
import org.technoindiahooghly.studentcompanion.databinding.FragmentTaskAddUpdateBinding
import org.technoindiahooghly.studentcompanion.utils.tasks.getFormattedTime
import org.technoindiahooghly.studentcompanion.viewmodel.tasks.TaskViewModel
import org.technoindiahooghly.studentcompanion.viewmodel.tasks.TaskViewModelFactory

class TaskAddUpdateFragment : Fragment() {
    private val viewModel: TaskViewModel by activityViewModels {
        TaskViewModelFactory((activity?.application as StudentApplication).taskDatabase.taskDao())
    }

    private var selectedDeadline = ""

    private val navigationArgs: TaskAddUpdateFragmentArgs by navArgs()

    private var _binding: FragmentTaskAddUpdateBinding? = null
    private val binding
        get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTaskAddUpdateBinding.inflate(inflater, container, false)
        return binding.root
    }

    private fun isTaskValid(): Boolean {
        return viewModel.isTaskValid(
            binding.taskAddUpdateText.editText?.text.toString(), selectedDeadline)
    }

    private fun bind(task: Task) {
        binding.apply {
            taskAddUpdateText.editText?.setText(task.taskName, TextView.BufferType.SPANNABLE)
            taskDeadlineAddUpdateText.editText?.setText(
                getFormattedTime(task.taskDeadline), TextView.BufferType.SPANNABLE)

            chooseDeadline(task.taskDeadline.toLong())
            saveButton.setOnClickListener { updateTask(task) }
        }
    }

    private fun updateTask(task: Task) {
        if (isTaskValid()) {
            val taskName = this.binding.taskAddUpdateText.editText?.text.toString()
            viewModel.updateTask(this.navigationArgs.id, taskName, selectedDeadline)

            if (task.taskNotification) {
                alarmHandler(requireContext(), task.id, taskName, selectedDeadline, SET_ALARM)
            }

            Toast.makeText(
                    requireContext(), "Updated details of task: $taskName", Toast.LENGTH_SHORT)
                .show()

            selectedDeadline = ""

            val action =
                TaskAddUpdateFragmentDirections.actionTaskAddUpdateFragmentToTaskListFragment()
            this.findNavController().navigate(action)
        } else {
            Toast.makeText(requireContext(), "Don't leave any fields empty!", Toast.LENGTH_SHORT)
                .show()
        }
    }

    private fun addNewTask() {
        if (isTaskValid()) {
            val task = binding.taskAddUpdateText.editText?.text.toString()
            viewModel.addNewTask(task, selectedDeadline)

            Toast.makeText(
                    requireContext(), "Successfully added task: ${task}!", Toast.LENGTH_SHORT)
                .show()

            selectedDeadline = ""

            val action =
                TaskAddUpdateFragmentDirections.actionTaskAddUpdateFragmentToTaskListFragment()
            this.findNavController().navigate(action)
        } else {
            Toast.makeText(requireContext(), "Please fill out all fields!", Toast.LENGTH_SHORT)
                .show()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val id = navigationArgs.id
        if (id > 0) {
            viewModel.retrieveTask(id).observe(this.viewLifecycleOwner) { bind(it) }
        } else {
            chooseDeadline()
            binding.saveButton.setOnClickListener { addNewTask() }
        }
    }

    private fun chooseDeadline(timeInMills: Long = todayInUtcMilliseconds()) {
        binding.taskDeadlineAddUpdateText.editText?.setOnClickListener {
            val constraints =
                CalendarConstraints.Builder().setValidator(DateValidatorPointForward.now())

            val picker =
                MaterialDatePicker.Builder.datePicker()
                    .setSelection(timeInMills)
                    .setCalendarConstraints(constraints.build())
                    .build()

            picker.show(childFragmentManager, "TAG")

            picker.addOnPositiveButtonClickListener {
                val calendar = Calendar.getInstance(Locale.US)
                calendar.timeInMillis = picker.selection!!
                calendar.set(Calendar.HOUR_OF_DAY, 8)
                calendar.set(Calendar.MINUTE, 0)

                selectedDeadline = calendar.timeInMillis.toString()

                binding.taskDeadlineAddUpdateText.editText?.setText(
                    getFormattedTime(selectedDeadline), TextView.BufferType.SPANNABLE)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        val inputMethodManager =
            requireActivity().getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(requireActivity().currentFocus?.windowToken, 0)
        _binding = null
    }

    companion object {
        private const val SET_ALARM = true
        private const val CANCEL_ALARM = !SET_ALARM
    }
}
