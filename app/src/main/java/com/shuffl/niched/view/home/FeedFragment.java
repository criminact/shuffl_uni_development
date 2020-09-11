package com.shuffl.niched.view.home;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.core.view.GravityCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.shuffl.niched.R;
import com.shuffl.niched.databinding.FragmentFeedBinding;
import com.shuffl.niched.view.addproduct.ProductAddActivity;
import com.shuffl.niched.view.auth.AuthActivity;

public class FeedFragment extends Fragment {

    FragmentFeedBinding binding;
    public static ActionBarDrawerToggle toggle;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.fragment_feed, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        toggle = new ActionBarDrawerToggle(getActivity(), MainActivity.binding.drawerLayout, binding.toolbarFeed, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        MainActivity.binding.drawerLayout.addDrawerListener(toggle);

        binding.toolbarFeed.setNavigationOnClickListener(view1 -> {
                MainActivity.binding.drawerLayout.openDrawer(GravityCompat.START);
        });

        binding.addProduct.setOnClickListener(view12 -> {
            if(validateUser()){
                startActivity(new Intent(getContext(), ProductAddActivity.class));
            }else{
                startActivity(new Intent(getContext(), AuthActivity.class));
            }
        });

    }

    private boolean validateUser() {
        if(MainActivity.user == null){
            Toast.makeText(getContext(), "Please login first", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
}