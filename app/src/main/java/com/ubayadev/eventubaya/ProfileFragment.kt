package com.ubayadev.eventubaya

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.ubayadev.eventubaya.databinding.FragmentProfileBinding

class ProfileFragment : Fragment() {

    private lateinit var binding: FragmentProfileBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val context = requireContext()
        val name = Global.getStudentName(context)
        val nrp = Global.getStudentNrp(context)
        val email = Global.getStudentEmail(context)
        val phone = Global.getStudentPhone(context)

        binding.txtHeaderName.text = name
        binding.txtHeaderNrp.text = "NRP: $nrp"

        binding.editProfileName.setText(name)
        binding.editProfileNrp.setText(nrp)
        binding.editProfileEmail.setText(email)
        binding.editProfilePhone.setText(phone)

        binding.btnSaveProfile.setOnClickListener {
            val newName = binding.editProfileName.text.toString().trim()
            val newNrp = binding.editProfileNrp.text.toString().trim()
            val newEmail = binding.editProfileEmail.text.toString().trim()
            val newPhone = binding.editProfilePhone.text.toString().trim()

            if (newName.isEmpty() || newNrp.isEmpty() || newEmail.isEmpty() || newPhone.isEmpty()) {
                Toast.makeText(context, "Harap lengkapi semua data profil", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            Global.saveStudentProfile(context, newName, newNrp, newEmail, newPhone)
            binding.txtHeaderName.text = newName
            binding.txtHeaderNrp.text = "NRP: $newNrp"

            Toast.makeText(context, "Profil berhasil diperbarui!", Toast.LENGTH_SHORT).show()
        }
    }

    companion object {
        fun newInstance() = ProfileFragment()
    }
}
