package universal.appfactory.smartcity.data

import com.google.gson.annotations.SerializedName

data class TransportLineFeatures(
    @SerializedName("id")
    val id: String,
    @SerializedName("properties")
    val properties: TransportLineProperties,
    @SerializedName("type")
    val type: String
)
