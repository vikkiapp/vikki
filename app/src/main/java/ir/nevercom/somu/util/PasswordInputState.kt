package ir.nevercom.somu.util

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

class PasswordInputState : TextFieldState(
    validator = ::isValidPassword,
    errorFor = ::passwordValidationError
) {
    var shouldHidePassword: Boolean by mutableStateOf(true)
}

private fun passwordValidationError(password: String): String {
    return "Please provide a password."
}

private fun isValidPassword(password: String): Boolean {
    return password.length >= 8
}