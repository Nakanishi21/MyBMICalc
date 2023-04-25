package com.example.mybmicalc

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.mybmicalc.databinding.MainFragmentBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainFragment : Fragment(R.layout.main_fragment) {
    private val vm: MainViewModel by viewModels()

    private var _binding: MainFragmentBinding? = null
    private val binding: MainFragmentBinding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        this._binding = MainFragmentBinding.bind(view)

        val adapter = MyBMICalcAdapter {
            val action = MainFragmentDirections.actionMainFragmentToBMIDetailFragment(it)
            findNavController().navigate(action)
        }
        binding.recycler.adapter = adapter

        binding.fab.setOnClickListener {
            findNavController().navigate(R.id.action_mainFragment_to_createBMIFragment)
        }

        binding.graphBtn.setOnClickListener {
            findNavController().navigate(R.id.action_mainFragment_to_weightGraphFragment)
        }

        vm.myBodyList.observe(viewLifecycleOwner) { list ->
            adapter.submitList(list)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        this._binding = null
    }
}