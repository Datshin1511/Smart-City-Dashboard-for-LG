package universal.appfactory.smartcity.API

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.*
import universal.appfactory.smartcity.data.*

interface ApiInterface {

//    SERVER_URL_1
    @GET("v1")
    fun getServices(@Query("search") search: String, @Query("categories") categories: String, @Query("maxResults") maxResults: Int): Call<ServicesModel>?

    @GET("v1/events")
    fun getEvents(): Call<EventsModel>?

    @GET("v1/location")
    fun getLocation(@Query("search") search: String): Call<LocationModel>?

    @GET("v1/tpl/agencies")
    fun getTransportAgency(): Call<TransportAgencyModel>?

    @GET("v1/tpl/bus-lines")
    fun getBusLines(@Query("agency") agency: String): Call<BusLinesModel>?

    @GET("v1/tpl/bus-routes")
    fun getSpecificBusRoutes(@Query("busStopName") busStopName: String): Call<SpecificBusRoutesModel>?

//    @GET("v1/tpl/bus-stops")
//    fun getBusStops(@Query("route") route: String, @Query("geometry") geometry: Boolean = true): Call<BusStopsModel>?

//    @GET("v1/tpl/")
//    fun getBusRoutes(@Query("selection") selection: String): Call<BusRoutesModel>?

//    @GET("v1/tpl/bus-position")
//    fun getBusPosition(@Query("agency") selection: String): Call<BusPositionModel>?

    @GET("v1/shortestpath")
    fun getShortestPath(): Call<ShortestPathModel>?

//    SERVER_URL_2
    @GET("webapp/")
    fun aroundMe(@Query("operation") operation: String)

}