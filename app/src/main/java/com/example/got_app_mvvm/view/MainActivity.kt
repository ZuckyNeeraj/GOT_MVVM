package com.example.got_app_mvvm.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.widget.TextView
import androidx.appcompat.app.ActionBar
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.example.got_app_mvvm.R
import com.example.got_app_mvvm.ViewModel.ItemViewModel
import com.example.got_app_mvvm.databinding.ActivityMainBinding
import com.example.got_app_mvvm.model.DataItem

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        actionBarSetup()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val fragment = DataDisplayFragment()

        supportFragmentManager.beginTransaction()
            .replace(binding.frameFl.id, fragment)
            .commit()
    }

    /**
     * This method is to set up the action bar.
     * Hiding the default action bar and displaying the custom action bar that is in layout section
     * @return null
     */
    private fun actionBarSetup() {
        supportActionBar?.hide()

        supportActionBar?.apply {
            displayOptions = ActionBar.DISPLAY_SHOW_TITLE or ActionBar.DISPLAY_SHOW_CUSTOM
            setCustomView(R.layout.custom_action_bar) // set your custom layout here
        }

        val customTextView = supportActionBar?.customView?.findViewById<TextView>(R.id.action_bar_title)
        customTextView?.apply {
            text = "Game of Thrones" // modify this line to set the title
            gravity = Gravity.CENTER // center the text horizontally

        }

        supportActionBar?.show()
    }
}

