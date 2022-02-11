package com.datotoda.carshop.repository

import androidx.lifecycle.LiveData
import com.datotoda.carshop.data.CarDao
import com.datotoda.carshop.model.Car

class CarRepository (private val carDao: CarDao) {
    val readAllData: LiveData<List<Car>> = carDao.readAllData()

    suspend fun addCar(car: Car) {
        carDao.addCar(car)
    }

    suspend fun updateCar(car: Car) {
        carDao.updateCar(car)
    }

    suspend fun deleteCar(car: Car) {
        carDao.deleteCar(car)
    }

    suspend fun deleteAllCars() {
        carDao.deleteAllCars()
    }

}