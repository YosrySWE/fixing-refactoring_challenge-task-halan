package com.example.halanchallenge.features.list

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.*
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.halanchallenge.databinding.FragmentProductsListBinding
import com.example.halanchallenge.domain.repository.remote.models.Product
import com.example.halanchallenge.domain.repository.remote.models.Profile
import com.example.halanchallenge.features.platform.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ProductsListFragment : BaseFragment<FragmentProductsListBinding>(), ProductsAdapter.ItemClickListener {

    override val viewModel: ProductsListViewModel by viewModels()
    lateinit var adapter: ProductsAdapter
    private val args: ProductsListFragmentArgs by navArgs()

    lateinit var profile: Profile

    // render
    override fun render() {
        profile = args.profile
        lifecycleScope.launch{
            repeatOnLifecycle(Lifecycle.State.STARTED){
                viewModel.state.collect {
                    when (it) {
                        is ProductsViewState.IsLoading -> {
                            loading(flag = it.isLoading)
                        }
                        is ProductsViewState.BackToLogin->{
                            findNavController().navigateUp()
                        }
                        is ProductsViewState.Success -> {
                            Log.i("Halan", "In ProductsViewState.Success")
                            adapter = ProductsAdapter(this@ProductsListFragment)
                            binding.usernameTv.text = profile.name
                            binding.phoneNumberTv.text = profile.phone
                            Glide.with(requireContext()).load(profile.image).override(100).into(binding.userIv)
                            binding.productsListRv.layoutManager = LinearLayoutManager(requireContext())
                            binding.productsListRv.adapter = adapter
                            Log.i("Halan", "In ProductsViewState.Success: ${it.response[0]}")
                            adapter.submitList(it.response)
                            binding.productsListRv.adapter = adapter
                            loading(false)

                        }
                        is ProductsViewState.Error -> {
                            Log.e("Halan", "In ProductsViewState.Error: ${it.message}")
                            viewModel.intentChannel.send(ProductsIntent.RefreshToken)
                        }
                        is ProductsViewState.Init -> {
                            Log.i("Halan", "In ProductsViewState.Init: ${it.profile}")
                            profile = it.profile
                            if (lifecycle.currentState.isAtLeast(Lifecycle.State.STARTED)) {
                                // load if not loaded
                                viewModel.intentChannel.trySend(ProductsIntent.ProductsAction)
                            }
                        }
                        is ProductsViewState.None -> {
                            Log.i("Halan", "In ProductsViewState.None: Initial State")
                            if(profile.name.isNullOrEmpty())
                                viewModel.intentChannel.trySend(ProductsIntent.LoadProfile)
                            else
                                viewModel.intentChannel.trySend(ProductsIntent.Idle(profile))
                        }
                    }
                }
            }
            repeatOnLifecycle(Lifecycle.State.RESUMED){
                viewModel.intentChannel.trySend(ProductsIntent.ProductsAction)
            }

            repeatOnLifecycle(Lifecycle.State.DESTROYED){
                viewModel.clear()
            }
        }


    }

    override fun onItemClick(item: Product) {
        val dest = ProductsListFragmentDirections.actionProductsToDetails(item)
        findNavController().navigate(dest)
    }

    override fun online() {
    }

    override fun offline() {
    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewModel.clear()
    }

    fun loading(flag: Boolean){
        if (flag) {
            binding.productsListRv.visibility = View.INVISIBLE
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.productsListRv.visibility = View.VISIBLE
            binding.progressBar.visibility = View.GONE
        }
    }
}