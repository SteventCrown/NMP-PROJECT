package com.ubayadev.eventubaya

import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.ubayadev.eventubaya.databinding.ActivityRegisterEventBinding
import org.json.JSONObject

class RegisterEventActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterEventBinding
    private var event: Event? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterEventBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.toolbarRegister.setNavigationOnClickListener {
            finish()
        }

        @Suppress("DEPRECATION")
        event = intent.getSerializableExtra("EVENT_EXTRA") as? Event

        if (event != null) {
            setupEventInfo(event!!)
            setupParticipantSpinner()
            loadSavedProfileData()
            setupSubmitButton()
        } else {
            Toast.makeText(this, "Event tidak ditemukan", Toast.LENGTH_SHORT).show()
            finish()
        }
    }

    private fun setupEventInfo(e: Event) {
        binding.txtFormEventTitle.text = e.title
        binding.txtFormEventInfo.text = "${e.date_time} | ${e.location}"
    }

    // Komponen UI Spinner (Dropdown)
    private fun setupParticipantSpinner() {
        val types = arrayOf(
            "Mahasiswa Teknik Informatika",
            "Mahasiswa Fakultas Bisnis & Ekonomika",
            "Mahasiswa Fakultas Industri Kreatif",
            "Mahasiswa Fakultas Kedokteran/Farmasi",
            "Mahasiswa Fakultas Hukum/Psikologi",
            "Umum / Luar Kampus Ubaya"
        )
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, types)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerParticipantType.adapter = adapter
    }

    private fun loadSavedProfileData() {
        binding.editStudentName.setText(Global.getStudentName(this))
        binding.editStudentNrp.setText(Global.getStudentNrp(this))
        binding.editStudentEmail.setText(Global.getStudentEmail(this))
        binding.editStudentPhone.setText(Global.getStudentPhone(this))
    }

    // Komponen UI Button & Validasi Form
    private fun setupSubmitButton() {
        binding.btnSubmitRegistration.setOnClickListener {
            val name = binding.editStudentName.text.toString().trim()
            val nrp = binding.editStudentNrp.text.toString().trim()
            val email = binding.editStudentEmail.text.toString().trim()
            val phone = binding.editStudentPhone.text.toString().trim()

            // Validasi Input EditText
            if (name.isEmpty() || nrp.isEmpty() || email.isEmpty() || phone.isEmpty()) {
                Toast.makeText(this, "Harap isi semua kolom data diri!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Validasi Komponen UI CheckBox
            if (!binding.checkTerms.isChecked) {
                Toast.makeText(this, "Anda wajib menyetujui ketentuan acara!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Membaca Komponen UI Spinner
            val participantType = binding.spinnerParticipantType.selectedItem.toString()

            // Membaca Komponen UI RadioButton
            val attendanceMethod = if (binding.radioOffline.isChecked) {
                "Offline di Kampus"
            } else {
                "Online via Zoom"
            }

            // Membaca CheckBox SKK
            val claimSkk = if (binding.checkClaimSkk.isChecked) 1 else 0

            // Kirim pendaftaran via Volley POST
            submitRegistrationToServer(
                name = name,
                nrp = nrp,
                email = email,
                phone = phone,
                participantType = participantType,
                attendanceMethod = attendanceMethod,
                claimSkk = claimSkk
            )
        }
    }

    private fun submitRegistrationToServer(
        name: String,
        nrp: String,
        email: String,
        phone: String,
        participantType: String,
        attendanceMethod: String,
        claimSkk: Int
    ) {
        binding.btnSubmitRegistration.isEnabled = false
        binding.btnSubmitRegistration.text = "Mengirim Data..."

        val q = Volley.newRequestQueue(this)
        val url = Global.SERVER_URL + "register_event.php"

        val stringRequest = object : StringRequest(
            Request.Method.POST, url,
            Response.Listener { response ->
                binding.btnSubmitRegistration.isEnabled = true
                binding.btnSubmitRegistration.text = "Kirim Formulir Pendaftaran"
                Log.d("api_register", response)
                try {
                    val obj = JSONObject(response)
                    if (obj.getString("result") == "SUCCESS") {
                        // Simpan profil agar otomatis terisi di masa depan
                        Global.saveStudentProfile(this, name, nrp, email, phone)

                        // Dialog Konfirmasi Sukses (sesuai materi AlertDialog)
                        AlertDialog.Builder(this)
                            .setTitle("Pendaftaran Berhasil!")
                            .setMessage("Selamat, Anda telah terdaftar pada event '${event?.title}'. Tiket elektronik Anda sudah tersimpan.")
                            .setPositiveButton("Lihat Tiket") { _, _ ->
                                finish()
                            }
                            .setCancelable(false)
                            .show()
                    } else {
                        val msg = obj.optString("message", "Pendaftaran gagal.")
                        Toast.makeText(this, msg, Toast.LENGTH_LONG).show()
                    }
                } catch (e: Exception) {
                    Log.e("reg_json_parse", e.message.toString())
                }
            },
            Response.ErrorListener { error ->
                binding.btnSubmitRegistration.isEnabled = true
                binding.btnSubmitRegistration.text = "Kirim Formulir Pendaftaran"
                Log.e("api_register_error", error.message.toString())
                Toast.makeText(this, "Gagal menghubungi server web service", Toast.LENGTH_LONG).show()
            }
        ) {
            // Mengirim parameter data form via getParams() sesuai materi Week 11
            override fun getParams(): MutableMap<String, String> {
                val params = HashMap<String, String>()
                params["event_id"] = event?.id.toString()
                params["student_name"] = name
                params["student_nrp"] = nrp
                params["student_email"] = email
                params["student_phone"] = phone
                params["participant_type"] = participantType
                params["attendance_method"] = attendanceMethod
                params["claim_skk"] = claimSkk.toString()
                return params
            }
        }

        q.add(stringRequest)
    }
}
