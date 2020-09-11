package com.shuffl.niched.view.auth;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.shuffl.niched.R;
import com.shuffl.niched.databinding.FragmentMoreDetailsBinding;
import com.shuffl.niched.model.University;
import com.shuffl.niched.model.User;

public class MoreDetailsFragment extends Fragment {

    private FragmentMoreDetailsBinding binding;
    private User user;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.fragment_more_details, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if(getArguments() != null){
            user = MoreDetailsFragmentArgs.fromBundle(getArguments()).getUser();
            binding.chooseUni.setText(user.university);
        }

        binding.chooseUni.setOnClickListener(view1 -> {
            NavDirections action = MoreDetailsFragmentDirections.actionMoreDetailsFragmentToSelectUniFragment(user);
            Navigation.findNavController(view1).navigate(action);
        });

        binding.nextMoreDetails.setOnClickListener(view12 -> {
            if (validateUserObject()) {
                NavDirections action = MoreDetailsFragmentDirections.actionMoreDetailsFragmentToAuthFragment1(user);
                Navigation.findNavController(view12).navigate(action);
            }
        });
    }

    private boolean validateUserObject() {
        if(user.university == null || user.university.equals("CHOOSE UNIVERSITY") || user.university.isEmpty()){
            Toast.makeText(getContext(), "There seems to be some problem with your University.", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
}