package com.kordia.achievements.domain.model

import android.os.Parcel
import android.os.Parcelable

/**
 * Setting dto class.
 *
 * @author Mohammedsaif Kordia
 */
data class SettingDto(
    var id: Long,
    var currentUserId: String
) : Parcelable {

    constructor(parcel: Parcel) : this(
        id = parcel.readLong(),
        currentUserId = parcel.readString() ?: ""
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeLong(id)
        parcel.writeString(currentUserId)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<SettingDto> {
        override fun createFromParcel(parcel: Parcel): SettingDto {
            return SettingDto(parcel)
        }

        override fun newArray(size: Int): Array<SettingDto?> {
            return arrayOfNulls(size)
        }
    }
}
