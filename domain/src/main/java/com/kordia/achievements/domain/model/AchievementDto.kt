package com.kordia.achievements.domain.model

import android.os.Parcel
import android.os.Parcelable

/**
 * Achievement dto class.
 *
 * @author Mohammedsaif Kordia
 */
data class AchievementDto(
    var id: String? = "",
    var dateAchieved: Long? = 0L,
    var dateExpected: Long? = 0L,
    var description: String? = "",
    var goalId: String? = "",
    var lastUpdated: Long? = 0L,
    var title: String? = "",
    var typeId: String? = "",
    var userId: String? = "",
    var wasGoal: Boolean? = false,
) : Parcelable {

    constructor(parcel: Parcel) : this(
        id = parcel.readString() ?: "",
        dateAchieved = parcel.readLong(),
        dateExpected = parcel.readLong(),
        description = parcel.readString() ?: "",
        goalId = parcel.readString() ?: "",
        lastUpdated = parcel.readLong(),
        title = parcel.readString() ?: "",
        typeId = parcel.readString() ?: "",
        userId = parcel.readString() ?: "",
        wasGoal = parcel.readByte() != 0.toByte(),
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeLong(dateAchieved!!)
        parcel.writeLong(dateExpected!!)
        parcel.writeString(description)
        parcel.writeString(goalId)
        parcel.writeLong(lastUpdated!!)
        parcel.writeString(title)
        parcel.writeString(typeId)
        parcel.writeString(userId)
        parcel.writeByte(if (wasGoal!!) 1 else 0)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<AchievementDto> {
        override fun createFromParcel(parcel: Parcel): AchievementDto {
            return AchievementDto(parcel)
        }

        override fun newArray(size: Int): Array<AchievementDto?> {
            return arrayOfNulls(size)
        }
    }
}
