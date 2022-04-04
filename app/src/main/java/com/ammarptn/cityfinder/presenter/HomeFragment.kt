package com.ammarptn.cityfinder.presenter

import android.os.Bundle
import android.util.Log
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


        viewModel.resultList.observe(viewLifecycleOwner) {
            searchResultController.setData(it)
        }

        viewModel.isLoadingLiveData.observe(viewLifecycleOwner) {
            if (it) {
                binding.layoutLoading.visibility = View.VISIBLE
            } else {
                binding.layoutLoading.visibility = View.GONE
            }
        }

        binding.editTextSearchBox.addTextChangedListener {
            viewModel.filterCityList(it.toString())
        }

        lifecycleScope.launchWhenStarted {
            viewModel.resultFlow.debounce(500).collectLatest {
                Log.d("HomeFragment", "onCreateView: $it")
                searchResultController.setData(it)
            }
        }

        return binding.root
    }

    override fun onClickSearchResult(position: Int) {

    }

    override fun onResume() {
        super.onResume()
    }


}