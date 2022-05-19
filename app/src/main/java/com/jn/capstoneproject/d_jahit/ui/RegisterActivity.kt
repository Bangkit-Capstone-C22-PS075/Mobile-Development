package com.jn.capstoneproject.d_jahit.ui

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import com.jn.capstoneproject.d_jahit.databinding.ActivityLoginBinding
import com.jn.capstoneproject.d_jahit.databinding.ActivityRegisterBinding

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var databaseReference: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = Firebase.auth


        val confirmPassword= binding.edtConfirmPassword.toString()
        val name=binding.edtName.text.toString()

        binding.btnRegister.setOnClickListener {

//        if (name.isEmpty()){
//            showToast("Name wajib di isi")
//        }
//        else if (email.isEmpty()){
//            showToast("Email wajib diisi")
//        }
//        else if (password.isEmpty() && confirmPassword.isEmpty()){
//            showToast("Password wajib diisi")
//        }
//        else{
//
//        }
            val email = binding.edtUsername.text.toString()
            val password= binding.edtPassword.text.toString()
            val confirmPassword= binding.edtConfirmPassword.toString()
            val name=binding.edtName.text.toString()
            registerUser(name,email,password)
        }

    }
    private fun registerUser(name: String,email: String,password: String){
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
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

                    databaseReference.setValue(hashMap).addOnCompleteListener(this){
                        if (it.isSuccessful){
                            val  intent= Intent(this,LoginActivity::class.java)
                            startActivity(intent)
                        }
                    }
                    updateUI(user)
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "createUserWithEmailAndPassword:failure", task.exception)
                    Toast.makeText(baseContext, "Authentication failed.",
                        Toast.LENGTH_SHORT).show()
                    updateUI(null)
                }
            }

    }

    private fun updateUI(currentUser: FirebaseUser?) {
        if (currentUser != null){
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
    }

    public override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = auth.currentUser
        if(currentUser != null){
            updateUI(currentUser);
        }
    }
    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    companion object {
        private const val TAG = "RegisterActivity"
    }
}