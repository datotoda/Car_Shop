package com.datotoda.carshop.adapters

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.datotoda.carshop.R
import com.datotoda.carshop.fragments.list.CarListFragmentDirections
import com.datotoda.carshop.model.Car
import com.datotoda.carshop.model.CarViewModel
import kotlinx.android.synthetic.main.car_list_item.view.*

class CarListAdapter(
    private val context: Context,
    private val dataSet: List<Car>,
    val viewModel: CarViewModel
): RecyclerView.Adapter<CarListAdapter.ItemViewHolder>(){

    class ItemViewHolder (itemView: View): RecyclerView.ViewHolder(itemView) {
        val imageImageView: ImageView = itemView.carItemImageView
        val modelTextView: TextView = itemView.carItemModelTextView
        val descriptionTextView: TextView = itemView.carItemDescriptionTextView
        val editImageButton: ImageButton = itemView.carItemEditImageButton
        val deleteImageButton: ImageButton = itemView.carItemDeleteImageButton
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val adapterLayout = LayoutInflater.from(parent.context)
            .inflate(R.layout.car_list_item, parent, false)

        return ItemViewHolder(adapterLayout)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val car = dataSet[position]

        holder.modelTextView.text = car.mark.displayName
        holder.descriptionTextView.text = car.description

        holder.editImageButton.setOnClickListener {
            val action = CarListFragmentDirections.actionCarListFragmentToUpdateCarFragment(car)
            holder.itemView.findNavController().navigate(action)
        }

        holder.deleteImageButton.setOnClickListener {
            AlertDialog.Builder(context)
                .apply {
                    setPositiveButton("Yes") { _, _ ->
                        viewModel.deleteCar(car)
                        Toast.makeText(
                            context,
                            "delete successfully",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                    setNegativeButton("No") { _, _ -> }
                    setTitle("Delete ${car.mark.displayName}")
                    setMessage("Are you sure that you want to delete ${car.mark.displayName}?")
                    create()
                    show()
                }
        }

        Glide.with(holder.itemView.context)
            .load(car.imageURL)
            .centerCrop()
            .into(holder.imageImageView)
    }

    override fun getItemCount() = dataSet.size
}