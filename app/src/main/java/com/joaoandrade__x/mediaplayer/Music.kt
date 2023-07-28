package com.joaoandrade__x.mediaplayer

import java.util.Locale

class Music {
    private var id: Int = 0
    private var name: String = ""
    private var artist: String = ""
    private var coverResId: Int = 0
    private var artistResId: Int = 0
    private var musicFileResId: Int = 0

    fun getId() : Int {
        return id
    }

    fun setId(id: Int) {
        this.id = id
    }

    fun getName() : String {
        return name
    }

    fun setName(name: String) {
        this.name = name
    }

    fun getArtist() : String {
        return artist
    }

    fun setArtist(artist: String) {
        this.artist = artist
    }

    fun getCoverResId() : Int {
        return coverResId
    }

    fun setCoverResId(coverResId: Int) {
        this.coverResId = coverResId
    }

    fun getArtistResId() : Int {
        return artistResId
    }

    fun setArtistResId(artistResId: Int) {
        this.artistResId = artistResId
    }

    fun getMusicFileResIdResId() : Int {
        return musicFileResId
    }

    fun setMusicFileResIdResId(musicFileResId: Int) {
        this.musicFileResId = musicFileResId
    }

     companion object {
         fun getList() : List<Music> {
             val musicList: ArrayList<Music> = ArrayList()
             val listName = listOf("Meu Ébano", "Meu Lugar", "Nosso Samba Ta Na Rua", "História Pra Ninar Gente Grande", "Vacilão", "Ser Humano")
             val listArtist = listOf("Alcione", "Arlindo Cruz", "Beth Carvalho", "Mangueira", "Zeca Pagodinho", "Zeca Pagodinho")
             val listResId = listOf(R.drawable.alcione, R.drawable.arlindo, R.drawable.beth, R.drawable.samba, R.drawable.zeca, R.drawable.zecaserhumano)
             val listMusicResId = listOf(R.raw.alcione, R.raw.arlindo, R.raw.beth, R.raw.samba, R.raw.zeca, R.raw.zecaserhumano)

             val size = listMusicResId.size

             for(position in 0 until size) {
                 val music = Music()
                 music.setName(listName[position])
                 music.setArtist(listArtist[position])
                 music.setCoverResId(listResId[position])
                 music.setArtistResId(listResId[position])
                 music.setMusicFileResIdResId(listMusicResId[position])
                 musicList.add(music)
             }

             return musicList
         }

         fun covertMillisToString(durationInMillis: Int) : String {
             val seconds: Int = (durationInMillis / 1000) % 60
             val minutes: Int = ((durationInMillis / 1000) / 60) % 60

             return String.format(Locale.US, "%02d:%02d", minutes, seconds)
         }
     }
}