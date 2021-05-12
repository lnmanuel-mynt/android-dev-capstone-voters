package ph.apper.android.capstone.voters.api

import ph.apper.android.capstone.voters.model.*
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface UserAPI {

    @POST("signup/")
    fun signup(@Body signupRequest: SignupRequest): Call<SignupResponse>

    @POST("login/")
    fun login(@Body loginRequest: LoginRequest): Call<LoginResponse>

    @GET("voter/{id}")
    fun getUser(
        @Path("id") id: String): Call<GetUserResponse>


}