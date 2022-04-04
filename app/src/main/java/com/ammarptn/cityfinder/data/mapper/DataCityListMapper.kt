package com.ammarptn.cityfinder.data.mapper

import com.ammarptn.cityfinder.data.entity.CityEntityItem
import com.ammarptn.cityfinder.domain.model.DomainCity
import javax.inject.Inject

class DataCityListMapper @Inject constructor() {

    fun map(cityEntity: List<CityEntityItem>): List<DomainCity> {
        return cityEntity.mapNotNull {
            it.name?.let { name ->
                it.country?.let { country ->
                    it.coord?.let { coordinate ->
                        coordinate.lat?.let { lat ->
                            coordinate.lon?.let { lon ->
                                DomainCity(name, country, lat, lon)
                            }
                        }
                    }
                }
            }

        }
    }
}