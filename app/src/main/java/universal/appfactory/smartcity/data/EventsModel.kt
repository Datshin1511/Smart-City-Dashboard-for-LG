package universal.appfactory.smartcity.data

import com.google.gson.annotations.SerializedName

data class EventsModel(
    @SerializedName("Event")
    val Event: EventsContentModel
)
