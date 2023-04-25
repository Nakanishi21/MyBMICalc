package com.example.mybmicalc.page.detail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mybmicalc.model.body.MyBody
import com.example.mybmicalc.repository.MyBodyRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BMIDetailViewModel @Inject constructor(
    private val repository: MyBodyRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    val myBody = savedStateHandle.getLiveData<MyBody>("myBody")
    val errorMessage = MutableLiveData<String>()
    val deleted = MutableLiveData<Boolean>()

    fun delete() {
        viewModelScope.launch {
            try {
                val myBody = this@BMIDetailViewModel.myBody.value ?: return@launch
                repository.delete(myBody)
                deleted.value = true
            } catch (e:Exception) {
                errorMessage.value = e.message
            }
        }
    }
}