package ir.nevercom.somu.repositories

import android.util.Log
import ir.nevercom.somu.model.ModelPreferencesManager
import ir.nevercom.somu.model.User

class SharedPreferencesUserRepositoryImpl : UserRepository {
    private val keyUser = "current_user"
    private val ketToken = "token"

    override fun getCurrentUser(): User? {
        Log.d("UserRepository", "getCurrentUser: ")
        return ModelPreferencesManager.get<User>(keyUser)
    }

    override fun saveUser(user: User) {
        ModelPreferencesManager.put(user, this.keyUser)
    }


    override fun getToken(): String {
        return ModelPreferencesManager.preferences.getString(ketToken, "").toString()
    }

    override fun saveToken(token: String) {
        ModelPreferencesManager.preferences.edit().putString(this.ketToken, token).apply()
    }

    override fun isLoggedIn(): Boolean {
        Log.d("UserRepository", "isLoggedIn: ")
        val currentUser = getCurrentUser()
        return getToken().isNotEmpty() && currentUser != null && currentUser.id.isNotEmpty()
    }
}