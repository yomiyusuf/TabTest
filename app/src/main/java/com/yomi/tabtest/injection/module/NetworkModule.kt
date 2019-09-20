package com.yomi.tabtest.injection.module

import com.yomi.tabtest.network.ListApi
import com.yomi.tabtest.network.WeatherApi
import com.yomi.tabtest.util.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.Reusable
import io.reactivex.schedulers.Schedulers
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory

@Module
@Suppress("unused")
object NetworkModule {
    /**
     * Provides the List service implementation.
     * @param retrofit the Retrofit object used to instantiate the service
     * @return the List service implementation.
     */
    @Provides
    @Reusable
    @JvmStatic
    internal fun provideListApi(retrofit: Retrofit): ListApi {
        return retrofit.create(ListApi::class.java)
    }

    /**
     * Provides the City weather service implementation.
     * @param retrofit the Retrofit object used to instantiate the service
     * @return the CityDetail service implementation.
     */
    @Provides
    @Reusable
    @JvmStatic
    internal fun provideWeatherApi(retrofit: Retrofit): WeatherApi {
        return retrofit.create(WeatherApi::class.java)
    }

    /**
     * Provides the Retrofit object.
     * @return the Retrofit object
     */
    @Provides
    @Reusable
    @JvmStatic
    internal fun provideRetrofitInterface(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
            .build()
    }
}