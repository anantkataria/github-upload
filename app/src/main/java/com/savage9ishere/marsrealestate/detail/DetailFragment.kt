package com.savage9ishere.marsrealestate.detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.savage9ishere.marsrealestate.R
import com.savage9ishere.marsrealestate.databinding.FragmentDetailBinding

/**
 * This [Fragment] will show the detailed information about a selected piece of Mars real estate.
 */
class DetailFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val application = requireNotNull(activity).application
        val binding = FragmentDetailBinding.inflate(inflater)
        binding.lifecycleOwner = this

        val marsPorperty = DetailFragmentArgs.fromBundle(requireArguments()).selectedProperty
        val viewModelFactory = DetailViewModelFactory(marsPorperty, application)

        binding.viewModel = ViewModelProvider(this, viewModelFactory).get(DetailViewModel::class.java)

        return binding.root
    }
}