package com.yomi.tabtest.ui.list

import android.os.Bundle
import androidx.annotation.StringRes
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.yomi.tabtest.R
import com.yomi.tabtest.databinding.ActivityCityListBinding
import com.yomi.tabtest.ui.BaseActivity
import com.yomi.tabtest.ui.cityDetails.CityDetailActivity

class MainActivity : BaseActivity() {
    private lateinit var binding: ActivityCityListBinding
    private lateinit var viewModel: ListViewModel
    private var errorSnackBar: Snackbar? = null

    private val boundingBox = "12,32,15,37,10"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_city_list)
        binding.cityList.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        viewModel = ViewModelProviders.of(
            this,
            viewModelFactory { ListViewModel(boundingBox) }
        ).get(ListViewModel::class.java)

        viewModel.errorMessage.observe(this, Observer { errorMessage ->
            if (errorMessage != null) displayError(errorMessage) else hideError()
        })
        viewModel.selectedCity.observe(this, Observer { cityId ->
            cityId?.run {
                loadDetailActivity(this)
            }
        })
        binding.viewModel = viewModel
    }

    private fun displayError(@StringRes errorMessage: Int) {
        errorSnackBar = Snackbar.make(binding.root, errorMessage, Snackbar.LENGTH_INDEFINITE)
        errorSnackBar?.setAction(R.string.retry, viewModel.retryClickListener)
        errorSnackBar?.show()
    }

    private fun hideError() {
        errorSnackBar?.dismiss()
    }

    private fun loadDetailActivity(cityId: String) {
        startActivity(CityDetailActivity.newInstance(this, cityId))
    }
}
