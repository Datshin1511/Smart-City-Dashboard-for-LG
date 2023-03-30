package universal.appfactory.smartcity.data

import com.google.gson.annotations.SerializedName

data class EventsContentModel(
    @SerializedName("count")
    val count: Int,
    @SerializedName("features")
    val features: ArrayList<String>,
    @SerializedName("fullCount")
    val fullCount: String,
    @SerializedName("type")
    val type: String
)
