package com.example.reviewapp

import android.app.Application
import com.github.stkent.amplify.feedback.DefaultEmailFeedbackCollector
import com.github.stkent.amplify.feedback.GooglePlayStoreFeedbackCollector
import com.github.stkent.amplify.tracking.Amplify


class ReviewApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        Amplify.initSharedInstance(this)
            .setPositiveFeedbackCollectors(GooglePlayStoreFeedbackCollector())
            .setCriticalFeedbackCollectors(DefaultEmailFeedbackCollector("someone@example.com"))
            .applyAllDefaultRules()
            .setAlwaysShow(BuildConfig.DEBUG)
    }
}