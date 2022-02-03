package com.example.halanchallenge.features.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.halanchallenge.databinding.FragmentLoginBinding
import com.example.halanchallenge.domain.models.Login
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
                viewModel.intentChannel.trySend(LoginIntent.LoginAction(Login(binding.usernameEt.text.toString(), binding.passwordEt.text.toString())))
            }
        }

    }

    // render
    private fun render() {
        lifecycleScope.launchWhenStarted {
            viewModel.state.collect {
                when(it){
                    is LoginViewState.IsLoading ->{
                        if(it.isLoading){
                            Toast.makeText(requireContext(), "Loading ...", Toast.LENGTH_SHORT).show()
                        }else{
                            Toast.makeText(requireContext(), "onComplete", Toast.LENGTH_SHORT).show()
                        }
                    }
                    is LoginViewState.SuccessLogin ->{
                        if(it.response.email!!.isNotEmpty()){
                            Toast.makeText(requireContext(), it.response.email, Toast.LENGTH_SHORT).show()
                        }
                    }
                    is LoginViewState.ErrorLogin ->{
                        Toast.makeText(requireContext(), it.error, Toast.LENGTH_SHORT).show()
                    }
                    is LoginViewState.Init -> Unit
                }
        }
    }
}



}