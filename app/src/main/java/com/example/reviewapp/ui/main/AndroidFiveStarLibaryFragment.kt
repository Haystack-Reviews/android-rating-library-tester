package com.example.reviewapp.ui.main

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import angtrim.com.fivestarslibrary.FiveStarsDialog
import hotchemi.android.rate.AppRate


class AndroidFiveStarLibaryFragment : Fragment() {
    override fun onResume() {
        super.onResume()
        showDialog()
    }

    companion object {
        fun newInstance() = AndroidFiveStarLibaryFragment()
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


}
