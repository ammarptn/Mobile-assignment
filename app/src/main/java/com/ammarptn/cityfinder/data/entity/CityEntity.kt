package com.ammarptn.cityfinder.data.entity

import com.google.gson.annotations.SerializedName

data class CityEntity(

    @field:SerializedName("CityEntity")
    val cityEntity: List<CityEntityItem?>? = null
)

data class Coord(

    @field:SerializedName("lon")
    val lon: Double? = null,

    @field:SerializedName("lat")
    val lat: Double? = null
)

data class CityEntityItem(

    @field:SerializedName("country")
    val country: String? = null,

    @field:SerializedName("coord")
    val coord: Coord? = null,

    @field:SerializedName("name")
    val name: String? = null,

    @field:SerializedName("_id")
    val id: Int? = null
)
