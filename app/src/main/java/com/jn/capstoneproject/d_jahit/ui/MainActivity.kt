package com.jn.capstoneproject.d_jahit.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.view.Menu
import android.view.MenuItem
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import com.jn.capstoneproject.d_jahit.R
import com.jn.capstoneproject.d_jahit.adapter.FirebaseMessageAdapter
import com.jn.capstoneproject.d_jahit.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var auth: FirebaseAuth

    private lateinit var db: FirebaseDatabase
    private lateinit var adapter: FirebaseMessageAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        binding.btnChat.setOnClickListener {
//            val intent= Intent (this, ChatActivity::class.java)
//            startActivity(intent)
//            finish()
//        }
        val navView: BottomNavigationView = findViewById(R.id.nav_view)
//
        val navController = findNavController(R.id.nav_host_fragment)
//
        val appBarConfiguration = AppBarConfiguration.Builder(
            R.id.homeFragment, R.id.profileFragment
        ).build()

        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.option_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {

            R.id.option -> {
                toLogin()
                true
            }


            else -> false
        }
    }

    private fun toLogin() {
        Firebase.auth.signOut()
        val intent= Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }

}