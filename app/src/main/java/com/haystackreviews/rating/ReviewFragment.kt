package com.haystackreviews.rating

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import angtrim.com.fivestarslibrary.FiveStarsDialog
import com.haystackreviews.rating.models.AndroidConfig
import com.haystackreviews.rating.models.Library.*
import com.haystackreviews.rating.models.LibraryConfig
import com.github.stkent.amplify.tracking.Amplify
import com.kobakei.ratethisapp.RateThisApp
import com.stepstone.apprating.AppRatingDialog
import hotchemi.android.rate.AppRate
import kotlinx.android.synthetic.main.review_fragment.*


class ReviewFragment : Fragment() {

    val args: ReviewFragmentArgs by navArgs()
    lateinit var navController: NavController


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        library_name.text = args.libraryConfig.library.displayName
        launchLibrary()

        navController = Navigation.findNavController(view)
        stop_and_reset_library.setOnClickListener { resetLibrariesAndNavigateToMainFragment() }
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.review_fragment, container, false)
    }

    private fun resetLibrariesAndNavigateToMainFragment() {
        // Reset amplify library (no public method exposed)
        activity?.getSharedPreferences("AMPLIFY_SHARED_PREFERENCES_NAME", 0)?.edit()?.clear()
            ?.commit()
        // Reset RateThisApp library (no public method exposed)
        activity?.getSharedPreferences("RateThisApp", 0)?.edit()?.clear()?.commit()
        // Reset Apprate library
        context?.let {
            AppRate.with(it).clearSettingsParam()
        }
        // Reset FiveStars library (uses package name :( and no public method exposed)
        context?.let {
            activity?.getSharedPreferences(it.packageName, 0)?.edit()?.clear()?.commit()
        }
        // Material library has no persisted data, nothing to reset

        navController.navigate(
            ReviewFragmentDirections.actionReviewFragmentToMainFragment()
        )
    }

    private fun launchLibrary() {
        val applicationContext = context?.applicationContext
        val fragmentContext = context
        val activity = activity
        if (applicationContext != null && fragmentContext != null && activity != null) {

            val libraryConfig = args.libraryConfig
            val androidConfig =
                AndroidConfig(applicationContext, fragmentContext, activity)

            when (libraryConfig.library) {
                AmplifyLibrary -> launchAmplify()
                RateThisAppLibrary -> launchRateThisApp(libraryConfig, androidConfig)
                RateLibrary -> launchAndroidRate(libraryConfig, androidConfig)
                MaterialAppRatingLibrary -> launchMaterialAppRating(libraryConfig, androidConfig)
                FiveStarsLibrary -> launchAndroidFiveStars(libraryConfig, androidConfig)
            }
        }
    }

    private fun launchAmplify() {
        Amplify.getSharedInstance().promptIfReady(amplify_prompt_view)
    }

    private fun launchRateThisApp(
        config: LibraryConfig,
        androidConfig: AndroidConfig
    ) {
        val rateConfig = RateThisApp.Config(0, 0)
        RateThisApp.init(rateConfig)
        RateThisApp.onCreate(androidConfig.fragmentContext)
        if (config.debugAlwaysLaunch) {
            RateThisApp.showRateDialog(androidConfig.fragmentContext)
        } else {
            RateThisApp.showRateDialogIfNeeded(androidConfig.fragmentContext)
        }
    }

    private fun launchMaterialAppRating(
        config: LibraryConfig,
        androidConfig: AndroidConfig
    ) {
        // Surprisingly the library doesn't provide
        // default text for many items.
        // Leaving hardcoded for now.
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
            .setTitle(config.initialTitle)
            .setDescription(config.initialMessage)
            .setCommentInputEnabled(true)
            .setDefaultComment("Default comment")
            .create(androidConfig.activity)
            .show()
    }

    private fun launchAndroidFiveStars(
        config: LibraryConfig,
        androidConfig: AndroidConfig
    ) {
        val fiveStarsDialog =
            FiveStarsDialog(androidConfig.activity, androidConfig.applicationContext.getString(R.string.feedback_email))
        fiveStarsDialog.setRateText(config.initialMessage)
            .setTitle(config.initialTitle)
            .setForceMode(config.debugAlwaysLaunch)
            .setUpperBound(4)
            .showAfter(0)
    }

    private fun launchAndroidRate(
        config: LibraryConfig,
        androidConfig: AndroidConfig
    ) {
        AppRate.with(androidConfig.fragmentContext)
            .setTitle(config.initialTitle)
            .setMessage(config.initialMessage)
            .setDebug(config.debugAlwaysLaunch)
            .monitor()

        AppRate.showRateDialogIfMeetsConditions(androidConfig.activity)
    }

}
