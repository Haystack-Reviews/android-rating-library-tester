package com.example.reviewapp.ui.main

import android.content.Intent
import android.net.Uri
import android.os.Bundle
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
import kotlinx.android.synthetic.main.review_fragment.*


class ReviewFragment : Fragment() {

    val args: ReviewFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        library_name.text = args.reviewLibDataData.name
        project_link.setOnClickListener {
            activity?.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(args.reviewLibDataData.projectUrl)))
        }
        license.text = args.reviewLibDataData.license

        launch_library.setOnClickListener {
            when(args.reviewLibDataData) {
                AmplifyLibrary -> launchAmplify()
                RateThisAppLibrary -> launchRateThisApp()
                RateLibrary -> launchAndroidRate()
                MaterialAppRatingLibrary -> launchMaterialAppRating()
                FiveStarsLibrary -> launchAndroidFiveStars()
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.review_fragment, container, false)
    }


    private fun launchAmplify() {
        Amplify.getSharedInstance().promptIfReady(amplify_prompt_view)
    }

    private fun launchRateThisApp() {
        context?.let {
            // Monitor launch times and interval from installation
            val config = RateThisApp.Config(0, 0)
            RateThisApp.init(config)
            RateThisApp.onCreate(it)
            // If the condition is satisfied, "Rate this app" dialog will be shown
            RateThisApp.showRateDialogIfNeeded(it)
        }
    }

    private fun launchMaterialAppRating() {
        activity?.let {
            AppRatingDialog.Builder()
                .setPositiveButtonText("Submit")
                .setNegativeButtonText("Cancel")
                .setNeutralButtonText("Later")
//            .setNoteDescriptions(
//                Arrays.asList(
//                    "Very Bad",
//                    "Not good",
//                    "Quite ok",
//                    "Very Good",
//                    "Excellent !!!"
//                )
//            )
                .setDefaultRating(2)
                .setTitle("Rate this application")
                .setDescription("Please select some stars and give your feedback")
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
                .create(it)
                // Why does this crash when using Navigation component?
//                .setTargetFragment(this, 1) // only if listener is implemented by fragment
                .show()
        }
    }

    private fun launchAndroidFiveStars() {
        activity?.let { activity ->
            // Future Seb: if you don't see this dialog, check if you pressed the "Never" box
            val fiveStarsDialog = FiveStarsDialog(activity, "angelo.gallarello@gmail.com")
            fiveStarsDialog.setRateText("Your custom text")
                .setTitle("Your custom title")
                .setForceMode(false)
                .setUpperBound(2) // Market opened if a rating >= 2 is selected
//                .setNegativeReviewListener() // OVERRIDE mail intent for negative review
//                .setReviewListener() // Used to listen for reviews (if you want to track them )
                .showAfter(0)
        }
    }

    private fun launchAndroidRate() {
        context?.applicationContext.let { appContext ->
            AppRate.with(appContext)
                .setInstallDays(0) // default 10, 0 means install day.
                .setLaunchTimes(3) // default 10
                .setRemindInterval(2) // default 1
                .setShowLaterButton(true) // default true
                .setDebug(true) // default false
//                .setOnClickButtonListener(object : OnClickButtonListener() { // callback listener.
//                    fun onClickButton(which: Int) {
//                        Log.d(MainActivity::class.java.name, Integer.toString(which))
//                    }
//                })
                .monitor()

            // Show a dialog if meets conditions
            activity?.let { activity ->
                AppRate.showRateDialogIfMeetsConditions(activity)
            }
        }
    }

}
