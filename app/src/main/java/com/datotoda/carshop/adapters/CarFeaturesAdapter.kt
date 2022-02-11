package com.datotoda.carshop.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import androidx.recyclerview.widget.RecyclerView
import com.datotoda.carshop.R
import com.datotoda.carshop.model.enums.Features
import kotlinx.android.synthetic.main.car_feature_item.view.*

class CarFeaturesAdapter(private val carFeatures: MutableList<Features>
): RecyclerView.Adapter<CarFeaturesAdapter.ItemViewHolder>(){

    class ItemViewHolder (itemView: View): RecyclerView.ViewHolder(itemView) {
        val featureCheckBox: CheckBox = itemView.carFeatureCheckBox
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val adapterLayout = LayoutInflater.from(parent.context)
            .inflate(R.layout.car_feature_item, parent, false)

        return ItemViewHolder(adapterLayout)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val feature = Features.values()[position]

        holder.featureCheckBox.text = feature.displayName
        holder.featureCheckBox.isChecked = feature in carFeatures

        holder.featureCheckBox.setOnCheckedChangeListener { _, state ->
            if (state) {
                if (feature !in carFeatures){
                    carFeatures.add(feature)
                }
            } else {
                if (feature in carFeatures){
                    carFeatures.remove(feature)
                }
            }
        }
    }

    override fun getItemCount() = Features.values().size
}