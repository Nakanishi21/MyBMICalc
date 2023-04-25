package com.example.mybmicalc

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.mybmicalc.repository.MyBodyRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: MyBodyRepository
): ViewModel() {
    val myBodyList = repository.getAllDate().asLiveData()
}