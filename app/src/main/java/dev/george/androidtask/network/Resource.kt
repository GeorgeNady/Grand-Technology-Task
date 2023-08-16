package dev.george.androidtask.network

import timber.log.Timber

data class Resource<T>(
    val status: Status,
    val data: T? = null,
    val message: String? = null
) {

    enum class Status {
        SUCCESS,
        ERROR,
        LOADING,
        FAILURE;

        fun isSuccess() = this == SUCCESS
        fun isError() = this == ERROR
        fun isLoading() = this == LOADING
        fun isFailure() = this == FAILURE
    }

    companion object {

        private const val TAG = "Resource"

        fun <T> success(data: T): Resource<T> {
            return Resource(Status.SUCCESS, data, null)
        }

        fun <T> error(message: String, data: T? = null): Resource<T> {
            return Resource(Status.ERROR, data, message)
        }

        fun <T> loading(): Resource<T> {
            return Resource(Status.LOADING, null, null)
        }

        fun <T> failed(message: String, data: T? = null): Resource<T> {
            return Resource(Status.FAILURE, data, message)
        }

    }

    fun handler(
        mLoading: (() -> Unit)? = null,
        mError: ((String) -> Unit)? = null,
        mFailed: ((String) -> Unit)? = null,
        mSuccess: (T) -> Unit,
    ) {
        when (this.status) {
            Status.LOADING -> {
                mLoading?.let { mLoading() }
                Timber.d(TAG, "$TAG >>> LOADING")
            }
            Status.ERROR -> {
                mError?.let { mError(message!!) }
                Timber.d(TAG, "$TAG >>> ERROR $message")
            }
            Status.FAILURE -> {
                mFailed?.let { mFailed(message!!) }
                Timber.d(TAG, "$TAG >>> FAILURE $message")
            }
            Status.SUCCESS -> {
                mSuccess(data!!)
                Timber.d(TAG, "$TAG >>> SUCCESS $data")
            }
        }
    }

    suspend fun suspendedHandler(
        mLoading: (() -> Unit)? = null,
        mError: ((String) -> Unit)? = null,
        mFailed: ((String) -> Unit)? = null,
        mSuccess: suspend (T) -> Unit,
    ) {
        when (this.status) {
            Status.LOADING -> {
                mLoading?.let { mLoading() }
                Timber.d(TAG, "$TAG >>> LOADING")
            }
            Status.ERROR -> {
                mError?.let { mError(message!!) }
                Timber.d(TAG, "$TAG >>> ERROR $message")
            }
            Status.FAILURE -> {
                mFailed?.let { mFailed(message!!) }
                Timber.d(TAG, "$TAG >>> FAILURE $message")
            }
            Status.SUCCESS -> {
                mSuccess(data!!)
                Timber.d(TAG, "$TAG >>> SUCCESS $data")
            }
        }
    }
    /*
    x.handler(
        mLoading = {},
        mError = {},
        mFailed = {},
     ) {
           TODO("success scope")
     }
    */


}