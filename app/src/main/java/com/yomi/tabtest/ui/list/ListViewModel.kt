package com.yomi.tabtest.ui.list

import android.view.View
import androidx.lifecycle.MutableLiveData
import com.yomi.tabtest.BuildConfig
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
    }

    private fun onRetrieveListFinish(){
        loadingVisibility.value = View.GONE
    }

    private fun onRetrieveListSuccess(){

    }

    private fun onRetrieveListError(){

    }

    override fun onCleared() {
        super.onCleared()
        subscription.dispose()
    }
}