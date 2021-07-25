package ir.nevercom.somu.util

import ir.nevercom.somu.repositories.UserRepository
import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor(private val userRepository: UserRepository) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        var req = chain.request()
        req = req.newBuilder()
            .addHeader("Authorization", "Bearer ${userRepository.getToken()}")
            .addHeader("Accept", "application/json")
            //.addHeader("prefer", "code=200, example=Example'")
            .build()
        return chain.proceed(req)
    }
}