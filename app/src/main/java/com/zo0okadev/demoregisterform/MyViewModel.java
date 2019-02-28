package com.zo0okadev.demoregisterform;

import com.zo0okadev.demoregisterform.model.ApiRequest;
import com.zo0okadev.demoregisterform.model.User;
import com.zo0okadev.demoregisterform.network.ApiClient;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

class MyViewModel extends ViewModel {

    private final MutableLiveData<String> responseMessage = new MutableLiveData<>();

    void register(User user) {
        if (user.isValid()) {
            ApiRequest apiRequest = new ApiRequest(user);
            ApiClient.registerUser(ApiClient.get(), apiRequest, new ApiClient.StatusCallback() {
                @Override
                public void onSuccess(String msg) {
                    responseMessage.setValue(msg);
                }

                @Override
                public void onError(String error) {
                    responseMessage.setValue(error);
                }
            });
        }
    }

    LiveData<String> getToastMessage() {
        return responseMessage;
    }
}
