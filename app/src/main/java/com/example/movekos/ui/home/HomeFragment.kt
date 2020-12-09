package com.example.movekos.ui.home

import android.annotation.SuppressLint
import android.app.Activity
import android.app.Activity.RESULT_OK
import android.app.AlertDialog
import android.content.Intent
import android.content.IntentSender
import android.content.pm.PackageManager
import android.graphics.Color
import android.location.Location
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.movekos.R
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.common.api.Status
import com.google.android.gms.location.*
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.PolylineOptions
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.widget.AutocompleteSupportFragment
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.maps.android.PolyUtil
import org.json.JSONObject

class HomeFragment: Fragment(), OnMapReadyCallback, GoogleMap.OnMarkerClickListener {

    private lateinit var map: GoogleMap
    private lateinit var rootView: View
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var lastLocation: Location
    private lateinit var locationCallback: LocationCallback
    private lateinit var locationRequest: LocationRequest
    private var locationUpdateState = false

    private lateinit var database: FirebaseDatabase
    private lateinit var myRef: DatabaseReference
    private var mAuth: FirebaseAuth = FirebaseAuth.getInstance()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        rootView = inflater.inflate(R.layout.fragment_home, container, false)

        database = FirebaseDatabase.getInstance()
        myRef = database.reference

        inisialisasiLokasi()
        createLocationRequest()

