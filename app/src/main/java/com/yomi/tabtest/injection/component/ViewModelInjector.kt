package com.yomi.tabtest.injection.component

import com.yomi.tabtest.injection.module.NetworkModule
import com.yomi.tabtest.ui.list.ListViewModel
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [(NetworkModule::class)])
interface ViewModelInjector {
    /**
     * Injects required dependencies into the specified ListViewModel.
     * @param cityListViewModel ListViewModel in which to inject the dependencies
     */
    fun inject(cityListViewModel: ListViewModel)

    @Component.Builder
    interface Builder {
        fun build(): ViewModelInjector

        fun networkModule(networkModule: NetworkModule): Builder
    }
}