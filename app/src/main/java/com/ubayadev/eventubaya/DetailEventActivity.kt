package com.ubayadev.eventubaya

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.squareup.picasso.Picasso
import com.ubayadev.eventubaya.databinding.ActivityDetailEventBinding

class DetailEventActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailEventBinding
    private var event: Event? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailEventBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Tombol kembali pada Toolbar
        binding.toolbarDetail.setNavigationOnClickListener {
            finish()
        }

        // Ambil data event yang dikirim melalui Intent
        @Suppress("DEPRECATION")
        event = intent.getSerializableExtra("EVENT_EXTRA") as? Event

        if (event != null) {
            displayEventDetail(event!!)
        } else {
            Toast.makeText(this, "Data event tidak ditemukan", Toast.LENGTH_SHORT).show()
            finish()
        }
    }

    private fun displayEventDetail(e: Event) {
        with(binding) {
            toolbarDetail.title = e.category
            txtDetailTitle.text = e.title
            txtDetailCategory.text = e.category
            txtDetailFormat.text = e.format
            txtDetailOrganizer.text = "Penyelenggara: ${e.organizer}"
            txtDetailDateTime.text = e.date_time
            txtDetailLocation.text = e.location
            txtDetailQuota.text = "Kuota: ${e.registered_count}/${e.quota} Terisi"
            txtDetailSkk.text = "Poin SKK: +${e.skk_points} Poin"
            txtDetailDescription.text = e.description
            btnDetailLike.text = "${e.likes} LIKES"

            if (e.poster_url.isNotEmpty()) {
                Picasso.get()
                    .load(e.poster_url)
                    .placeholder(R.drawable.ic_launcher_background)
                    .error(R.drawable.ic_launcher_background)
                    .into(imgDetailPoster)
            }

            // Tombol Like di halaman detail
            btnDetailLike.setOnClickListener {
                val q = Volley.newRequestQueue(this@DetailEventActivity)
                val url = Global.SERVER_URL + "set_likes.php"
                val stringRequest = object : StringRequest(
                    Request.Method.POST, url,
                    Response.Listener { response ->
                        Log.d("api_like", response)
                        e.likes++
                        btnDetailLike.text = "${e.likes} LIKES"
                        Toast.makeText(this@DetailEventActivity, "Berhasil menyukai event ini", Toast.LENGTH_SHORT).show()
                    },
                    Response.ErrorListener { error ->
                        Log.e("api_like_error", error.message.toString())
                    }
                ) {
                    override fun getParams(): MutableMap<String, String> {
                        val params = HashMap<String, String>()
                        params["id"] = e.id.toString()
                        return params
                    }
                }
                q.add(stringRequest)
            }

            // Tombol Bagikan (Implicit Intent sederhana untuk share teks ke WhatsApp/aplikasi lain)
            btnDetailShare.setOnClickListener {
                val shareIntent = Intent(Intent.ACTION_SEND)
                shareIntent.type = "text/plain"
                shareIntent.putExtra(
                    Intent.EXTRA_TEXT,
                    "Ayo ikuti event '${e.title}' oleh ${e.organizer} pada ${e.date_time} di ${e.location}! Dapatkan ${e.skk_points} poin SKK Ubaya."
                )
                startActivity(Intent.createChooser(shareIntent, "Bagikan Informasi Event"))
            }

            // Tombol Daftar Event
            btnRegisterNow.setOnClickListener {
                if (e.registered_count >= e.quota) {
                    Toast.makeText(this@DetailEventActivity, "Mohon maaf, kuota event ini sudah penuh!", Toast.LENGTH_LONG).show()
                } else {
                    val intent = Intent(this@DetailEventActivity, RegisterEventActivity::class.java)
                    intent.putExtra("EVENT_EXTRA", e)
                    startActivity(intent)
                }
            }
        }
    }
}
