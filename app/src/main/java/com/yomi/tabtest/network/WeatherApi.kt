package com.yomi.tabtest.network

import com.yomi.tabtest.model.CityDetail
import com.yomi.tabtest.model.ListResponse
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {
    @GET("data/2.5/weather")
    fun getCities(@Query("id") cityId: String, @Query("appid") appId: String): Observable<CityDetail>
}