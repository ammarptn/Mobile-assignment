package com.ammarptn.cityfinder.presenter

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.ammarptn.cityfinder.databinding.HomeFragmentBinding
import com.ammarptn.cityfinder.presenter.epoxy.controller.SearchResultController
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import javax.inject.Inject


@AndroidEntryPoint
class HomeFragment : Fragment(), SearchResultController.AddOnClickListener {


    private val viewModel: HomeViewModel by viewModels()

    private var _binding: HomeFragmentBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var searchResultController: SearchResultController

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = HomeFragmentBinding.inflate(inflater, container, false)


        binding.resultList.setController(searchResultController)

        searchResultController.setListener(this)

        viewModel.isLoadingLiveData.observe(viewLifecycleOwner) {
            if (it) {
                binding.layoutLoading.visibility = View.VISIBLE
                binding.textViewSuggestSearch.visibility = View.GONE
            } else {
                binding.layoutLoading.visibility = View.GONE
                binding.textViewSuggestSearch.visibility = View.VISIBLE
            }
        }

        binding.editTextSearchBox.addTextChangedListener {
            viewModel.filterCityList(it.toString())
            binding.progressBarSearchCity.visibility = View.VISIBLE
        }

        lifecycleScope.launchWhenStarted {
            viewModel.resultFlow.debounce(timeoutMillis = 500).collectLatest {
                searchResultController.setData(it)
                binding.progressBarSearchCity.visibility = View.GONE

                if (it.isNullOrEmpty() && binding.editTextSearchBox.text.isEmpty() && viewModel.isLoadingLiveData.value == false) {
                    binding.textViewSuggestSearch.visibility = View.VISIBLE
                } else {
                    binding.textViewSuggestSearch.visibility = View.GONE
                }
            }
        }

        return binding.root
    }

    override fun onClickSearchResult(cityName: String, coordinate: Pair<Double, Double>) {

        val gmmIntentUri =
            Uri.parse("geo:${coordinate.first},${coordinate.second}?q=" + Uri.encode(cityName))
        val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
        startActivity(mapIntent)


    }


}