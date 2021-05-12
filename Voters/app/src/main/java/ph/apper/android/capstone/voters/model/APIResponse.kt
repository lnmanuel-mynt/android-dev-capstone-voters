package ph.apper.android.capstone.voters.model

import com.google.gson.annotations.SerializedName

class SignupResponse {

    @SerializedName("message")
    var message: String = ""

}

class LoginResponse {

    @SerializedName("profile")
    var profile: UserInfo = UserInfo()

}

class UserInfo {

    @SerializedName("id")
    var id = ""

    @SerializedName("first_name")
    var firstName = ""

    @SerializedName("middle_name")
    var middleName = ""

    @SerializedName("last_name")
    var lastName = ""

    @SerializedName("email")
    var email = ""

    @SerializedName("password")
    var password = ""

}

class GetUserResponse {

    @SerializedName("registration_status")
    var status = ""

    @SerializedName("precinct_number")
    var precinctNumber = ""

    @SerializedName("voter_id_number")
    var votersId = ""

    @SerializedName("barangay")
    var barangay = ""

    @SerializedName("city")
    var city = ""

    @SerializedName("province")
    var province = ""

}