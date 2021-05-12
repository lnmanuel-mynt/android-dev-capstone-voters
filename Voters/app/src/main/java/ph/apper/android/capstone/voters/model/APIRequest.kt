package ph.apper.android.capstone.voters.model

import com.google.gson.annotations.SerializedName

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

    constructor(
        firstName: String,
        middleName: String,
        lastName: String,
        email: String,
        password: String
    ) {
        this.firstName = firstName
        this.middleName = middleName
        this.lastName = lastName
        this.email = email
        this.password = password
    }
}

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