package ph.apper.android.capstone.voters.model

data class Candidate(
    val name: String,
    val position:Positions,
    val party: String,
    val level: String,
    val province: String,
    val municipality: String,
    val district: String
)

enum class Positions{
    PRESIDENT,
    VICEPRESIDENT,
    SENATOR,
    CONGRESSMAN,
    GOVERNOR,
    VICEGOVERNOR,
    MAYOR,
    VICEMAYOR,
    COUNCILOR
}