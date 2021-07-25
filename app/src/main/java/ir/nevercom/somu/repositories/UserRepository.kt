package ir.nevercom.somu.repositories

import ir.nevercom.somu.model.User

interface UserRepository {
    fun getCurrentUser(): User?
    fun saveUser(user: User)
    fun getToken(): String
    fun saveToken(token: String)
    fun isLoggedIn(): Boolean
}