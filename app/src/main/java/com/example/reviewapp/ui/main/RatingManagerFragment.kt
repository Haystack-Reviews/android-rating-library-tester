package com.example.reviewapp.ui.main

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import angtrim.com.fivestarslibrary.FiveStarsDialog
import com.robj.ratingmanager.RatingManager
import hotchemi.android.rate.AppRate


class RatingManagerFragment : Fragment() {
    override fun onResume() {
        super.onResume()
        showDialog()
    }

    companion object {
        fun newInstance() = RatingManagerFragment()
    }

    private lateinit var viewModel: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(com.example.reviewapp.R.layout.main_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
        // TODO: Use the ViewModel
    }

    private fun showDialog() {
        activity?.let { activity ->
            RatingManager.Builder(context)
                .setMinDaysSinceInstall(0)
                .setMinDaysSinceFeedback(0)
                .setMinDaysSinceAskLater(0)
                .showFeedbackOption(true, "fake@email.com")
                .build()
                .shouldShowRatingDialog()
        }
    }


}
