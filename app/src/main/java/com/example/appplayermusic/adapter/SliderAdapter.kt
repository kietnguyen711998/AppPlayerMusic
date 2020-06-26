package com.example.appplayermusic.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.example.appplayermusic.R
import com.example.appplayermusic.model.SongItem
import com.example.appplayermusic.ui.MusicActivity
import com.makeramen.roundedimageview.RoundedImageView

class SliderAdapter(
    var context: Context,
    var songItems: ArrayList<SongItem>,
    var viewPager2: ViewPager2
) : RecyclerView.Adapter<SliderAdapter.SliderViewHolder>() {


    class SliderViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imageSlide: RoundedImageView = view.findViewById(R.id.imageSlide)
        fun setImage(songItem: SongItem) {
            imageSlide.setImageResource(songItem.image)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SliderViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.slide_item_container, parent, false)
        return SliderViewHolder(
            view
        )
    }

    override fun getItemCount(): Int {
        return songItems.size
    }

    override fun onBindViewHolder(holder: SliderViewHolder, position: Int) {
        val songObject = songItems[position]
        holder.setImage(songItems[position])
        if (position == songItems.size - 2) {
            viewPager2.post(runnable)
        }
        holder.imageSlide.setOnClickListener {
            val intent = Intent(context, MusicActivity::class.java)
            intent.putExtra("songImage", songObject.image)
            intent.putExtra("songTitle", songObject.title)
            intent.putExtra("song", songObject.song)
            context.startActivity(intent)
        }
    }

    val runnable = Runnable {
        songItems.addAll(songItems)
        notifyDataSetChanged()
    }
}