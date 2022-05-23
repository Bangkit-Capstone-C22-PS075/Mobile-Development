package com.jn.capstoneproject.d_jahit.ui.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import com.jn.capstoneproject.d_jahit.databinding.FragmentRegisterBinding


class RegisterFragment : Fragment() {
    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!
    private lateinit var auth: FirebaseAuth
    private lateinit var databaseReference: DatabaseReference

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRegisterBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        auth = Firebase.auth


        val confirmPassword= binding.edtConfirmPassword.toString()
        val name=binding.edtName.text.toString()

        binding.btnRegister.setOnClickListener {

            val email = binding.edtUsername.text.toString()
            val password= binding.edtPassword.text.toString()
            val confirmPassword= binding.edtConfirmPassword.toString()
            val name=binding.edtName.text.toString()
            registerUser(name,email,password)
        }

    }
    private fun registerUser(name: String,email: String,password: String){
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(requireActivity()) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "createUserWithEmailAndPassword:success")
                    val user = auth.currentUser
                    val userid= user!!.uid
                    databaseReference = FirebaseDatabase.getInstance().getReference("Users").child(userid)
                    val hashMap:HashMap<String,String> = HashMap()
                    hashMap.put("userId",userid)
                    hashMap.put("name",name)
                    hashMap.put("profileImage","")

                    databaseReference.setValue(hashMap).addOnCompleteListener(requireActivity()){
                        if (it.isSuccessful){
                            findNavController().popBackStack()
                        }
                    }
                    updateUI(user)
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "createUserWithEmailAndPassword:failure", task.exception)
                    showToast("Authentication failed")
                    updateUI(null)
                }
            }

    }

    private fun updateUI(currentUser: FirebaseUser?) {
        if (currentUser != null){
            findNavController().popBackStack()
        }
    }

    override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = auth.currentUser
        if(currentUser != null){
            updateUI(currentUser);
        }
    }
    private fun showToast(message: String) {
        Toast.makeText(requireActivity(), message, Toast.LENGTH_SHORT).show()
    }

    companion object {
        private const val TAG = "RegisterActivity"
    }
}