package com.example.mybmicalc.page.edit

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mybmicalc.model.body.MyBody
import com.example.mybmicalc.repository.MyBodyRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditBMIViewModel @Inject constructor(
    private val repository: MyBodyRepository
): ViewModel() {
    val errorMessage = MutableLiveData<String>()
    val done = MutableLiveData<MyBody>()

    fun save(myBody: MyBody, height: Int, weight: Int, date: Long) {
        viewModelScope.launch {
            try {
                val updateMyBody = repository.update(myBody, height, weight, date)
                done.value = updateMyBody
            } catch (e: Exception) {
                errorMessage.value = e.toString()
            }
        }
    }
}