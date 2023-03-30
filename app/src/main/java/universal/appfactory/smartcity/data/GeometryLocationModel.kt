package universal.appfactory.smartcity.data

import com.google.gson.annotations.SerializedName

data class GeometryLocationModel(
    @SerializedName("type")
    val type: String = "-",
    @SerializedName("coordinates")
    val coordinates: ArrayList<Double> = arrayListOf(0.0, 0.0)
)
