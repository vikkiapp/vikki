package ir.nevercom.somu.repositories

import com.haroldadmin.cnradapter.NetworkResponse
import ir.nevercom.somu.model.ErrorResponse
import ir.nevercom.somu.model.LoginResponse
import ir.nevercom.somu.model.Movie
import ir.nevercom.somu.model.SearchResponse
import ir.nevercom.somu.model.body.Login
import retrofit2.http.*

interface Api {
    @POST("login")
    suspend fun login(@Body request: Login): NetworkResponse<LoginResponse, ErrorResponse>

    @GET("search")
    suspend fun search(
        @Query("q") query: String,
        @Query("page") page: Int = 1
    ): NetworkResponse<SearchResponse, ErrorResponse>

    @GET("movie/{id}")
    suspend fun getMovieById(@Path("id") id: Int): NetworkResponse<Movie, ErrorResponse>
}