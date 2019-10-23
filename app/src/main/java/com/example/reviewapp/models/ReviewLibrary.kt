package com.example.reviewapp.models

import android.net.Uri

data class LibraryLicense(val name: String)

val ApacheLicense2 = "Apache License 2.0"

data class ReviewLibrary(val name: String, val projectUrl: String, val license: String)

val AmplifyLibrary = ReviewLibrary("Amplify", "https://github.com/stkent/amplify", ApacheLicense2)



