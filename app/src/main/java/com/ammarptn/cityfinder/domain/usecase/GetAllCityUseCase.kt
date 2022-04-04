package com.ammarptn.cityfinder.domain.usecase

import com.ammarptn.cityfinder.domain.model.DomainCity
import com.ammarptn.cityfinder.domain.repository.CityRepository
import javax.inject.Inject

class GetAllCityUseCase @Inject constructor(var cityRepository: CityRepository) {

    suspend fun invoke(): List<DomainCity> {
        return cityRepository.getAllCity()
    }
}