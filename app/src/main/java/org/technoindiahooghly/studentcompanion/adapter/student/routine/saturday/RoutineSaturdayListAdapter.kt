package org.technoindiahooghly.studentcompanion.adapter.student.routine.saturday

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import org.technoindiahooghly.studentcompanion.R
import org.technoindiahooghly.studentcompanion.data.student.SaturdayData
import org.technoindiahooghly.studentcompanion.databinding.RoutineListItemBinding
import org.technoindiahooghly.studentcompanion.utils.student.getFormattedTime

class RoutineSaturdayListAdapter(
    private val deleteClickInterface: SaturdayDeleteClickInterface,
    private val notifyImageViewClick: SaturdayNotifyClickInterface,
    private val onEntryClicked: (SaturdayData) -> Unit
) :
    ListAdapter<SaturdayData, RoutineSaturdayListAdapter.RoutineSaturdayViewHolder>(
        SaturdayDiffCallback) {

    private lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RoutineSaturdayViewHolder {
        return RoutineSaturdayViewHolder(
            RoutineListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false),
            context)
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        context = recyclerView.context
    }

    override fun onBindViewHolder(holder: RoutineSaturdayViewHolder, position: Int) {
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

    class RoutineSaturdayViewHolder(var binding: RoutineListItemBinding, val context: Context) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(entry: SaturdayData) {
            if (!entry.saturdayNotification) {
                binding.notify.setImageResource(R.drawable.ic_notification_off)
            } else {
                binding.notify.setImageResource(R.drawable.ic_notification_on)
            }

            binding.subjectListText.text = entry.subjectName
            binding.teacherListText.text = entry.teacherName
            binding.timeStartText.text = getFormattedTime(context, entry.saturdayStartTime)
            binding.timeEndText.text = getFormattedTime(context, entry.saturdayEndTime)
        }
    }

    companion object {
        private val SaturdayDiffCallback =
            object : DiffUtil.ItemCallback<SaturdayData>() {
                override fun areItemsTheSame(
                    oldItem: SaturdayData,
                    newItem: SaturdayData
                ): Boolean {
                    return oldItem.id == newItem.id
                }

                override fun areContentsTheSame(
                    oldItem: SaturdayData,
                    newItem: SaturdayData
                ): Boolean {
                    return oldItem == newItem
                }
            }
    }

    interface SaturdayDeleteClickInterface {
        fun onDeleteImageViewClick(entry: SaturdayData)
    }

    interface SaturdayNotifyClickInterface {
        fun onNotifyImageViewClick(entry: SaturdayData)
    }
}
