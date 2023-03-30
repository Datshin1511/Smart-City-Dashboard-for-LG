package universal.appfactory.smartcity.data

import com.google.gson.annotations.SerializedName

data class SpecificBusRoutesModel(
    @SerializedName("BusRoutes")
    val BusRoutes: ArrayList<String>
)
