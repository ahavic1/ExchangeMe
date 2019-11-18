package ba.ahavic.exchangeme.data.base

import ba.ahavic.exchangeme.core.ApiError
import ba.ahavic.exchangeme.core.AppError
import ba.ahavic.exchangeme.core.AppException
import kotlinx.coroutines.Dispatchers
import retrofit2.Response
import javax.inject.Inject

abstract class BaseRepository {
    @Inject
    protected lateinit var dispachers: Dispatchers
}

fun <T> Response<List<T>>.asBodyList(): List<T> =
    if (isSuccessful) body() ?: listOf()
    else throw AppException(ApiError.fromResponse(this) as AppError)

inline fun <reified T> Response<T>.asBody(): T {
    if (isSuccessful) {
        return if (T::class.java.isInstance(Unit))
            Unit as T
        else
            body() ?: T::class.java.newInstance()
    }
    throw AppException(ApiError.fromResponse(this) as AppError)
}