package ph.apper.android.capstone.voters.model

data class Candidate(
    val id: String,
    val name: String,
    val position: String,
    val party: String,
    val level: String,
    val province: String,
    val municipality: String,
    val district: String){
    constructor(): this(
        "",
        "",
        "",
        "",
        "",
        "",
        "",
        "")
}
