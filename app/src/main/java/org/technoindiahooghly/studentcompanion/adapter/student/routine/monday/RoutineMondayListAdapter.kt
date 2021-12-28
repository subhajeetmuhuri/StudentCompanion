package org.technoindiahooghly.studentcompanion.adapter.student.routine.monday

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import org.technoindiahooghly.studentcompanion.R
import org.technoindiahooghly.studentcompanion.data.student.MondayData
import org.technoindiahooghly.studentcompanion.databinding.RoutineListItemBinding
import org.technoindiahooghly.studentcompanion.utils.student.getFormattedTime

class RoutineMondayListAdapter(
    private val deleteClickInterface: MondayDeleteClickInterface,
    private val notifyImageViewClick: MondayNotifyClickInterface,
    private val onEntryClicked: (MondayData) -> Unit
) : ListAdapter<MondayData, RoutineMondayListAdapter.RoutineMondayViewHolder>(MondayDiffCallback) {

    private lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RoutineMondayViewHolder {
        return RoutineMondayViewHolder(
            RoutineListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false),
            context)
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        context = recyclerView.context
    }

    override fun onBindViewHolder(holder: RoutineMondayViewHolder, position: Int) {
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

    class RoutineMondayViewHolder(var binding: RoutineListItemBinding, val context: Context) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(entry: MondayData) {
            if (!entry.mondayNotification) {
                binding.notify.setImageResource(R.drawable.ic_notification_off)
            } else {
                binding.notify.setImageResource(R.drawable.ic_notification_on)
            }

            binding.subjectListText.text = entry.subjectName
            binding.teacherListText.text = entry.teacherName
            binding.timeStartText.text = getFormattedTime(context, entry.mondayStartTime)
            binding.timeEndText.text = getFormattedTime(context, entry.mondayEndTime)
        }
    }

    companion object {
        private val MondayDiffCallback =
            object : DiffUtil.ItemCallback<MondayData>() {
                override fun areItemsTheSame(oldItem: MondayData, newItem: MondayData): Boolean {
                    return oldItem.id == newItem.id
                }

                override fun areContentsTheSame(oldItem: MondayData, newItem: MondayData): Boolean {
                    return oldItem == newItem
                }
            }
    }

    interface MondayDeleteClickInterface {
        fun onDeleteImageViewClick(entry: MondayData)
    }

    interface MondayNotifyClickInterface {
        fun onNotifyImageViewClick(entry: MondayData)
    }
}
