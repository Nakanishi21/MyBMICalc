package com.example.mybmicalc.page.edit

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.mybmicalc.R
import com.example.mybmicalc.databinding.EditBmiFragmentBinding
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat

@AndroidEntryPoint
class EditBMIFragment : Fragment(R.layout.edit_bmi_fragment) {
    private val args: EditBMIFragmentArgs by navArgs()
    private val viewModel: EditBMIViewModel by viewModels()

    private var _binding: EditBmiFragmentBinding? = null
    private val binding: EditBmiFragmentBinding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        this._binding = EditBmiFragmentBinding.bind(view)

        val myBody = args.mybody
        binding.heightEdit.setText(myBody.height.toString())
        binding.weightEdit.setText(myBody.weight.toString())

        val format = SimpleDateFormat("yyyy/MM/dd")
        binding.dateEdit.setText(format.format(myBody.date))

        viewModel.errorMessage.observe(viewLifecycleOwner) { msg ->
            if (msg.isEmpty()) return@observe

            Snackbar.make(requireView(), msg, Snackbar.LENGTH_SHORT).show()
            viewModel.errorMessage.value = ""
        }
        viewModel.done.observe(viewLifecycleOwner) { myBody ->
            setFragmentResult("edit", bundleOf("myBody" to myBody))
            findNavController().popBackStack()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        this._binding = null
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_edit, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.action_save -> {
                save()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun save() {
        val height = binding.heightEdit.text.toString().toInt()
        val weight = binding.weightEdit.text.toString().toInt()

        val dateText = binding.dateEdit.text.toString()
        val date = SimpleDateFormat("yyyy/MM/dd").parse(dateText).time

        viewModel.save(args.mybody, height, weight, date)
    }
}