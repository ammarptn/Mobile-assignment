package com.ammarptn.cityfinder.data.repository

import com.ammarptn.cityfinder.data.entity.CityEntityItem
import com.ammarptn.cityfinder.data.mapper.DataCityListMapper
import com.ammarptn.cityfinder.domain.repository.CityRepository
import com.google.gson.Gson
import com.google.gson.stream.JsonReader
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.withContext
import java.io.InputStream
import java.io.InputStreamReader
import javax.inject.Inject
import javax.inject.Named
import kotlin.coroutines.CoroutineContext


class CityRepositoryImpl @Inject constructor(
    var gson: Gson,
    @Named("CITY_JSON_DATA_STREAM") var cityJsonStream: InputStream,
    var dataCityListMapper: DataCityListMapper
) : CityRepository {

    private var parentJob = Job()
    private val coroutineContext: CoroutineContext get() = parentJob + Dispatchers.Main
    private val scope = CoroutineScope(coroutineContext)

    override suspend fun getAllCity() =
        withContext(scope.coroutineContext + Dispatchers.IO) {
            val cityList = mutableListOf<CityEntityItem>()

            JsonReader(InputStreamReader(cityJsonStream)).use { reader ->
                reader.beginArray()
                while (reader.hasNext()) {
                    val cityEntityItem: CityEntityItem =
                        gson.fromJson(reader, CityEntityItem::class.java)
                    cityList.add(cityEntityItem)
                }
                reader.endArray()
            }
            return@withContext dataCityListMapper.map(cityList)
        }


    override fun findCity(query: String) {

    }


}