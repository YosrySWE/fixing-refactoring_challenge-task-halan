package com.example.halanchallenge.features.login

import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
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
            }
            Handler(Looper.getMainLooper()).postDelayed({
                binding.connectionTextView.animate().scaleY(0f).setDuration(450).start()
            }, 2000)
        }
    }

    override fun offline() {
        if(binding.connectionTextView.scaleY == 0f){
            binding.connectionTextView.apply {
                text = context.getString(R.string.offline_mode)
                setTextColor(resources.getColor(R.color.black, resources.newTheme()))
                animate().scaleY(1f).setDuration(1000).start()
            }
        }
    }

    // render
    override fun render() {
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
                    is LoginViewState.Success -> {

                        val dest = LoginFragmentDirections.actionLoginToProducts(it.response)
                        findNavController().safeNavigate(dest)
                    }
                    is LoginViewState.Error -> {
                        Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                    }
                    is LoginViewState.Init -> Unit
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


}