package universal.appfactory.smartcity.data

import com.google.gson.annotations.SerializedName

data class TransportLineProperties(
    @SerializedName("agency")
    val agency: String,
    @SerializedName("agencyUri")
    val agencyUri: String,
    @SerializedName("direction")
    val direction: String,
    @SerializedName("lineName")
    val lineName: String,
    @SerializedName("lineNumber")
    val lineNumber: String,
    @SerializedName("polyline")
    val polyline: String,
    @SerializedName("route")
    val route: String,
    @SerializedName("routeUri")
    val routeUri: String,
    @SerializedName("serviceType")
    val serviceType: String
)
