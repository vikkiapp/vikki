package ir.nevercom.somu.model

import com.google.gson.annotations.SerializedName

data class LoginResponse(
    val token: String,
    val user: User
)
