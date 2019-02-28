package com.zo0okadev.demoregisterform.network;

import com.zo0okadev.demoregisterform.model.ApiRequest;
import com.zo0okadev.demoregisterform.model.ApiResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ApiService {

    @POST("register")
    Call<ApiResponse> registerUser(@Body ApiRequest apiRequest);
}
