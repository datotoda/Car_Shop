package com.datotoda.carshop.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.datotoda.carshop.model.enums.Marks
import kotlinx.android.parcel.Parcelize


@Parcelize
@Entity(tableName = "cars")
data class Car (
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    var mark: Marks,
    var description: String,
    var owner: String,
    var imageURL: String,
    var ABS: Boolean = false,
    var ELECTRIC_LIGHT_UPPER: Boolean = false,
    var SUNROOF: Boolean = false,
    var BLUETOOTH: Boolean = false,
    var ALARM: Boolean = false,
    var PARKING_CONTROL: Boolean = false,
    var NAVIGATION: Boolean = false,
    var ONBOARD_COMPUTER: Boolean = false,
    var MULTI_WHEEL: Boolean = false
) : Parcelable