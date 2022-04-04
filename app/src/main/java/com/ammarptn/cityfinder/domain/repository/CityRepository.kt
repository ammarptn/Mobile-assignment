package com.ammarptn.cityfinder.domain.repository

import com.ammarptn.cityfinder.domain.model.DomainCity

interface CityRepository {

    suspend fun getAllCity(): List<DomainCity>
}