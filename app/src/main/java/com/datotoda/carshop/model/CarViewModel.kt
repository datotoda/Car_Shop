package com.datotoda.carshop.model

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.datotoda.carshop.data.CarDatabase
import com.datotoda.carshop.repository.CarRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CarViewModel (application: Application): AndroidViewModel(application){
    val readAllData: LiveData<List<Car>>
    private val repository: CarRepository

    init {
        val userDao = CarDatabase.getDatabase(application).useDao()

        repository = CarRepository(userDao)
        readAllData = repository.readAllData
    }

    fun addCar(car: Car) {
        viewModelScope.launch(Dispatchers.IO){
            repository.addCar(car)
        }
    }

    fun updateCar(car: Car) {
        viewModelScope.launch(Dispatchers.IO){
            repository.updateCar(car)
        }
    }

    fun deleteCar(car: Car) {
        viewModelScope.launch(Dispatchers.IO){
            repository.deleteCar(car)
        }
    }

    fun deleteAllCars() {
        viewModelScope.launch(Dispatchers.IO){
            repository.deleteAllCars()
        }
    }
}








