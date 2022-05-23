package com.jn.capstoneproject.d_jahit.ui.fragment

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.jn.capstoneproject.d_jahit.R
import com.jn.capstoneproject.d_jahit.Utils
import com.jn.capstoneproject.d_jahit.databinding.FragmentProfileBinding
import com.jn.capstoneproject.d_jahit.ui.activity.LoginActivity
import java.io.File

class ProfileFragment : Fragment() {
    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    private lateinit var navbar: BottomNavigationView
    private var currentFile: File? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentProfileBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.clheader.setOnClickListener {
            navbar=requireActivity().findViewById(R.id.nav_view)
            navbar.visibility=View.GONE
            findNavController().navigate(R.id.action_profileFragment_to_userFragment)
        }
        binding.setting.setOnClickListener {
            Firebase.auth.signOut()
            val intent= Intent(requireActivity(), LoginActivity::class.java)
            startActivity(intent)

        }
        binding.imgProfile.setOnClickListener {
            startGallery()
        }
    }
    private fun startGallery() {
        val intent = Intent().apply {
            action = Intent.ACTION_GET_CONTENT
            type = "image/*"
        }
        launcherIntentGallery.launch(Intent.createChooser(intent, "Choose a picture!"))
    }

    private val launcherIntentGallery =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == AppCompatActivity.RESULT_OK) {
                currentFile = Utils.uriToFile(it.data?.data as Uri, requireContext())

            }
        }

}