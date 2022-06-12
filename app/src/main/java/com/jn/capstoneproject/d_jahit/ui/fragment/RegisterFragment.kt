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
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import com.jn.capstoneproject.d_jahit.ApiCallbackString
import com.jn.capstoneproject.d_jahit.ViewModelFactory
import com.jn.capstoneproject.d_jahit.databinding.FragmentRegisterBinding
import com.jn.capstoneproject.d_jahit.ui.activity.MainActivity
import com.jn.capstoneproject.d_jahit.viewmodel.RegisterViewModel


class RegisterFragment : Fragment() {
    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!
    private lateinit var auth: FirebaseAuth
    private lateinit var databaseReference: DatabaseReference

    private val viewModel: RegisterViewModel by viewModels {
        ViewModelFactory(requireActivity())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRegisterBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        auth = Firebase.auth
        setupViewModel()
        binding.btnRegister.setOnClickListener {

            val email = binding.edtEmail.text.toString()
            val password = binding.edtPassword.text.toString()
            val confirmPassword = binding.edtConfirmPassword.text.toString()
            val name = binding.edtName.text.toString()
            val username= binding.edtUsername.text.toString()
            if (email.isEmpty() && password.isEmpty() && confirmPassword.isEmpty()) {
                showToast("field tidak boleh ada yg kosong")
            } else if (password != confirmPassword) {
                showToast("Password tidak sama")
            } else {

                viewModel.registerUser(name, username, email, password, object : ApiCallbackString {
                    override fun onResponse(success: Boolean, message: String) {
                        onSuccess(success, message)

                    }
                })
                registerUser(name, email, password)

            }
        }
    }
    private fun setupViewModel() {
        viewModel.isLoading.observe(requireActivity()) { showLoading(it) }
    }
    private fun registerUser(name: String, email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(requireActivity()) { task ->
                if (task.isSuccessful) {
                    Log.d(TAG, "createUserWithEmailAndPassword:success")
                    val user = auth.currentUser
                    val userid = user!!.uid
//                    val request = UserProfileChangeRequest.Builder()
//                        .displayName(name)
//                        .build()
                    databaseReference =
                        FirebaseDatabase.getInstance().getReference("Users").child(userid)
                    val hashMap: HashMap<String, String> = HashMap()
                    hashMap["userId"] = userid
                    hashMap["name"] = name
                    hashMap["profileImage"] = ""

                    databaseReference.setValue(hashMap).addOnCompleteListener(requireActivity()){}

                    updateUI(user)
                    showLoading(false)
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "createUserWithEmailAndPassword:failure", task.exception)
                    showToast("Authentication failed")
                    updateUI(null)
                }
            }
    }

    private fun onSuccess(param: Boolean, message: String) {
        if (param) {
            showToast(message)


        } else {
            showToast(message)
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
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
        if (currentUser != null) {
            updateUI(currentUser)
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(requireActivity(), message, Toast.LENGTH_SHORT).show()
    }

    companion object {
        private const val TAG = "RegisterActivity"
    }
}