package universal.appfactory.smartcity.data

import com.google.gson.annotations.SerializedName

data class ServicesModel(
    @SerializedName("BusStops")
    val BusStops: ServiceFeatureModel,
    @SerializedName("SensorSites")
    val SensorSites: ServiceFeatureModel,
    @SerializedName("Services")
    val Services: ServiceFeatureModel,
    @SerializedName("apiDoc")
    val apiDoc: String = "-",
    @SerializedName("failure")
    val failure: String = "SAFE",
    @SerializedName("httpcode")
    val httpcode: Int = 0,
    @SerializedName("message")
    val message: String = "-"
)
