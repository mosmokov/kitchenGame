package com.wradchuk.utils.net.https;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.wradchuk.utils.net.https.result.AuthResult;
import com.wradchuk.utils.net.https.result.RegResult;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class MyRetrofit
{
    private static final String BASE_URL = "https://url/";

    private HttpLoggingInterceptor interceptor;
    private final MyApi api;

    public MyRetrofit() {
        final Gson gson = new GsonBuilder().setLenient().create();
        interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        final OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();
        final Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(client)
                .build();
        api = retrofit.create(MyApi.class);
    }

    public Call<RegResult> reg(String Email, String Phone, String Password) {
        return api.getUserReg(Email, Phone, Password);
    }
    public Call<AuthResult> auth(String Login, String Password, String Sid) {
        return api.getUserAuth(Login, Password, Sid);
    }

}