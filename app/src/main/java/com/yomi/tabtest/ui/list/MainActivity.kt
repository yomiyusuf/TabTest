package com.yomi.tabtest.ui.list

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.annotation.StringRes
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.yomi.tabtest.R
import com.yomi.tabtest.databinding.ActivityCityListBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCityListBinding
    private lateinit var viewModel: ListViewModel
    private var errorSnackBar: Snackbar? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_city_list)
        binding.cityList.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        viewModel = ViewModelProviders.of(this).get(ListViewModel::class.java)
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
}
