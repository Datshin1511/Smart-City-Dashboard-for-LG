package universal.appfactory.smartcity.data

import com.google.gson.annotations.SerializedName

data class LocationFeaturesModel(
    @SerializedName("geometry")
    val geometry: GeometryLocationModel,
    @SerializedName("type")
    val type: String = "-",
    @SerializedName("id")
    val id: Int = 0,
    @SerializedName("properties")
    val properties: PropertiesLocationModel
)
