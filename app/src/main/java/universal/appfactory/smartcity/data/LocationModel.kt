package universal.appfactory.smartcity.data

import com.google.gson.annotations.SerializedName

data class LocationModel(
    @SerializedName("type")
    val type: String,
    @SerializedName("count")
    val count: Int,
    @SerializedName("features")
    val features: ArrayList<LocationFeaturesModel>,
    @SerializedName("apiDoc")
    val apiDoc: String = "None",
    @SerializedName("failure")
    val failure: String = "SAFE",
    @SerializedName("httpcode")
    val httpcode: Int = 0,
    @SerializedName("message")
    val message: String = "Success"
)
