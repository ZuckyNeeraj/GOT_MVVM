package com.example.got_app_mvvm.view

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.example.got_app_mvvm.R
import com.example.got_app_mvvm.databinding.ActivityCharacterInfoBinding
import com.example.got_app_mvvm.model.DataItem

class CharacterInfoActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCharacterInfoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        actionBarSetup()
        binding = ActivityCharacterInfoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        displayDataFromMainActivity()
    }

    /**
     * Setting the data that is being received by Main Activity.
     * @return null
     */
    private fun displayDataFromMainActivity() {
        val character = intent.getParcelableExtra<DataItem>("character")

        character?.let {
            binding.firstNameTextViewCharacterInfo.text = it.firstName
            binding.lastNameTextViewCharacterInfo.text = it.lastName
            Glide.with(this)
                .load(character.imageUrl)
                .override(400, 400)
                .placeholder(R.drawable.loading) //placeholder image
                .circleCrop()
                .into(binding.imageViewCharacterInfo)
            binding.familyTextViewCharacterInfo.text = it.family
            binding.titleTextViewCharacterInfo.text= it.title
            binding.fullNameTextViewCharacterInfo.text = it.fullName
        }
    }

    /**
     * This method is to set up the action bar.
     * Hiding the default action bar and displaying the custom action bar that is in layout section
     * @return null
     */
    private fun actionBarSetup() {
        // Hide the default action bar
        supportActionBar?.hide()

        // Set the custom action bar as the action bar for this activity
        supportActionBar?.apply {
            displayOptions = ActionBar.DISPLAY_HOME_AS_UP or ActionBar.DISPLAY_SHOW_TITLE or ActionBar.DISPLAY_SHOW_CUSTOM
            title = "Game of Thrones" // modify this line to set the title
        }

        supportActionBar?.show()
    }

    /**
     * Setting up back functionality.
     */
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
            return true
        }
        return super.onOptionsItemSelected(item)
    }


}
