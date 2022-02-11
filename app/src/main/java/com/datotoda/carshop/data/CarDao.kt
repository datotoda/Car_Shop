package com.datotoda.carshop.data

import androidx.lifecycle.LiveData
import androidx.room.*
import com.datotoda.carshop.model.Car


@Dao
interface CarDao {
    @Query("SELECT * FROM cars ORDER BY id ASC")
    fun readAllData(): LiveData<List<Car>>

    @Query("DELETE FROM cars")
    suspend fun deleteAllCars()

    @Delete
    suspend fun deleteCar(car: Car)

    @Update
    suspend fun updateCar(car: Car)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addCar(car: Car)
}