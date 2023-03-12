package com.example.got_app_mvvm.view

import android.os.Bundle
import android.transition.TransitionInflater
import android.view.*
import android.view.animation.AnimationUtils
import androidx.fragment.app.Fragment
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.got_app_mvvm.R
import com.example.got_app_mvvm.databinding.FragmentCharacterInfoBinding
import com.example.got_app_mvvm.model.DataItem

class CharacterInfoFragment : Fragment() {
    private var _binding: FragmentCharacterInfoBinding? = null
    private val binding get() = _binding!!
    private lateinit var actionBar: ActionBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true) // Enable options menu
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                requireActivity().supportFragmentManager.popBackStack() // Navigate back to previous fragment
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onResume() {
        super.onResume()
        actionBar = (requireActivity() as AppCompatActivity).supportActionBar!! // Get action bar
        setHasOptionsMenu(true) // Enable options menu
        actionBar.setDisplayHomeAsUpEnabled(true) // Enable back button
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment using View Binding
        _binding = FragmentCharacterInfoBinding.inflate(inflater, container, false)
        // Apply fade-in animation to the fragment's root view
        val fadeInAnim = AnimationUtils.loadAnimation(requireContext(), R.anim.fade_in)
        binding.root.startAnimation(fadeInAnim)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        displayDataFromDataFragment()
    }

    /**
     * Setting the data that is being received by Main Activity.
     * @return null
     */
    private fun displayDataFromDataFragment() {
        val character = arguments?.getSerializable("character") as DataItem

        binding.firstNameTextViewCharacterInfo.text = character.firstName
        binding.lastNameTextViewCharacterInfo.text = character.lastName
        Glide.with(this)
            .load(character.imageUrl)
            .override(400, 400)
            .placeholder(R.drawable.loading) //placeholder image
            .circleCrop()
            .into(binding.imageViewCharacterInfo)
        binding.familyTextViewCharacterInfo.text = character.family
        binding.titleTextViewCharacterInfo.text= character.title
        binding.fullNameTextViewCharacterInfo.text = character.fullName
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

