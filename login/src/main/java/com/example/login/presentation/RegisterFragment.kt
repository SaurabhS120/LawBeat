package com.example.login.presentation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.core.MainActivityInterface
import com.example.core.NavigationDestination
import com.example.login.data.demo_db.repo.DemoDbRegisterRepo
import com.example.login.databinding.FragmentRegisterBinding
import com.example.login.domain.entity.RegistrationDetailsEntity
import com.example.login.domain.states.States
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class RegisterFragment : Fragment() {
    lateinit var mainActivity:MainActivityInterface
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val viewModel :RegistrationViewModel by viewModels()
        mainActivity = requireActivity() as MainActivityInterface
        val binding = FragmentRegisterBinding.inflate(inflater,container,false)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        binding.gotoLoginButton.setOnClickListener {
            mainActivity.navigateTo(NavigationDestination.LoginDestination())
        }
        viewModel.apply {
            fullNameError.observe(viewLifecycleOwner){
                binding.fullNameText.error = it
            }
            mobile_noError.observe(viewLifecycleOwner){
                binding.mobileNoText.error = it
            }
            emailError.observe(viewLifecycleOwner){
                binding.emailText.error = it
            }
            gstError.observe(viewLifecycleOwner){
                binding.gstText.error = it
            }
            passwordError.observe(viewLifecycleOwner){
                binding.passwordText.error = it
            }
            confirmPasswordError.observe(viewLifecycleOwner){
                binding.confirmPasswordText.error = it
            }
            tc_acceptError.observe(viewLifecycleOwner){
                Toast.makeText(requireContext(),it,Toast.LENGTH_SHORT).show()
            }
            uiState.observe(viewLifecycleOwner){
                this@RegisterFragment.render(it)
            }
        }
        binding.registerButton.setOnClickListener {
            viewModel.register(requireContext().applicationContext)
        }
        return binding.root
    }

    fun render(state: States){
        when (state){
            is States.Success -> {
                Toast.makeText(requireContext(),"Registration Successful",Toast.LENGTH_SHORT).show()
            }
            is States.Failure -> {
                val failure = state as States.Failure
                Toast.makeText(requireContext(),failure.msg,Toast.LENGTH_SHORT).show()
            }
        }
    }
}