package com.haystackreviews.rating.models

// Licenses
private val ApacheLicense2 = "Apache License 2.0"
private val MITLicense = "MIT License"
private val NoLicense = "No License"

enum class Library(val displayName: String, val projectUrl: String, val license: String) {
    AmplifyLibrary("amplify", "https://github.com/stkent/amplify", ApacheLicense2),
    FiveStarsLibrary(
        "Android Five Stars Library",
        "https://github.com/Angtrim/Android-Five-Stars-Library",
        NoLicense
    ),
    MaterialAppRatingLibrary(
        "Android Material App Rating",
        "https://github.com/stepstone-tech/android-material-app-rating",
        ApacheLicense2
    ),
    RateThisAppLibrary(
        "Android-RateThisApp",
        "https://github.com/kobakei/Android-RateThisApp",
        ApacheLicense2
    ),
    RateLibrary("Android-Rate", "https://github.com/hotchemi/Android-Rate", MITLicense);


    override fun toString(): String {
        return displayName
    }
}