package com.shuffl.niched.view.auth;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import com.shuffl.niched.R;
import com.shuffl.niched.databinding.FragmentAuth1Binding;
import com.shuffl.niched.model.User;


public class AuthFragment1 extends Fragment {

    FragmentAuth1Binding binding;
    private User user;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.fragment_auth1, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if(getArguments() != null){
            user = MoreDetailsFragmentArgs.fromBundle(getArguments()).getUser();
        }

        binding.sendOtp.setOnClickListener(view1 -> {
            if (validateNumber()) {
                user.mobileNumber = binding.number.getText().toString();
                NavDirections action = AuthFragment1Directions.actionAuthFragment1ToAuthFragment2(user);
                Navigation.findNavController(view1).navigate(action);
            }
        });
    }

    private boolean validateNumber() {
        if (binding.number.getText().toString().length() == 10) {
            return true;
        }
        binding.number.setError("Please check your number again.");
        return false;
    }
}