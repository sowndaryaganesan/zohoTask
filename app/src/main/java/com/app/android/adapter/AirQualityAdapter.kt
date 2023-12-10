package com.app.android.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.app.android.databinding.InflateAirQualityBinding
import com.app.android.model.Components

class AirQualityAdapter: RecyclerView.Adapter<AirQualityAdapter.MyViewHolder>() {

    private var airQualityLists = ArrayList<Components>()

    @SuppressLint("NotifyDataSetChanged")
    fun setAirQualityList(airQuality: ArrayList<Components>) {
        this.airQualityLists = airQuality
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyViewHolder {

        val binding = InflateAirQualityBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return MyViewHolder(binding)
    }


    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val airQuality = airQualityLists[position]

        holder.airTitle.text = airQuality.name
        holder.airValue.text = airQuality.value.toString()


    }

    override fun getItemCount(): Int {
        return airQualityLists.size
    }


    class MyViewHolder(binding: InflateAirQualityBinding) : RecyclerView.ViewHolder(binding.root) {

        var airTitle = binding.txtAirName
        var airValue = binding.txtAirValue

    }



}