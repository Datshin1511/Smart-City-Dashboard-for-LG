package universal.appfactory.smartcity.data

import com.google.gson.annotations.SerializedName

data class ShortestPathModel(
    @SerializedName("failure")
    val failure: String = "SAFE",
    @SerializedName("httpcode")
    val httpcode: Int,
    @SerializedName("message")
    val message: String,
    @SerializedName("apiDoc")
    val apiDoc: String
)
