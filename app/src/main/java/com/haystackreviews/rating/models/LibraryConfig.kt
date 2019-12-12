package com.haystackreviews.rating.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class LibraryConfig(
    val library: Library,
    val debugAlwaysLaunch: Boolean,
    val initialTitle: String,
    val initialMessage: String
) : Parcelable