package com.ubayadev.eventubaya

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ubayadev.eventubaya.databinding.CardRegistrationBinding

class RegistrationAdapter(val registrations: ArrayList<Registration>) :
    RecyclerView.Adapter<RegistrationAdapter.RegistrationViewHolder>() {

    class RegistrationViewHolder(val binding: CardRegistrationBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RegistrationViewHolder {
        val binding = CardRegistrationBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return RegistrationViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RegistrationViewHolder, position: Int) {
        val reg = registrations[position]
        with(holder.binding) {
            txtRegId.text = "E-TIKET #${reg.registration_id}"
            txtEventTitle.text = reg.title
            txtOrganizer.text = "Penyelenggara: ${reg.organizer}"
            txtStudentName.text = reg.student_name
            txtStudentNrp.text = reg.student_nrp
            txtAttendanceMethod.text = reg.attendance_method
            txtClaimSkk.text = if (reg.claim_skk == 1) "Ya (+${reg.skk_points} SKK)" else "Tidak"
            txtRegDate.text = "Didaftarkan: ${reg.registration_date}"
        }
    }

    override fun getItemCount(): Int {
        return registrations.size
    }
}
