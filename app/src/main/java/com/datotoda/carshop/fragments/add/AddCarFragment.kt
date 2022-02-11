package com.datotoda.carshop.fragments.add

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity.RESULT_OK
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.provider.ContactsContract
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.datotoda.carshop.R
import com.datotoda.carshop.adapters.CarFeaturesAdapter
import com.datotoda.carshop.model.Car
import com.datotoda.carshop.model.CarViewModel
import com.datotoda.carshop.model.enums.Features
import com.datotoda.carshop.model.enums.Marks
import kotlinx.android.synthetic.main.fragment_add_car.view.*

const val CONTACT_PERMISSION_CODE = 1
const val CONTACT_PICK_CODE = 2

class AddCarFragment : Fragment(), AdapterView.OnItemSelectedListener {

    private val features: MutableList<Features> = mutableListOf()
    private val carViewModel: CarViewModel by lazy {
        ViewModelProvider(this)[CarViewModel::class.java]
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_add_car, container, false)

        val spinner = view.addCarMarkSpinner

        spinner.onItemSelectedListener = this

        ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_item,
            Marks.displayNames()
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner.adapter = adapter
        }

        view.addCarFeaturesRecyclerView.layoutManager = GridLayoutManager(context, 2)
        view.addCarFeaturesRecyclerView.adapter = CarFeaturesAdapter(features)


        view.addCarPickContactButton.setOnClickListener {
            if (checkContactPermission()) {
                pickContactIntent()
            } else {
                ActivityCompat.requestPermissions(requireActivity(),
                    arrayOf(Manifest.permission.READ_CONTACTS), CONTACT_PERMISSION_CODE)
            }
        }

        view.addCarSaveFloatingActionButton.setOnClickListener {
            saveCar()
            Toast.makeText(requireContext(), getString(R.string.saved), Toast.LENGTH_SHORT).show()
            findNavController().popBackStack()
        }

        return view
    }


    override fun onItemSelected(parent: AdapterView<*>, view: View?, pos: Int, id: Long) { }

    override fun onNothingSelected(parent: AdapterView<*>) { }

    private fun checkContactPermission() = ContextCompat.checkSelfPermission(
        requireContext(),
        Manifest.permission.READ_CONTACTS) == PackageManager.PERMISSION_GRANTED

    private fun pickContactIntent() {
        Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI).also {
            startActivityForResult(it, CONTACT_PICK_CODE)
        }
    }

    @SuppressLint("Range", "Recycle")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK && requestCode == CONTACT_PICK_CODE){
            if (data != null && data.data != null) {
                val cursor = requireActivity().application.contentResolver
                    .query(data.data!!, null, null, null, null, null)

                if (cursor != null && cursor.moveToFirst()){
                    val name = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME))

                    requireView().addCarFullNameEditText.setText(name)
                }
            }
        }
    }

    private fun saveCar(){
        val view = requireView()
        val car = Car(
            0,
            Marks.getMarkByDisplayName(view.addCarMarkSpinner.selectedItem.toString()),
            view.addCarDescriptionEditText.text.toString(),
            view.addCarFullNameEditText.text.toString(),
            view.addCarImageLinkEditText.text.toString(),
        )

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

        carViewModel.addCar(car)
        Log.d("saveCar", car.toString())
    }
}