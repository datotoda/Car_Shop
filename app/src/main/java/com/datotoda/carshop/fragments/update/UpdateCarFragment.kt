package com.datotoda.carshop.fragments.update


import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import com.datotoda.carshop.R
import com.datotoda.carshop.adapters.CarFeaturesAdapter
import com.datotoda.carshop.model.Car
import com.datotoda.carshop.model.CarViewModel
import com.datotoda.carshop.model.enums.Features
import com.datotoda.carshop.model.enums.Marks
import kotlinx.android.synthetic.main.fragment_update_car.view.*

class UpdateCarFragment : Fragment(), AdapterView.OnItemSelectedListener {

    private lateinit var car: Car
    private val features = mutableListOf<Features>()
    private val updateCarArgs by navArgs<UpdateCarFragmentArgs>()
    private val carViewModel: CarViewModel by lazy {
        ViewModelProvider(this)[CarViewModel::class.java]
    }

    @SuppressLint("SetTextI18n")
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_update_car, container, false)
        val spinner = view.updateCarMarkSpinner
        car = updateCarArgs.editCar
        car.apply {
            if (ABS) { features.add(Features.ABS) }
            if (ELECTRIC_LIGHT_UPPER) { features.add(Features.ELECTRIC_LIGHT_UPPER) }
            if (SUNROOF) { features.add(Features.SUNROOF) }
            if (BLUETOOTH) { features.add(Features.BLUETOOTH) }
            if (ALARM) { features.add(Features.ALARM) }
            if (PARKING_CONTROL) { features.add(Features.PARKING_CONTROL) }
            if (NAVIGATION) { features.add(Features.NAVIGATION) }
            if (ONBOARD_COMPUTER) { features.add(Features.ONBOARD_COMPUTER) }
            if (MULTI_WHEEL) { features.add(Features.MULTI_WHEEL) }
        }


        spinner.onItemSelectedListener = this

        ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_item,
            Marks.displayNames()
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner.adapter = adapter
        }

        view.updateCarFeaturesRecyclerView.layoutManager = GridLayoutManager(context, 2)
        view.updateCarFeaturesRecyclerView.adapter = CarFeaturesAdapter(features)


        view.updateCarSaveFloatingActionButton.setOnClickListener {
            saveCar()
            Toast.makeText(requireContext(), getString(R.string.saved), Toast.LENGTH_SHORT).show()
        }

        view.updateCarMarkSpinner.setSelection(Marks.values().indexOf(car.mark))
        view.updateCarDescriptionEditText.setText(car.description)
        view.updateCarImageLinkEditText.setText(car.imageURL)
        view.updateCarOwnerTextView.text = "${getString(R.string.owner)}: ${car.owner}"

        return view
    }

    override fun onItemSelected(parent: AdapterView<*>, view: View?, pos: Int, id: Long) {  }

    override fun onNothingSelected(parent: AdapterView<*>) { }


    private fun saveCar() {
        val view = requireView()

        car.mark = Marks.getMarkByDisplayName(view.updateCarMarkSpinner.selectedItem.toString())
        car.description = view.updateCarDescriptionEditText.text.toString()
        car.imageURL = view.updateCarImageLinkEditText.text.toString()
        car.apply {
            ABS = false
            ELECTRIC_LIGHT_UPPER = false
            SUNROOF = false
            BLUETOOTH = false
            ALARM = false
            PARKING_CONTROL = false
            NAVIGATION = false
            ONBOARD_COMPUTER = false
            MULTI_WHEEL = false
        }
        features.forEach {
            when(it) {
                Features.ABS -> car.ABS = true
                Features.ELECTRIC_LIGHT_UPPER -> car.ELECTRIC_LIGHT_UPPER = true
                Features.SUNROOF -> car.SUNROOF = true
                Features.BLUETOOTH -> car.BLUETOOTH = true
                Features.ALARM -> car.ALARM = true
                Features.PARKING_CONTROL -> car.PARKING_CONTROL = true
                Features.NAVIGATION -> car.NAVIGATION = true
                Features.ONBOARD_COMPUTER -> car.ONBOARD_COMPUTER = true
                Features.MULTI_WHEEL -> car.MULTI_WHEEL = true
            }
        }

        carViewModel.updateCar(car)
    }

}