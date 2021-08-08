package com.kordia.achievements.domain.model

import android.os.Parcel
import android.os.Parcelable

/**
 * Type dto class.
 *
 * @author Mohammedsaif Kordia
 */
data class TypeDto(
    var id: String,
    var image: String,
) : Parcelable {

    constructor(parcel: Parcel) : this(
        id = parcel.readString() ?: "",
        image = parcel.readString() ?: ""
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeString(image)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<TypeDto> {
        override fun createFromParcel(parcel: Parcel): TypeDto {
            return TypeDto(parcel)
        }

        override fun newArray(size: Int): Array<TypeDto?> {
            return arrayOfNulls(size)
        }
    }
}
