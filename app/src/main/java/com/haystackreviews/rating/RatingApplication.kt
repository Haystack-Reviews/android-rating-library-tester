package com.haystackreviews.rating

import android.app.Application
import com.github.stkent.amplify.feedback.DefaultEmailFeedbackCollector
import com.github.stkent.amplify.feedback.GooglePlayStoreFeedbackCollector
import com.github.stkent.amplify.tracking.Amplify


class RatingApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        Amplify.initSharedInstance(this)
            .setPositiveFeedbackCollectors(GooglePlayStoreFeedbackCollector())
            .setCriticalFeedbackCollectors(DefaultEmailFeedbackCollector(getString(R.string.feedback_email)))
            .applyAllDefaultRules()
            .setAlwaysShow(true) // TODO tie this to always launch checkmark option
    }
}