package com.example.got_app_mvvm.view

import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.example.got_app_mvvm.R
import com.example.got_app_mvvm.ViewModel.ItemViewModel
import com.example.got_app_mvvm.databinding.FragmentDataDisplayBinding
import com.example.got_app_mvvm.model.DataItem


class DataDisplayFragment : Fragment() {

    private lateinit var adapter: DataAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var itemViewModel: ItemViewModel
    private lateinit var searchView: SearchView
    private lateinit var actionBar: ActionBar

    private var _binding: FragmentDataDisplayBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment using View Binding
        _binding = FragmentDataDisplayBinding.inflate(inflater, container, false)
        setupRecyclerView()
        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        menu.clear() // remove all items from the menu
    }
    override fun onResume() {
        super.onResume()
        actionBar = (requireActivity() as AppCompatActivity).supportActionBar!! // Get action bar
        setHasOptionsMenu(true) // Enable options menu
        actionBar.setDisplayHomeAsUpEnabled(false) // Enable back button
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        instantiateViewModel()
        search()
        onItemClick()
    }

    /**
     * Method to perform on clicking any items of recyclerview.
     * On clicking items of recycler view it will redirect to CharacterInfoActivity.
     * @return null
     */
    private fun onItemClick() {
        adapter.onItemClick = { character ->
            val bundle = Bundle()
            bundle.putSerializable("character", character)
            val fragment = CharacterInfoFragment()
            fragment.arguments = bundle
            val transaction = requireActivity().supportFragmentManager.beginTransaction()
            transaction.replace(R.id.frameFl, fragment)
            transaction.addToBackStack(null)
            transaction.commit()
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
        itemViewModel = ViewModelProvider(requireActivity())[ItemViewModel::class.java]
        itemViewModel.getItems().observe(viewLifecycleOwner) { items ->
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
        recyclerView.layoutManager = MyLayoutManager(requireContext())
        val spacingInPixels = resources.getDimensionPixelSize(R.dimen.vertical_spacing)
        recyclerView.addItemDecoration(VerticalSpaceItemDecoration(spacingInPixels))
        adapter = DataAdapter(requireContext(), binding.root.findViewById(R.id.searchView))
        recyclerView.adapter = adapter
    }


    override fun onDestroyView() {
        super.onDestroyView()
        // Clean up the binding instance to avoid memory leaks
        _binding = null
    }
}
