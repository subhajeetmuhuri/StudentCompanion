package org.technoindiahooghly.studentcompanion.adapter.student.subject

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import org.technoindiahooghly.studentcompanion.data.student.Student
import org.technoindiahooghly.studentcompanion.databinding.SubjectListItemBinding

class SubjectListAdapter(
    private val deleteClickInterface: StudentDeleteClickInterface,
    private val onEntryClicked: (Student) -> Unit
) : ListAdapter<Student, SubjectListAdapter.SubjectViewHolder>(StudentDiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SubjectViewHolder {
        return SubjectViewHolder(
            SubjectListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: SubjectViewHolder, position: Int) {
        val current = getItem(position)

        holder.binding.subjectListItem.setOnClickListener { onEntryClicked(current) }

        holder.binding.deleteEntry.setOnClickListener {
            deleteClickInterface.onDeleteImageViewClick(current)
        }

        holder.bind(current)
    }

    class SubjectViewHolder(var binding: SubjectListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(entry: Student) {
            binding.subjectListText.text = entry.subjectName
            binding.teacherListText.text = entry.teacherName
        }
    }

    companion object {
        private val StudentDiffCallback =
            object : DiffUtil.ItemCallback<Student>() {
                override fun areItemsTheSame(oldItem: Student, newItem: Student): Boolean {
                    return oldItem.id == newItem.id
                }

                override fun areContentsTheSame(oldItem: Student, newItem: Student): Boolean {
                    return oldItem == newItem
                }
            }
    }

    interface StudentDeleteClickInterface {
        fun onDeleteImageViewClick(entry: Student)
    }
}
