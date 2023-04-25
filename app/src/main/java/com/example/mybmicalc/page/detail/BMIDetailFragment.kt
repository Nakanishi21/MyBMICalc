package com.example.mybmicalc.page.detail

import android.content.DialogInterface
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.mybmicalc.R
import com.example.mybmicalc.databinding.BmiDetailFragmentBinding
import com.example.mybmicalc.model.body.MyBody
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import kotlin.math.pow
import kotlin.math.roundToLong

@AndroidEntryPoint
class BMIDetailFragment : Fragment(R.layout.bmi_detail_fragment) {
    private val viewModel: BMIDetailViewModel by viewModels()

    private var _binding: BmiDetailFragmentBinding? = null
    private val binding: BmiDetailFragmentBinding get() = _binding!!

    private val args: BMIDetailFragmentArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        setFragmentResultListener("edit") { _, data ->
            val myBody = data.getParcelable("myBody") as? MyBody ?: return@setFragmentResultListener
            viewModel.myBody.value = myBody
        }

        setFragmentResultListener("confirm") { _, data ->
            val which = data.getInt("result")
            if (which == DialogInterface.BUTTON_POSITIVE) {
                viewModel.delete()
            }
        }

        if (savedInstanceState == null) {
            viewModel.myBody.value = args.mybody
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        this._binding = BmiDetailFragmentBinding.bind(view)

        viewModel.myBody.observe(viewLifecycleOwner) { myBody ->
            binding.heightText.text = myBody.height.toString()
            binding.weightText.text = myBody.weight.toString()

            val format = SimpleDateFormat("yyyy/MM/dd")
            binding.dateText.text = format.format(myBody.date)

            val bmi = ((myBody.weight / myBody.height.toFloat().pow(2)) * 100000).roundToLong() / 10.0
            binding.bmiText.text =bmi.toString()
        }

        viewModel.errorMessage.observe(viewLifecycleOwner) { msg ->
            if(msg.isEmpty())  return@observe

            Snackbar.make(requireView(), msg, Snackbar.LENGTH_SHORT).show()
            viewModel.errorMessage.value = ""
        }

        viewModel.deleted.observe(viewLifecycleOwner) { deleted ->
            if(deleted) {
                findNavController().popBackStack(R.id.mainFragment, false)
            }
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        this._binding = null
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_detail, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_edit -> {
                val myBody = viewModel.myBody.value ?: return true
                val action = BMIDetailFragmentDirections.actionBMIDetailFragmentToEditBMIFragment(myBody)
                findNavController().navigate(action)
                true
            }
            R.id.action_delete -> {
                findNavController().navigate(
                    R.id.action_BMIDetailFragment_to_confirmDialogFragment
                )
                true
            } else -> super.onOptionsItemSelected(item)
        }
    }
}