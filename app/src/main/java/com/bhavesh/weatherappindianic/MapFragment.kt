package com.bhavesh.weatherappindianic

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.bhavesh.weatherappindianic.ezpermission.EzPermission
import com.bhavesh.weatherappindianic.livedata.LocationViewModel
import com.bhavesh.weatherappindianic.utils.Constant
import com.bhavesh.weatherappindianic.utils.ConvertDate
import com.bhavesh.weatherappindianic.utils.LocationUtil
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions

class MapFragment : Fragment() {

    private lateinit var locationViewModel: LocationViewModel
    private var isGPSEnabled = false
    private lateinit var markerCenter: Marker
    private lateinit var txtAddress : TextView
    private lateinit var btnChoose : Button
    private var lat : Double? = null
    private var long : Double? = null
    private var city : String? = null
    private val locationPermissions = arrayOf(
        android.Manifest.permission.ACCESS_FINE_LOCATION,
        android.Manifest.permission.ACCESS_COARSE_LOCATION
    )

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_map, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        txtAddress = view.findViewById(R.id.txtAddress)
        btnChoose = view.findViewById(R.id.btnChoose)
        locationViewModel = ViewModelProviders.of(this)[LocationViewModel::class.java]
        LocationUtil(requireContext()).turnGPSOn(object : LocationUtil.OnLocationOnListener {
            override fun locationStatus(isLocationOn: Boolean) {
                this@MapFragment.isGPSEnabled = isLocationOn
            }
        })
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)

        btnChoose.setOnClickListener {
            val bundle = Bundle()
            bundle.putString("address",ConvertDate.getAddress(lat!!,long!!,requireContext()))
            bundle.putDouble("lat",lat!!)
            bundle.putDouble("long",long!!)
            bundle.putString("city",ConvertDate.getCity(lat!!, long!!,requireContext()))
            findNavController().navigate(R.id.action_mapFragment_to_chooseFragment,bundle)
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
                txtAddress.text = ConvertDate.getAddress(sydney.latitude,sydney.longitude,requireContext())
                //println("Address1 "+ ConvertDate.getAddress(sydney.latitude,sydney.longitude,requireContext()))
            }

            googleMap.setOnCameraIdleListener {
                val target = googleMap.cameraPosition.target
                if(this::markerCenter.isInitialized) {
                    markerCenter.remove()
                    val markerOptions = MarkerOptions().position(target).title("Marker in New Position").draggable(true)
                    markerCenter = googleMap.addMarker(markerOptions)!!
                    sydney = markerCenter.position
                    //println("Address2 "+ ConvertDate.getAddress(target.latitude,target.longitude,requireContext()))
                    txtAddress.text = ConvertDate.getAddress(target.latitude,target.longitude,requireContext())
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
        return (EzPermission.isGranted(requireContext(), locationPermissions[0])
                && EzPermission.isGranted(requireContext(), locationPermissions[1]))
    }

    private fun askLocationPermission() {
        EzPermission
            .with(requireContext())
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
        val dialog = AlertDialog.Builder(requireContext())
        dialog.setTitle(getString(R.string.title_permission_permanently_denied))
        dialog.setMessage(getString(R.string.message_permission_permanently_denied))
        dialog.setNegativeButton(getString(R.string.not_now)) { _, _ -> }
        dialog.setPositiveButton(getString(R.string.settings)) { _, _ ->
            startActivity(
                EzPermission.appDetailSettingsIntent(
                    requireContext()
                )
            )
        }
        dialog.setOnCancelListener { } //important
        dialog.show()
    }

    private fun showDeniedDialog() {
        val dialog = AlertDialog.Builder(requireContext())
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
            if (requestCode == Constant.LOCATION_PERMISSION_REQUEST) {
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