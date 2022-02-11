package com.datotoda.carshop.fragments.list

import android.app.AlertDialog
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.datotoda.carshop.R
import com.datotoda.carshop.adapters.CarListAdapter
import com.datotoda.carshop.model.Car
import com.datotoda.carshop.model.CarViewModel
import com.datotoda.carshop.model.enums.Marks
import kotlinx.android.synthetic.main.fragment_car_list.view.*

class CarListFragment : Fragment() {

    private val carViewModel: CarViewModel by lazy {
        ViewModelProvider(this)[CarViewModel::class.java]
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_car_list, container, false)

        carViewModel.readAllData.observe(viewLifecycleOwner) { carsList ->
            makeRecyclerView(carsList)
        }

        view.carListFloatingActionButton.setOnClickListener {
            findNavController().navigate(R.id.action_carListFragment_to_addCarFragment)
        }

        setHasOptionsMenu(true)

        return view
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.car_list_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.listMenuRestoreList -> menuRestoreClicked()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun makeRecyclerView(cars: List<Car>){
        val view = requireView()
        view.carListRecyclerView.adapter = CarListAdapter(
            requireContext(),
            cars,
            carViewModel
        )
        view.carListRecyclerView.setHasFixedSize(true)
    }

    private fun menuRestoreClicked() {
        AlertDialog.Builder(context)
            .apply {
                setPositiveButton("Yes") { _, _ ->
                    restoreDemoList()

                    Toast.makeText(
                        context,
                        "successfully restored demo list!",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                setNegativeButton("No") { _, _ -> }
                setTitle("Restore Demo list")
                setMessage("Are you sure that you want to restore demo list?")
                create()
                show()
            }
    }


    private fun restoreDemoList() {
        val imageUrl = listOf(
            "https://i.picsum.photos/id/891/80/80.jpg?hmac=pDS0otjDpgljXMrIuFJBvnK1Z9wnFYrNC48sKzWNXtc",
            "https://i.picsum.photos/id/615/80/80.jpg?hmac=ikStaZgsnTu9LKUpRx7AE5sYBYoOFFuJ-PHHdwZv3Gk",
            "https://i.picsum.photos/id/857/80/80.jpg?hmac=hElbsyd1Ny1_yCqrjX4YRymm6KNHDHlfS95qr3FF4cs",
            "https://i.picsum.photos/id/658/80/80.jpg?hmac=Cwk_6KTVFLJ_lGR0-VbohGGvOqVznOixihNaSd_Trr8",
            "https://i.picsum.photos/id/992/80/80.jpg?hmac=VW451EsK9vBBXtYzAD82WjpQ7mw13vgkoDrT4Y63U9k",
            "https://i.picsum.photos/id/335/80/80.jpg?hmac=CgZD3D0jJ5sEI_gm1c_n-Esuh1Fe45sP6et_uYyrpgI",
            "https://i.picsum.photos/id/430/80/80.jpg?hmac=JCfGg7rH3CcH2r4mYDnwRYOyi1G66WkUcgVzJC481rU",
            "https://i.picsum.photos/id/127/80/80.jpg?hmac=BiipBoJfa0dVqiY2aCnzplTijx13y3brtt8JWiEqAhc",
            "https://i.picsum.photos/id/719/80/80.jpg?hmac=8q6_aoSlIoNoAHFawaoI5u8DIpnSsT22SxIgywY-OKY",
            "https://i.picsum.photos/id/47/80/80.jpg?hmac=S9iCdgyU1Fcb8qP5LL8gBGgsItfKjw_khnKTKf248Mk",
            "https://i.picsum.photos/id/829/80/80.jpg?hmac=B5D4SXxhAWRSLYtG5-g6Q4Kh4GTHkIp66NV1xg3OctQ",
            "https://i.picsum.photos/id/336/80/80.jpg?hmac=gkjRET3y8aoTtST71Go3tA8Mq5gAl5DrQHKXW4ZGPgg",
            "https://i.picsum.photos/id/931/80/80.jpg?hmac=uejnBuLeYUZ5dOqZy66TjU5hOX0zVIYnnzkxIEytyCE",
        )
        carViewModel.apply {
            deleteAllCars()

            addCar(Car(0, Marks.BMW, "cool", "Me", imageUrl[0], ABS=true))
            addCar(Car(0, Marks.AUDI, "", "Bob", imageUrl[1], ELECTRIC_LIGHT_UPPER=true))
            addCar(Car(0, Marks.MITSUBISHI, "", "Jhon", imageUrl[2], SUNROOF=true))
            addCar(Car(0, Marks.BMW, "nice car", "BMW factory", imageUrl[3], BLUETOOTH=true))
            addCar(Car(0, Marks.MITSUBISHI, "sale", "Me", imageUrl[4], ALARM=true, ABS=true))
            addCar(Car(0, Marks.OTHER, "", "Jack", imageUrl[5], PARKING_CONTROL=true, ))
            addCar(Car(0, Marks.TOYOTA, "", "George", imageUrl[6], NAVIGATION=true, ))
            addCar(Car(0, Marks.MERCEDES, "good", "James", imageUrl[7]))
            addCar(Car(0, Marks.BMW, "perfect", "Leo", imageUrl[8], ALARM=true))
            addCar(Car(0, Marks.AUDI, "", "Mike", imageUrl[9], PARKING_CONTROL=true))
            addCar(Car(0, Marks.MERCEDES, "ok", "Anna", imageUrl[10], NAVIGATION=true))
            addCar(Car(0, Marks.TOYOTA, "nice", "Davit", imageUrl[11], ONBOARD_COMPUTER=true))
            addCar(Car(0, Marks.AUDI, "cool", "Me", imageUrl[12]))
        }
    }
}








