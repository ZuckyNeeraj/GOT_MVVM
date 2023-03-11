package com.example.got_app_mvvm.view

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.got_app_mvvm.R
import com.example.got_app_mvvm.databinding.ItemDisplayBinding
import com.example.got_app_mvvm.model.DataItem
import jp.wasabeef.glide.transformations.ColorFilterTransformation
import java.util.*
import kotlin.collections.ArrayList

class DataAdapter(private val context: Context, private val searchView: androidx.appcompat.widget.SearchView) : RecyclerView.Adapter<DataAdapter.MyViewHolder>() {

    //data that we are receiving
    private var characters: ArrayList<DataItem>? = null
    //filtered data used for search purpose
    private var filteredCharacters: ArrayList<DataItem>? = null

    var onItemClick : ((DataItem) -> Unit)? = null


    //view holder class
    inner class MyViewHolder(val binding: ItemDisplayBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemDisplayBinding.inflate(layoutInflater, parent, false)
        return MyViewHolder(binding)
    }


    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.binding.apply {
            characterFirstName.text = filteredCharacters?.get(position)?.firstName
            characterFullName.text = filteredCharacters?.get(position)?.fullName
            characterTitle.text = filteredCharacters?.get(position)?.title
            Glide.with(context) // use the context of the parent view
                .load(filteredCharacters?.get(position)?.imageUrl)
                .placeholder(R.drawable.loading)
                .circleCrop()
                .into(imageCharacter)
        }
        holder.itemView.setOnClickListener{
            filteredCharacters?.get(position)?.let { it1 -> onItemClick?.invoke(it1) }
        }

        holder.itemView.startAnimation(
            AnimationUtils.loadAnimation(holder.itemView.context, R.anim.anim_1))
    }

    override fun getItemCount(): Int {
        return filteredCharacters?.size ?: 0
    }


    @SuppressLint("NotifyDataSetChanged")
    fun setData(characters: ArrayList<DataItem>?) {
        this.filteredCharacters = characters
        notifyDataSetChanged()
    }

    fun setMainData(characters: ArrayList<DataItem>?){
        this.characters = characters
        setData(characters)
    }

    /**
     * Filter for searching the characters
     * @param String name of character is being passed.
     * If string is matched it will update it in filtered list.
     * Otherwise data will be in original characters
     * @return null
     */
    @SuppressLint("NotifyDataSetChanged")
    fun filter(query: String?) {
        filteredCharacters = ArrayList<DataItem>()
        Log.d("message", query.toString())

        if (query.isNullOrBlank()){
            setData(characters)
        }else{
            for (character in characters!!){
                if(query?.lowercase(Locale.ROOT)?.let { character.firstName?.lowercase(Locale.ROOT)?.contains(it) } == true){
                    filteredCharacters!!.add(character)
                }
            }
            if(filteredCharacters!!.isEmpty()){
                Toast.makeText(context, "No data found", Toast.LENGTH_LONG).show()
            }else{
                setData(filteredCharacters)
            }
        }

    }


}