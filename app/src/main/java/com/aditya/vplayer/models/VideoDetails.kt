package com.aditya.vplayer.models

import android.os.Parcel
import android.os.Parcelable

data class VideoDetails(
    var description: String,
    var id: String,
    var thumb: String,
    var title: String,
    var url: String,
    var selected: Int = 1
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readInt()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(description)
        parcel.writeString(id)
        parcel.writeString(thumb)
        parcel.writeString(title)
        parcel.writeString(url)
        parcel.writeInt(selected)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<VideoDetails> {
        override fun createFromParcel(parcel: Parcel): VideoDetails {
            return VideoDetails(parcel)
        }

        override fun newArray(size: Int): Array<VideoDetails?> {
            return arrayOfNulls(size)
        }
    }
}