package com.example.googlemapstest

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Canvas
import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.googlemapstest.databinding.ActivityMapsBinding
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.MarkerOptions
import com.google.maps.android.SphericalUtil


class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private val REQUEST_CODE = 101
    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityMapsBinding
    private var latitude: Double = 0.0
    private var longitude: Double = 0.0
    private var address: String = "random"

    lateinit var currentLocation:LatLng

    @RequiresApi(Build.VERSION_CODES.R)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)

     currentLocation = LatLng(19.18487462365647,73.21459198017035)
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
 
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        val coordinateIntent = intent
        if(intent.hasExtra("latitude")&&intent.hasExtra("longitude")&&intent.hasExtra("address")){
            latitude = coordinateIntent.getDoubleExtra("latitude", 0.00)
            longitude = coordinateIntent.getDoubleExtra("longitude", 0.00)
            address = coordinateIntent.getStringExtra("address").toString()

            // Add a marker in Sydney and move the camera
            val custom_1 = LatLng(19.18179666972765, 73.19393754120675)
            mMap.addMarker(
                MarkerOptions().position(custom_1).title("Custom Marker 1")
                    .icon(bitMapDesscriptor(this@MapsActivity, R.drawable.baseline_location_pin_24))
            )
            mMap.moveCamera(CameraUpdateFactory.newLatLng(custom_1))

            val custom_2 = LatLng(19.18435218569643, 73.16534578758778)
            mMap.addMarker(
                MarkerOptions().position(custom_2).title("Custom Marker 2")
                    .icon(bitMapDesscriptor(this@MapsActivity, R.drawable.baseline_location_pin_24))
            )
            mMap.moveCamera(CameraUpdateFactory.newLatLng(custom_2))

            val custom_3 = LatLng(19.20105055952484, 73.19203913117605)
            mMap.addMarker(
                MarkerOptions().position(custom_3).title("Custom Marker 3")
                    .icon(bitMapDesscriptor(this@MapsActivity, R.drawable.baseline_location_pin_24))
            )
            mMap.moveCamera(CameraUpdateFactory.newLatLng(custom_3))



            val custom_4 = LatLng(latitude, longitude)
            mMap.addMarker(
                MarkerOptions().position(custom_4).title(address)
                    .icon(bitMapDesscriptor(this@MapsActivity, R.drawable.baseline_location_pin_24))
            )
            mMap.moveCamera(CameraUpdateFactory.newLatLng(custom_4))


            val builder = LatLngBounds.Builder()
            builder.include(custom_1)
            builder.include(custom_2)
            builder.include(custom_3)
            builder.include(custom_4)
            val bounds = builder.build()

            // Zoom to the bounds
            mMap.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds, 150))

//            mMap.setOnMarkerClickListener { marker ->
//                marker.snippet = "Approximate Distance: ${calculateApproximateDistance(currentLocation, marker.position).toString()} meters"
//                true
//            }

        }else{
            // Add a marker in Sydney and move the camera
            val custom_1 = LatLng(19.18179666972765, 73.19393754120675)
            mMap.addMarker(
                MarkerOptions().position(custom_1).title("Custom Marker 1")
                    .icon(bitMapDesscriptor(this@MapsActivity, R.drawable.baseline_location_pin_24))
            )
            mMap.moveCamera(CameraUpdateFactory.newLatLng(custom_1))

            val custom_2 = LatLng(19.18435218569643, 73.16534578758778)
            mMap.addMarker(
                MarkerOptions().position(custom_2).title("Custom Marker 2")
                    .icon(bitMapDesscriptor(this@MapsActivity, R.drawable.baseline_location_pin_24))
            )
            mMap.moveCamera(CameraUpdateFactory.newLatLng(custom_2))

            val custom_3 = LatLng(19.20105055952484, 73.19203913117605)
            mMap.addMarker(
                MarkerOptions().position(custom_3).title("Custom Marker 3")
                    .icon(bitMapDesscriptor(this@MapsActivity, R.drawable.baseline_location_pin_24))
            )
            mMap.moveCamera(CameraUpdateFactory.newLatLng(custom_3))

            val builder = LatLngBounds.Builder()
            builder.include(custom_1)
            builder.include(custom_2)
            builder.include(custom_3)
            val bounds = builder.build()

            // Zoom to the bounds
            mMap.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds, 150))

//            mMap.setOnMarkerClickListener { marker ->
//                marker.snippet = "Approximate Distance: ${calculateApproximateDistance(currentLocation, marker.position).toString()} meters"
//                true
//            }

        }


    }

    private fun bitMapDesscriptor(context: Context, vectorResId: Int): BitmapDescriptor {
        val vectorDrawable = ContextCompat.getDrawable(context, vectorResId)!!
        vectorDrawable.setBounds(
            0,
            0,
            vectorDrawable.intrinsicWidth,
            vectorDrawable.intrinsicHeight
        )
        val bitmap = Bitmap.createBitmap(
            vectorDrawable.intrinsicWidth,
            vectorDrawable.intrinsicHeight,
            Bitmap.Config.ARGB_8888
        )
        val canvas = Canvas(bitmap)
        vectorDrawable.draw(canvas)
        return BitmapDescriptorFactory.fromBitmap(bitmap)
    }

    private fun checkPermission() {
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(this, arrayOf<String>(), REQUEST_CODE)
            return
        }
    }

    fun calculateApproximateDistance(currentLocation: LatLng, markerLocation: LatLng): Double {
        return SphericalUtil.computeDistanceBetween(currentLocation, markerLocation)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (REQUEST_CODE) {
            REQUEST_CODE -> {
                if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    checkPermission()
                }
            }
        }
    }

}