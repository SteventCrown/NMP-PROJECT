package com.ubayadev.eventubaya

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.ubayadev.eventubaya.databinding.FragmentEventBinding
import org.json.JSONObject

class EventFragment : Fragment() {

    private lateinit var binding: FragmentEventBinding
    private var allEvents: ArrayList<Event> = ArrayList()
    private var currentCategory: String = "Semua Kategori"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentEventBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupCategorySpinner()
    }

    override fun onResume() {
        super.onResume()
        loadEventsFromServer()
    }

    private fun setupCategorySpinner() {
        val categories = arrayOf(
            "Semua Kategori",
            "Seminar",
            "Workshop",
            "Kompetisi",
            "Volunteer"
        )

        val adapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_item,
            categories
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerFilterCategory.adapter = adapter

        binding.spinnerFilterCategory.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                currentCategory = categories[position]
                filterAndDisplayEvents()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
    }

    private fun loadEventsFromServer() {
        binding.progressBar.visibility = View.VISIBLE
        val q = Volley.newRequestQueue(activity)
        val url = Global.SERVER_URL + "get_events.php"

        val stringRequest = StringRequest(
            Request.Method.GET, url,
            { response ->
                binding.progressBar.visibility = View.GONE
                try {
                    val obj = JSONObject(response)
                    if (obj.getString("result") == "SUCCESS") {
                        val dataArray = obj.getJSONArray("data")
                        val listType = object : TypeToken<ArrayList<Event>>() {}.type
                        allEvents = Gson().fromJson(dataArray.toString(), listType)
                        filterAndDisplayEvents()
                    } else {
                        val msg = obj.optString("message", "Gagal memuat event")
                        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
                    }
                } catch (e: Exception) {
                    Log.e("event_json_error", e.message.toString())
                }
            },
            { error ->
                binding.progressBar.visibility = View.GONE
                Log.e("event_volley_error", error.message.toString())
                Toast.makeText(context, "Tidak dapat terhubung ke server", Toast.LENGTH_SHORT).show()
            }
        )

        q.add(stringRequest)
    }

    private fun filterAndDisplayEvents() {
        val filteredList = if (currentCategory == "Semua Kategori") {
            allEvents
        } else {
            ArrayList(allEvents.filter { it.category.equals(currentCategory, ignoreCase = true) })
        }

        if (filteredList.isEmpty()) {
            binding.txtNoData.visibility = View.VISIBLE
        } else {
            binding.txtNoData.visibility = View.GONE
        }

        val lm = LinearLayoutManager(activity)
        binding.recyclerEvents.layoutManager = lm
        binding.recyclerEvents.setHasFixedSize(true)
        binding.recyclerEvents.adapter = EventAdapter(filteredList)
    }

    companion object {
        fun newInstance() = EventFragment()
    }
}
