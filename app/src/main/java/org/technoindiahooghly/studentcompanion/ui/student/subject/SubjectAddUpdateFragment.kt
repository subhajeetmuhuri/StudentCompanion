package org.technoindiahooghly.studentcompanion.ui.student.subject

import android.content.Context.INPUT_METHOD_SERVICE
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import org.technoindiahooghly.studentcompanion.R
import org.technoindiahooghly.studentcompanion.StudentApplication
import org.technoindiahooghly.studentcompanion.data.student.Student
import org.technoindiahooghly.studentcompanion.databinding.FragmentSubjectAddUpdateBinding
import org.technoindiahooghly.studentcompanion.viewmodel.student.StudentViewModel
import org.technoindiahooghly.studentcompanion.viewmodel.student.StudentViewModelFactory

class SubjectAddUpdateFragment : Fragment() {
    private val viewModel: StudentViewModel by activityViewModels {
        StudentViewModelFactory(
            (activity?.application as StudentApplication).studentDatabase.studentDao())
    }

    private val navigationArgs: SubjectAddUpdateFragmentArgs by navArgs()

    private var _binding: FragmentSubjectAddUpdateBinding? = null
    private val binding
        get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSubjectAddUpdateBinding.inflate(inflater, container, false)
        return binding.root
    }

    private fun isEntryValid(): Boolean {
        return viewModel.isEntryValid(
            binding.subjectAddUpdateText.editText?.text.toString(),
            binding.teacherAddUpdateText.editText?.text.toString())
    }

    private fun updateEntry() {
        if (isEntryValid()) {
            val subject = this.binding.subjectAddUpdateText.editText?.text.toString()
            viewModel.updateEntry(
                this.navigationArgs.id,
                subject,
                this.binding.teacherAddUpdateText.editText?.text.toString())

            Toast.makeText(
                    requireContext(),
                    requireContext().getString(R.string.subject_update_success_toast, subject),
                    Toast.LENGTH_SHORT)
                .show()

            val action =
                SubjectAddUpdateFragmentDirections
                    .actionSubjectAddUpdateFragmentToSubjectListFragment()
            this.findNavController().navigate(action)
        } else {
            Toast.makeText(
                    requireContext(),
                    requireContext().getString(R.string.subject_update_empty_warning_toast),
                    Toast.LENGTH_SHORT)
                .show()
        }
    }

    private fun bind(entry: Student) {
        binding.apply {
            subjectAddUpdateText.editText?.setText(entry.subjectName, TextView.BufferType.SPANNABLE)
            teacherAddUpdateText.editText?.setText(entry.teacherName, TextView.BufferType.SPANNABLE)
            saveButton.setOnClickListener { updateEntry() }
        }
    }

    private fun addNewEntry() {
        if (isEntryValid()) {
            val subject = binding.subjectAddUpdateText.editText?.text.toString()
            viewModel.addNewEntry(subject, binding.teacherAddUpdateText.editText?.text.toString())

            Toast.makeText(
                    requireContext(),
                    requireContext().getString(R.string.subject_add_success_toast, subject),
                    Toast.LENGTH_SHORT)
                .show()

            val action =
                SubjectAddUpdateFragmentDirections
                    .actionSubjectAddUpdateFragmentToSubjectListFragment()
            this.findNavController().navigate(action)
        } else {
            Toast.makeText(
                    requireContext(),
                    requireContext().getString(R.string.subject_add_empty_warning_toast),
                    Toast.LENGTH_SHORT)
                .show()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val id = navigationArgs.id
        if (id > 0) {
            viewModel.retrieveEntry(id).observe(this.viewLifecycleOwner) { bind(it) }
        } else {
            binding.saveButton.setOnClickListener { addNewEntry() }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        val inputMethodManager =
            requireActivity().getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(requireActivity().currentFocus?.windowToken, 0)
        _binding = null
    }
}
