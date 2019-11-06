package com.example.reviewapp.models

import android.content.Context
import androidx.fragment.app.FragmentActivity

data class ReviewLibraryConfig(
    val applicationContext: Context,
    val fragmentContext: Context,
    val activity: FragmentActivity,
    val debugAlwaysLaunch: Boolean,
    val initialMessage: String,
    val initialTitle: String
)