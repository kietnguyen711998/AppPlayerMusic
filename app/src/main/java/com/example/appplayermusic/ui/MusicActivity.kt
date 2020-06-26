package com.example.appplayermusic.ui

import android.annotation.SuppressLint
import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.appplayermusic.utils.OnSwipeTouchListener
import com.example.appplayermusic.R
import kotlinx.android.synthetic.main.activity_music.*
import java.util.concurrent.TimeUnit

class MusicActivity : AppCompatActivity(), Runnable {
    lateinit var mediaPlayer: MediaPlayer
    var forwardTime: Int = 5000
    var backwardTime: Int = 5000
    var songImage: Int = 100
    var songTitle: String? = "hello"
    var song: Int = 200
    var startTime: Int = 0
    var finalTime: Int = 0
    var handler = Handler()
    var oneTimeOnly: Int = 0

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_music)
        if (intent != null) {
            songImage = intent.getIntExtra("songImage", 0)
            songTitle = intent.getStringExtra("songTitle")
            song = intent.getIntExtra("song", 0)

        } else {
            finish()
            Toast.makeText(
                this@MusicActivity,
                "Some unexpected error occurred",
                Toast.LENGTH_LONG
            ).show()
        }
        if (song == 200 || songImage == 100 || songTitle == "hello") {
            finish()
            Toast.makeText(
                this@MusicActivity,
                "Some unexpected error occurred",
                Toast.LENGTH_LONG
            ).show()

        }
        txtSongName.text = songTitle
        imgSongImage.setImageResource(songImage)

        mediaPlayer = MediaPlayer.create(this, song)
        seekBar.isClickable = false
        mediaPlayer.start()
        finalTime = mediaPlayer.duration
        startTime = mediaPlayer.currentPosition
        if (oneTimeOnly == 0) {
            seekBar.max = finalTime
            oneTimeOnly = 1
        }
        txtTimeStop.text =
            "${TimeUnit.MILLISECONDS.toMinutes(finalTime.toLong())}:${TimeUnit.MILLISECONDS.toSeconds(
                finalTime.toLong()
            ) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(finalTime.toLong()))}"

        txtTimeStart.text =
            "${TimeUnit.MILLISECONDS.toMinutes(startTime.toLong())}:${TimeUnit.MILLISECONDS.toSeconds(
                startTime.toLong()
            ) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(startTime.toLong()))}"

        seekBar.progress = startTime
        handler.postDelayed(updateSongTime, 100)

        imgPlay.setOnClickListener {
            mediaPlayer.start()
            finalTime = mediaPlayer.duration
            startTime = mediaPlayer.currentPosition
            if (oneTimeOnly == 0) {
                seekBar.max = finalTime
                oneTimeOnly = 1
            }
            txtTimeStop.text =
                "${TimeUnit.MILLISECONDS.toMinutes(finalTime.toLong())}:${TimeUnit.MILLISECONDS.toSeconds(
                    finalTime.toLong()
                ) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(finalTime.toLong()))}"

            txtTimeStart.text =
                "${TimeUnit.MILLISECONDS.toMinutes(startTime.toLong())}:${TimeUnit.MILLISECONDS.toSeconds(
                    startTime.toLong()
                ) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(startTime.toLong()))}"

            seekBar.progress = startTime
            handler.postDelayed(updateSongTime, 100)
            imgPlay.visibility = View.GONE
            imgStop.visibility = View.VISIBLE
        }
        imgStop.setOnClickListener {
            mediaPlayer.pause()
            imgStop.visibility = View.GONE
            imgPlay.visibility = View.VISIBLE
        }
        imgFastForward.setOnClickListener {
            val temp = startTime
            if ((temp + forwardTime) <= finalTime) {
                startTime += forwardTime
                mediaPlayer.seekTo(startTime)

            }

        }
        imgFastRewind.setOnClickListener {
            val temp = startTime
            if ((temp - backwardTime) > 0) {
                startTime -= backwardTime
                mediaPlayer.seekTo(startTime)

            }
        }
        relativeLayout.setOnTouchListener(object : OnSwipeTouchListener(this@MusicActivity) {
            override fun onSwipeRight() {
                super.onSwipeRight()
                onBackPressed()
            }
        })
    }

    val updateSongTime = Runnable {
        run()
    }

    override fun run() {
        startTime = mediaPlayer.currentPosition
        txtTimeStart.text =
            "${TimeUnit.MILLISECONDS.toMinutes(startTime.toLong())}:${TimeUnit.MILLISECONDS.toSeconds(
                startTime.toLong()
            ) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(startTime.toLong()))}"
        seekBar.progress = startTime
        handler.postDelayed(this@MusicActivity, 100)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        mediaPlayer.stop()
        finish()
    }
}