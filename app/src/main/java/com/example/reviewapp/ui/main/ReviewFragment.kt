package com.example.reviewapp.ui.main

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import angtrim.com.fivestarslibrary.FiveStarsDialog
import com.example.reviewapp.R
import com.example.reviewapp.models.*
import com.github.stkent.amplify.tracking.Amplify
import com.kobakei.ratethisapp.RateThisApp
import com.stepstone.apprating.AppRatingDialog
import hotchemi.android.rate.AppRate
import hotchemi.android.rate.OnClickButtonListener
import kotlinx.android.synthetic.main.review_fragment.*


class ReviewFragment : Fragment() {

    val args: ReviewFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        library_name.text = args.reviewLibDataData.name
        project_link.setOnClickListener {
            activity?.startActivity(
                Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse(args.reviewLibDataData.projectUrl)
                )
            )
        }
        license.text = args.reviewLibDataData.license

        val applicationContext = context?.applicationContext
        val activity = activity
        val fragmentContext = context
        if (applicationContext != null && activity != null && fragmentContext != null) {
            val config = ReviewLibraryConfig(
                applicationContext,
                fragmentContext,
                activity,
                true,
                "Rate our app",
                "Let us know what you think of our app"
            )

            launch_library.setOnClickListener {
                when (args.reviewLibDataData) {
                    AmplifyLibraryData -> launchAmplify(config)
                    RateThisAppLibraryData -> launchRateThisApp(config)
                    RateLibraryData -> launchAndroidRate(config)
                    MaterialAppRatingLibraryData -> launchMaterialAppRating(config)
                    FiveStarsLibraryData -> launchAndroidFiveStars(config)
                }
            }
        }


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.review_fragment, container, false)
    }


    private fun launchAmplify(config: ReviewLibraryConfig) {
        Amplify.getSharedInstance().promptIfReady(amplify_prompt_view)
    }

    private fun launchRateThisApp(config: ReviewLibraryConfig) {
        // Monitor launch times and interval from installation
        val rateConfig = RateThisApp.Config(0, 0)
        RateThisApp.init(rateConfig)
        RateThisApp.onCreate(config.fragmentContext)
        if (config.debugAlwaysLaunch) {
            RateThisApp.showRateDialog(config.applicationContext)
        } else {
            RateThisApp.showRateDialogIfNeeded(config.applicationContext)
        }
    }

    private fun launchMaterialAppRating(config: ReviewLibraryConfig) {
        AppRatingDialog.Builder()
            .setPositiveButtonText("Submit")
            .setNegativeButtonText("Cancel")
            .setNeutralButtonText("Later")
            .setNoteDescriptions(
                listOf(
                    "Very Bad",
                    "Not good",
                    "Quite ok",
                    "Very Good",
                    "Excellent !!!"
                )
            )
            .setDefaultRating(2)
            .setTitle(config.initialTitle)
            .setDescription(config.initialMessage)
            .setCommentInputEnabled(true)
            .setDefaultComment("This app is pretty cool !")
//            .setStarColor(R.color.starColor)
//            .setNoteDescriptionTextColor(R.color.noteDescriptionTextColor)
//            .setTitleTextColor(R.color.titleTextColor)
//            .setDescriptionTextColor(R.color.contentTextColor)
//            .setHint("Please write your comment here ...")
//            .setHintTextColor(R.color.hintTextColor)
//            .setCommentTextColor(R.color.commentTextColor)
//            .setCommentBackgroundColor(R.color.colorPrimaryDark)
//            .setWindowAnimation(R.style.MyDialogFadeAnimation)
            .setCancelable(false)
            .setCanceledOnTouchOutside(false)
            .create(config.activity)
            // Why does this crash when using Navigation component?
//                .setTargetFragment(this, 1) // only if listener is implemented by fragment
            .show()
    }

    private fun launchAndroidFiveStars(config: ReviewLibraryConfig) {
        // Future Seb: if you don't see this dialog, check if you pressed the "Never" box
        val fiveStarsDialog =
            FiveStarsDialog(config.activity, "angelo.gallarello@gmail.com")
        fiveStarsDialog.setRateText(config.initialMessage)
            .setTitle(config.initialTitle)
            .setForceMode(config.debugAlwaysLaunch)
            .setUpperBound(2) // Market opened if a rating >= 2 is selected
//                .setNegativeReviewListener() // OVERRIDE mail intent for negative review
//                .setReviewListener() // Used to listen for reviews (if you want to track them )
            .showAfter(0)
    }

    private fun launchAndroidRate(config: ReviewLibraryConfig) {
        AppRate.with(config.fragmentContext)
            .setInstallDays(0) // default 10, 0 means install day.
            .setLaunchTimes(3) // default 10
            .setRemindInterval(2) // default 1
            .setShowLaterButton(true) // default true
            .setDebug(config.debugAlwaysLaunch) // default false
            .setOnClickButtonListener(object : OnClickButtonListener { // callback listener.
                override fun onClickButton(which: Int) {
                    Log.d("SEBLOG", "launchAndroidRate ${which}")
                }
            })
            .monitor()

        // Show a dialog if meets conditions
        AppRate.showRateDialogIfMeetsConditions(config.activity)
    }

}
