package universal.appfactory.smartcity.data

import com.google.gson.annotations.SerializedName

data class PublicTransportLinesModel(
    @SerializedName("features")
    val features: ArrayList<TransportLineFeatures>,
    @SerializedName("type")
    val type: String
)
