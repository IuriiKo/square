package com.example.square.api.services

import com.example.square.api.models.SquareApiModel
import io.reactivex.Single
import retrofit2.http.GET

/**
 * Service is using host [com.example.square.common.Config.SQUARE_BASE_URL]
 */
interface SquareService {
    @GET("orgs/square/repos")
    fun getAllData(): Single<List<SquareApiModel>>
}