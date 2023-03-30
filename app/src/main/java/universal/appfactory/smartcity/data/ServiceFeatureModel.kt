package universal.appfactory.smartcity.data

import com.google.gson.annotations.SerializedName

data class ServiceFeatureModel(
    @SerializedName("type")
    val type: String="-",
    @SerializedName("features")
    val features: ArrayList<LocationFeaturesModel>,
    @SerializedName("fullCount")
    val fullCount: Int = 0
)
