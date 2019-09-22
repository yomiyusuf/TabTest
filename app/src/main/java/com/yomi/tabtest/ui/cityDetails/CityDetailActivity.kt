package com.yomi.tabtest.ui.cityDetails

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.annotation.StringRes
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.google.android.material.snackbar.Snackbar
import com.yomi.tabtest.R
import com.yomi.tabtest.databinding.ActivityCityDetailBinding
import com.yomi.tabtest.ui.BaseActivity

class CityDetailActivity : BaseActivity() {
    private lateinit var binding: ActivityCityDetailBinding
    private lateinit var viewModel: WeatherViewModel
    private var errorSnackBar: Snackbar? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_city_detail)

        viewModel = ViewModelProviders.of(
            this,
            viewModelFactory {WeatherViewModel(intent.getStringExtra(ARG_CITY_ID)?: "")}
        ).get(WeatherViewModel::class.java)
        viewModel.errorMessage.observe(this, Observer { errorMessage ->
            if(errorMessage != null) displayError(errorMessage) else hideError()
        })
        binding.viewModel = viewModel
    }

    private fun displayError(@StringRes errorMessage:Int){
        errorSnackBar = Snackbar.make(binding.root, errorMessage, Snackbar.LENGTH_INDEFINITE)
        errorSnackBar?.setAction(R.string.retry, viewModel.retryClickListener)
        errorSnackBar?.show()
    }

    private fun hideError(){
        errorSnackBar?.dismiss()
    }

    companion object {
        private const val ARG_CITY_ID = "cityId"

        /**
         * Use this factory method to create a new instance of
         * this activity using the provided parameters.
         *
         * @param cityId as String
         * @return A new instance of fragment CityDetailActivity.
         */
        fun newInstance(context: Context, cityId: String) = Intent(context, CityDetailActivity::class.java).apply  {
            putExtra(ARG_CITY_ID, cityId)
        }
    }
}