        return rootView
    }

  @SuppressLint("MissingPermission")
  override fun onMapReady(googleMap: GoogleMap?) {
    map = googleMap!!

    map.uiSettings.isZoomControlsEnabled = false
    map.uiSettings.isMapToolbarEnabled = false
    map.uiSettings.isCompassEnabled = false
    map.uiSettings.isMyLocationButtonEnabled = false
    map.setOnMarkerClickListener(this)

    setUpMap()

    map.isMyLocationEnabled = true

    fusedLocationClient.lastLocation.addOnSuccessListener(activity as AppCompatActivity) { location ->
      if (location != null) {
        lastLocation = location
        val currentLatLng = LatLng(location.latitude, location.longitude)
        map.animateCamera(CameraUpdateFactory.newLatLngZoom(currentLatLng, 12f))
      }
    }
    inisialisasiAutoComplete()
  }

  companion object{
    const val LOCATION_PERMISSION_REQUEST_CODE = 1
    const val REQUEST_CHECK_SETTINGS = 2
  }

  override fun onMarkerClick(p0: Marker?) = false

  private fun setUpMap() {
    if (ActivityCompat.checkSelfPermission(rootView.context,
        android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
      ActivityCompat.requestPermissions(activity as AppCompatActivity,
        arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION), LOCATION_PERMISSION_REQUEST_CODE)
      return
    }
    map.isMyLocationEnabled = true

    fusedLocationClient.lastLocation.addOnSuccessListener(activity as AppCompatActivity) { location ->
      // Got last known location. In some rare situations this can be null.
      if (location != null) {
        lastLocation = location
        val currentLatLng = LatLng(location.latitude, location.longitude)
        map.animateCamera(CameraUpdateFactory.newLatLngZoom(currentLatLng, 12f))
      }
    }
  }

  private fun startLocationUpdates() {
    if (ActivityCompat.checkSelfPermission(rootView.context,
        android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
      ActivityCompat.requestPermissions(activity as AppCompatActivity,
        arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),
        LOCATION_PERMISSION_REQUEST_CODE)
      return
    }
    fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, null /* Looper */)
  }

  private fun createLocationRequest() {
    locationRequest = LocationRequest()
    locationRequest.interval = 10000
    locationRequest.fastestInterval = 5000
    locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY

    val builder = LocationSettingsRequest.Builder()
      .addLocationRequest(locationRequest)

    // 4
    val client = LocationServices.getSettingsClient(activity as Activity)
    val task = client.checkLocationSettings(builder.build())

    // 5
    task.addOnSuccessListener {
      locationUpdateState = true
      startLocationUpdates()
    }
    task.addOnFailureListener { e ->
      // 6
      if (e is ResolvableApiException) {
        try {
          e.startResolutionForResult(activity,
            REQUEST_CHECK_SETTINGS)
        } catch (sendEx: IntentSender.SendIntentException) {
          // Ignore the error.
        }
      }
    }
  }

  override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
    super.onActivityResult(requestCode, resultCode, data)
    if (requestCode == REQUEST_CHECK_SETTINGS) {
      if (resultCode == RESULT_OK) {
        locationUpdateState = true
        startLocationUpdates()
      }
    }
  }

  override fun onPause() {
    super.onPause()
    fusedLocationClient.removeLocationUpdates(locationCallback)
  }

  override fun onResume() {
    super.onResume()
    if (!locationUpdateState) {
      startLocationUpdates()
    }
  }

    // TODO: 12/9/2020 INI FUNGSI BUAT SI AUTOCOMPLETE
  private fun inisialisasiAutoComplete(){
    if (!Places.isInitialized()) {
      Places.initialize(requireContext(), getString(R.string.google_maps_API_key))
    }

    val autocompleteFragment = childFragmentManager.findFragmentById(R.id.autocomplete_fragment) as AutocompleteSupportFragment
    autocompleteFragment.setPlaceFields(listOf(Place.Field.ID, Place.Field.NAME, Place.Field.LAT_LNG))
    autocompleteFragment.setOnPlaceSelectedListener(object : PlaceSelectionListener {
      @SuppressLint("MissingPermission")
      override fun onPlaceSelected(place: Place) {

          // TODO: EDIT EDIT
          Log.i("TEMPAT", "Place: ${place.name}, ${place.latLng}")



          val source = LatLng(lastLocation.latitude, lastLocation.longitude)

          buatDirection(source, place.latLng!!)
      }

      override fun onError(status: Status) {
        Log.i("TEMPAT", "An error occurred: $status")
      }
    })
  }

    // TODO: 12/9/2020 INI LOKASI ORANGNYA
      private fun inisialisasiLokasi(){
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity().applicationContext)

        locationCallback = object : LocationCallback() {
            override fun onLocationResult(p0: LocationResult) {
                super.onLocationResult(p0)
                lastLocation = p0.lastLocation
                // TODO: last locationnya
            }
        }
    }

    // TODO: 12/9/2020 INI FUNGSI BUAT EDIT ROUTENYA YA


    private fun buatDirection(origin: LatLng, destination: LatLng){
        map.clear()
        map.addMarker(MarkerOptions().position(origin).title("Origin"))
        map.addMarker(MarkerOptions().position(destination).title("Destination"))
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(origin, 14.5f))
        val path: MutableList<List<LatLng>> = ArrayList()
        val ori = "${origin.latitude},${origin.longitude}"
        val des = "${destination.latitude},${destination.longitude}"

        val urlDirections = "https://maps.googleapis.com/maps/api/directions/json?origin=$ori&destination=$des&key=AIzaSyAQ7FVP_6AWZD1AEC8DX2YEv340-esE6Mo"

        val directionsRequest = object : StringRequest(Method.GET, urlDirections, Response.Listener<String> {
              response ->
            val jsonResponse = JSONObject(response)
            // Get routes
            val routes = jsonResponse.getJSONArray("routes")
            val legs = routes.getJSONObject(0).getJSONArray("legs")
            val steps = legs.getJSONObject(0).getJSONArray("steps")
            val distance = legs.getJSONObject(0).getJSONObject("distance").getString("text")
            val duration = legs.getJSONObject(0).getJSONObject("duration").getString("text")

            Log.d("SANA", "$distance, $duration")

            val builder: AlertDialog.Builder = AlertDialog.Builder(context)
            builder.setTitle("Konfirmasi")
            builder.setMessage("Durasi: $duration\nJarak: $distance")
            builder.setCancelable(false)
            builder.setPositiveButton("OK") { _, _ ->
                // TODO: 12/9/2020 KALO PENCET OK LAKUIN APA
                for (i in 0 until steps.length()) {
                    val points = steps.getJSONObject(i).getJSONObject("polyline").getString("points")
                    path.add(PolyUtil.decode(points))
                }
                for (i in 0 until path.size) {
                    map.addPolyline(PolylineOptions().addAll(path[i]).color(Color.RED))
                }

                pushFirebase(ori, des, distance, duration)
            }
            builder.setNegativeButton("Cancel") { dialog, _ ->
                dialog.cancel()
            }
            val alertDialog: AlertDialog = builder.create()
            alertDialog.show()

        }, Response.ErrorListener {}){}

        val requestQueue = Volley.newRequestQueue(context)
        requestQueue.add(directionsRequest)
    }

    private fun pushFirebase(origin: String, destination: String, distance: String, duration: String){
        val currentUser = mAuth.currentUser
        val recordRef = myRef.child("Users").child(currentUser!!.uid).child("record").child(java.util.Calendar.getInstance().time.toString())
        recordRef.child("origin").setValue(origin)
        recordRef.child("destination").setValue(destination)
        recordRef.child("distance").setValue(distance)
        recordRef.child("duration").setValue(duration)
    }

}