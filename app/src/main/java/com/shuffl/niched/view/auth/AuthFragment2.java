package com.shuffl.niched.view.auth;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import com.google.android.gms.tasks.TaskExecutors;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.shuffl.niched.R;
import com.shuffl.niched.common.Constants;
import com.shuffl.niched.common.SharedPref;
import com.shuffl.niched.databinding.FragmentAuth2Binding;
import com.shuffl.niched.model.University;
import com.shuffl.niched.model.User;
import com.shuffl.niched.utils.firebase.FirebaseObject;
import com.shuffl.niched.view.home.MainActivity;

import java.util.concurrent.TimeUnit;

import in.aabhasjindal.otptextview.OTPListener;

public class AuthFragment2 extends Fragment {

    private FragmentAuth2Binding binding;
    private User user;
    private String mVerificationId;
    private PhoneAuthProvider.ForceResendingToken mResendToken;
    private FirebaseAuth mAuth;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.fragment_auth2, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mAuth = FirebaseObject.getFirebaseAuth();
        if (getArguments() != null) {
            user = AuthFragment2Args.fromBundle(getArguments()).getUser();
            sendOTP(user.mobileNumber);
        }

        binding.otpView.setOtpListener(new OTPListener() {
            @Override
            public void onInteractionListener() {
                //
            }

            @Override
            public void onOTPComplete(String otp) {
                verifyVerificationCode(otp);
            }
        });
    }

    private void sendOTP(String number) {
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                "+91" + number,
                60,
                TimeUnit.SECONDS,
                TaskExecutors.MAIN_THREAD,
                mCallbacks);
    }

    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        @Override
        public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
            String code = phoneAuthCredential.getSmsCode();
            if (code != null) {
                binding.otpView.setOTP(code);
                verifyVerificationCode(code);
            }
        }

        @Override
        public void onVerificationFailed(FirebaseException e) {
            Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);
            mVerificationId = s;
            mResendToken = forceResendingToken;
        }
    };

    private void verifyVerificationCode(String otp) {
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(mVerificationId, otp);
        signInWithPhoneAuthCredential(credential);
    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(getActivity(), task -> {
                    if (task.isSuccessful()) {
                        if(task.getResult().getAdditionalUserInfo().isNewUser()){
                            user.id = mAuth.getCurrentUser().getUid();
                            NavDirections action = AuthFragment2Directions.actionAuthFragment2ToProfileFragment(user);
                            Navigation.findNavController(binding.getRoot()).navigate(action);
                        }else{
                            startActivity(new Intent(getContext(), MainActivity.class));
                        }
                    } else {
                        String message = "Something went wrong";
                        if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                            message = "Invalid code";
                        }
                    }
                });
    }
}