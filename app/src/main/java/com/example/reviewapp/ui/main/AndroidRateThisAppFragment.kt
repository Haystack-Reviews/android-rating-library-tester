package com.example.reviewapp.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.kobakei.ratethisapp.RateThisApp


class AndroidRateThisAppFragment : Fragment() {
    override fun onResume() {
        super.onResume()
        showDialog()
    }

    companion object {
        fun newInstance() = AndroidRateThisAppFragment()
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
        context?.let {
            // Monitor launch times and interval from installation
            val config = RateThisApp.Config(0, 0)
            RateThisApp.init(config)
            RateThisApp.onCreate(it)
            // If the condition is satisfied, "Rate this app" dialog will be shown
            RateThisApp.showRateDialogIfNeeded(it)
        }
    }


}
