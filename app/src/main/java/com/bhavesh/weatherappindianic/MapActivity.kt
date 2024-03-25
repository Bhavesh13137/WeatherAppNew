package com.bhavesh.weatherappindianic

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.bhavesh.weatherappindianic.ezpermission.EzPermission
import com.bhavesh.weatherappindianic.livedata.LocationViewModel
import com.bhavesh.weatherappindianic.utils.Constant.LOCATION_PERMISSION_REQUEST
import com.bhavesh.weatherappindianic.utils.ConvertDate
import com.bhavesh.weatherappindianic.utils.LocationUtil
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions

class MapActivity : AppCompatActivity() {
    private lateinit var locationViewModel: LocationViewModel
    private var isGPSEnabled = false
    private lateinit var markerCenter:Marker
    private lateinit var txtAddress : TextView
    private lateinit var btnChoose : Button
    private var lat : Double? = null
    private var long : Double? = null
    private var city : String? = null

    private val locationPermissions = arrayOf(
        android.Manifest.permission.ACCESS_FINE_LOCATION,
        android.Manifest.permission.ACCESS_COARSE_LOCATION
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map)
        txtAddress = findViewById(R.id.txtAddress)
        btnChoose = findViewById(R.id.btnChoose)
        locationViewModel = ViewModelProviders.of(this).get(LocationViewModel::class.java)
        LocationUtil(this).turnGPSOn(object : LocationUtil.OnLocationOnListener {
            override fun locationStatus(isLocationOn: Boolean) {
                this@MapActivity.isGPSEnabled = isLocationOn
            }
        })
        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)

        btnChoose.setOnClickListener {
            val intent = Intent(this,ChooseActivity::class.java)
            intent.putExtra("address", ConvertDate.getAddress(lat!!,long!!,this))
            intent.putExtra("lat",lat!!)
            intent.putExtra("long",long!!)
            intent.putExtra("city", ConvertDate.getCity(lat!!, long!!,this))
            startActivity(intent)
        }
    }

    private val callback = OnMapReadyCallback { googleMap ->
        var sydney : LatLng
        locationViewModel.getLocationData.observe(this, Observer {
            lat = it.latitude
            long = it.longitude
            sydney = LatLng(it.latitude, it.longitude)

            if(!this::markerCenter.isInitialized){
                val markerOptions = MarkerOptions().position(sydney).title("Marker in Sydney").draggable(true)
                markerCenter = googleMap.addMarker(markerOptions)!!
                googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney,15f))
                txtAddress.text = ConvertDate.getAddress(sydney.latitude,sydney.longitude,applicationContext)
                println("Address1 "+ ConvertDate.getAddress(sydney.latitude,sydney.longitude,applicationContext))
            }

            googleMap.setOnCameraIdleListener {
                val target = googleMap.cameraPosition.target
                if(this::markerCenter.isInitialized) {
                    markerCenter.remove()
                    val markerOptions = MarkerOptions().position(target).title("Marker in New Position").draggable(true)
                    markerCenter = googleMap.addMarker(markerOptions)!!
                    sydney = markerCenter.position
                    println("Address2 "+ ConvertDate.getAddress(target.latitude,target.longitude,applicationContext))
                    txtAddress.text = ConvertDate.getAddress(target.latitude,target.longitude,applicationContext)
                    lat = target.latitude
                    long = target.longitude
                }
            }
        })
    }


    override fun onStart() {
        super.onStart()
        startLocationUpdates()
    }

    private fun startLocationUpdates() {
        when {
            !isGPSEnabled -> {
                //info.text = getString(R.string.enable_gps)
            }

            isLocationPermissionsGranted() -> {
                observeLocationUpdates()
            }
            else -> {
                askLocationPermission()
            }
        }
    }

    private fun isLocationPermissionsGranted(): Boolean {
        return (EzPermission.isGranted(this, locationPermissions[0])
                && EzPermission.isGranted(this, locationPermissions[1]))
    }

    private fun askLocationPermission() {
        EzPermission
            .with(this)
            .permissions(locationPermissions[0], locationPermissions[1])
            .request { granted, denied, permanentlyDenied ->
                if (granted.contains(locationPermissions[0]) &&
                    granted.contains(locationPermissions[1])
                ) { // Granted
                    startLocationUpdates()

                } else if (denied.contains(locationPermissions[0]) ||
                    denied.contains(locationPermissions[1])
                ) { // Denied

                    showDeniedDialog()

                } else if (permanentlyDenied.contains(locationPermissions[0]) ||
                    permanentlyDenied.contains(locationPermissions[1])
                ) { // Permanently denied
                    showPermanentlyDeniedDialog()
                }

            }
    }

    private fun showPermanentlyDeniedDialog() {
        val dialog = AlertDialog.Builder(this)
        dialog.setTitle(getString(R.string.title_permission_permanently_denied))
        dialog.setMessage(getString(R.string.message_permission_permanently_denied))
        dialog.setNegativeButton(getString(R.string.not_now)) { _, _ -> }
        dialog.setPositiveButton(getString(R.string.settings)) { _, _ ->
            startActivity(
                EzPermission.appDetailSettingsIntent(
                    this
                )
            )
        }
        dialog.setOnCancelListener { } //important
        dialog.show()
    }

    private fun showDeniedDialog() {
        val dialog = AlertDialog.Builder(this)
        dialog.setTitle(getString(R.string.title_permission_denied))
        dialog.setMessage(getString(R.string.message_permission_denied))
        dialog.setNegativeButton(getString(R.string.cancel)) { _, _ -> }
        dialog.setPositiveButton(getString(R.string.allow)) { _, _ ->
            askLocationPermission()
        }
        dialog.setOnCancelListener { } //important
        dialog.show()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == LOCATION_PERMISSION_REQUEST) {
                isGPSEnabled = true
                startLocationUpdates()
            }
        }
    }

    private fun observeLocationUpdates() {
        /*locationViewModel.getLocationData.observe(this, Observer {
            it.longitude.toString()
            it.latitude.toString()
            println(it.longitude.toString())
            println(it.latitude.toString())
        })*/
    }
}