package com.jn.capstoneproject.d_jahit.ui.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.jn.capstoneproject.d_jahit.R
import com.jn.capstoneproject.d_jahit.adapter.FirebaseMessageAdapter
import com.jn.capstoneproject.d_jahit.databinding.ActivityDetailChatBinding
import com.jn.capstoneproject.d_jahit.model.Message
import java.util.*

class DetailChat : AppCompatActivity() {
    private lateinit var binding: ActivityDetailChatBinding

    private lateinit var auth: FirebaseAuth

    private lateinit var db: FirebaseDatabase
    private lateinit var adapter: FirebaseMessageAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailChatBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = Firebase.auth
        val firebaseUser = auth.currentUser
        if (firebaseUser == null) {
            // Not signed in, launch the Login activity
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
            return
        }

        db = Firebase.database

        val messagesRef = db.reference.child(MESSAGES_CHILD)

        binding.sendButton.setOnClickListener {
            val friendlyMessage = Message(
                binding.messageEditText.text.toString(),
                firebaseUser.displayName.toString(),
                firebaseUser.photoUrl.toString(),
                Date().time
            )
            messagesRef.push().setValue(friendlyMessage) { error, _ ->
                if (error != null) {
                    Toast.makeText(this, getString(R.string.send_error) + error.message, Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, getString(R.string.send_success), Toast.LENGTH_SHORT).show()
                }
            }
            binding.messageEditText.setText("")
        }

        val manager = LinearLayoutManager(this)
        manager.stackFromEnd = true
        binding.messageRecyclerView.layoutManager = manager

        val options = FirebaseRecyclerOptions.Builder<Message>()
            .setQuery(messagesRef, Message::class.java)
            .build()
        adapter = FirebaseMessageAdapter(options, firebaseUser.displayName)
        binding.messageRecyclerView.adapter = adapter
    }

    public override fun onResume() {
        super.onResume()
        adapter.startListening()
    }

    public override fun onPause() {
        adapter.stopListening()
        super.onPause()
    }





    companion object {
        const val MESSAGES_CHILD = "messages"
    }
}