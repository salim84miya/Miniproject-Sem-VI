package com.example.googlemapstest

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.example.googlemapstest.databinding.ActivityCurrentLocationBinding
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import java.util.Locale

class CurrentLocationActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCurrentLocationBinding
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private val REQUEST_CODE = 102
    private var latitude :Double =0.0
    private var longitude:Double =0.0
    private var address :String = "random"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCurrentLocationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        binding.fetchLocationButton.setOnClickListener {
            fetchLocation()
            binding.addMapLocationBtn.visibility = View.VISIBLE
        }
        binding.addMapLocationBtn.setOnClickListener {
            val intent = Intent(this@CurrentLocationActivity,MapsActivity::class.java)
            intent.putExtra("latitude",latitude)
            intent.putExtra("longitude",longitude)
            intent.putExtra("address",address)
            startActivity(intent)
        }
    }
    private fun fetchLocation() {
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(this, arrayOf<String>(Manifest.permission.ACCESS_FINE_LOCATION), REQUEST_CODE)
            return
        }

        fusedLocationClient.lastLocation
            .addOnSuccessListener { location: Location? ->
                if (location != null) {
                    val latitude = location.latitude
                    val longitude = location.longitude

                    // Get address using Geocoder
                    val geocoder = Geocoder(this, Locale.getDefault())
                    val addresses: MutableList<Address>? = geocoder.getFromLocation(latitude, longitude, 1)
                    if (addresses?.isNotEmpty() == true) {
                        val address = addresses[0].getAddressLine(0)
                        displayLocationDetails(latitude, longitude, address)
                        Toast.makeText(this@CurrentLocationActivity, "got location Successfully", Toast.LENGTH_SHORT).show()
                    } else {
                        Log.e("CurrentLocationActivity", "Geocoder failed to get address")
                        // Handle address retrieval failure
                    }
                } else {
                    Log.d("CurrentLocationActivity", "Location is null")
                    // Handle location unavailable error
                }
            }
            .addOnFailureListener { e ->
                Log.e("CurrentLocationActivity", "Error fetching location: $e")
                // Handle location retrieval failure
            }
    }

    private fun displayLocationDetails(latitude: Double, longitude: Double, address: String) {

        this.latitude = latitude
        this.longitude = longitude
        this.address = address

        binding.latitudeTextview.visibility = View.VISIBLE
        binding.longitudeTextview.visibility = View.VISIBLE
        binding.addressTextView.visibility = View.VISIBLE
        binding.latitudeTextview.text = "Latitude: $latitude"
        binding.longitudeTextview.text = "Longitude: $longitude"
        binding.addressTextView.text= "Address: $address"
    }
}

