package com.example.reviewapp.ui.main

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.reviewapp.R
import com.example.reviewapp.models.AmplifyLibrary
import com.github.stkent.amplify.tracking.Amplify
import kotlinx.android.synthetic.main.amplify_fragment.*




class AmplifyFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.amplify_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val library = AmplifyLibrary;

        library_name.text = library.name
        project_link.setOnClickListener {
            activity?.startActivity(Intent(Intent.ACTION_VIEW, library.projectUrl))
        }
        license.text = library.license.name

        launch_amplify.setOnClickListener {
            launchAmplify()
        }
    }

    private fun launchAmplify() {
        Amplify.getSharedInstance().promptIfReady(amplify_prompt_view)
    }


}
