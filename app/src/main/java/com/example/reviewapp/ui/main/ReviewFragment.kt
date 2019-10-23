package com.example.reviewapp.ui.main

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.example.reviewapp.R
import com.github.stkent.amplify.tracking.Amplify
import kotlinx.android.synthetic.main.review_fragment.*


class ReviewFragment : Fragment() {

    val args: ReviewFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        library_name.text = args.library
        project_link.setOnClickListener {
            activity?.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(args.projectUrl)))
        }
        license.text = args.license

        launch_amplify.setOnClickListener {
            launchAmplify()
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

}
