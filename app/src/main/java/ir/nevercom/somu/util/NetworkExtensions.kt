package ir.nevercom.somu.util

import org.json.JSONException
import org.json.JSONObject
import retrofit2.Response

class HttpError(val errorCode: Int, val errorMessage: String)

inline fun <T : Any> Response<T>.onSuccess(action: (T) -> Unit): Response<T> {
    if (isSuccessful) body()?.run(action)
    return this
}

inline fun <T : Any> Response<T>.onFailure(action: (HttpError) -> Unit) {
    if (!isSuccessful) errorBody()?.run {
        val jsonString = string()
        try {
            val json = JSONObject(jsonString)
            val errorCode = try {
                json.getInt("error_code")
            } catch (e: JSONException) {
                -999
            }
            val errorMessage = try {
                json.getString("error_message")
            } catch (e: JSONException) {
                "Unknown error"
            }
            action(HttpError(errorCode, errorMessage))
        } catch (e: Exception) {
            action(HttpError(-999, "Unknown error"))
        }

    }
}