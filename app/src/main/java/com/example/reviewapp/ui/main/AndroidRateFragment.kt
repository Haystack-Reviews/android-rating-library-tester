package com.example.reviewapp.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import hotchemi.android.rate.AppRate


class AndroidRateFragment : Fragment() {
    override fun onResume() {
        super.onResume()
        showDialog()
    }

    companion object {
        fun newInstance() = AndroidRateFragment()
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
