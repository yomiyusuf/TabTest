package com.yomi.tabtest.ui.list

import android.util.Log
import android.view.View
import androidx.lifecycle.MutableLiveData
import com.yomi.tabtest.BuildConfig
import com.yomi.tabtest.R
import com.yomi.tabtest.base.BaseViewModel
import com.yomi.tabtest.model.ListResponse
import com.yomi.tabtest.network.ListApi
import com.yomi.tabtest.ui.list.CityListAdapter.ClickListener
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class ListViewModel(val boundingBox: String) : BaseViewModel() {
    @Inject
    lateinit var listApi: ListApi

    val loadingVisibility: MutableLiveData<Int> = MutableLiveData()
    val errorMessage: MutableLiveData<Int> = MutableLiveData()
    val selectedCity: MutableLiveData<String?> = MutableLiveData()
    val retryClickListener = View.OnClickListener { loadListResponse() }

    val cityListAdapter: CityListAdapter = CityListAdapter(object : ClickListener {
        override fun onCityClick(cityId: String) {
            selectedCity.value = cityId
        }
    })

    private lateinit var subscription: Disposable

    init {
        loadListResponse()
        selectedCity.value = null
    }

    private fun loadListResponse() {
        subscription = listApi.getCities(boundingBox, BuildConfig.API_TOKEN)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { onRetrieveListStart() }
            .doOnTerminate { onRetrieveListFinish() }
            .subscribe(
                { result -> onRetrieveListSuccess(result) },
                { err -> onRetrieveListError(err) }
            )
    }

    private fun onRetrieveListStart() {
        loadingVisibility.value = View.VISIBLE
        errorMessage.value = null
    }

    private fun onRetrieveListFinish() {
        loadingVisibility.value = View.GONE
    }

    private fun onRetrieveListSuccess(result: ListResponse) {
        cityListAdapter.updateCityList(result.list)
    }

    private fun onRetrieveListError(err: Throwable) {
        Log.e("Error", err.message)
        errorMessage.value = R.string.network_error
    }

    override fun onCleared() {
        super.onCleared()
        subscription.dispose()
    }
}