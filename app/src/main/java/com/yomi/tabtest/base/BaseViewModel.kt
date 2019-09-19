package com.yomi.tabtest.base

import androidx.lifecycle.ViewModel
import com.yomi.tabtest.injection.component.DaggerViewModelInjector
import com.yomi.tabtest.injection.component.ViewModelInjector
import com.yomi.tabtest.injection.module.NetworkModule
import com.yomi.tabtest.ui.list.ListViewModel

abstract class BaseViewModel: ViewModel(){
    private val injector: ViewModelInjector = DaggerViewModelInjector
        .builder()
        .networkModule(NetworkModule)
        .build()

    init {
        inject()
    }

    private fun inject(){
        when (this) {
            is ListViewModel -> injector.inject(this)
        }
    }
}