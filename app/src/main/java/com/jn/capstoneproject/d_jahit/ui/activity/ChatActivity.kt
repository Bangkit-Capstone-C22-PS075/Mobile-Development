package com.jn.capstoneproject.d_jahit.ui.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.jn.capstoneproject.d_jahit.adapter.FirebaseMessageAdapter
import com.jn.capstoneproject.d_jahit.databinding.ActivityChatBinding
import com.jn.capstoneproject.d_jahit.model.Message

class ChatActivity : AppCompatActivity() {
    private lateinit var binding: ActivityChatBinding
    private lateinit var auth: FirebaseAuth

    private lateinit var db: FirebaseDatabase
    private lateinit var adapter: FirebaseMessageAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChatBinding.inflate(layoutInflater)
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

        val manager=LinearLayoutManager(this)
        manager.stackFromEnd=true
        binding.userRecyclerView.layoutManager=manager

        val options = FirebaseRecyclerOptions.Builder<Message>()
            .setQuery(messagesRef, Message::class.java)
            .build()
        adapter = FirebaseMessageAdapter(options, firebaseUser.displayName)
        binding.userRecyclerView.adapter= adapter
    }
    companion object {
        const val MESSAGES_CHILD = "messages"
    }
}