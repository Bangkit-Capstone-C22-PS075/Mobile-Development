package com.jn.capstoneproject.d_jahit.ui.fragment

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.jn.capstoneproject.d_jahit.*
import com.jn.capstoneproject.d_jahit.Constanta.EXTRA_USER
import com.jn.capstoneproject.d_jahit.databinding.FragmentProfileBinding
import com.jn.capstoneproject.d_jahit.model.dataresponse.UserResponse
import com.jn.capstoneproject.d_jahit.ui.activity.LoginActivity
import com.jn.capstoneproject.d_jahit.viewmodel.LoginViewModel
import com.jn.capstoneproject.d_jahit.viewmodel.ProfileViewModel
import java.io.File

class ProfileFragment : Fragment() {
    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
    private lateinit var sessionManager: SessionManager

    private lateinit var navbar: BottomNavigationView
    private var currentFile: File? = null
    private val viewModel: ProfileViewModel by viewModels {
        ViewModelFactory(requireActivity())
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentProfileBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navbar=requireActivity().findViewById(R.id.nav_view)
        navbar.visibility=View.VISIBLE
        sessionManager= SessionManager(requireActivity())
        val id= sessionManager.fetchAccessId()
        if (id !=null){

            viewModel.getUserById(id, object : ApiCallbackString{
                override fun onResponse(success: Boolean, message: String) {
                    onSucces(success,message)
                }
            })
            viewModel.getUser.observe(requireActivity(),{ user ->
                setUser(user)
            })
        }

        binding.clheader.setOnClickListener {
            findNavController().navigate(R.id.action_profileFragment_to_userFragment)
        }
        binding.btnLogout.setOnClickListener {
            Firebase.auth.signOut()
            val intent= Intent(requireActivity(), LoginActivity::class.java)
            startActivity(intent)
            sessionManager.deleteAccessToken()
        }
        binding.imgProfile.setOnClickListener {
            startGallery()
        }
        binding.tvCreateStore.setOnClickListener {
            findNavController().navigate(R.id.action_profileFragment_to_createStroreFragment)
        }


    }

    private fun onSucces(param: Boolean, message: String) {
    if (param){
        Toast.makeText(requireActivity(),"HALLO", Toast.LENGTH_SHORT).show()
    }
    }

    private fun setUser(user: UserResponse?) {

        binding.tvUsername.text= user?.fullName

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