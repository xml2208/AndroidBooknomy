package com.example.androidbooknomy.ui.feature.main.main_app.entertainment.music.music_list.lyrics

data class Lyrics(
    val title: String,
    val artist: String?,
    val album: String?,
    val durationMillis: Long?,
    val lines: List<Line>,
) {

    val optimalDurationMillis = optimalDurationMillis()

    init {
        for(i in lines) {
            require(i.startAt >= 0) { "startAt in the LyricsLine must >= 0" }
            require(i.durationMillis >= 0) { "durationMillis in the LyricsLine >= 0" }
        }
    }

    private fun optimalDurationMillis(): Long {
        if (durationMillis != null) {
            return durationMillis
        }
        return lines.maxOfOrNull { it.startAt + it.durationMillis } ?: 0L
    }

    data class Line(
        val content: String,
        val startAt: Long,
        val durationMillis: Long,
    )
}