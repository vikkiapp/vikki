package ir.nevercom.somu.model

import androidx.annotation.Keep

@Keep
class ErrorResponse(val errorCode: Int, val errorMessage: String)