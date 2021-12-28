package org.technoindiahooghly.studentcompanion.adapter.student.routine.wednesday

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import org.technoindiahooghly.studentcompanion.R
import org.technoindiahooghly.studentcompanion.data.student.WednesdayData
import org.technoindiahooghly.studentcompanion.databinding.RoutineListItemBinding
import org.technoindiahooghly.studentcompanion.utils.student.getFormattedTime

class RoutineWednesdayListAdapter(
    private val deleteClickInterface: WednesdayDeleteClickInterface,
    private val notifyImageViewClick: WednesdayNotifyClickInterface,
    private val onEntryClicked: (WednesdayData) -> Unit
) :
    ListAdapter<WednesdayData, RoutineWednesdayListAdapter.RoutineWednesdayViewHolder>(
        WednesdayDiffCallback) {

    private lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RoutineWednesdayViewHolder {
        return RoutineWednesdayViewHolder(
            RoutineListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false),
            context)
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        context = recyclerView.context
    }

    override fun onBindViewHolder(holder: RoutineWednesdayViewHolder, position: Int) {
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

    class RoutineWednesdayViewHolder(var binding: RoutineListItemBinding, val context: Context) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(entry: WednesdayData) {
            if (!entry.wednesdayNotification) {
                binding.notify.setImageResource(R.drawable.ic_notification_off)
            } else {
                binding.notify.setImageResource(R.drawable.ic_notification_on)
            }

            binding.subjectListText.text = entry.subjectName
            binding.teacherListText.text = entry.teacherName
            binding.timeStartText.text = getFormattedTime(context, entry.wednesdayStartTime)
            binding.timeEndText.text = getFormattedTime(context, entry.wednesdayEndTime)
        }
    }

    companion object {
        private val WednesdayDiffCallback =
            object : DiffUtil.ItemCallback<WednesdayData>() {
                override fun areItemsTheSame(
                    oldItem: WednesdayData,
                    newItem: WednesdayData
                ): Boolean {
                    return oldItem.id == newItem.id
                }

                override fun areContentsTheSame(
                    oldItem: WednesdayData,
                    newItem: WednesdayData
                ): Boolean {
                    return oldItem == newItem
                }
            }
    }

    interface WednesdayDeleteClickInterface {
        fun onDeleteImageViewClick(entry: WednesdayData)
    }

    interface WednesdayNotifyClickInterface {
        fun onNotifyImageViewClick(entry: WednesdayData)
    }
}
