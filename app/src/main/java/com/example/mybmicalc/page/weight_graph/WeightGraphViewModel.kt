package com.example.mybmicalc.page.weight_graph

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.mybmicalc.repository.MyBodyRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class WeightGraphViewModel @Inject constructor(
    private val repository: MyBodyRepository
): ViewModel() {
    val myBodyList = repository.getAllDate().asLiveData()
}