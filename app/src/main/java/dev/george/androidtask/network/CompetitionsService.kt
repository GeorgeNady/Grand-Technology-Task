package dev.george.androidtask.network

import dev.george.androidtask.BuildConfig
import dev.george.androidtask.model.remote.CompetitionsResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers

interface CompetitionsService {

    @GET("competitions/2021/matches")
    @Headers("X-Auth-Token: ${BuildConfig.API_KEY}")
    suspend fun getAllCompetitions() : Response<CompetitionsResponse>

}