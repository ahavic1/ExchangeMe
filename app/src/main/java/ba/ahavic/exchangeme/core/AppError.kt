package ba.ahavic.exchangeme.core

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import okhttp3.Headers
import retrofit2.Response

enum class ReasonOfError(val identifier: String = "") {
    InvalidBase("invalid_base"),
    ResourceNotFound("not_found"),
    HostNotFound,
    UnknownError
}

data class ErrorBody(val error: String)

open class AppError(errorBody: ErrorBody? = null, reason: ReasonOfError? = null) {

    val reason: ReasonOfError

    init {
        this.reason = reason ?: parseErrorBody(errorBody)
    }

    private fun parseErrorBody(error: ErrorBody?): ReasonOfError {
        return try {
            ReasonOfError.valueOf(error?.error!!)
        } catch (ex: IllegalAccessException) {
            ReasonOfError.UnknownError
        }
    }
}

class ApiError(
    val message: String,
    val headers: Headers,
    val code: Int,
    val errorBody: ErrorBody?
): AppError(errorBody) {

    companion object {
        fun <T> fromResponse(response: Response<T>): ApiError? {
            if (!response.isSuccessful) {
                val type = object : TypeToken<ErrorBody>() {}.type
                val errorResponse: ErrorBody? = Gson().fromJson(response.errorBody()!!.charStream(), type)
                return ApiError(
                    response.message(),
                    response.headers(),
                    response.code(),
                    errorResponse
                )
            }
            return null
        }
    }
}
