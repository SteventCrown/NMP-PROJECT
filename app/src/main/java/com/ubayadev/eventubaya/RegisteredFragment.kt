package com.ubayadev.eventubaya

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.ubayadev.eventubaya.databinding.FragmentRegisteredBinding
import org.json.JSONObject

class RegisteredFragment : Fragment() {

    private lateinit var binding: FragmentRegisteredBinding
    private var registrations: ArrayList<Registration> = ArrayList()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRegisteredBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        loadUserRegistrations()
    }

    private fun loadUserRegistrations() {
        val context = requireContext()
        val nrp = Global.getStudentNrp(context)
        val name = Global.getStudentName(context)

        binding.txtStudentInfo.text = "Tiket aktif untuk $name ($nrp)"
        binding.progressBar.visibility = View.VISIBLE
        binding.txtEmpty.visibility = View.GONE

        val q = Volley.newRequestQueue(activity)
        val url = Global.SERVER_URL + "get_registrations.php?student_nrp=" + nrp

        val stringRequest = StringRequest(
            Request.Method.GET, url,
            { response ->
                binding.progressBar.visibility = View.GONE
                try {
                    val obj = JSONObject(response)
                    if (obj.getString("result") == "SUCCESS") {
                        val dataArray = obj.getJSONArray("data")
                        val listType = object : TypeToken<ArrayList<Registration>>() {}.type
                        registrations = Gson().fromJson(dataArray.toString(), listType)

                        if (registrations.isEmpty()) {
                            binding.txtEmpty.visibility = View.VISIBLE
                        } else {
                            binding.txtEmpty.visibility = View.GONE
                        }

                        val lm = LinearLayoutManager(activity)
                        binding.recyclerRegistrations.layoutManager = lm
                        binding.recyclerRegistrations.setHasFixedSize(true)
                        binding.recyclerRegistrations.adapter = RegistrationAdapter(registrations)
                    }
                } catch (e: Exception) {
                    Log.e("reg_json_error", e.message.toString())
                }
            },
            { error ->
                binding.progressBar.visibility = View.GONE
                Log.e("reg_volley_error", error.message.toString())
                Toast.makeText(context, "Gagal mengambil data pendaftaran", Toast.LENGTH_SHORT).show()
            }
        )

        q.add(stringRequest)
    }

    companion object {
        fun newInstance() = RegisteredFragment()
    }
}
