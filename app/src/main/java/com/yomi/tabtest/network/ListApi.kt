package com.yomi.tabtest.network

import com.yomi.tabtest.model.ListResponse
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface ListApi {
    @GET("data/2.5/box/city")
    fun getCities(@Query("bbox") bbox: String, @Query("appid") appId: String): Observable<ListResponse>
}