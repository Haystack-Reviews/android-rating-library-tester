package com.example.reviewapp.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.example.reviewapp.R
import com.stepstone.apprating.AppRatingDialog


class MaterialRatingFragment : Fragment() {
    override fun onResume() {
        super.onResume()
        showDialog()
    }

    companion object {
        fun newInstance() = MaterialRatingFragment()
    }

    private lateinit var viewModel: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.main_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
        // TODO: Use the ViewModel
    }

    private fun showDialog() {
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


}
