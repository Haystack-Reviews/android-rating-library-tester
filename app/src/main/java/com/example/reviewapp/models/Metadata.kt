package com.example.reviewapp.models


import android.os.Parcelable
import com.example.reviewapp.models.Library.*
import com.example.reviewapp.models.Library.AmplifyLibrary
import com.example.reviewapp.models.Library.RateLibrary
import kotlinx.android.parcel.Parcelize

// Licenses
val ApacheLicense2 = "Apache License 2.0"
val MITLicense = "MIT License"
val NoLicense = "No License"

@Parcelize
data class Metadata(val library: Library, val projectUrl: String, val license: String) :
    Parcelable

val RateLibraryData = Metadata(RateLibrary, "https://github.com/hotchemi/Android-Rate", MITLicense)
val AmplifyLibraryData = Metadata(AmplifyLibrary, "https://github.com/stkent/amplify", ApacheLicense2)
val RateThisAppLibraryData =
    Metadata(RateThisAppLibrary, "https://github.com/kobakei/Android-RateThisApp", ApacheLicense2)
val MaterialAppRatingLibraryData =
    Metadata(MaterialAppRatingLibrary, "https://github.com/stepstone-tech/android-material-app-rating", ApacheLicense2)
val FiveStarsLibraryData =
    Metadata(FiveStarsLibrary, "https://github.com/Angtrim/Android-Five-Stars-Library", NoLicense)

