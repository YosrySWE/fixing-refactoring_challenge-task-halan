package com.example.halanchallenge.features.login

import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.halanchallenge.R
import com.example.halanchallenge.databinding.FragmentLoginBinding
import com.example.halanchallenge.domain.repository.remote.models.Login
import com.example.halanchallenge.features.platform.BaseFragment
import com.example.halanchallenge.utils.ValidationUtil
import com.example.halanchallenge.utils.ValidationUtil.REASONS.*
import com.example.halanchallenge.utils.extensions.safeNavigate
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LoginFragment : BaseFragment<FragmentLoginBinding>() {

    override val viewModel: LoginViewModel by viewModels()

    private fun isValid(userName: String, password: String): Boolean {
        val data = ValidationUtil.validateLogin(userName, password)
        return when (data.tag) {
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

    override fun online() {
        if (binding.connectionTextView.scaleY == 1f) {

            binding.connectionTextView.apply {
                text = context.getString(R.string.online)
                setTextColor(resources.getColor(R.color.colorPrimary, resources.newTheme()))
                binding.connectionTextView.animate().scaleY(0f).setDuration(2000).start()
            }
        }
    }

    override fun offline() {
        if (binding.connectionTextView.scaleY == 0f) {
            binding.connectionTextView.apply {
                text = context.getString(R.string.offline_mode)
                setTextColor(resources.getColor(R.color.black, resources.newTheme()))
                animate().scaleY(1f).setDuration(1000).start()
            }
        }
    }

    // render
    override fun render() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.RESUMED) {
                Log.i("Halan", "current state is ${lifecycle.currentState}")

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
                        is LoginViewState.Success -> {
                            val dest = LoginFragmentDirections.actionLoginToProducts(it.response)
                            findNavController().safeNavigate(dest)
                        }
                        is LoginViewState.Error -> {
                            Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                        }
                        is LoginViewState.NavigateToProducts -> {
                            Log.i("Halan", "LoginViewState.NavigateToProducts block")
                            viewModel.intentChannel.send(LoginIntent.NavigateScreen(it.username))
                        }
                        is LoginViewState.IsLoggedInState -> viewModel.intentChannel.trySend(
                            LoginIntent.IsLoggedIn
                        )
                        is LoginViewState.Init -> viewModel.intentChannel.trySend(LoginIntent.IsLoggedIn)
                    }
                }
            }
        }

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

    override fun onDestroyView() {
        viewModel.clear()
        super.onDestroyView()
    }

}