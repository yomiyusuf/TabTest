package com.yomi.tabtest.ui.cityDetails

import android.view.View
import androidx.lifecycle.MutableLiveData
import com.yomi.tabtest.BuildConfig
import com.yomi.tabtest.R
import com.yomi.tabtest.base.BaseViewModel
import com.yomi.tabtest.model.CityDetail
import com.yomi.tabtest.network.WeatherApi
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class WeatherViewModel(val cityId: String): BaseViewModel() {
    @Inject
    lateinit var weatherApi: WeatherApi

    val loadingVisibility: MutableLiveData<Int> = MutableLiveData()
    val errorMessage:MutableLiveData<Int> = MutableLiveData()
    val cityDetail: MutableLiveData<String> = MutableLiveData()
    val retryClickListener = View.OnClickListener { loadWeatherResponse() }

    private lateinit var subscription: Disposable

    init {
        loadWeatherResponse()
    }

    private fun loadWeatherResponse(){
        subscription = weatherApi.getCities(cityId, BuildConfig.API_TOKEN)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { onRetrieveWeatherStart() }
            .doOnTerminate { onRetrieveWeatherFinish() }
            .subscribe(
                {result -> onRetrieveWeatherSuccess(result)  },
                { onRetrieveWeatherError() }
            )
    }

    private fun onRetrieveWeatherStart(){
        loadingVisibility.value = View.VISIBLE
        errorMessage.value = null
    }

    private fun onRetrieveWeatherFinish(){
        loadingVisibility.value = View.GONE
    }

    private fun onRetrieveWeatherSuccess(result: CityDetail) {
        cityDetail.value = result.toString()
    }

    private fun onRetrieveWeatherError() {
        errorMessage.value = R.string.network_error
    }

    override fun onCleared() {
        super.onCleared()
        subscription.dispose()
    }
}