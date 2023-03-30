package universal.appfactory.smartcity.data

import com.google.gson.annotations.SerializedName

data class AgencyModel(
    @SerializedName("agency")
    val agency: String,
    @SerializedName("name")
    val name: String
)
