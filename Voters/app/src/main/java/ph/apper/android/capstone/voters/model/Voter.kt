package ph.apper.android.capstone.voters.model

data class PersonalInformation(val birthday: String, val province: String, val city: String,
                               var civilStatus: CivilStatus, var sex: Sex) {
    constructor(): this("", "", "", CivilStatus.SINGLE, Sex.MALE)
}

enum class CivilStatus {
    SINGLE,
    MARRIED,
    DIVORCED,
    WIDOWED;

    companion object {
        fun getCivilStatus(civilStatus: String) =
                when(civilStatus) {
                    "MARRIED"-> MARRIED
                    "DIVORCED" -> DIVORCED
                    "WIDOWED" -> WIDOWED
                    else -> SINGLE
        }
    }
}

enum class Sex {
    MALE,
    FEMALE;

    companion object {
        fun getSex(sex: String) =
                when(sex) {
                    "FEMALE" -> FEMALE
                    else -> MALE
                }
    }
}

enum class RegistrationStatus {
    ACTIVE,
    DEACTIVATED,
    PENDING,
    NOT_APPLICABLE;

    companion object {
        fun getRegistrationStatus(registrationStatus: String) =
                when(registrationStatus) {
                    "ACTIVE" -> ACTIVE
                    "DEACTIVATED" -> DEACTIVATED
                    "PENDING" -> PENDING
                    else -> NOT_APPLICABLE
                }
    }
}