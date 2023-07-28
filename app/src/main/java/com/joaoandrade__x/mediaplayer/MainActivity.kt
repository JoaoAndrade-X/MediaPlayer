package com.joaoandrade__x.mediaplayer

import android.media.MediaPlayer
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.facebook.drawee.backends.pipeline.Fresco
import com.google.android.material.slider.Slider
import com.joaoandrade__x.mediaplayer.databinding.ActivityMainBinding
import java.util.Timer
import java.util.TimerTask

class MainActivity : ComponentActivity(), OnMusicClickListener {

    private lateinit var binding: ActivityMainBinding
    private val musicList: List<Music> = Music.getList()
    private lateinit var mediaPlayer: MediaPlayer
    private lateinit var timer: Timer
    private var cursor: Int = 0
    private lateinit var musicAdapter: MusicAdapter
    private var isDragging: Boolean = false
    private var musicState: MusicState = MusicState.STOPPED

    override fun onCreate(savedInstanceState: Bundle?) {
        Fresco.initialize(this)
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val recyclerView = binding.rvMain
        recyclerView.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        musicAdapter = MusicAdapter(musicList, this)
        recyclerView.adapter = musicAdapter
        onMusicChange(musicList[0])
        binding.playBtn.setOnClickListener {
            when (musicState) {
                MusicState.PLAYING -> {
                    mediaPlayer.pause()
                    musicState = MusicState.PAUSED
                    binding.playBtn.setImageResource(R.drawable.baseline_play_circle_outline_24)
                }

                else -> {
                    mediaPlayer.start()
                    musicState = MusicState.PLAYING
                    binding.playBtn.setImageResource(R.drawable.baseline_pause_circle_outline_24)
                }
            }
        }

        binding.musicSlider.addOnChangeListener(Slider.OnChangeListener { _, value, _ ->
            binding.positionTv.text = Music.covertMillisToString(value.toInt())
        })

        binding.musicSlider.addOnSliderTouchListener(object: Slider.OnSliderTouchListener {
            override fun onStartTrackingTouch(slider: Slider) {
                isDragging = true
            }

            override fun onStopTrackingTouch(slider: Slider) {
                isDragging = false
                mediaPlayer.seekTo(slider.value.toInt())
            }
        })

        binding.nextBtn.setOnClickListener {
            goNext()
        }

        binding.previousBtn.setOnClickListener {
            goPrevious()
        }

    }

    private fun onMusicChange(music: Music) {
        musicAdapter.notifyMusicChange(music)
        binding.musicSlider.value = 0F
        mediaPlayer = MediaPlayer.create(this, music.getMusicFileResIdResId())
        mediaPlayer.setOnPreparedListener(MediaPlayer.OnPreparedListener {
            mediaPlayer.start()
            timer = Timer()

            timer.schedule(object : TimerTask() {
                override fun run() {
                    runOnUiThread {
                        if(!isDragging)
                            binding.positionTv.text = Music.covertMillisToString(mediaPlayer.currentPosition)
                            binding.musicSlider.value = mediaPlayer.currentPosition.toFloat()
                    }
                }
            }, 1000, 1000)

            binding.durationTv.text = Music.covertMillisToString(mediaPlayer.duration)
            binding.musicSlider.valueTo = mediaPlayer.duration.toFloat()
            musicState = MusicState.PLAYING
            binding.playBtn.setImageResource(R.drawable.baseline_pause_circle_outline_24)
            mediaPlayer.setOnCompletionListener { goNext() }
        })

        binding.coverIv.setActualImageResource(music.getCoverResId())
        binding.artistIv.setActualImageResource(music.getArtistResId())
        binding.artistTv.text = music.getArtist()
        binding.musicNameTv.text = music.getName()
    }

    override fun onDestroy() {
        super.onDestroy()
        timer.cancel()
        mediaPlayer.release()
    }

    private fun goNext() {
        timer.cancel()
        timer.purge()
        mediaPlayer.release()
        if (cursor < musicList.size - 1) {
            cursor++
        } else {
            cursor = 0
        }

        onMusicChange(musicList[cursor])
    }

    private fun goPrevious() {
        timer.cancel()
        timer.purge()
        mediaPlayer.release()
        if (cursor > 0) {
            cursor--
        } else {
            cursor = musicList.size -1
        }

        onMusicChange(musicList[cursor])
    }

    override fun onClick(music: Music, position: Int) {
        timer.cancel()
        timer.purge()
        mediaPlayer.release()
        cursor = position

        onMusicChange(musicList[cursor])
    }

}