package com.ubayadev.eventubaya

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.squareup.picasso.Picasso
import com.ubayadev.eventubaya.databinding.CardEventBinding

class EventAdapter(val events: ArrayList<Event>) :
    RecyclerView.Adapter<EventAdapter.EventViewHolder>() {

    class EventViewHolder(val binding: CardEventBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventViewHolder {
        val binding = CardEventBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return EventViewHolder(binding)
    }

    override fun onBindViewHolder(holder: EventViewHolder, position: Int) {
        val event = events[position]
        val context = holder.itemView.context

        with(holder.binding) {
            txtTitle.text = event.title
            txtCategory.text = event.category
            txtFormat.text = event.format
            txtOrganizer.text = "Penyelenggara: ${event.organizer}"
            txtDateTime.text = event.date_time
            txtLocation.text = event.location
            txtQuota.text = "Kuota: ${event.registered_count}/${event.quota} Terisi"
            txtSkkPoints.text = "SKK: +${event.skk_points} Poin"
            btnLikes.text = "${event.likes} LIKES"

            // Memuat gambar menggunakan Picasso (sesuai materi kuliah)
            if (event.poster_url.isNotEmpty()) {
                Picasso.get()
                    .load(event.poster_url)
                    .placeholder(R.drawable.ic_launcher_background)
                    .error(R.drawable.ic_launcher_background)
                    .into(imgPoster)
            }

            // Tombol Like (menggunakan Volley POST sesuai materi Week 11)
            btnLikes.setOnClickListener {
                val q = Volley.newRequestQueue(context)
                val url = Global.SERVER_URL + "set_likes.php"
                val stringRequest = object : StringRequest(
                    Request.Method.POST, url,
                    Response.Listener { response ->
                        Log.d("api_like", response)
                        event.likes++
                        btnLikes.text = "${event.likes} LIKES"
                        Toast.makeText(context, "Menyukai ${event.title}", Toast.LENGTH_SHORT).show()
                    },
                    Response.ErrorListener { error ->
                        Log.e("api_like_error", error.message.toString())
                    }
                ) {
                    override fun getParams(): MutableMap<String, String> {
                        val params = HashMap<String, String>()
                        params["id"] = event.id.toString()
                        return params
                    }
                }
                q.add(stringRequest)
            }

            // Klik tombol detail atau klik seluruh kartu
            btnDetail.setOnClickListener {
                val intent = Intent(context, DetailEventActivity::class.java)
                intent.putExtra("EVENT_EXTRA", event)
                context.startActivity(intent)
            }

            root.setOnClickListener {
                val intent = Intent(context, DetailEventActivity::class.java)
                intent.putExtra("EVENT_EXTRA", event)
                context.startActivity(intent)
            }
        }
    }

    override fun getItemCount(): Int {
        return events.size
    }
}
