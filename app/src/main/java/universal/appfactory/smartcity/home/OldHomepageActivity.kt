package universal.appfactory.smartcity.home

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import universal.appfactory.smartcity.API.*
import universal.appfactory.smartcity.R
import universal.appfactory.smartcity.data.*
import universal.appfactory.smartcity.Settings.*

// GOOGLE MAPS API KEY: AIzaSyC-d1CUddRIqpOmweMSdUhYvvq6IbcW6oU

@Suppress("UNUSED_PARAMETER")
class OldHomepageActivity : AppCompatActivity() {

    private lateinit var navigableIntent: Intent
    private var backpress: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_homepage)

    }

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

                               Log.i("Response", response.body().toString())

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
            "help" -> Intent(this@OldHomepageActivity, HelpActivity::class.java)
            "connect" -> Intent(this@OldHomepageActivity, SettingsActivity::class.java)
            "tasks" -> Intent(this@OldHomepageActivity, TasksActivity::class.java)
            "credits" -> Intent(this@OldHomepageActivity, CreditsActivity::class.java)
            else -> Intent(this@OldHomepageActivity, OldHomepageActivity::class.java)
        }

        startActivity(navigableIntent)
    }

}