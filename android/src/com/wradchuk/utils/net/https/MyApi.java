package com.wradchuk.utils.net.https;

import com.wradchuk.utils.net.https.result.AuthResult;
import com.wradchuk.utils.net.https.result.RegResult;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface MyApi
{
    @GET("reg/getUserReg/{email}/{phone}/{password}")
    Call<RegResult> getUserReg(@Path("email") String email, @Path("phone") String phone, @Path("password") String password);

    @GET("auth/getUserAuth/{login}/{password}/{sid}")
    Call<AuthResult> getUserAuth(@Path("login") String login, @Path("password") String password, @Path("sid") String sid);

}