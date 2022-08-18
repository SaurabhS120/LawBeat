package com.example.login.domain.entity

data class RegistrationDetailsEntity(val fullName:String,val mobile_no:String,val email:String,val state:String,val city:String,val gst:String,val password:String,val confirmPassword:String) {
    class Builder{
        var fullName:String=""
        var mobile_no:String=""
        var email:String=""
        var state:String=""
        var city:String=""
        var gst:String=""
        var password:String=""
        var confirmPassword:String=""
        fun build() = RegistrationDetailsEntity(fullName, mobile_no, email, state, city, gst, password, confirmPassword)
    }
}