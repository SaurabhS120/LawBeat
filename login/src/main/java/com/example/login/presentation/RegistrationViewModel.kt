package com.example.login.presentation

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewModelScope
import com.example.login.data.demo_db.repo.DemoDbRegisterRepo
import com.example.login.domain.entity.RegistrationDetailsEntity
import com.example.login.domain.repo.RegistrationRepo
import com.example.login.domain.states.States
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class RegistrationViewModel :ViewModel(){
    companion object{
        //Registration repo assigned here
        val repo:RegistrationRepo = DemoDbRegisterRepo()
    }
    val fullName= MutableLiveData<String>()
    val mobile_no=MutableLiveData<String>()
    val email=MutableLiveData<String>()
    val state=MutableLiveData<String>()
    val city=MutableLiveData<String>()
    val gst=MutableLiveData<String>()
    val password=MutableLiveData<String>()
    val confirmPassword=MutableLiveData<String>()
    val tc_accept=MutableLiveData(false)
    val fullNameError= MutableLiveData<String>()
    val mobile_noError=MutableLiveData<String>()
    val emailError=MutableLiveData<String>()
    val stateError=MutableLiveData<String>()
    val cityError=MutableLiveData<String>()
    val gstError=MutableLiveData<String>()
    val passwordError=MutableLiveData<String>()
    val confirmPasswordError=MutableLiveData<String>()
    val tc_acceptError=MutableLiveData<String>()
    val uiState=MutableLiveData<States>()
    fun checkDataHasError():Boolean{
        var error = false
        if (fullName.value.isNullOrEmpty()){
            fullNameError.postValue("Full-name is empty")
            error=true
        }
        if (mobile_no.value.isNullOrEmpty()){
            mobile_noError.postValue("Mobile no. is empty")
            error=true
        }
        if (email.value.isNullOrEmpty()){
            emailError.postValue("Email is empty")
            error=true
        }
        if (password.value.isNullOrEmpty()){
            passwordError.postValue("Email is empty")
            error=true
        }
        if (confirmPassword.value.isNullOrEmpty()){
            confirmPasswordError.postValue("Confirm password is empty")
            error=true
        }
        if (confirmPassword.value.isNullOrEmpty()){
            confirmPasswordError.postValue("Please accept terms and conditions")
            error=true
        }
        if (tc_accept.value==false){
            tc_acceptError.postValue("Please accept terms and conditions")
            error = true
        }
        return error
    }
    fun register(applicationContext:Context){
        if (checkDataHasError().not()){
            val registrationDetailsBuilder = RegistrationDetailsEntity.Builder()
            registrationDetailsBuilder.fullName = fullName.value.toString()
            registrationDetailsBuilder.mobile_no = mobile_no.value.toString()
            registrationDetailsBuilder.email = email.value.toString()
            registrationDetailsBuilder.gst = gst.value.toString()
            registrationDetailsBuilder.password = password.value.toString()
            registrationDetailsBuilder.confirmPassword = confirmPassword.value.toString()
            val registrationDetailsEntity = registrationDetailsBuilder.build()
            viewModelScope.launch(Dispatchers.IO){
                val state = repo.invoke(applicationContext,registrationDetailsEntity)
                render(state)
            }
        }
    }
    fun render(states: States){
        uiState.postValue(states)
    }
}