package com.yomi.tabtest.network

import com.yomi.tabtest.model.ListResponse
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface ListApi {
    /**
     * Get the list of the pots from the API
     */
    @GET("/box/city")
    fun getPosts(@Query("bbox") bbox: String, @Query("appid") appId: String): Observable<ListResponse>
}