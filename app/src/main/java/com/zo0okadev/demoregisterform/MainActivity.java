package com.zo0okadev.demoregisterform;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.zo0okadev.demoregisterform.model.User;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

public class MainActivity extends AppCompatActivity implements TextWatcher {

    private static final String TAG = MainActivity.class.getSimpleName();
    private TextInputLayout nameLayout, emailLayout, passwordLayout, phoneLayout;
    private TextInputEditText nameField, emailField, passwordField, phoneField;
    private MyViewModel myViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (!isConnected()) {
            Toast.makeText(this, "No internet connection found \n Please check your connection",
                    Toast.LENGTH_SHORT).show();
        }

        initializeViews();

        myViewModel = ViewModelProviders.of(this).get(MyViewModel.class);

        // observe the message received from server response and assign it to a Toast
        myViewModel.getToastMessage().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                if (!TextUtils.isEmpty(s)) {
                    Toast.makeText(MainActivity.this, s, Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    private boolean isConnected() {
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();
    }

    private void initializeViews() {
        nameLayout = findViewById(R.id.name_layout_wrapper);
        emailLayout = findViewById(R.id.email_layout_wrapper);
        passwordLayout = findViewById(R.id.password_layout_wrapper);
        phoneLayout = findViewById(R.id.phone_layout_wrapper);

        nameField = findViewById(R.id.name_field);
        emailField = findViewById(R.id.email_field);
        passwordField = findViewById(R.id.password_field);
        phoneField = findViewById(R.id.phone_field);
        Button registerButton = findViewById(R.id.register_button);

        nameField.addTextChangedListener(this);
        emailField.addTextChangedListener(this);
        passwordField.addTextChangedListener(this);
        phoneField.addTextChangedListener(this);

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isConnected()) {
                    registerUser();
                } else {
                    Toast.makeText(MainActivity.this,
                            "No internet connection Found \n Please check your connection and try again.",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    @SuppressWarnings("ConstantConditions")
    private void registerUser() {
        if (nameLayout.isErrorEnabled() || emailLayout.isErrorEnabled()
                || passwordLayout.isErrorEnabled() || phoneLayout.isErrorEnabled()) {
            Toast.makeText(this, "Please check errors above", Toast.LENGTH_SHORT).show();
        } else {
            User user = new User(nameField.getText().toString().trim(),
                    emailField.getText().toString().trim(),
                    passwordField.getText().toString().trim(),
                    phoneField.getText().toString().trim());
            Log.d(TAG, "registerUser: " + user.toString());
            myViewModel.register(user);
        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    // used as the first validation check
    @Override
    public void afterTextChanged(Editable s) {

        if (s.hashCode() == nameField.getEditableText().hashCode() && nameField.hasFocus()) {
            if (s.length() < 3) {
                nameLayout.setHelperTextEnabled(false);
                nameLayout.setErrorEnabled(true);
                nameLayout.setError("Name must be longer than 3 letters");
            } else {
                nameLayout.setErrorEnabled(false);
                nameLayout.setHelperTextEnabled(true);
            }

        } else if (s.hashCode() == emailField.getEditableText().hashCode() && emailField.hasFocus()) {
            if (TextUtils.isEmpty(s) || !Patterns.EMAIL_ADDRESS.matcher(s).matches()) {
                emailLayout.setHelperTextEnabled(false);
                emailLayout.setErrorEnabled(true);
                emailLayout.setError("Enter a valid email address");
            } else {
                emailLayout.setErrorEnabled(false);
                emailLayout.setHelperTextEnabled(true);
            }
        } else if (s.hashCode() == passwordField.getEditableText().hashCode() && passwordField.hasFocus()) {
            if (s.length() < 8) {
                passwordLayout.setHelperTextEnabled(false);
                passwordLayout.setErrorEnabled(true);
                passwordLayout.setError("Password must be longer than 8 characters");
            } else {
                passwordLayout.setErrorEnabled(false);
                passwordLayout.setHelperTextEnabled(true);
            }
        } else if (s.hashCode() == phoneField.getEditableText().hashCode() && phoneField.hasFocus()) {
            if (TextUtils.isEmpty(s) || !Patterns.PHONE.matcher(s).matches()) {
                phoneLayout.setErrorEnabled(true);
                phoneLayout.setError("Enter a valid phone number");
            } else {
                phoneLayout.setErrorEnabled(false);
            }
        }

    }
}
