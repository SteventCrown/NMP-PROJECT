package com.ubayadev.eventubaya

import java.io.Serializable

data class Event(
    var id: Int,
    var title: String,
    var category: String,
    var organizer: String,
    var date_time: String,
    var location: String,
    var quota: Int,
    var registered_count: Int,
    var description: String,
    var poster_url: String,
    var likes: Int,
    var format: String,
    var skk_points: Int
) : Serializable
