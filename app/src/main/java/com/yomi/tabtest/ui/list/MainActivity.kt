package com.yomi.tabtest.ui.list

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.yomi.tabtest.R
import com.yomi.tabtest.databinding.ActivityCityListBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCityListBinding
    private lateinit var viewModel: ListViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_city_list)
        binding.cityList.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        viewModel = ViewModelProviders.of(this).get(ListViewModel::class.java)
        binding.viewModel = viewModel
    }
}
