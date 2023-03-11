package com.example.got_app_mvvm.view

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.widget.Button
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
    private lateinit var adapter: DataAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var itemViewModel: ItemViewModel
    private lateinit var searchView: SearchView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        actionBarSetup()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRecyclerView()
        instantiateViewModel()
        search()
        onItemClick()
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





    /**
     * Method to perform on clicking any items of recyclerview.
     * On clicking items of recycler view it will redirect to CharacterInfoActivity.
     * @return null
     */
    private fun onItemClick() {
        adapter.onItemClick ={
            val intent =  Intent(this, CharacterInfoActivity::class.java)
            intent.putExtra("character", it)
            startActivity(intent)
            overridePendingTransition(R.transition.slide_in_right, R.transition.slide_out_left);
        }
    }

    /**
     * Method to perform search based on search box input.
     * Whatever user will enter in the search view, it will send it as parameter
     * to adapter.
     * @param String
     * @return Returns true if text is changed otherwise false
     */
    private fun search() {
        searchView = binding.searchView
        searchView.clearFocus()
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }
            override fun onQueryTextChange(newText: String?): Boolean {
                adapter.filter(newText)
                return true
            }
        })
    }

    /**
     * Creating an object of view model to communicate with adapter class.
     * @return null
     */
    private fun instantiateViewModel() {
        itemViewModel = ViewModelProvider(this)[ItemViewModel::class.java]
        itemViewModel.getItems().observe(this) { items ->
            adapter.setMainData(items as ArrayList<DataItem>?)
        }
    }

    /**
     * setting up the recycler view, layout manager, adding some spacing pixels,
     * connecting recycler view to the adapter
     * @return null
     */
    private fun setupRecyclerView() {
        recyclerView = binding.rv
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = MyLayoutManager(this)
        val spacingInPixels = resources.getDimensionPixelSize(R.dimen.vertical_spacing)
        recyclerView.addItemDecoration(VerticalSpaceItemDecoration(spacingInPixels))
        adapter = DataAdapter(this, binding.searchView)
        recyclerView.adapter = adapter
    }
}

