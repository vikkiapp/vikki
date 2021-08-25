package ir.nevercom.somu.model.body

import androidx.annotation.Keep

@Keep
data class Login(val email: String, val password: String)