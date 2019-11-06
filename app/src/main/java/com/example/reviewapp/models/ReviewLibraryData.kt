package com.example.reviewapp.models


import android.os.Parcelable
import com.example.reviewapp.models.ReviewLibrary.*
import com.example.reviewapp.models.ReviewLibrary.AmplifyLibrary
import com.example.reviewapp.models.ReviewLibrary.RateLibrary
import kotlinx.android.parcel.Parcelize

// Licenses
val ApacheLicense2 = "Apache License 2.0"
val MITLicense = "MIT License"
val NoLicense = "No License"

@Parcelize
data class ReviewLibraryData(val reviewLibrary: ReviewLibrary, val projectUrl: String, val license: String) :
    Parcelable

val RateLibraryData = ReviewLibraryData(RateLibrary, "https://github.com/hotchemi/Android-Rate", MITLicense)
val AmplifyLibraryData = ReviewLibraryData(AmplifyLibrary, "https://github.com/stkent/amplify", ApacheLicense2)
val RateThisAppLibraryData =
    ReviewLibraryData(RateThisAppLibrary, "https://github.com/kobakei/Android-RateThisApp", ApacheLicense2)
val MaterialAppRatingLibraryData =
    ReviewLibraryData(MaterialAppRatingLibrary, "https://github.com/stepstone-tech/android-material-app-rating", ApacheLicense2)
val FiveStarsLibraryData =
    ReviewLibraryData(FiveStarsLibrary, "https://github.com/Angtrim/Android-Five-Stars-Library", NoLicense)

