package com.ammarptn.cityfinder.presenter.epoxy.model

import com.airbnb.epoxy.EpoxyAttribute
import com.airbnb.epoxy.EpoxyModelClass
import com.ammarptn.cityfinder.R
import com.ammarptn.cityfinder.databinding.ItemSearchResultBinding
import com.ammarptn.cityfinder.presenter.epoxy.base.ViewBindingEpoxyModelWithHolder

@EpoxyModelClass
abstract class SearchResultEpoxyModel : ViewBindingEpoxyModelWithHolder<ItemSearchResultBinding>() {

    @EpoxyAttribute
    open var title: String? = null

    @EpoxyAttribute
    open var subtitle: String? = null

    @EpoxyAttribute
    open var onClickSearchResult: (() -> Unit)? = null


    override fun ItemSearchResultBinding.bind() {

        textViewTitle.text = title
        textViewSubTitle.text = subtitle

        root.setOnClickListener {
            onClickSearchResult?.invoke()
        }

    }

    override fun getDefaultLayout(): Int = R.layout.item_search_result
}