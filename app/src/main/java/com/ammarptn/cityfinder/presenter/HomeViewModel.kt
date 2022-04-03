package com.ammarptn.cityfinder.presenter

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor() : ViewModel() {

    var resultList: MutableLiveData<List<String>?> = MutableLiveData<List<String>?>()

    fun getCountryList(filter: String? = null) {
        viewModelScope.launch {

            filter?.let {

            } ?: kotlin.run {
                resultList.value = listOf("Test1", "Test2")
            }

        }
    }

}