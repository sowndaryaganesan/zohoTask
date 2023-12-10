package com.app.android.model

data class NewsListResponse(
    val count: Int,
    val next: String,
    val results: ArrayList<Results>,
) {
    data class Results(
        val id: Int,
        val title: String,
        val url: String,
        val image_url: String,
        val news_site: String,
        val summary: String,
        val published_at: String,
        val updated_at: String,
        val featured: Boolean,
        val launches: ArrayList<Launches>,
    ) {

        data class Launches(
            val launch_id: String,
            val provider: Boolean,
        )
    }
}
