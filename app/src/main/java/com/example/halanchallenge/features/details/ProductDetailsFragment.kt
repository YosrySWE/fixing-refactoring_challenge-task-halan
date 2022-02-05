package com.example.halanchallenge.features.details

import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.halanchallenge.databinding.FragmentProductDetailsBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class ProductDetailsFragment : Fragment() {

    private val viewModel: ProductDetailsViewModel by viewModels()
    lateinit var binding: FragmentProductDetailsBinding
    lateinit var adapter: ImagesAdapter
    private val args: ProductDetailsFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProductDetailsBinding.inflate(layoutInflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = ImagesAdapter()
        render()
    }


    private fun render() {
        lifecycleScope.launchWhenStarted {
            viewModel.state.collect {
                when (viewModel.state.value) {
                    is DetailsViewState.Init -> {
                        binding.backButton.setOnClickListener { findNavController().navigateUp() }
                        binding.productDescriptionTv.text = args.item.deal_description
                        binding.productTitleTv.text = args.item.name_ar
                        binding.productDescriptionTv.movementMethod = ScrollingMovementMethod()
                        binding.productPriceTv.text = "كاش           ${args.item.price}جنيه"
                        adapter.submitList(args.item.images)
                        binding.productImagesBanner.adapter = adapter
                        binding.arIndicator.attachTo(binding.productImagesBanner, true)
                    }
                }
            }
        }
    }
}

