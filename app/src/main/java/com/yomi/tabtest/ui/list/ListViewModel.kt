package com.yomi.tabtest.ui.list

import com.yomi.tabtest.base.BaseViewModel
import com.yomi.tabtest.network.ListApi
import javax.inject.Inject

class ListViewModel:BaseViewModel(){
    @Inject
    lateinit var listApi: ListApi
}