package com.example.halanchallenge.features.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.R
import androidx.navigation.fragment.findNavController
import com.example.halanchallenge.databinding.FragmentLoginBinding
import com.example.halanchallenge.domain.models.Login
import com.example.halanchallenge.utils.ValidationUtil
import com.example.halanchallenge.utils.ValidationUtil.REASONS.*
import com.example.halanchallenge.utils.safeNavigate
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LoginFragment : Fragment() {

    private val viewModel: LoginViewModel by viewModels()

    private lateinit var binding: FragmentLoginBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLoginBinding.inflate(layoutInflater, container, false)
        render()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.loginButton.setOnClickListener {
            lifecycleScope.launch {
                if (isValid(binding.usernameEt.text.toString(), binding.passwordEt.text.toString()))
                    viewModel.intentChannel.trySend(
                        LoginIntent.LoginAction(
                            Login(
                                binding.usernameEt.text.toString(),
                                binding.passwordEt.text.toString()
                            )
                        )
                    )
            }
        }

    }

    private fun isValid(userName: String, password: String): Boolean {
        val data = ValidationUtil.validateLogin(userName, password)
        return when(data.tag) {
            USERNAME -> {
                binding.usernameEt.error = data.message
                false
            }
            PASSWORD -> {
                binding.passwordEt.error = data.message
                false
            }
            NONE -> {
                binding.usernameEt.error = null
                binding.passwordEt.error = null
                true
            }
        }
    }

    // render
    private fun render() {

        lifecycleScope.launchWhenStarted {
            viewModel.state.collect {
                when (it) {
                    is LoginViewState.IsLoading -> {
                        binding.loginButton.isEnabled = !it.isLoading
                        if (it.isLoading) {
                            binding.progressBar.visibility = View.VISIBLE
                        } else {
                            binding.progressBar.visibility = View.GONE
                        }
                    }
                    is LoginViewState.SuccessLogin -> {

                        val dest = LoginFragmentDirections.actionLoginToProducts(it.response)
                        findNavController().safeNavigate(dest)
                    }
                    is LoginViewState.ErrorLogin -> {
                        Toast.makeText(requireContext(), it.error, Toast.LENGTH_SHORT).show()
                    }
                    is LoginViewState.Init -> Unit
                }
            }
        }
    }


}