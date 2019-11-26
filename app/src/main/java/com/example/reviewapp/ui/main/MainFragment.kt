package com.example.reviewapp.ui.main


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.reviewapp.R
import com.example.reviewapp.models.*
import kotlinx.android.synthetic.main.main_fragment.*

class MainFragment : Fragment(), AdapterView.OnItemSelectedListener {

    lateinit var navController: NavController
    lateinit var selectLibrary: Spinner

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.main_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)

        setupLibrarySpinner()
        launch_button.setOnClickListener { navigateToReviewFragment() }
    }

    private fun setupLibrarySpinner() {
        selectLibrary = library_select_spinner
        selectLibrary.onItemSelectedListener = this
        context?.let {
            ArrayAdapter.createFromResource(
                it,
                R.array.select_library,
                android.R.layout.simple_spinner_item
            )
                .also { adapter ->
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                    selectLibrary.adapter = adapter
                }
        }
    }

    private fun navigateToReviewFragment() {
        val config = LibraryConfig(
            selectedLibrary(),
            library_debug_mode.isChecked,
            initial_title.text.toString(),
            initial_message.text.toString()
        )

        navController.navigate(
            MainFragmentDirections.actionMainFragmentToReviewFragment(config)
        )
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        val metadata = when (selectedLibrary()) {
            Library.RateLibrary -> RateLibraryData
            Library.AmplifyLibrary -> AmplifyLibraryData
            Library.RateThisAppLibrary -> RateThisAppLibraryData
            Library.MaterialAppRatingLibrary -> MaterialAppRatingLibraryData
            Library.FiveStarsLibrary -> FiveStarsLibraryData
        }
        updateLibrary(metadata)
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
        // Not needed for now
    }

    private fun selectedLibrary(): Library {
        val selectedString = selectLibrary.selectedItem
        return if (selectedString is String) {
            libraryStringToModel(selectedString)
        } else {
            Library.AmplifyLibrary
        }
    }

    private fun libraryStringToModel(libraryString: String): Library {
        return when (libraryString) {
            getString(R.string.amplify_library) -> Library.AmplifyLibrary
            getString(R.string.five_stars_library) -> Library.FiveStarsLibrary
            getString(R.string.material_app_rating_library) -> Library.MaterialAppRatingLibrary
            getString(R.string.rate_library) -> Library.RateLibrary
            getString(R.string.rate_this_app_library) -> Library.RateThisAppLibrary
            else -> Library.AmplifyLibrary
        }
    }

    private fun updateLibrary(metadata: Metadata) {
        project_link.text = metadata.projectUrl
        license.text = metadata.license
    }
}
