package com.ammarptn.cityfinder.presenter.mapper

import com.ammarptn.cityfinder.domain.model.DomainCity
import javax.inject.Inject

class PresenterCityMapper @Inject constructor() {

    fun map(domainCityList: List<DomainCity>?): MutableMap<String, DomainCity> {

        val cityMap: MutableMap<String, DomainCity> = mutableMapOf()

        domainCityList?.forEach {
            it.cityName.let { name ->
                cityMap[name.lowercase()] = it
            }
        }

        return cityMap

    }
}