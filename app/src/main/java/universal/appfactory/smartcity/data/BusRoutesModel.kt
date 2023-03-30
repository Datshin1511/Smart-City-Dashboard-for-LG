package universal.appfactory.smartcity.data

import com.google.gson.annotations.SerializedName

data class BusRoutesModel(
    @SerializedName("PublicTransportLine")
    val PublicTransportLine: PublicTransportLinesModel
)
