package com.yomi.tabtest.ui.list

import android.util.Log
import android.view.View
import androidx.lifecycle.MutableLiveData
import com.yomi.tabtest.BuildConfig
import com.yomi.tabtest.R
import com.yomi.tabtest.base.BaseViewModel
import com.yomi.tabtest.network.ListApi
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class ListViewModel:BaseViewModel(){
    @Inject
    lateinit var listApi: ListApi

    val loadingVisibility: MutableLiveData<Int> = MutableLiveData()
    val errorMessage:MutableLiveData<Int> = MutableLiveData()
    val retryClickListener = View.OnClickListener { loadListResponse() }

    private lateinit var subscription: Disposable

    init {
        loadListResponse()
    }

    private fun loadListResponse(){
        subscription = listApi.getCities("12,32,15,37,10", BuildConfig.API_TOKEN)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { onRetrieveListStart() }
            .doOnTerminate { onRetrieveListFinish() }
            .subscribe(
                { onRetrieveListSuccess() },
                { onRetrieveListError() }
            )
    }

    private fun onRetrieveListStart(){
        loadingVisibility.value = View.VISIBLE
        errorMessage.value = null
    }

    private fun onRetrieveListFinish(){
        loadingVisibility.value = View.GONE

        //Log.e("NetworkModule", retrofit.create(ListApi::class.java).toString())
    }

    private fun onRetrieveListSuccess(){

    }

    private fun onRetrieveListError(){
        errorMessage.value = R.string.network_error
    }

    override fun onCleared() {
        super.onCleared()
        subscription.dispose()
    }
}