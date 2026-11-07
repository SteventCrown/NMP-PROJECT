package com.ubayadev.eventubaya

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.ubayadev.eventubaya.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Tampilkan fragment pertama saat aplikasi dibuka
        if (savedInstanceState == null) {
            loadFragment(EventFragment.newInstance())
        }

        // Navigasi Bottom Navigation View (sesuai materi Material Design Week 9)
        binding.bottomNavigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.itemEvents -> {
                    loadFragment(EventFragment.newInstance())
                    true
                }
                R.id.itemRegistered -> {
                    loadFragment(RegisteredFragment.newInstance())
                    true
                }
                R.id.itemProfile -> {
                    loadFragment(ProfileFragment.newInstance())
                    true
                }
                else -> false
            }
        }
    }

    private fun loadFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, fragment)
            .commit()
    }
}
