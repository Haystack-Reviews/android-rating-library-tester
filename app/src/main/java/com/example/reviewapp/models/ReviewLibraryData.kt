package com.example.reviewapp.models


import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

// Licenses
val ApacheLicense2 = "Apache License 2.0"
val MITLicense = "MIT License"
val NoLicense = "No License"

@Parcelize
data class ReviewLibraryData(val name: String, val projectUrl: String, val license: String) :
    Parcelable

val RateLibrary = ReviewLibraryData("Android Rate", "https://github.com/hotchemi/Android-Rate", MITLicense)
val AmplifyLibrary = ReviewLibraryData("Amplify", "https://github.com/stkent/amplify", ApacheLicense2)
val RateThisAppLibrary =
    ReviewLibraryData("RateThisApp", "https://github.com/kobakei/Android-RateThisApp", ApacheLicense2)
val MaterialAppRatingLibrary =
    ReviewLibraryData("Material App Rating", "https://github.com/stepstone-tech/android-material-app-rating", ApacheLicense2)
val FiveStarsLibrary =
    ReviewLibraryData("Five Stars", "https://github.com/Angtrim/Android-Five-Stars-Library", NoLicense)
