package ride.happyy.user.model


import com.google.android.gms.location.places.Place
import com.google.android.gms.maps.model.LatLng

class DestinationBean : BaseBean() {

    //    10.015861  76.341867  10.107570  76.345662

    var id: Int = 0
    var address: String = ""
    var latitude: String = ""
    var longitude: String = ""
    var name: String = ""
    var place: Place? = null
    var latLng: LatLng? = null
        get() {
            this.latLng = LatLng(dLatitude, dLongitude)
            return field
        }
    val dLatitude: Double
        get() {
            try {
                return java.lang.Double.parseDouble(latitude)
            } catch (e: NumberFormatException) {
                e.printStackTrace()
                return 0.0
            }

        }


    val dLongitude: Double
        get() {
            try {
                return java.lang.Double.parseDouble(longitude)
            } catch (e: NumberFormatException) {
                e.printStackTrace()
                return 0.0
            }

        }



}
