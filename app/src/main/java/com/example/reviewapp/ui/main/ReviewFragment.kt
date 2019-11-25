package com.example.reviewapp.ui.main

import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import angtrim.com.fivestarslibrary.FiveStarsDialog
import com.example.reviewapp.R
import com.example.reviewapp.models.AndroidSpecificConfig
import com.example.reviewapp.models.ReviewLibrary.*
import com.example.reviewapp.models.ReviewLibraryConfig
import com.github.stkent.amplify.tracking.Amplify
import com.kobakei.ratethisapp.RateThisApp
import com.stepstone.apprating.AppRatingDialog
import hotchemi.android.rate.AppRate
import hotchemi.android.rate.OnClickButtonListener
import kotlinx.android.synthetic.main.review_fragment.*


class ReviewFragment : Fragment() {

    val args: ReviewFragmentArgs by navArgs()
    lateinit var navController: NavController


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navController = Navigation.findNavController(view)


        library_name.text = args.reviewLibConfig.library.name

        val config = args.reviewLibConfig

        val applicationContext = context?.applicationContext
        val fragmentContext = context
        val activity = activity
        if (applicationContext != null && fragmentContext != null && activity != null) {

            val androidSpecificConfig =
                AndroidSpecificConfig(applicationContext, fragmentContext, activity)

            when (args.reviewLibConfig.library) {
                AmplifyLibrary -> launchAmplify(config, androidSpecificConfig)
                RateThisAppLibrary -> launchRateThisApp(config, androidSpecificConfig)
                RateLibrary -> launchAndroidRate(config, androidSpecificConfig)
                MaterialAppRatingLibrary -> launchMaterialAppRating(config, androidSpecificConfig)
                FiveStarsLibrary -> launchAndroidFiveStars(config, androidSpecificConfig)
            }
        }

        stop_and_reset_library.setOnClickListener {

            activity?.getSharedPreferences("AMPLIFY_SHARED_PREFERENCES_NAME", 0)?.edit()?.clear()
                ?.commit()
            activity?.getSharedPreferences("RateThisApp", 0)?.edit()?.clear()?.commit()
            context?.let {
                AppRate.with(it).clearSettingsParam()
            }
            // Material library has no persisted data
            // FiveStarsLibary uses package name :(
            context?.let {
                activity?.getSharedPreferences(it.packageName, 0)?.edit()?.clear()?.commit()
            }


            navController.navigate(
                ReviewFragmentDirections.actionReviewFragmentToMainFragment()
            )
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.review_fragment, container, false)
    }


    private fun launchAmplify(
        config: ReviewLibraryConfig,
        androidSpecificConfig: AndroidSpecificConfig
    ) {
        Amplify.getSharedInstance().promptIfReady(amplify_prompt_view)
    }

    private fun launchRateThisApp(
        config: ReviewLibraryConfig,
        androidSpecificConfig: AndroidSpecificConfig
    ) {
        // Monitor launch times and interval from installation
        val rateConfig = RateThisApp.Config(0, 0)
        RateThisApp.init(rateConfig)
        RateThisApp.onCreate(androidSpecificConfig.fragmentContext)
        if (config.debugAlwaysLaunch) {
            RateThisApp.showRateDialog(androidSpecificConfig.fragmentContext)
        } else {
            RateThisApp.showRateDialogIfNeeded(androidSpecificConfig.fragmentContext)
        }
    }

    private fun launchMaterialAppRating(
        config: ReviewLibraryConfig,
        androidSpecificConfig: AndroidSpecificConfig
    ) {
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
            .create(androidSpecificConfig.activity)
            // Why does this crash when using Navigation component?
//                .setTargetFragment(this, 1) // only if listener is implemented by fragment
            .show()
    }

    private fun launchAndroidFiveStars(
        config: ReviewLibraryConfig,
        androidSpecificConfig: AndroidSpecificConfig
    ) {
        // Future Seb: if you don't see this dialog, check if you pressed the "Never" box
        val fiveStarsDialog =
            FiveStarsDialog(androidSpecificConfig.activity, "angelo.gallarello@gmail.com")
        fiveStarsDialog.setRateText(config.initialMessage)
            .setTitle(config.initialTitle)
            .setForceMode(config.debugAlwaysLaunch)
            .setUpperBound(2) // Market opened if a rating >= 2 is selected
//                .setNegativeReviewListener() // OVERRIDE mail intent for negative review
//                .setReviewListener() // Used to listen for reviews (if you want to track them )
            .showAfter(0)
    }

    private fun launchAndroidRate(
        config: ReviewLibraryConfig,
        androidSpecificConfig: AndroidSpecificConfig
    ) {
        AppRate.with(androidSpecificConfig.fragmentContext)
            .setInstallDays(0) // default 10, 0 means install day.
            .setLaunchTimes(3) // default 10
            .setRemindInterval(2) // default 1
            .setShowLaterButton(true) // default true
            .setTitle(config.initialTitle)
            .setMessage(config.initialMessage)
            .setDebug(config.debugAlwaysLaunch) // default false
            .setOnClickButtonListener(object : OnClickButtonListener { // callback listener.
                override fun onClickButton(which: Int) {
                    Log.d("SEBLOG", "launchAndroidRate ${which}")
                }
            })
            .monitor()

        // Show a dialog if meets conditions
        AppRate.showRateDialogIfMeetsConditions(androidSpecificConfig.activity)
    }

}
