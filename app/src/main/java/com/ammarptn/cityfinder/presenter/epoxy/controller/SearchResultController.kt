package com.ammarptn.cityfinder.presenter.epoxy.controller

import com.airbnb.epoxy.TypedEpoxyController
import com.ammarptn.cityfinder.presenter.epoxy.model.searchResult
import com.ammarptn.cityfinder.presenter.uimodel.SearchResultHolder
import javax.inject.Inject

class SearchResultController @Inject constructor() :
    TypedEpoxyController<List<SearchResultHolder>>() {

    private var listener: AddOnClickListener? = null

    interface AddOnClickListener {
        fun onClickSearchResult(cityName: String, coordinate: Pair<Double, Double>)
    }

    fun setListener(listener: AddOnClickListener) {
        this.listener = listener
    }

    override fun buildModels(data: List<SearchResultHolder>?) {

        data?.forEachIndexed { index, result ->
            searchResult {
                id("result$index")
                title(result.city + ", " + result.country)
                subtitle("Coordinate : " + result.coordinate)
                onClickSearchResult {
                    this@SearchResultController.listener?.onClickSearchResult(result.city,result.coordinate)
                }
            }
        }
    }
}