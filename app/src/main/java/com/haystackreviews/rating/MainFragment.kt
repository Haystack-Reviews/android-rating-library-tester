package com.haystackreviews.rating


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
import com.haystackreviews.rating.R
import com.haystackreviews.rating.models.*
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
                selectLibrary.adapter = ArrayAdapter<Library>(it, android.R.layout.simple_spinner_dropdown_item, Library.values())
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
        updateLibrary(selectedLibrary())
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
        // Not needed for now
    }

    private fun selectedLibrary(): Library {
        val selectedLibrary = selectLibrary.selectedItem
        return if (selectedLibrary is Library) {
            selectedLibrary
        } else {
            Library.AmplifyLibrary
        }
    }

    private fun updateLibrary(library: Library) {
        project_link.text = library.projectUrl
        license.text = library.license
    }
}
