package universal.appfactory.smartcity.home

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.ActionBar

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import universal.appfactory.smartcity.R
import universal.appfactory.smartcity.Settings.CreditsActivity
import universal.appfactory.smartcity.Settings.HelpActivity
import universal.appfactory.smartcity.Settings.SettingsActivity
import universal.appfactory.smartcity.Settings.TasksActivity
import universal.appfactory.smartcity.databinding.ActivityMapsBinding

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityMapsBinding
    private lateinit var navigableIntent: Intent

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        supportActionBar!!.displayOptions = ActionBar.DISPLAY_SHOW_CUSTOM
        supportActionBar!!.setDisplayShowCustomEnabled(true)
        supportActionBar!!.setDisplayShowTitleEnabled(false)
        supportActionBar!!.setCustomView(R.layout.action_bar_layout_homepage)

        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)

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

        // Add a marker in Sydney and move the camera
        val location = LatLng(25.2623, 82.9893)
        val zoomFactor = 14.0F
        mMap.addMarker(MarkerOptions().position(location).title("Marker in Varanasi, India"))
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location, zoomFactor))
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }
    fun menu(item: MenuItem){

        navigableIntent = when(item.titleCondensed){
            "help" -> Intent(this@MapsActivity, HelpActivity::class.java)
            "connect" -> Intent(this@MapsActivity, SettingsActivity::class.java)
            "tasks" -> Intent(this@MapsActivity, TasksActivity::class.java)
            "credits" -> Intent(this@MapsActivity, CreditsActivity::class.java)
            else -> Intent(this@MapsActivity, OldHomepageActivity::class.java)
        }

        startActivity(navigableIntent)
    }
}