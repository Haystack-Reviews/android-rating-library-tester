package com.example.reviewapp.ui.main


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.navigation.Navigation

import com.example.reviewapp.R
import com.example.reviewapp.models.*
import kotlinx.android.synthetic.main.fragment_main.*


/**
 * A simple [Fragment] subclass.
 * Use the [MainFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class MainFragment : Fragment(), View.OnClickListener {

    lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)

        amplify_button.setOnClickListener(this)
        five_star_button.setOnClickListener(this)
        material_button.setOnClickListener(this)
        rate_button.setOnClickListener(this)
        rate_this_app_button.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v) {
            amplify_button -> navController.navigate(
                MainFragmentDirections.actionMainFragmentToReviewFragment(
                    AmplifyLibrary
                )
            )
            five_star_button -> navController.navigate(
                MainFragmentDirections.actionMainFragmentToReviewFragment(
                    FiveStarsLibrary
                )
            )
            material_button -> navController.navigate(
                MainFragmentDirections.actionMainFragmentToReviewFragment(
                    MaterialAppRatingLibrary
                )
            )
            rate_button -> navController.navigate(
                MainFragmentDirections.actionMainFragmentToReviewFragment(
                    RateLibrary
                )
            )
            rate_this_app_button -> navController.navigate(
                MainFragmentDirections.actionMainFragmentToReviewFragment(
                    RateThisAppLibrary
                )
            )
        }
    }


    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment MainFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance() =
            MainFragment().apply {
            }
    }
}
