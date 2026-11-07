package com.ubayadev.eventubaya

import android.content.Context
import android.content.SharedPreferences

object Global {
    // Gunakan 10.0.2.2 jika menggunakan Android Emulator
    // Ganti dengan IP Wi-Fi laptop (misal: "http://192.168.100.9/event_ubaya/") jika memakai HP fisik
    val SERVER_URL = "http://10.0.2.2/event_ubaya/"

    private const val PREF_NAME = "event_ubaya_prefs"
    private const val KEY_NAME = "key_name"
    private const val KEY_NRP = "key_nrp"
    private const val KEY_EMAIL = "key_email"
    private const val KEY_PHONE = "key_phone"

    fun getSharedPreferences(context: Context): SharedPreferences {
        return context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
    }

    fun getStudentName(context: Context): String {
        return getSharedPreferences(context).getString(KEY_NAME, "Stevent Crown") ?: "Stevent Crown"
    }

    fun getStudentNrp(context: Context): String {
        return getSharedPreferences(context).getString(KEY_NRP, "160421001") ?: "160421001"
    }

    fun getStudentEmail(context: Context): String {
        return getSharedPreferences(context).getString(KEY_EMAIL, "s160421001@student.ubaya.ac.id") ?: "s160421001@student.ubaya.ac.id"
    }

    fun getStudentPhone(context: Context): String {
        return getSharedPreferences(context).getString(KEY_PHONE, "08123456789") ?: "08123456789"
    }

    fun saveStudentProfile(context: Context, name: String, nrp: String, email: String, phone: String) {
        val editor = getSharedPreferences(context).edit()
        editor.putString(KEY_NAME, name)
        editor.putString(KEY_NRP, nrp)
        editor.putString(KEY_EMAIL, email)
        editor.putString(KEY_PHONE, phone)
        editor.apply()
    }
}
