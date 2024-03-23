package com.bhavesh.weatherappindianic.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bhavesh.weatherappindianic.R
import com.bhavesh.weatherappindianic.response.ListData
import com.bhavesh.weatherappindianic.utils.Constant
import com.bhavesh.weatherappindianic.utils.ConvertDate
import com.bumptech.glide.Glide
import java.util.*

class ForecastAdapter() : RecyclerView.Adapter<ForecastAdapter.ViewHolder>() {

    private var itemViewModels = mutableListOf<ListData>()

    @SuppressLint("NotifyDataSetChanged")
    fun setList(list : List<ListData>){
        this.itemViewModels.clear()
        this.itemViewModels.addAll(list)
        notifyDataSetChanged()
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var wedate = view.findViewById<TextView>(R.id.weatherDate)
        var wedesc = view.findViewById<TextView>(R.id.weatherDescription)
        var wemain = view.findViewById<TextView>(R.id.weatherTemperature)
        var welogo = view.findViewById<ImageView>(R.id.weatherIcon)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_forecast, parent, false)
        return ViewHolder(view)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val weatherName = itemViewModels[position]

        holder.wedate.text = ConvertDate.getDate2String(weatherName.dt!!)

        val iconUrl = Constant.imageUrl.plus(weatherName.weather?.get(0)?.icon.toString()).plus(".png")
        Glide.with(holder.welogo.context)
            .load(iconUrl)
            .into(holder.welogo)


        holder.wedesc.text = weatherName.weather?.get(0)?.description

        holder.wemain.text = weatherName.main?.temp.toString().plus(" ").plus("\u2103")

    }

    override fun getItemCount(): Int {
        return itemViewModels.size
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

}