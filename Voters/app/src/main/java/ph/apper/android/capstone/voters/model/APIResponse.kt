package ph.apper.android.capstone.voters.model

import com.google.gson.annotations.SerializedName

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
}

class VoterInfoResponse {
    @SerializedName("status")
    var status = ""
    @SerializedName("precinct_number")
    var precinctNumber = ""
    @SerializedName("voter_id_number")
    var voterIdNumber = ""
    @SerializedName("barangay")
    var barangay = ""
    @SerializedName("city")
    var city = ""
    @SerializedName("province")
    var province = ""
}

class VoterRegistrationResponse {
    @SerializedName(value = "message")
    var message = ""
}

class FindMyPrecinctResponse {
    @SerializedName(value = "precinct_data")
    var precinctInfo = PrecinctInfo()
}

class PrecinctInfo {
    @SerializedName(value = "precinct_number")
    var precinctNumber = ""

    @SerializedName(value = "barangay")
    var barangay = ""

    @SerializedName(value = "city")
    var city = ""

    @SerializedName(value = "province")
    var province = ""

    @SerializedName(value = "polling_place")
    var pollingPlace = ""
}
class SignupResponse {

    @SerializedName("message")
    var message: String = ""

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

class CandidateInfo{
    @SerializedName("id")
    var id = ""
    @SerializedName("name")
    var name = ""
    @SerializedName("position")
    var position = ""
    @SerializedName("party")
    var party = ""
    @SerializedName("is_national")
    var is_national = ""
    @SerializedName("province")
    var province = ""
    @SerializedName("municipality")
    var municipality = ""
    @SerializedName("district")
    var district = ""
    @SerializedName("image")
    var image = ""

}

class GetCandidateListResponse{
    @SerializedName("candidates")
    var candidateList:ArrayList<CandidateInfo> = ArrayList<CandidateInfo>()
}