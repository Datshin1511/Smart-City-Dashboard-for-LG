package universal.appfactory.smartcity.data

import com.google.gson.annotations.SerializedName

data class PropertiesLocationModel(
    @SerializedName("serviceUri")
    val serviceUri: String = "-",
    @SerializedName("serviceType")
    val serviceType: String = "-",
    @SerializedName("address")
    val address: String = "-",
    @SerializedName("civic")
    val civic: String = "-",
    @SerializedName("city")
    val city: String = "-",
    @SerializedName("score")
    val score: String = "-",
    @SerializedName("id")
    val id: Int = 0,
    @SerializedName("agency")
    val agency: String = "-",
    @SerializedName("agencyUri")
    val agencyUri: String = "-",
    @SerializedName("busLines")
    val busLines: String = "-",
    @SerializedName("distance")
    val distance: String = "-",
    @SerializedName("multimedia")
    val multimedia: String = "-",
    @SerializedName("name")
    val name: String = "-",
    @SerializedName("photoThumbs")
    val photoThumbs: ArrayList<String> = arrayListOf("-"),
    @SerializedName("tipo")
    val tipo: String = "-",
    @SerializedName("typeLabel")
    val typeLabel: String = "-"
)
