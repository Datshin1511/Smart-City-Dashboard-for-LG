package universal.appfactory.smartcity.data

import com.google.gson.annotations.SerializedName

data class TransportAgencyModel(
    @SerializedName("Agencies")
    val Agencies: ArrayList<AgencyModel>
)
