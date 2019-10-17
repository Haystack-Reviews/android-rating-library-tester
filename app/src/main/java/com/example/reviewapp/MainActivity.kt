package com.example.reviewapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.reviewapp.ui.main.*
import com.stepstone.apprating.listener.RatingDialogListener

class MainActivity : AppCompatActivity(), RatingDialogListener {
    override fun onNegativeButtonClicked() {
        Toast.makeText(applicationContext, "Negative Clicked", Toast.LENGTH_SHORT).show()
    }

    override fun onNeutralButtonClicked() {
        Toast.makeText(applicationContext, "Neutral Clicked", Toast.LENGTH_SHORT).show()
    }

    override fun onPositiveButtonClicked(rate: Int, comment: String) {
        Toast.makeText(applicationContext, "Positive Clicked", Toast.LENGTH_SHORT).show()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, RatingManagerFragment.newInstance())
                .commitNow()
        }
    }



}
