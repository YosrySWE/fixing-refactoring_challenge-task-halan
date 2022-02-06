package com.example.halanchallenge.features.details

import android.text.method.ScrollingMovementMethod
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.halanchallenge.databinding.FragmentProductDetailsBinding
import com.example.halanchallenge.features.platform.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class ProductDetailsFragment : BaseFragment<FragmentProductDetailsBinding>() {

    override val viewModel: ProductDetailsViewModel by viewModels()
    lateinit var adapter: ImagesAdapter
    private val args: ProductDetailsFragmentArgs by navArgs()

    override fun render() {
        adapter = ImagesAdapter()

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

    override fun online() {
    }

    override fun offline() {
    }
}

