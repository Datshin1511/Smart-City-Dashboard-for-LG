package universal.appfactory.smartcity.data

import com.google.gson.annotations.SerializedName

data class BusLineInfoModel(
    @SerializedName("agency")
    val agency: String?,
    @SerializedName("shortName")
    val shortName: String?,
    @SerializedName("longName")
    val longName: String?,
    @SerializedName("uri")
    val uri: String?
)
