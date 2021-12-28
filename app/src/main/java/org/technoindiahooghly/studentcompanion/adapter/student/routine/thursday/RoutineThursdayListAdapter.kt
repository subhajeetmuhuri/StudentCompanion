package org.technoindiahooghly.studentcompanion.adapter.student.routine.thursday

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import org.technoindiahooghly.studentcompanion.R
import org.technoindiahooghly.studentcompanion.data.student.ThursdayData
import org.technoindiahooghly.studentcompanion.databinding.RoutineListItemBinding
import org.technoindiahooghly.studentcompanion.utils.student.getFormattedTime

class RoutineThursdayListAdapter(
    private val deleteClickInterface: ThursdayDeleteClickInterface,
    private val notifyImageViewClick: ThursdayNotifyClickInterface,
    private val onEntryClicked: (ThursdayData) -> Unit
) :
    ListAdapter<ThursdayData, RoutineThursdayListAdapter.RoutineThursdayViewHolder>(
        ThursdayDiffCallback) {

    private lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RoutineThursdayViewHolder {
        return RoutineThursdayViewHolder(
            RoutineListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false),
            context)
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        context = recyclerView.context
    }

    override fun onBindViewHolder(holder: RoutineThursdayViewHolder, position: Int) {
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

    class RoutineThursdayViewHolder(var binding: RoutineListItemBinding, val context: Context) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(entry: ThursdayData) {
            if (!entry.thursdayNotification) {
                binding.notify.setImageResource(R.drawable.ic_notification_off)
            } else {
                binding.notify.setImageResource(R.drawable.ic_notification_on)
            }

            binding.subjectListText.text = entry.subjectName
            binding.teacherListText.text = entry.teacherName
            binding.timeStartText.text = getFormattedTime(context, entry.thursdayStartTime)
            binding.timeEndText.text = getFormattedTime(context, entry.thursdayEndTime)
        }
    }

    companion object {
        private val ThursdayDiffCallback =
            object : DiffUtil.ItemCallback<ThursdayData>() {
                override fun areItemsTheSame(
                    oldItem: ThursdayData,
                    newItem: ThursdayData
                ): Boolean {
                    return oldItem.id == newItem.id
                }

                override fun areContentsTheSame(
                    oldItem: ThursdayData,
                    newItem: ThursdayData
                ): Boolean {
                    return oldItem == newItem
                }
            }
    }

    interface ThursdayDeleteClickInterface {
        fun onDeleteImageViewClick(entry: ThursdayData)
    }

    interface ThursdayNotifyClickInterface {
        fun onNotifyImageViewClick(entry: ThursdayData)
    }
}
