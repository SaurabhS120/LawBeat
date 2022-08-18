package com.example.login.presentation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.example.core.MainActivityInterface
import com.example.core.NavigationDestination
import com.example.login.data.demo.repo.DemoLoginRepoImpl
import com.example.login.data.demo_db.repo.DemoDbLoginRepo
import com.example.login.databinding.FragmentLoginBinding
import com.example.login.domain.repo.LoginRepo
import com.example.login.domain.states.States
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LoginFragment : Fragment() {
    lateinit var binding: FragmentLoginBinding
    lateinit var mainActivity:MainActivityInterface
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        //Login repo assigned here
        val loginRepo:LoginRepo = DemoDbLoginRepo(requireContext().applicationContext)

        mainActivity = requireActivity() as MainActivityInterface
        binding = FragmentLoginBinding.inflate(layoutInflater,container,false)
        binding.gotoRegisterButton.setOnClickListener {
            mainActivity.navigateTo(NavigationDestination.RegisterDestination())
        }
        binding.loginButton.setOnClickListener {
            val email = binding.loginText.text.toString()
            val password = binding.passwordText.text.toString()
            lifecycleScope.launch(Dispatchers.IO){
                val state = loginRepo.invoke(email,password)
                withContext(Dispatchers.Main){
                    render(state)
                }
            }
        }
        return binding.root
    }
    fun render(state:States){
        when (state){
            is States.Success -> {
                Toast.makeText(requireContext(),"Login Successful",Toast.LENGTH_SHORT).show()
            }
            is States.Failure -> {
                val failure = state as States.Failure
                Toast.makeText(requireContext(),failure.msg,Toast.LENGTH_SHORT).show()
            }
        }
    }
}
