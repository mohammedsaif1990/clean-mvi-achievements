package com.kordia.achievements.domain.model

import android.os.Parcel
import android.os.Parcelable

/**
 * Home statistics dto class.
 *
 * @author Mohammedsaif Kordia
 */
data class HomeStatisticsDto(
    val achievements: Int,
    val achieved: Int,
    val goalsTotal: Int,
    val goalsWaiting: Int
) : Parcelable {

    constructor(parcel: Parcel) : this(
        achievements = parcel.readInt(),
        achieved = parcel.readInt(),
        goalsTotal = parcel.readInt(),
        goalsWaiting = parcel.readInt()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(achievements)
        parcel.writeInt(achieved)
        parcel.writeInt(goalsTotal)
        parcel.writeInt(goalsWaiting)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<HomeStatisticsDto> {
        override fun createFromParcel(parcel: Parcel): HomeStatisticsDto {
            return HomeStatisticsDto(parcel)
        }

        override fun newArray(size: Int): Array<HomeStatisticsDto?> {
            return arrayOfNulls(size)
        }
    }
}
