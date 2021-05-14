package ph.apper.android.capstone.voters.model

import com.google.gson.annotations.SerializedName

class LoginRequest {
    @SerializedName("email")
    var email = ""
    @SerializedName("password")
    var password = ""

    constructor(email: String, password: String) {
        this.email = email
        this.password = password
    }
}

class RegisterRequest {
    @SerializedName("id")
    var id = ""
    @SerializedName("first_name")
    var firstName = ""
    @SerializedName("middle_name")
    var middleName = ""
    @SerializedName("last_name")
    var lastName = ""
    @SerializedName("birth_date")
    var birthDate = ""
    @SerializedName("birth_province")
    var birthProvince = ""
    @SerializedName("birth_city")
    var birthCity = ""
    @SerializedName("civil_status")
    var civilStatus = ""
    @SerializedName("sex")
    var sex = ""
    @SerializedName("street")
    var street = ""
    @SerializedName("subdivision")
    var subdivision = ""
    @SerializedName("barangay")
    var barangay = ""
    @SerializedName("city")
    var city = ""
    @SerializedName("province")
    var province = ""
    @SerializedName("years_in_city")
    var yearsInCity = ""
    @SerializedName("years_in_ph")
    var yearsInPH = ""
    @SerializedName("date_registered")
    var dateRegistered = ""

    constructor(id: String, firstName: String, middleName: String, lastName: String,
                birthDate: String, birthProvince: String, birthCity: String,
                civilStatus: String, sex: String,
                street: String, subdivision: String, barangay: String, city: String, province: String,
                yearsInCity: String, yearsInPH: String, dateRegistered: String) {
        this.id = id
        this.firstName = firstName
        this.middleName = middleName
        this.lastName = lastName
        this.birthDate = birthDate
        this.birthProvince = birthProvince
        this.birthCity = birthCity
        this.civilStatus = civilStatus
        this.sex = sex
        this.street = street
        this.subdivision = subdivision
        this.barangay = barangay
        this.city = city
        this.province = province
        this.yearsInCity = yearsInCity
        this.yearsInPH = yearsInPH
        this.dateRegistered = dateRegistered
    }
}

class FindMyPrecinctRequest {
    @SerializedName("first_name")
    var firstName = ""

    @SerializedName("middle_name")
    var middleName = ""

    @SerializedName("last_name")
    var lastName = ""

    @SerializedName("birth_date")
    var birthDate = ""

    constructor(firstName: String, middleName: String, lastName: String, birthDate: String) {
        this.firstName = firstName
        this.middleName = middleName
        this.lastName = lastName
        this.birthDate = birthDate
    }
}
class SignupRequest {

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

    constructor(firstName: String, middleName: String, lastName: String,
        email: String, password: String) {
        this.firstName = firstName
        this.middleName = middleName
        this.lastName = lastName
        this.email = email
        this.password = password
    }
}
