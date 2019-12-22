package edu.uoc.android

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ProgressBar

import androidx.core.app.ActivityCompat
import androidx.fragment.app.FragmentActivity

import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.location.FusedLocationProviderClient


import edu.uoc.android.models.Museums
import edu.uoc.android.rest.RetrofitFactory
import kotlinx.android.synthetic.main.activity_maps.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MapsActivity : FragmentActivity(), OnMapReadyCallback {

    private var mMap: GoogleMap? = null
    // Request code
    private val REQUEST_PERMISSION_LOCATION = 101

    private var fusedLocationClient: FusedLocationProviderClient? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
                .findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment!!.getMapAsync(this)

//       val myProgressBarForMaps = findViewById<View>(R.id.myProgressBarForMaps) as ProgressBar

        // Create an instance of GoogleAPIClient.
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

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

        getMuseums()
        enableLocation()
        // Add a marker in XX and move the camera
        val center = LatLng(41.5669224, 2.0179072)
        //mMap.addMarker(new MarkerOptions().position(center).title("Marker in XX"));
        //mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(center,10));


    }

    private fun centerMapToUser(location: Location) {
        val latLng = LatLng(location.latitude, location.longitude)
        mMap!!.moveCamera(CameraUpdateFactory.newLatLng(latLng))
        mMap!!.animateCamera(CameraUpdateFactory.zoomTo(DEFAULT_ZOOM))
    }

    private fun enableLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                    REQUEST_PERMISSION_LOCATION)
        } else {
            // Enable location button
            mMap!!.isMyLocationEnabled = true
            fusedLocationClient!!.lastLocation
                    .addOnSuccessListener(this) { location ->
                        // Got last known location. In some rare situations this can be null.
                        if (location != null) {
                            centerMapToUser(location)
                        }
                    }
        }
    }

    protected fun showProgress(show: Boolean) {
        this.myProgressBarForMaps!!.visibility = if (show) View.VISIBLE else View.GONE
    }

    private fun getMuseums() {
        val museumAPI = RetrofitFactory.getMuseumAPI()

        showProgress(true)
        val callsList = museumAPI.museums("1", "25")

        callsList.enqueue(object : Callback<Museums> {
            override fun onResponse(call: Call<Museums>, response: Response<Museums>) {
                val statusCode = response.code()
                if (response.isSuccessful) {
                    showProgress(false)
                    val museums = response.body()
                    for (element in museums!!.elements) {
                        val localitzacio = element.localitzacio.split(",".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
                        val latLng = LatLng(java.lang.Double.parseDouble(localitzacio[0]), java.lang.Double.parseDouble(localitzacio[1]))
                        val option = MarkerOptions()
                        option.position(latLng).title(element.adrecaNom).icon(BitmapDescriptorFactory.fromResource(
                                R.drawable.ic_library))
                        mMap!!.addMarker(option)
                    }
                }
            }

            override fun onFailure(call: Call<Museums>, t: Throwable) {
                Log.d("PEC3", "  onFAILURE!!!!: msg:" + t.message)
            }
        })
    }

    companion object {
        private val DEFAULT_ZOOM = 10.0f
    }
}
