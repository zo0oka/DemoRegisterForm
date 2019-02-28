package com.zo0okadev.demoregisterform.network;

import com.zo0okadev.demoregisterform.model.ApiRequest;
import com.zo0okadev.demoregisterform.model.ApiResponse;

import java.io.IOException;

import androidx.annotation.NonNull;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {

    private static final String BASE_URL = "https://demo8492465.mockable.io/";
    private static ApiService sInstance = null;

    public static ApiService get() {

        if (sInstance == null) {

            // For logging
            HttpLoggingInterceptor loggingInterceptor =
                    new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY);


            // Building OkHttp client
            OkHttpClient client = new OkHttpClient.Builder()
                    .addInterceptor(loggingInterceptor)
                    .build();

            // Retrofit Builder
            Retrofit.Builder builder =
                    new Retrofit
                            .Builder()
                            .baseUrl(BASE_URL)
                            .client(client)
                            .addConverterFactory(GsonConverterFactory.create());

            sInstance = builder.build().create(ApiService.class);

        }

        return sInstance;
    }

    public static void registerUser(ApiService service, ApiRequest apiRequest, final StatusCallback callbacks) {

        service.registerUser(apiRequest).enqueue(
                new Callback<ApiResponse>() {
                    @Override
                    public void onResponse(@NonNull Call<ApiResponse> call, @NonNull Response<ApiResponse> response) {
                        if (response.isSuccessful()) {
                            String msg;
                            if (response.body() != null) {
                                msg = response.body().getMsg();
                            } else {
                                msg = "Please try again.";
                            }
                            callbacks.onSuccess(msg);
                        } else {
                            try {
                                String body = response.errorBody().string();
                                callbacks.onError(body != null ? body : "Unknown error");
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<ApiResponse> call, @NonNull Throwable t) {
                        callbacks.onError(t.getMessage() != null ? t.getMessage() : "Unknown error");
                    }
                }
        );
    }

    public interface StatusCallback {
        void onSuccess(String msg);

        void onError(String error);
    }
}