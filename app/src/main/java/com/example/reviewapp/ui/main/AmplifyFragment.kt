package com.example.reviewapp.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.example.reviewapp.R
import com.github.stkent.amplify.tracking.Amplify
import kotlinx.android.synthetic.main.amplify_fragment.*


class AmplifyFragment : Fragment() {
    override fun onResume() {
        super.onResume()
        showDialog()
    }

    companion object {
        fun newInstance() = AmplifyFragment()
    }

    private lateinit var viewModel: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.amplify_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
        // TODO: Use the ViewModel
    }

    private fun showDialog() {
        Amplify.getSharedInstance().promptIfReady(prompt_view)
    }


}
