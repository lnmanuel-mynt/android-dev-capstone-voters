package ph.apper.android.capstone.voters.api

import ph.apper.android.capstone.voters.model.*
import retrofit2.Call
import retrofit2.http.*

interface VoterAPI {

    @Headers(
            "Accept: application/json",
            "Content-type:application/json")
    @POST("voter/register")
    fun register(@Body registerRequest: RegisterRequest): Call<VoterRegistrationResponse>

    @Headers(
            "Accept: application/json",
            "Content-type:application/json")
    @POST("find-my-precinct")
    fun findMyPrecinct(@Body findMyPrecinctRequest: FindMyPrecinctRequest): Call<FindMyPrecinctResponse>
}