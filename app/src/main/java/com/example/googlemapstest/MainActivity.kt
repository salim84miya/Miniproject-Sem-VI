package com.example.googlemapstest

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.googlemapstest.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var  binding :ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)

        binding.btnMap.setOnClickListener {
            val intent = Intent(this@MainActivity,MapsActivity::class.java)
            startActivity(intent)
        }

        binding.getCurrentLocationBtn.setOnClickListener {
            val intent = Intent(this@MainActivity,CurrentLocationActivity::class.java)
            startActivity(intent)
        }
    }
}