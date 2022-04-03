package com.ammarptn.cityfinder.presenter.epoxy.controller

import com.airbnb.epoxy.TypedEpoxyController
import com.ammarptn.cityfinder.presenter.epoxy.model.searchResult
import javax.inject.Inject

class SearchResultController @Inject constructor() : TypedEpoxyController<List<String>>() {

    private var listener: AddOnClickListener? = null

    interface AddOnClickListener {
        fun onClickSearchResult(position: Int)
    }

    fun setListener(listener: AddOnClickListener) {
        this.listener = listener
    }

    override fun buildModels(data: List<String>?) {

        data?.forEachIndexed { index, result ->
            searchResult {
                id("result$index")
                title(result)
            }
        }
    }
}