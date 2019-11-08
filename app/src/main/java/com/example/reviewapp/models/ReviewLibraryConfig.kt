package com.example.reviewapp.models

import android.content.Context
import android.os.Parcelable
import androidx.fragment.app.FragmentActivity
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ReviewLibraryConfig(
    val library: ReviewLibrary,
    val debugAlwaysLaunch: Boolean,
    val initialTitle: String,
    val initialMessage: String
) : Parcelable