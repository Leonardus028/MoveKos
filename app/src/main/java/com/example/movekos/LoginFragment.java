package com.example.movekos;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import static android.content.Context.MODE_PRIVATE;


public class LoginFragment extends Fragment implements View.OnClickListener{

    private EditText editTextEmail, editTextPassword;
    private TextView forgotPassword;
    private Button mLogin;
    private ProgressBar progressBar;
    private FirebaseAuth mAuth;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        mAuth = FirebaseAuth.getInstance();
        mLogin = (Button) view.findViewById(R.id.button_login);
        mLogin.setOnClickListener(this);
        editTextEmail = (EditText) view.findViewById(R.id.et_email);
        editTextPassword = (EditText) view.findViewById(R.id.et_password);
        forgotPassword = (TextView) view.findViewById(R.id.et_forgotPassword);
        forgotPassword.setOnClickListener(this);
        progressBar = (ProgressBar) view.findViewById(R.id.progressBar);
        progressBar.setVisibility(View.INVISIBLE);
        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_title:
                startActivity(new Intent(getContext(), HomeActivity.class));
                break;
            case R.id.button_login:
                userLogin();
                break;
            case R.id.et_forgotPassword:
                startActivity(new Intent(getContext(), ForgotPasswordActivity.class));
                break;
    }
}

    private void userLogin() {
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();

        if (email.isEmpty())
        {
            editTextEmail.setError("Email is required");
            editTextEmail.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            editTextEmail.setError("Please provide valid email");
            editTextEmail.requestFocus();
            return;
        }
        if (password.isEmpty()) {
            editTextPassword.setError("Password is required");
            editTextPassword.requestFocus();
            return;
        }
        if (password.length() < 6) {
            editTextPassword.setError("Minimum Password length is 6");
            editTextPassword.requestFocus();
            return;
        }
        progressBar.setVisibility(View.VISIBLE);
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>(){
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task){
                        if(task.isSuccessful()) {
                            //redirect to user profile
                            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                            if(user.isEmailVerified()){
                                startActivity(new Intent(getContext(), HomeActivity.class));
                                getActivity().finish();
                            }else{
                                user.sendEmailVerification();
                                Toast.makeText(getContext(), "Check Your Email Verification", Toast.LENGTH_LONG).show();
                                progressBar.setVisibility(View.GONE);
                            }

                        }else{
                            Toast.makeText(getContext(), "Failed to Login, Please Check Your Credentials", Toast.LENGTH_LONG).show();
                            progressBar.setVisibility(View.GONE);

                        }
                        }
                });

    }
}