package com.jn.capstoneproject.d_jahit.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.jn.capstoneproject.d_jahit.ApiCallbackString
import com.jn.capstoneproject.d_jahit.R
import com.jn.capstoneproject.d_jahit.SessionManager
import com.jn.capstoneproject.d_jahit.ViewModelFactory
import com.jn.capstoneproject.d_jahit.databinding.FragmentLoginBinding
import com.jn.capstoneproject.d_jahit.ui.activity.MainActivity
import com.jn.capstoneproject.d_jahit.viewmodel.LoginViewModel

class LoginFragment : Fragment() {
    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!
    private lateinit var auth: FirebaseAuth
    private val viewModel: LoginViewModel by viewModels {
        ViewModelFactory(requireActivity())
    }
    private lateinit var sessionManager: SessionManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        sessionManager = SessionManager(requireActivity())
//        val token = sessionManager.fetchAccessId()
//        if (token != null) {
//            val intent= Intent(requireActivity(), MainActivity::class.java)
//            startActivity(intent)
//        }



        // Initialize Firebase Auth
        auth = Firebase.auth

        binding.apply {

            btnSignup.setOnClickListener {
                toSignup()
            }
            btnLogin.setOnClickListener {
                val email = edtUsername.text.toString()
                val password = edtPassword.text.toString()
                if (email.isEmpty() && password.isEmpty()) {
                    Toast.makeText(
                        requireActivity(),
                        "Email dan Password tidak boleh kosong",
                        Toast.LENGTH_SHORT
                    ).show()
                } else if (email.isEmpty()) {
                    Toast.makeText(
                        requireActivity(),
                        "Email  tidak boleh kosong",
                        Toast.LENGTH_SHORT
                    ).show()
                } else if (password.isEmpty()) {
                    Toast.makeText(
                        requireActivity(),
                        "Password  tidak boleh kosong",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    viewModel.loginUser(email, password, object : ApiCallbackString{
                        override fun onResponse(success: Boolean, message: String) {
                            onSuccess(success,message)
                            loginWithEmailAndPass(email, password)
                        }
                    })




                }
            }
        }



    }

    private fun onSuccess(param: Boolean, message: String) {
        if (param){
            val intent=Intent (requireActivity(), MainActivity::class.java)
            startActivity(intent)
            Toast.makeText(requireActivity(),message, Toast.LENGTH_SHORT).show()
        }
    }

    private fun loginWithEmailAndPass(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(requireActivity()) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "signInWithEmail:success")
                    val user = auth.currentUser
                    updateUI(user)
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "signInWithEmail:failure", task.exception)
                    Toast.makeText(
                        requireContext(), "Authentication failed.",
                        Toast.LENGTH_SHORT
                    ).show()
                    updateUI(null)
                }
            }
    }

    private fun toSignup() {
        findNavController().navigate(R.id.action_loginFragment_to_registerFragment)

    }


    private fun updateUI(currentUser: FirebaseUser?) {
        if (currentUser != null) {
            val intent = Intent(requireActivity(), MainActivity::class.java)
            startActivity(intent)

        }
    }

    override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = auth.currentUser
        updateUI(currentUser)
    }

    companion object {
        private const val TAG = "LoginActivity"
    }
}