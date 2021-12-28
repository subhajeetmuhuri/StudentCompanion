package org.technoindiahooghly.studentcompanion.adapter.student.routine.tuesday

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import org.technoindiahooghly.studentcompanion.R
import org.technoindiahooghly.studentcompanion.data.student.TuesdayData
import org.technoindiahooghly.studentcompanion.databinding.RoutineListItemBinding
import org.technoindiahooghly.studentcompanion.utils.student.getFormattedTime

class RoutineTuesdayListAdapter(
    private val deleteClickInterface: TuesdayDeleteClickInterface,
    private val notifyImageViewClick: TuesdayNotifyClickInterface,
    private val onEntryClicked: (TuesdayData) -> Unit
) :
    ListAdapter<TuesdayData, RoutineTuesdayListAdapter.RoutineTuesdayViewHolder>(
        TuesdayDiffCallback) {

    private lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RoutineTuesdayViewHolder {
        return RoutineTuesdayViewHolder(
            RoutineListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false),
            context)
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        context = recyclerView.context
    }

    override fun onBindViewHolder(holder: RoutineTuesdayViewHolder, position: Int) {
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

    class RoutineTuesdayViewHolder(var binding: RoutineListItemBinding, val context: Context) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(entry: TuesdayData) {
            if (!entry.tuesdayNotification) {
                binding.notify.setImageResource(R.drawable.ic_notification_off)
            } else {
                binding.notify.setImageResource(R.drawable.ic_notification_on)
            }

            binding.subjectListText.text = entry.subjectName
            binding.teacherListText.text = entry.teacherName
            binding.timeStartText.text = getFormattedTime(context, entry.tuesdayStartTime)
            binding.timeEndText.text = getFormattedTime(context, entry.tuesdayEndTime)
        }
    }

    companion object {
        private val TuesdayDiffCallback =
            object : DiffUtil.ItemCallback<TuesdayData>() {
                override fun areItemsTheSame(oldItem: TuesdayData, newItem: TuesdayData): Boolean {
                    return oldItem.id == newItem.id
                }

                override fun areContentsTheSame(
                    oldItem: TuesdayData,
                    newItem: TuesdayData
                ): Boolean {
                    return oldItem == newItem
                }
            }
    }

    interface TuesdayDeleteClickInterface {
        fun onDeleteImageViewClick(entry: TuesdayData)
    }

    interface TuesdayNotifyClickInterface {
        fun onNotifyImageViewClick(entry: TuesdayData)
    }
}
