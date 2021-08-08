package com.kordia.achievements.domain.model

import android.os.Parcel
import android.os.Parcelable

/**
 * User dto class.
 *
 * @author Mohammedsaif Kordia
 */
data class UserDto(
    var id: String? = "",
    var activated: Boolean? = false,
    var email: String? = "",
    var firstName: String? = "",
    var imageUrl: String? = "",
    var lastName: String? = "",
    var login: String? = "",
    var token: String? = "",
) : Parcelable {

    constructor(parcel: Parcel) : this(
        id = parcel.readString(),
        activated = parcel.readByte() != 0.toByte(),
        email = parcel.readString(),
        firstName = parcel.readString(),
        imageUrl = parcel.readString(),
        lastName = parcel.readString(),
        login = parcel.readString(),
        token = parcel.readString(),
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeByte(if (activated!!) 1 else 0)
        parcel.writeString(email)
        parcel.writeString(firstName)
        parcel.writeString(imageUrl)
        parcel.writeString(lastName)
        parcel.writeString(login)
        parcel.writeString(token)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<UserDto> {
        override fun createFromParcel(parcel: Parcel): UserDto {
            return UserDto(parcel)
        }

        override fun newArray(size: Int): Array<UserDto?> {
            return arrayOfNulls(size)
        }
    }
}
