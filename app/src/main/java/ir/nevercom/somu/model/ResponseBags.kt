package ir.nevercom.somu.model

import androidx.annotation.Keep

@Keep
data class LoginResponse(
    val token: String,
    val user: User
)
