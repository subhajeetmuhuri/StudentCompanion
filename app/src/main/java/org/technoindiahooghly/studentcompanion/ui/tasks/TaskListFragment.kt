package org.technoindiahooghly.studentcompanion.ui.tasks

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
import org.technoindiahooghly.studentcompanion.adapter.tasks.TaskListAdapter
import org.technoindiahooghly.studentcompanion.alarm.tasks.alarmHandler
import org.technoindiahooghly.studentcompanion.data.tasks.Task
import org.technoindiahooghly.studentcompanion.databinding.FragmentTaskListBinding
import org.technoindiahooghly.studentcompanion.viewmodel.tasks.TaskViewModel
import org.technoindiahooghly.studentcompanion.viewmodel.tasks.TaskViewModelFactory

class TaskListFragment :
    Fragment(),
    TaskListAdapter.TaskDeleteClickInterface,
    TaskListAdapter.TaskNotifyClickInterface,
    TaskListAdapter.TaskDoneClickInterface {
    private val viewModel: TaskViewModel by activityViewModels {
        TaskViewModelFactory((activity?.application as StudentApplication).taskDatabase.taskDao())
    }

    private var _binding: FragmentTaskListBinding? = null
    private val binding
        get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTaskListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter =
            TaskListAdapter(this, this, this) {
                if (!it.taskDone) {
                    val action =
                        TaskListFragmentDirections.actionTaskListFragmentToTaskAddUpdateFragment(
                            it.id)
                    this.findNavController().navigate(action)
                }
            }

        binding.taskListRecyclerView.layoutManager = LinearLayoutManager(this.context)
        binding.taskListRecyclerView.adapter = adapter

        viewModel.getTasks.observe(this.viewLifecycleOwner) { it.let { adapter.submitList(it) } }

        binding.addTaskFAB.setOnClickListener {
            val action = TaskListFragmentDirections.actionTaskListFragmentToTaskAddUpdateFragment()
            this.findNavController().navigate(action)
        }
    }

    override fun onDeleteImageViewClick(task: Task) {
        val builder = MaterialAlertDialogBuilder(requireContext())
        builder.setPositiveButton(requireContext().getString(R.string.yes_alert_dialog)) { _, _ ->
            alarmHandler(requireContext(), task.id, task.taskName, task.taskDeadline, CANCEL_ALARM)

            viewModel.setNotify(CANCEL_ALARM, task.id)
            viewModel.deleteEntry(task)

            Toast.makeText(
                    requireContext(),
                    requireContext().getString(R.string.task_remove_success_toast, task.taskName),
                    Toast.LENGTH_SHORT)
                .show()
        }

        builder.setNegativeButton(requireContext().getString(R.string.no_alert_dialog)) { _, _ -> }
        builder.setTitle(
            requireContext().getString(R.string.task_remove_prompt_title, task.taskName))
        builder.setMessage(requireContext().getString(R.string.task_remove_prompt_message))
        builder.create().show()
    }

    override fun onNotifyImageViewClick(task: Task) {
        if (task.taskDone) {
            Toast.makeText(requireContext(), "Task already marked as done!", Toast.LENGTH_SHORT)
                .show()
        } else {
            if (!task.taskNotification) {
                alarmHandler(requireContext(), task.id, task.taskName, task.taskDeadline, SET_ALARM)
                viewModel.setNotify(SET_ALARM, task.id)

                Toast.makeText(
                        requireContext(),
                        requireContext().getString(R.string.task_notify_on, task.taskName),
                        Toast.LENGTH_SHORT)
                    .show()
            } else {
                alarmHandler(
                    requireContext(), task.id, task.taskName, task.taskDeadline, CANCEL_ALARM)
                viewModel.setNotify(CANCEL_ALARM, task.id)

                Toast.makeText(
                        requireContext(),
                        requireContext().getString(R.string.task_notify_off, task.taskName),
                        Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }

    override fun onDoneCheckBoxClick(task: Task) {
        if (!task.taskDone) {
            alarmHandler(requireContext(), task.id, task.taskName, task.taskDeadline, CANCEL_ALARM)
            viewModel.setNotify(CANCEL_ALARM, task.id)
            viewModel.setDone(true, task.id)
        } else {
            viewModel.setDone(false, task.id)
        }
    }

    companion object {
        private const val SET_ALARM = true
        private const val CANCEL_ALARM = !SET_ALARM
    }
}
