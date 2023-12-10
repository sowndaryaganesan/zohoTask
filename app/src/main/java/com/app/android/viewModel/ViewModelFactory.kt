package com.app.android.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.app.android.repository.MainRepository

class ViewModelFactory constructor(private val mainRepository: MainRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            MainViewModel(mainRepository) as T
        } else {
            throw IllegalArgumentException("ViewModel Not Found")
        }
    }
}