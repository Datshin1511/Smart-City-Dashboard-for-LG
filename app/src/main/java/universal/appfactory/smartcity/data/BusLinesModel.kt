package universal.appfactory.smartcity.data

import com.google.gson.annotations.SerializedName

data class BusLinesModel(
    @SerializedName("BusLines")
    val BusLines: ArrayList<BusLineInfoModel>?
)
