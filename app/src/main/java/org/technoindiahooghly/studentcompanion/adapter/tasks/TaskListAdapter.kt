package org.technoindiahooghly.studentcompanion.adapter.tasks

import android.content.Context
import android.graphics.Paint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import org.technoindiahooghly.studentcompanion.R
import org.technoindiahooghly.studentcompanion.data.tasks.Task
import org.technoindiahooghly.studentcompanion.databinding.TaskListItemBinding
import org.technoindiahooghly.studentcompanion.utils.tasks.getFormattedTime

class TaskListAdapter(
    private val deleteClickInterface: TaskDeleteClickInterface,
    private val notifyImageViewClick: TaskNotifyClickInterface,
    private val doneCheckBoxClick: TaskDoneClickInterface,
    private val onEntryClicked: (Task) -> Unit
) : ListAdapter<Task, TaskListAdapter.TaskViewHolder>(TaskDiffCallback) {
    private lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        return TaskViewHolder(
            TaskListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false),
            context)
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        context = recyclerView.context
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val current = getItem(position)

        holder.binding.taskListItem.setOnClickListener { onEntryClicked(current) }

        holder.binding.taskDoneCheckBox.setOnClickListener {
            doneCheckBoxClick.onDoneCheckBoxClick(current)
        }

        holder.binding.notify.setOnClickListener {
            notifyImageViewClick.onNotifyImageViewClick(current)
        }

        holder.binding.deleteTask.setOnClickListener {
            deleteClickInterface.onDeleteImageViewClick(current)
        }

        holder.bind(current)
    }

    class TaskViewHolder(var binding: TaskListItemBinding, val context: Context) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(task: Task) {
            binding.taskDoneCheckBox.isChecked = task.taskDone
            binding.taskListText.text = task.taskName
            binding.taskDeadlineText.text = getFormattedTime(task.taskDeadline)

            if (binding.taskDoneCheckBox.isChecked) {
                binding.taskListText.apply {
                    paintFlags = paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
                }
                binding.taskDeadlineText.apply {
                    paintFlags = paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
                }
            } else {
                binding.taskListText.apply {
                    paintFlags = paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
                }
                binding.taskDeadlineText.apply {
                    paintFlags = paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
                }
            }

            if (!task.taskNotification) {
                binding.notify.setImageResource(R.drawable.ic_notification_off)
            } else {
                binding.notify.setImageResource(R.drawable.ic_notification_on)
            }
        }
    }

    companion object {
        private val TaskDiffCallback =
            object : DiffUtil.ItemCallback<Task>() {
                override fun areItemsTheSame(oldItem: Task, newItem: Task): Boolean {
                    return oldItem.id == newItem.id
                }

                override fun areContentsTheSame(oldItem: Task, newItem: Task): Boolean {
                    return oldItem == newItem
                }
            }
    }

    interface TaskDeleteClickInterface {
        fun onDeleteImageViewClick(task: Task)
    }

    interface TaskNotifyClickInterface {
        fun onNotifyImageViewClick(task: Task)
    }

    interface TaskDoneClickInterface {
        fun onDoneCheckBoxClick(task: Task)
    }
}
