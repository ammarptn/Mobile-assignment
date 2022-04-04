package com.ammarptn.cityfinder.presenter

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ammarptn.cityfinder.domain.model.DomainCity
import com.ammarptn.cityfinder.domain.usecase.GetAllCityUseCase
import com.ammarptn.cityfinder.presenter.mapper.PresenterCityMapper
import com.ammarptn.cityfinder.presenter.uimodel.SearchResultHolder
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    var getAllCityUseCase: GetAllCityUseCase,
    var presenterCityMapper: PresenterCityMapper
) : ViewModel() {

    // this flow will help debounce the search action every time user type something on the search box
    private val _resultFlow = MutableStateFlow<List<SearchResultHolder>?>(emptyList())
    val resultFlow = _resultFlow.asStateFlow()


    private var cityList: MutableMap<String, DomainCity>? = null

    var isLoadingLiveData = MutableLiveData(false)

    init {
        initCountryList()
    }

    private fun initCountryList() {
        viewModelScope.launch {
            isLoadingLiveData.value = true

            cityList = presenterCityMapper.map(getAllCityUseCase.invoke())

            isLoadingLiveData.value = false

        }
    }

    fun filterList(
        query: String,
        list: MutableMap<String, DomainCity>?
    ): List<SearchResultHolder>? {

        // the whole data is already put in the map, this will help speed up the query time by referring to the key
        // I also use city name as they key
        // then find the key that start with the text of user input
        // after that, sort it in alphabets order
        val result =
            list?.filterKeys { key -> key.startsWith(query.lowercase()) }?.toList()
                ?.sortedBy { it.second.cityName }

        return result?.map {
            SearchResultHolder(
                it.second.cityName, it.second.countryName,
                Pair(it.second.latitude, it.second.longitude)
            )
        }
    }

    fun filterCityList(query: String) {
        viewModelScope.launch {
            if (query.isEmpty()) {
                _resultFlow.emit(null)
            } else {
                _resultFlow.emit(filterList(query, cityList))
            }
        }
    }


}