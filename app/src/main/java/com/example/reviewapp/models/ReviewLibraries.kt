package com.example.reviewapp.models

import android.net.Uri

data class LibraryLicense(val name: String)
val ApacheLicense2 = LibraryLicense("Apache License 2.0")

data class ReviewLibraries(val name: String, val projectUrl: Uri, val license: LibraryLicense)
val AmplifyLibrary = ReviewLibraries("Amplify", Uri.parse("https://github.com/stkent/amplify"), ApacheLicense2)



