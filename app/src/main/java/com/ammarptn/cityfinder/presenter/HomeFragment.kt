package com.ammarptn.cityfinder.presenter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.ammarptn.cityfinder.databinding.HomeFragmentBinding
import com.ammarptn.cityfinder.presenter.epoxy.controller.SearchResultController
import dagger.hilt.android.AndroidEntryPoint
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

        return binding.root
    }

    override fun onClickSearchResult(position: Int) {

    }

    override fun onResume() {
        super.onResume()
        viewModel.getCountryList()
    }


}