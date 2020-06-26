package com.example.appplayermusic.ui

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import com.example.appplayermusic.R
import com.example.appplayermusic.adapter.SliderAdapter
import com.example.appplayermusic.model.SongItem
import com.example.appplayermusic.adapter.SongRecyclerAdapter
import com.smarteist.autoimageslider.DefaultSliderView
import com.smarteist.autoimageslider.IndicatorAnimations
import kotlinx.android.synthetic.main.activity_home.*
import kotlin.math.abs

class HomeActivity : AppCompatActivity() {
    lateinit var layoutManager: RecyclerView.LayoutManager
    lateinit var layoutManager2: RecyclerView.LayoutManager
    lateinit var recyclerAdapter: SongRecyclerAdapter

    val sliderList = arrayListOf<SongItem>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        layoutManager2 = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        imageSlider.setIndicatorAnimation(IndicatorAnimations.FILL)
        imageSlider.scrollTimeInSec = 2
        setSliderViews()

        sliderList.add(
            SongItem(
                R.drawable.intentions,
                "Intentions",
                R.raw.intentions
            )
        )
        sliderList.add(
            SongItem(
                R.drawable.congratulations,
                "Congratulation",
                R.raw.congratulations
            )
        )
        sliderList.add(
            SongItem(
                R.drawable.psycho,
                "Psycho",
                R.raw.psycho
            )
        )
        sliderList.add(
            SongItem(
                R.drawable.seeyouagain,
                "See You Again",
                R.raw.seeyouagain
            )
        )
        sliderList.add(
            SongItem(
                R.drawable.youngwildfree,
                "Young, Wild & Free",
                R.raw.youngwildandfree
            )
        )

        recyclerAdapter = SongRecyclerAdapter(
            this@HomeActivity,
            sliderList
        )
        recyclerViewOne.layoutManager = layoutManager
        recyclerViewOne.adapter = recyclerAdapter

        recyclerViewTwo.layoutManager = layoutManager2
        recyclerViewTwo.adapter = recyclerAdapter

        viewPagerImageSlider.adapter =
            SliderAdapter(
                this@HomeActivity,
                sliderList,
                viewPagerImageSlider
            )
        viewPagerImageSlider.clipToPadding = false
        viewPagerImageSlider.clipChildren = false
        viewPagerImageSlider.offscreenPageLimit = 3
        viewPagerImageSlider.getChildAt(0).overScrollMode = RecyclerView.OVER_SCROLL_NEVER
        val compositePageTransformer = CompositePageTransformer()
        compositePageTransformer.addTransformer(MarginPageTransformer(40))
        compositePageTransformer.addTransformer { view: View, fl: Float ->
            val r = 1 - abs(fl)
            view.scaleY = 0.85f + r * 0.15f

        }
        viewPagerImageSlider.setPageTransformer(compositePageTransformer)
    }

    private fun setSliderViews() {
        for (i in 0 until 5) {
            val sliderView = DefaultSliderView(this@HomeActivity)
            when (i) {
                0 -> {
                    sliderView.setImageDrawable(R.drawable.intentions)
                }
                1 -> {
                    sliderView.setImageDrawable(R.drawable.congratulations)
                }
                2 -> {
                    sliderView.setImageDrawable(R.drawable.psycho)
                }
                3 -> {
                    sliderView.setImageDrawable(R.drawable.seeyouagain)
                }
                4 -> {
                    sliderView.setImageDrawable(R.drawable.youngwildfree)
                }

            }
            sliderView.setImageScaleType(ImageView.ScaleType.CENTER_CROP)
            imageSlider.addSliderView(sliderView)
        }
    }
}