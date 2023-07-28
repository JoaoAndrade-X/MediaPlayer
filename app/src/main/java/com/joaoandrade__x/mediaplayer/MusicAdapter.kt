package com.joaoandrade__x.mediaplayer

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.airbnb.lottie.LottieAnimationView
import com.facebook.drawee.view.SimpleDraweeView

class MusicAdapter() : RecyclerView.Adapter<MusicAdapter.MusicViewHolder>() {

    private lateinit var musicList: List<Music>
    private lateinit var musicClickListener: OnMusicClickListener

    companion object {
        var playingMusicPos: Int = -1
    }

    constructor(musicList: List<Music>, musicClickListener: OnMusicClickListener) : this() {
        this.musicList = musicList
        this.musicClickListener = musicClickListener
    }

    inner class MusicViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        private val simpleDraweeView: SimpleDraweeView = itemView.findViewById(R.id.iv_music)
        private val artistTv: TextView = itemView.findViewById(R.id.tv_music_artist)
        private val musicNameTv: TextView = itemView.findViewById(R.id.tv_music_name)
        private val animationView: LottieAnimationView = itemView.findViewById(R.id.animationView)

        fun bindMusic(music: Music) {
            simpleDraweeView.setActualImageResource(music.getCoverResId())
            artistTv.text = music.getArtist()
            musicNameTv.text = music.getName()
            if(adapterPosition == playingMusicPos) {
                animationView.visibility = View.VISIBLE
            } else {
                animationView.visibility = View.GONE
            }
            itemView.setOnClickListener {
                 musicClickListener.onClick(music, adapterPosition)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MusicViewHolder {
        return MusicViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_music, parent, false))
    }

    override fun getItemCount(): Int {
        return musicList.size
    }

    override fun onBindViewHolder(holder: MusicViewHolder, position: Int) {
        holder.bindMusic(musicList[position])
    }

    fun notifyMusicChange(music: Music) {
        val index = musicList.indexOf(music)
        if (index != -1) {
            if(index != playingMusicPos) {
                notifyItemChanged(playingMusicPos)
                playingMusicPos = index
                notifyItemChanged(playingMusicPos)
            }
        }
    }

}