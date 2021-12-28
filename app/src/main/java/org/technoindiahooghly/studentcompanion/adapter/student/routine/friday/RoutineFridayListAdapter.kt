package org.technoindiahooghly.studentcompanion.adapter.student.routine.friday

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import org.technoindiahooghly.studentcompanion.R
import org.technoindiahooghly.studentcompanion.data.student.FridayData
import org.technoindiahooghly.studentcompanion.databinding.RoutineListItemBinding
import org.technoindiahooghly.studentcompanion.utils.student.getFormattedTime

class RoutineFridayListAdapter(
    private val deleteClickInterface: FridayDeleteClickInterface,
    private val notifyImageViewClick: FridayNotifyClickInterface,
    private val onEntryClicked: (FridayData) -> Unit
) : ListAdapter<FridayData, RoutineFridayListAdapter.RoutineFridayViewHolder>(FridayDiffCallback) {

    private lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RoutineFridayViewHolder {
        return RoutineFridayViewHolder(
            RoutineListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false),
            context)
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        context = recyclerView.context
    }

    override fun onBindViewHolder(holder: RoutineFridayViewHolder, position: Int) {
        val current = getItem(position)

        holder.binding.routineListItem.setOnClickListener { onEntryClicked(current) }

        holder.binding.notify.setOnClickListener {
            notifyImageViewClick.onNotifyImageViewClick(current)
        }

        holder.binding.deleteItem.setOnClickListener {
            deleteClickInterface.onDeleteImageViewClick(current)
        }

        holder.bind(current)
    }

    class RoutineFridayViewHolder(var binding: RoutineListItemBinding, val context: Context) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(entry: FridayData) {
            if (!entry.fridayNotification) {
                binding.notify.setImageResource(R.drawable.ic_notification_off)
            } else {
                binding.notify.setImageResource(R.drawable.ic_notification_on)
            }

            binding.subjectListText.text = entry.subjectName
            binding.teacherListText.text = entry.teacherName
            binding.timeStartText.text = getFormattedTime(context, entry.fridayStartTime)
            binding.timeEndText.text = getFormattedTime(context, entry.fridayEndTime)
        }
    }

    companion object {
        private val FridayDiffCallback =
            object : DiffUtil.ItemCallback<FridayData>() {
                override fun areItemsTheSame(oldItem: FridayData, newItem: FridayData): Boolean {
                    return oldItem.id == newItem.id
                }

                override fun areContentsTheSame(oldItem: FridayData, newItem: FridayData): Boolean {
                    return oldItem == newItem
                }
            }
    }

    interface FridayDeleteClickInterface {
        fun onDeleteImageViewClick(entry: FridayData)
    }

    interface FridayNotifyClickInterface {
        fun onNotifyImageViewClick(entry: FridayData)
    }
}
