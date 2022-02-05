package com.example.halanchallenge.features.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.halanchallenge.databinding.FragmentProductsListBinding
import com.example.halanchallenge.domain.repository.remote.models.Product
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class ProductsListFragment : Fragment(), ProductsAdapter.ItemClickListener {

    private val viewModel: ProductsListViewModel by viewModels()
    lateinit var binding: FragmentProductsListBinding
    lateinit var adapter: ProductsAdapter
    private val args: ProductsListFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProductsListBinding.inflate(layoutInflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = ProductsAdapter(this)
        viewModel.intentChannel.trySend(ProductsIntent.None)
        render()


    }

    // render
    private fun render() {

        lifecycleScope.launchWhenStarted {
            viewModel.state.collect {
                when (it) {
                    is ProductsViewState.IsLoading -> {
                        if (it.isLoading) {
                            binding.productsListRv.visibility = View.INVISIBLE

                            binding.progressBar.visibility = View.VISIBLE
                        } else {
                            binding.productsListRv.visibility = View.VISIBLE
                            binding.progressBar.visibility = View.GONE
                        }
                    }
                    is ProductsViewState.Success -> {
                        adapter.submitList(it.response)
                        binding.productsListRv.adapter = adapter
                    }
                    is ProductsViewState.Error -> {
                        Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                        findNavController().navigateUp()
                    }
                    is ProductsViewState.Init -> {
                        binding.usernameTv.text = args.profile.name
                        binding.phoneNumberTv.text = args.profile.phone
//                        binding.emailTv.text = args.profile.email
                        Glide.with(requireContext()).load(args.profile.image).override(100).into(binding.userIv)
                        binding.productsListRv.layoutManager = LinearLayoutManager(requireContext())
                        binding.productsListRv.adapter = adapter
                        viewModel.intentChannel.trySend(ProductsIntent.ProductsAction)


                    }
                }
            }
        }
    }

    override fun onItemClick(item: Product) {
        val dest = ProductsListFragmentDirections.actionProductsToDetails(item)
        findNavController().navigate(dest)
    }


}