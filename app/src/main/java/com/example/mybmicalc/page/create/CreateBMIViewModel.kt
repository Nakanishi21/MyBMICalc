package com.example.mybmicalc.page.create

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mybmicalc.repository.MyBodyRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.util.Date
import javax.inject.Inject

@HiltViewModel
class CreateBMIViewModel @Inject constructor(private val repo: MyBodyRepository) : ViewModel() {
    val errorMessage = MutableLiveData<String>()
    val done = MutableLiveData<Boolean>()

    fun save(height: Int, weight: Int, date: Long) {
        viewModelScope.launch {
            try {
                repo.create(height, weight, date)
                done.value = true
            } catch (e: Exception) {
                errorMessage.value = e.message
            }
        }
    }
}