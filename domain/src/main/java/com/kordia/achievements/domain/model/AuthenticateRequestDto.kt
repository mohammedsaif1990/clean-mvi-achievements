package com.kordia.achievements.domain.model

import android.os.Parcel
import android.os.Parcelable

/**
 * Authenticate dto class.
 *
 * @author Mohammedsaif Kordia
 */
data class AuthenticateRequestDto(
    var password: String? = "",
    var rememberMe: Boolean? = false,
    var username: String? = "",
) : Parcelable {

    constructor(parcel: Parcel) : this(
        password = parcel.readString(),
        rememberMe = parcel.readByte() != 0.toByte(),
        username = parcel.readString(),
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(password)
        parcel.writeByte(if (rememberMe!!) 1 else 0)
        parcel.writeString(username)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<AuthenticateRequestDto> {
        override fun createFromParcel(parcel: Parcel): AuthenticateRequestDto {
            return AuthenticateRequestDto(parcel)
        }

        override fun newArray(size: Int): Array<AuthenticateRequestDto?> {
            return arrayOfNulls(size)
        }
    }
}