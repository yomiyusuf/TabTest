package com.yomi.tabtest.ui.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.yomi.tabtest.R
import com.yomi.tabtest.databinding.ItemCityBinding
import com.yomi.tabtest.model.City

class CityListAdapter: RecyclerView.Adapter<CityListAdapter.ViewHolder>() {
    private lateinit var cities:List<City>

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CityListAdapter.ViewHolder {
        val binding: ItemCityBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.item_city, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CityListAdapter.ViewHolder, position: Int) {
        holder.bind(cities[position])
    }

    override fun getItemCount(): Int {
        return if(::cities.isInitialized) cities.size else 0
    }

    fun updateCityList(list:List<City>){
        this.cities = list
        notifyDataSetChanged()
    }

    class ViewHolder(private val binding: ItemCityBinding):RecyclerView.ViewHolder(binding.root){
        private val viewModel = ListItemViewModel()

        fun bind(city:City){
            viewModel.bind(city)
            binding.viewModel = viewModel
        }
    }
}