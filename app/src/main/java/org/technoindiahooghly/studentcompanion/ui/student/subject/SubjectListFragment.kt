package org.technoindiahooghly.studentcompanion.ui.student.subject

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
import org.technoindiahooghly.studentcompanion.adapter.student.subject.SubjectListAdapter
import org.technoindiahooghly.studentcompanion.data.student.Student
import org.technoindiahooghly.studentcompanion.databinding.FragmentSubjectListBinding
import org.technoindiahooghly.studentcompanion.viewmodel.student.StudentViewModel
import org.technoindiahooghly.studentcompanion.viewmodel.student.StudentViewModelFactory

class SubjectListFragment : Fragment(), SubjectListAdapter.StudentDeleteClickInterface {
    private val viewModel: StudentViewModel by activityViewModels {
        StudentViewModelFactory(
            (activity?.application as StudentApplication).studentDatabase.studentDao())
    }

    private var _binding: FragmentSubjectListBinding? = null
    private val binding
        get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSubjectListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter =
            SubjectListAdapter(this) {
                val action =
                    SubjectListFragmentDirections
                        .actionSubjectListFragmentToSubjectAddUpdateFragment(it.id)
                this.findNavController().navigate(action)
            }

        binding.subjectListRecyclerView.layoutManager = LinearLayoutManager(this.context)
        binding.subjectListRecyclerView.adapter = adapter

        viewModel.getAll.observe(this.viewLifecycleOwner) { it.let { adapter.submitList(it) } }

        binding.addSubjectsFAB.setOnClickListener {
            val action =
                SubjectListFragmentDirections.actionSubjectListFragmentToSubjectAddUpdateFragment()
            this.findNavController().navigate(action)
        }
    }

    override fun onDeleteImageViewClick(entry: Student) {
        val builder = MaterialAlertDialogBuilder(requireContext())
        builder.setPositiveButton(requireContext().getString(R.string.yes_alert_dialog)) { _, _ ->
            viewModel.deleteEntry(entry)
            Toast.makeText(
                    requireContext(),
                    requireContext()
                        .getString(R.string.subject_remove_success_toast, entry.subjectName),
                    Toast.LENGTH_SHORT)
                .show()
        }
        builder.setNegativeButton(requireContext().getString(R.string.no_alert_dialog)) { _, _ -> }
        builder.setTitle(
            requireContext().getString(R.string.subject_remove_prompt_title, entry.subjectName))
        builder.setMessage(requireContext().getString(R.string.subject_remove_prompt_message))
        builder.create().show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
