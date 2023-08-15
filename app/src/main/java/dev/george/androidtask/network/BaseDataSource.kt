package dev.george.androidtask.network

import dev.george.androidtask.util.InternetConnectionUtils.hasInternetConnection
import retrofit2.Response
import timber.log.Timber
import java.io.IOException

abstract class BaseDataSource {

    companion object {
        const val TAG = "BaseDataSource"
    }

    // info : safe api call
    suspend fun <T> safeApiCall(apiCall: suspend () -> Response<T>): Resource<T> {
        try {

            if (hasInternetConnection()) {

                val response = apiCall()

                if (response.isSuccessful) {
                    val body = response.body()
                    Timber.d("safeApiCall: body >>> $body")

                    if (body != null) {
                        return Resource.success(body)
                    }

                }

                return Resource.failed("Something went wrong, try again")


            } else {

                return Resource.failed("No Internet Connection")

            }


        } catch (t: Throwable) {
            Timber.e("Conversion Error " + t.stackTraceToString())
            return when (t) {
                is IOException -> Resource.failed("Network Failure")
                else -> Resource.failed("Conversion Error")
            }

        }
    }

    // info: Error Body Model
    private data class ErrorBody(
        val message: String,
    )

}