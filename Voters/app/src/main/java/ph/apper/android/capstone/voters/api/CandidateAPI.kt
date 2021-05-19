package ph.apper.android.capstone.voters.api

import ph.apper.android.capstone.voters.model.GetCandidateListResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface CandidateAPI {
    @GET("candidates/local/{position}&{province}&{municipality}")
    fun getLocalCandidates(
        @Path("position") position: String,
        @Path("province") province: String,
        @Path("municipality") municipality: String)
            : Call<GetCandidateListResponse>

    @GET("candidates/national/{position}")
    fun getNationalCandidates(@Path("position") position: String)
            : Call<GetCandidateListResponse>

    @GET("candidates/national/{id}")
    fun getCandidateProfile(@Path("id") id: String)
            : Call<GetCandidateListResponse>

    @GET("candidates/local/{province}&{municipality}")
    fun getLocalCandidatesByPosition(
            @Path("province") province: String,
            @Path("municipality") municipality: String)
            : Call<GetCandidateListResponse>
}