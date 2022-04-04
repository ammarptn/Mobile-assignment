package com.ammarptn.cityfinder.presenter

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ammarptn.cityfinder.domain.model.DomainCity
import com.ammarptn.cityfinder.domain.usecase.GetAllCityUseCase
import com.ammarptn.cityfinder.presenter.mapper.PresenterCityMapper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    var getAllCityUseCase: GetAllCityUseCase,
    var presenterCityMapper: PresenterCityMapper
) : ViewModel() {

    private var cityList: MutableMap<String, DomainCity>? = null
    var resultList: MutableLiveData<List<String>?> = MutableLiveData<List<String>?>()

    var isLoadingLiveData = MutableLiveData(false)

    init {
        initCountryList()
    }

    fun initCountryList() {
        viewModelScope.launch {
            isLoadingLiveData.value = true

            cityList = presenterCityMapper.map(getAllCityUseCase.invoke())
            Log.d(
                "HomeViewModel",
                "getCountryList: " + cityList?.size
            )

            isLoadingLiveData.value = false

        }
    }

    fun filterList(query: String) {
        val result =
            cityList?.filterKeys { key -> key.contains(query.lowercase()) }?.toList()?.take(100)

        Log.d(
            "HomeViewModel",
            "getCountryList: ma" + result?.size
        )
        resultList.value = result?.map { it.second.cityName }
    }

}