package com.yomi.tabtest.ui.list

import androidx.lifecycle.MutableLiveData
import com.yomi.tabtest.base.BaseViewModel
import com.yomi.tabtest.model.City

class ListItemViewModel: BaseViewModel() {
    private val cityName = MutableLiveData<String>()

    fun bind(city: City){
        cityName.value = city.name
    }

    fun getCityName():MutableLiveData<String>{
        return cityName
    }
}