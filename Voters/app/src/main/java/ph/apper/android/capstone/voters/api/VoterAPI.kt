package ph.apper.android.capstone.voters.api

import ph.apper.android.capstone.voters.model.*
import retrofit2.Call
import retrofit2.http.*

interface VoterAPI {

    @Headers(
            "Accept: application/json",
            "Content-type:application/json")
    @POST("login")
    fun login(@Body loginRequest: LoginRequest): Call<LoginResponse>

    @Headers(
            "Accept: application/json",
            "Content-type:application/json")
    @POST("voter/register")
    fun register(@Body registerRequest: RegisterRequest): Call<VoterRegistrationResponse>

    @GET("voter/{id}")
    fun getVoter(@Path("id") id: String)
            : Call<VoterInfoResponse>

    @Headers(
            "Accept: application/json",
            "Content-type:application/json")
    @POST("findmyprecinct")
    fun findMyPrecinct(@Body findMyPrecinctRequest: FindMyPrecinctRequest): Call<FindMyPrecinctResponse>
}