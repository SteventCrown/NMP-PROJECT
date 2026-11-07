package com.ubayadev.eventubaya

import java.io.Serializable

data class Registration(
    var registration_id: Int,
    var event_id: Int,
    var student_name: String,
    var student_nrp: String,
    var student_email: String,
    var student_phone: String,
    var participant_type: String,
    var attendance_method: String,
    var claim_skk: Int,
    var registration_date: String,
    var title: String,
    var category: String,
    var organizer: String,
    var date_time: String,
    var location: String,
    var poster_url: String,
    var skk_points: Int
) : Serializable
