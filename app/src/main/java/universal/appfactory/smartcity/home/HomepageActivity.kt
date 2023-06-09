package universal.appfactory.smartcity.home

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.speech.RecognizerIntent
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener
import com.jcraft.jsch.ChannelExec
import com.jcraft.jsch.JSch
import com.jcraft.jsch.Session
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import universal.appfactory.smartcity.API.ApiInterface
import universal.appfactory.smartcity.API.ServiceBuilder
import universal.appfactory.smartcity.R
import universal.appfactory.smartcity.Settings.CreditsActivity
import universal.appfactory.smartcity.Settings.HelpActivity
import universal.appfactory.smartcity.Settings.SettingsActivity
import universal.appfactory.smartcity.Settings.TasksActivity
import universal.appfactory.smartcity.adapters.PageAdapter
import universal.appfactory.smartcity.data.*

@Suppress("UNUSED_PARAMETER")
class HomepageActivity : AppCompatActivity() {

    private lateinit var navigableIntent: Intent
    private var backpress: Long = 0
    private lateinit var viewPager: ViewPager
    private lateinit var tabLayout: TabLayout

    private var busStopKML: ArrayList<LocationFeaturesModel>? = null
    private var sensorSiteKML: ArrayList<LocationFeaturesModel>? = null
    private var serviceKML: ArrayList<LocationFeaturesModel>? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_homepage)

        supportActionBar!!.displayOptions = ActionBar.DISPLAY_SHOW_CUSTOM
        supportActionBar!!.setDisplayShowCustomEnabled(true)
        supportActionBar!!.setDisplayShowTitleEnabled(false)
        supportActionBar!!.setCustomView(R.layout.action_bar_layout_homepage)

        viewPager = findViewById(R.id.pager)
        tabLayout = findViewById(R.id.tabLayout)
        viewPager.adapter = PageAdapter(supportFragmentManager, 3)

        tabLayout.setSelectedTabIndicatorColor(Color.GREEN)
        tabLayout.addOnTabSelectedListener(object : OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                if(tabLayout.selectedTabPosition == 1){
                    startActivity(Intent(this@HomepageActivity, MapsActivity::class.java))
                    tabLayout.getTabAt(0)?.select()
                }

            }

            override fun onTabUnselected(tab: TabLayout.Tab) {}
            override fun onTabReselected(tab: TabLayout.Tab) {}
        })

        tabLayout.setupWithViewPager(viewPager)
    }

    // Dashboard
    fun smartTask(view: View){
        when(view.tag.toString()){

            "1" -> {
                val response = ServiceBuilder.buildServiceURL1(ApiInterface::class.java)

                @OptIn(DelicateCoroutinesApi::class)
                GlobalScope.launch(Dispatchers.IO) {

                    // Constant values passed to the function
                    response.getServices(search="3.7741;11.2453;43.7768;11.2515", categories="Accommodation;BusStop;SensorSite;Car_park", maxResults=10)?.enqueue(
                        object : Callback<ServicesModel> {
                            override fun onResponse(
                                call: Call<ServicesModel>,
                                response: Response<ServicesModel>
                            ) {
                                val busStops: ServiceFeatureModel? = response.body()?.BusStops
                                val sensorSites: ServiceFeatureModel? = response.body()?.SensorSites
                                val services: ServiceFeatureModel? = response.body()?.Services

                                val busStopsFeatures = busStops?.features
                                val sensorSitesFeatures = sensorSites?.features
                                val servicesFeatures = services?.features

                                if (busStopsFeatures != null) {
                                    for(i in 0 until busStopsFeatures.size){
                                        Log.i("Bus stops ${i+1}", "Type: ${busStopsFeatures[i].type}\n" +
                                                "Name: ${busStopsFeatures[i].properties.name}\n" +
                                                "Geometry type: ${busStopsFeatures[i].geometry.type}\n" +
                                                "Geometry coordinates: ${busStopsFeatures[i].geometry.coordinates}\n" +
                                                "Service URI: ${busStopsFeatures[i].properties.serviceUri}\n" +
                                                "Service Type: ${busStopsFeatures[i].properties.serviceType}\n" +
                                                "Agency: ${busStopsFeatures[i].properties.agency}\n" +
                                                "Photo thumbs: ${busStopsFeatures[i].properties.photoThumbs}\n" +
                                                "Agency URI: ${busStopsFeatures[i].properties.agencyUri}\n" +
                                                "Distance: ${busStopsFeatures[i].properties.distance}\n" +
                                                "Tipo: ${busStopsFeatures[i].properties.tipo}\n" +
                                                "Type Label: ${busStopsFeatures[i].properties.typeLabel}\n" +
                                                "Feature type: ${busStopsFeatures[i].type}")
                                    }
                                    busStopKML = busStopsFeatures
                                }

                                if (sensorSitesFeatures != null) {
                                    for(i in 0 until sensorSitesFeatures.size){
                                        Log.i("Sensor sites ${i+1}", "Type: ${sensorSitesFeatures[i].type}\n" +
                                                "Name: ${sensorSitesFeatures[i].properties.name}\n" +
                                                "Geometry type: ${sensorSitesFeatures[i].geometry.type}\n" +
                                                "Geometry coordinates: ${sensorSitesFeatures[i].geometry.coordinates}\n" +
                                                "Service URI: ${sensorSitesFeatures[i].properties.serviceUri}\n" +
                                                "Service Type: ${sensorSitesFeatures[i].properties.serviceType}\n" +
                                                "Photo thumbs: ${sensorSitesFeatures[i].properties.photoThumbs}\n" +
                                                "Distance: ${sensorSitesFeatures[i].properties.distance}\n" +
                                                "Tipo: ${sensorSitesFeatures[i].properties.tipo}\n" +
                                                "Type Label: ${sensorSitesFeatures[i].properties.typeLabel}\n" +
                                                "Feature type: ${sensorSitesFeatures[i].type}")
                                    }
                                    sensorSiteKML = sensorSitesFeatures
                                }

                                if (servicesFeatures != null) {
                                    for(i in 0 until servicesFeatures.size){
                                        Log.i("Service ${i+1}", "Type: ${servicesFeatures[i].type}\n" +
                                                "Name: ${servicesFeatures[i].properties.name}\n" +
                                                "Geometry type: ${servicesFeatures[i].geometry.type}\n" +
                                                "Geometry coordinates: ${servicesFeatures[i].geometry.coordinates}\n" +
                                                "Service URI: ${servicesFeatures[i].properties.serviceUri}\n" +
                                                "Service Type: ${servicesFeatures[i].properties.serviceType}\n" +
                                                "Multimedia: ${servicesFeatures[i].properties.multimedia}\n" +
                                                "Photo thumbs: ${servicesFeatures[i].properties.photoThumbs}\n" +
                                                "Distance: ${servicesFeatures[i].properties.distance}\n" +
                                                "Tipo: ${servicesFeatures[i].properties.tipo}\n" +
                                                "Type Label: ${servicesFeatures[i].properties.typeLabel}\n" +
                                                "Feature type: ${servicesFeatures[i].type}")
                                    }
                                    serviceKML = servicesFeatures
                                }

                                Log.i("API status", "Success")
                                findViewById<ProgressBar>(R.id.progressbar).visibility = View.GONE
                            }

                            override fun onFailure(call: Call<ServicesModel>, t: Throwable) {
                                Log.e("Error message", t.toString())
                                findViewById<ProgressBar>(R.id.progressbar).visibility = View.GONE
                            }

                        }
                    )
                }
                findViewById<ProgressBar>(R.id.progressbar).visibility = View.VISIBLE
            }

            "2" -> {
                val response = ServiceBuilder.buildServiceURL1(ApiInterface::class.java)

                @OptIn(DelicateCoroutinesApi::class)
                GlobalScope.launch(Dispatchers.IO) {

                    response.getEvents()?.enqueue(
                        object : Callback<EventsModel> {
                            override fun onResponse(
                                call: Call<EventsModel>,
                                response: Response<EventsModel>
                            ) {
                                val message = response.body()?.Event as EventsContentModel
                                Log.i("Events data", "Count: ${message.count}" +
                                        "\nFeatures: ${message.features}" +
                                        "\nFull count: ${message.fullCount}" +
                                        "\nType: ${message.type}")

                                findViewById<ProgressBar>(R.id.progressbar).visibility = View.GONE
                            }

                            override fun onFailure(call: Call<EventsModel>, t: Throwable) {
                                Log.e("Error message", t.toString())
                                findViewById<ProgressBar>(R.id.progressbar).visibility = View.VISIBLE
                            }

                        }
                    )
                }
            }

            "3" -> {
                val response = ServiceBuilder.buildServiceURL1(ApiInterface::class.java)

                @OptIn(DelicateCoroutinesApi::class)
                GlobalScope.launch(Dispatchers.IO) {

                    response.getLocation(search = findViewById<EditText>(R.id.city).text.toString())?.enqueue(
                        object : Callback<LocationModel> {
                            override fun onResponse(
                                call: Call<LocationModel>,
                                response: Response<LocationModel>
                            ) {
                                val failure = response.body()?.failure.toString()

                                if(failure=="null"){
                                    Log.i("Location API Response", "Type: ${response.body()?.type.toString()}\n" +
                                            "Count: ${response.body()?.count.toString()}")

                                    val features: ArrayList<LocationFeaturesModel>? = response.body()?.features
                                    if (features != null) {
                                        Log.i("Feature size", features.size.toString())
                                    }

                                    if (features != null) {
                                        for(i in 0 until features.size){
                                            Log.i("Feature ${i+1}", "Type: ${features[i].type}\n" +
                                                    "Geometry type: ${features[i].geometry.type}\n" +
                                                    "Geometry coordinates: ${features[i].geometry.coordinates}\n" +
                                                    "Service URI: ${features[i].properties.serviceUri}\n" +
                                                    "Service Type: ${features[i].properties.serviceType}\n" +
                                                    "Address: ${features[i].properties.address}\n" +
                                                    "Civic: ${features[i].properties.civic}\n" +
                                                    "City: ${features[i].properties.city}\n" +
                                                    "Score: ${features[i].properties.score}\n" +
                                                    "ID: ${features[i].properties.id}")
                                        }
                                    }
                                }
                                else{
                                    Log.i("Location API status", "Message: ${response.body()?.message.toString()}\n" +
                                            "API DOC: ${response.body()?.apiDoc.toString()}\n" +
                                            "HTTP CODE: ${response.body()?.httpcode.toString()}")
                                }
                                findViewById<ProgressBar>(R.id.progressbar).visibility = View.GONE
                            }

                            override fun onFailure(call: Call<LocationModel>, t: Throwable) {
                                Log.e("Error message", t.toString())
                                findViewById<ProgressBar>(R.id.progressbar).visibility = View.GONE
                            }

                        }
                    )
                }
                findViewById<ProgressBar>(R.id.progressbar).visibility = View.VISIBLE
            }

            "4" -> {
                val response = ServiceBuilder.buildServiceURL1(ApiInterface::class.java)

                @OptIn(DelicateCoroutinesApi::class)
                GlobalScope.launch(Dispatchers.IO) {

                    response.getTransportAgency()?.enqueue(
                        object : Callback<TransportAgencyModel> {
                            override fun onResponse(
                                call: Call<TransportAgencyModel>,
                                response: Response<TransportAgencyModel>
                            ) {
                                val agencies = response.body()?.Agencies as ArrayList<AgencyModel>

                                for(i in 0 until agencies.size){
                                    Log.i("Agency data ${i+1}", "Agency: ${agencies[i].agency}" +
                                            "\nName: ${agencies[i].name}")
                                }

                                findViewById<ProgressBar>(R.id.progressbar).visibility = View.GONE
                            }

                            override fun onFailure(call: Call<TransportAgencyModel>, t: Throwable) {
                                Log.e("Error message", t.toString())
                                findViewById<ProgressBar>(R.id.progressbar).visibility = View.GONE
                            }

                        }
                    )
                }
                findViewById<ProgressBar>(R.id.progressbar).visibility = View.VISIBLE
            }

            "5" -> {
                val response = ServiceBuilder.buildServiceURL1(ApiInterface::class.java)

                @OptIn(DelicateCoroutinesApi::class)
                GlobalScope.launch(Dispatchers.IO) {

                    response.getShortestPath()?.enqueue(
                        object : Callback<ShortestPathModel> {
                            override fun onResponse(
                                call: Call<ShortestPathModel>,
                                response: Response<ShortestPathModel>
                            ) {

                                Log.i("Location API status", "Message: ${response.body()?.message.toString()}\n" +
                                        "API DOC: ${response.body()?.apiDoc.toString()}\n" +
                                        "HTTP CODE: ${response.body()?.httpcode.toString()}")

                                findViewById<ProgressBar>(R.id.progressbar).visibility = View.GONE
                            }

                            override fun onFailure(call: Call<ShortestPathModel>, t: Throwable) {
                                Log.e("Error message", t.toString())
                                findViewById<ProgressBar>(R.id.progressbar).visibility = View.GONE
                            }

                        }
                    )
                }
                findViewById<ProgressBar>(R.id.progressbar).visibility = View.VISIBLE
            }

            "6" -> {
                val response = ServiceBuilder.buildServiceURL1(ApiInterface::class.java)

                @OptIn(DelicateCoroutinesApi::class)
                GlobalScope.launch(Dispatchers.IO) {

                    response.getBusLines(agency = "http://www.disit.org/km4city/resource/Bus_de_lijn_zip_ST_Agency_1")?.enqueue(
                        object : Callback<BusLinesModel> {
                            override fun onResponse(
                                call: Call<BusLinesModel>,
                                response: Response<BusLinesModel>
                            ) {

                                val busLines = response.body()?.BusLines as ArrayList<BusLineInfoModel>

                                for(i in 0 until busLines.size){
                                    Log.i("BUS LINE ${i+1}", "Agency: ${busLines[i].agency}\n" +
                                            "Short name: ${busLines[i].shortName}\n" +
                                            "Long name: ${busLines[i].longName}\n" +
                                            "URI: ${busLines[i].uri}")
                                }

                                findViewById<ProgressBar>(R.id.progressbar).visibility = View.GONE
                            }

                            override fun onFailure(call: Call<BusLinesModel>, t: Throwable) {
                                Log.e("Error message", t.toString())
                                findViewById<ProgressBar>(R.id.progressbar).visibility = View.GONE
                            }

                        }
                    )
                }
                findViewById<ProgressBar>(R.id.progressbar).visibility = View.VISIBLE
            }

            "7" -> {
                val response = ServiceBuilder.buildServiceURL1(ApiInterface::class.java)

                @OptIn(DelicateCoroutinesApi::class)
                GlobalScope.launch(Dispatchers.IO) {

                    response.getSpecificBusRoutes(busStopName = "Stazione Pensilina")?.enqueue(
                        object : Callback<SpecificBusRoutesModel> {
                            override fun onResponse(
                                call: Call<SpecificBusRoutesModel>,
                                response: Response<SpecificBusRoutesModel>
                            ) {
                                val busRoutes = response.body()?.BusRoutes
                                Log.i("Bus Routes", busRoutes.toString())

                                findViewById<ProgressBar>(R.id.progressbar).visibility = View.GONE
                            }

                            override fun onFailure(call: Call<SpecificBusRoutesModel>, t: Throwable) {
                                Log.e("Error message", t.toString())
                                findViewById<ProgressBar>(R.id.progressbar).visibility = View.GONE
                            }

                        }
                    )
                }
                findViewById<ProgressBar>(R.id.progressbar).visibility = View.VISIBLE
            }

            "8" -> {
                val response = ServiceBuilder.buildServiceURL1(ApiInterface::class.java)

                @OptIn(DelicateCoroutinesApi::class)
                GlobalScope.launch(Dispatchers.IO) {

                    response.getBusRoutes()?.enqueue(
                        object : Callback<BusRoutesModel> {
                            override fun onResponse(
                                call: Call<BusRoutesModel>,
                                response: Response<BusRoutesModel>
                            ) {
                                val routeFeatures = response.body()?.PublicTransportLine?.features

                                if (routeFeatures != null) {
                                    for(i in 0 until routeFeatures.size)
                                        Log.i("Route ${i+1}", "ID: ${routeFeatures[i].id}\n" +
                                                "Service Type: ${routeFeatures[i].properties.serviceType}\n" +
                                                "Type: ${routeFeatures[i].type}\n" +
                                                "Agency: ${routeFeatures[i].properties.agency}\n" +
                                                "Agency URI: ${routeFeatures[i].properties.agencyUri}\n" +
                                                "Direction: ${routeFeatures[i].properties.direction}\n" +
                                                "Line name: ${routeFeatures[i].properties.lineName}\n" +
                                                "Line number: ${routeFeatures[i].properties.lineNumber}\n" +
                                                "Poly line: ${routeFeatures[i].properties.polyline}\n" +
                                                "Route: ${routeFeatures[i].properties.route}\n" +
                                                "Route URI: ${routeFeatures[i].properties.routeUri}\n"
                                        )
                                }

                                findViewById<ProgressBar>(R.id.progressbar).visibility = View.GONE
                            }

                            override fun onFailure(call: Call<BusRoutesModel>, t: Throwable) {
                                Log.e("Error message", t.toString())
                                findViewById<ProgressBar>(R.id.progressbar).visibility = View.GONE
                            }

                        }
                    )
                }
                findViewById<ProgressBar>(R.id.progressbar).visibility = View.VISIBLE
            }

            else -> { Log.i("Smart Task", "No API response")}
        }
    }
    fun visualize(view: View){
        val builder: AlertDialog.Builder = AlertDialog.Builder(this)
        builder.setPositiveButton("Cancel") {_, _ ->}
        val customLayout: View = layoutInflater.inflate(R.layout.visualization_alertbox, null)
        builder.setView(customLayout)

        builder.create().show()
    }
    fun visualizeInApp(it: View){
        val BusKMLdata= buildBusStopKML()
        val SensorSitesKMLData = buildSensorSiteKML()
        val ServiceKMLData = buildServiceKML()

        if(BusKMLdata != "null"){
            val navigableIntent = Intent(this@HomepageActivity, MapsActivity::class.java)
            navigableIntent.putExtra("BusKMLData", BusKMLdata)
            navigableIntent.putExtra("SensorSitesKMLData", SensorSitesKMLData)
            navigableIntent.putExtra("ServiceKMLData", ServiceKMLData)
            startActivity(navigableIntent)
        }
        else
            Toast.makeText(this, "Select a parameter", Toast.LENGTH_SHORT).show()
    }
    fun visualizeInLG(it: View){
        val BusKMLData = buildBusStopKML()
        val SensorSitesKMLData = buildSensorSiteKML()
        val ServiceKMLData = buildServiceKML()

        @OptIn(DelicateCoroutinesApi::class)
        GlobalScope.launch(Dispatchers.IO) {

            try{
                val session = setSession()
                session?.connect()

                val channel = session?.openChannel("Exec")
                val command = ""

                (channel as ChannelExec).setCommand(command)

                // Disconnection from LG rigs
                channel.disconnect()
                session.disconnect()

                findViewById<ProgressBar>(R.id.progressbar).visibility = View.GONE
            }
            catch(e: Exception){
                runOnUiThread {
                    findViewById<ProgressBar>(R.id.progressbar).visibility = View.GONE
                    Toast.makeText(this@HomepageActivity, "Error", Toast.LENGTH_SHORT).show()
                }
                e.printStackTrace()
            }
        }
        findViewById<ProgressBar>(R.id.progressbar).visibility = View.VISIBLE
    }

    private fun buildBusStopKML(): String {
        var KMLdata = "null"

        if (busStopKML != null) {
            var placemarkers: String = """"""

            for (i in 0 until busStopKML!!.size) {
                placemarkers += """
                <Placemark>
                  <name>Bus Stop: ${i + 1}, ${busStopKML!![i].properties.name}</name>
                  <description>Distance: ${busStopKML!![i].properties.distance}, 
                  Service Type: ${busStopKML!![i].properties.typeLabel}</description>
                  <Point>
                    <coordinates>${busStopKML!![i].geometry.coordinates[0]}, ${busStopKML!![i].geometry.coordinates[1]}</coordinates>
                  </Point>
                </Placemark>
            """
            }

            KMLdata = """
            <kml xmlns="http://www.opengis.net/kml/2.2">
              <Document>
                <name>My Placemark</name>
                <description>Sample placemark</description>
                $placemarkers
              </Document>
            </kml>
        """
        }

        return KMLdata
    }
    private fun buildSensorSiteKML(): String {
        var KMLdata = "null"

        if (sensorSiteKML != null) {
            var placemarkers: String = """"""

            for (i in 0 until sensorSiteKML!!.size) {
                placemarkers += """
                <Placemark>
                  <name>Sensor site: ${i + 1}, ${sensorSiteKML!![i].properties.name}</name>
                  <description>Distance: ${sensorSiteKML!![i].properties.distance}, 
                  Service Type: ${sensorSiteKML!![i].properties.typeLabel}</description>
                  <Point>
                    <coordinates>${sensorSiteKML!![i].geometry.coordinates[0]}, ${sensorSiteKML!![i].geometry.coordinates[1]}</coordinates>
                  </Point>
                </Placemark>
            """
            }

            KMLdata = """
            <kml xmlns="http://www.opengis.net/kml/2.2">
              <Document>
                <name>Sensor sites placemarks</name>
                <description>Sensor sites placemarks that are used to display on a map</description>
                $placemarkers
              </Document>
            </kml>
        """
        }

        return KMLdata
    }
    private fun buildServiceKML(): String {
        var KMLdata = "null"

        if (serviceKML != null) {
            var placemarkers: String = """"""

            for (i in 0 until serviceKML!!.size) {
                placemarkers += """
                <Placemark>
                  <name>Service: ${i + 1}, ${serviceKML!![i].properties.name}</name>
                  <description>Distance: ${serviceKML!![i].properties.distance}, 
                  Service Type: ${serviceKML!![i].properties.typeLabel}</description>
                  <Point>
                    <coordinates>${serviceKML!![i].geometry.coordinates[0]}, ${serviceKML!![i].geometry.coordinates[1]}</coordinates>
                  </Point>
                </Placemark>
            """
            }

            KMLdata = """
            <kml xmlns="http://www.opengis.net/kml/2.2">
              <Document>
                <name>Services placemarks</name>
                <description>Service placemarks that are used to display on a map.</description>
                $placemarkers
              </Document>
            </kml>
        """
        }

        return KMLdata
    }
    private fun setSession(): Session? {

        val sharedPreferences = getSharedPreferences("LGConnectionData", MODE_PRIVATE)
        val port = sharedPreferences.getString("port", "22")

        val jsch = JSch()
        val session = port?.let {
            jsch.getSession(sharedPreferences.getString("username", "lg").toString(),
                sharedPreferences.getString("host", "192.168.201.3").toString(),
                it.toInt())
        }
        session?.setPassword(sharedPreferences.getString("password", "lg").toString())
        session?.timeout = 10000
        session?.setConfig("StrictHostKeyChecking", "no")

        return session
    }


    // Speech Recognition
    private val speechRecognitionLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                val data = result.data
                val taskText = data?.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS) as ArrayList<String>
                Log.i("Speech processed", taskText[0])
            }
        }
    private fun speechRecognition(){
        val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
        intent.putExtra(
            RecognizerIntent.EXTRA_LANGUAGE_MODEL,
            RecognizerIntent.LANGUAGE_MODEL_FREE_FORM
        )
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Speak now")
        speechRecognitionLauncher.launch(intent)

    }


    // Common
    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        if(backpress + 2000 > System.currentTimeMillis())
            this.finishAffinity()
        else
            Toast.makeText(this, "Press back again to exit", Toast.LENGTH_SHORT).show()

        backpress = System.currentTimeMillis()
        Log.i("System current Time in millis", backpress.toString())
    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }
    fun menu(item: MenuItem){

        navigableIntent = when(item.titleCondensed){
            "help" -> Intent(this@HomepageActivity, HelpActivity::class.java)
            "connect" -> Intent(this@HomepageActivity, SettingsActivity::class.java)
            "tasks" -> Intent(this@HomepageActivity, TasksActivity::class.java)
            "credits" -> Intent(this@HomepageActivity, CreditsActivity::class.java)
            else -> Intent(this@HomepageActivity, OldHomepageActivity::class.java)
        }

        if(item.titleCondensed == "Voice")
            speechRecognition()
        else
            startActivity(navigableIntent)
    }

}