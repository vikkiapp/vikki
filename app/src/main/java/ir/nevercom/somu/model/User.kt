package ir.nevercom.somu.model

import androidx.annotation.Keep

@Keep
data class User(
    val id: String,
    val username: String,
    val displayName: String?,
    val avatar: String?,
    val email: String
)
