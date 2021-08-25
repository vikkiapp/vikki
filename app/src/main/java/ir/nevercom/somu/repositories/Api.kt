package ir.nevercom.somu.repositories

import com.haroldadmin.cnradapter.NetworkResponse
import ir.nevercom.somu.model.ErrorResponse
import ir.nevercom.somu.model.LoginResponse
import ir.nevercom.somu.model.body.Login
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface Api {
    @POST("login")
    @Headers("Prefer: code=200, example=Successful Login")
    suspend fun login(@Body request: Login): NetworkResponse<LoginResponse, ErrorResponse>
}