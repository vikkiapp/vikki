package ir.nevercom.somu.model

import com.google.gson.annotations.SerializedName

data class LoginResponse(
    val token: String,
    val user: User
)

data class SearchResponse(
    val page: Int,
    @SerializedName("total_pages") val totalPages: Int,
    @SerializedName("total_results") val totalResults: Int,
    val results: List<Movie>
)
