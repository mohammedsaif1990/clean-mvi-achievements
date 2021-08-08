package com.kordia.achievements.domain.model

import android.os.Parcel
import android.os.Parcelable

/**
 * Goal dto class.
 *
 * @author Mohammedsaif Kordia
 */
data class GoalDto(
    var id: String? = "",
    var achieved: Boolean? = false,
    var dateAchieved: Long? = 0L,
    var dateCreated: Long? = 0L,
    var dateExpected: Long? = 0L,
    var description: String? = "",
    var lastUpdated: Long? = 0L,
    var title: String? = "",
    var typeId: String? = "",
    var userId: String? = "",
) : Parcelable {

    constructor(parcel: Parcel) : this(
        id = parcel.readString(),
        achieved = parcel.readByte() != 0.toByte(),
        dateCreated = parcel.readLong(),
        dateExpected = parcel.readLong(),
        dateAchieved = parcel.readLong(),
        description = parcel.readString(),
        lastUpdated = parcel.readLong(),
        title = parcel.readString(),
        typeId = parcel.readString(),
        userId = parcel.readString(),
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeByte(if (achieved!!) 1 else 0)
        parcel.writeLong(dateAchieved!!)
        parcel.writeLong(dateCreated!!)
        parcel.writeLong(dateExpected!!)
        parcel.writeString(description)
        parcel.writeLong(lastUpdated!!)
        parcel.writeString(title)
        parcel.writeString(typeId)
        parcel.writeString(userId)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<GoalDto> {
        override fun createFromParcel(parcel: Parcel): GoalDto {
            return GoalDto(parcel)
        }

        override fun newArray(size: Int): Array<GoalDto?> {
            return arrayOfNulls(size)
        }
    }
}
