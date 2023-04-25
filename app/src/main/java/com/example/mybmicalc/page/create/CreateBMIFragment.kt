package com.example.mybmicalc.page.create

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.mybmicalc.R
import com.example.mybmicalc.databinding.CreateBmiFragmentBinding
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.time.format.DateTimeFormatter
import java.util.*

@AndroidEntryPoint
class CreateBMIFragment : Fragment(R.layout.create_bmi_fragment) {
    private val viewModel: CreateBMIViewModel by viewModels()

    private var _binding: CreateBmiFragmentBinding? = null
    private val binding: CreateBmiFragmentBinding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_create, menu)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        this._binding = CreateBmiFragmentBinding.bind(view)

        viewModel.errorMessage.observe(viewLifecycleOwner) { msg ->
            if(msg.isEmpty())  return@observe

            Snackbar.make(requireView(), msg, Snackbar.LENGTH_SHORT).show()
            viewModel.errorMessage.value = ""
        }
        viewModel.done.observe(viewLifecycleOwner) {
            findNavController().popBackStack()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_save -> {
                save()
                return true
            }
            else -> return super.onContextItemSelected(item)
        }
    }

    private fun save(){
        val height = binding.heightEdit.text.toString().toInt()
        val weight = binding.weightEdit.text.toString().toInt()

        val dateText = binding.dateEdit.text.toString()
        val date = SimpleDateFormat("yyyy/MM/dd").parse(dateText).time

        viewModel.save(height, weight, date)
    }
}