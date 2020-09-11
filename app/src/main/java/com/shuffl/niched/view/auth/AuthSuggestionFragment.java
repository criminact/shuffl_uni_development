package com.shuffl.niched.view.auth;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.shuffl.niched.R;
import com.shuffl.niched.databinding.FragmentAuthSuggestionBinding;
import com.shuffl.niched.model.User;
import com.shuffl.niched.view.adapters.ViewPagerImageAdapter;

public class AuthSuggestionFragment extends Fragment {

    private FragmentAuthSuggestionBinding binding;
    private ViewPagerImageAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.fragment_auth_suggestion, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        adapter = new ViewPagerImageAdapter(getContext());
        binding.viewPagerLogin.setOffscreenPageLimit(2);
        binding.viewPagerLogin.setAdapter(adapter);

        binding.viewPagerLogin.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {/*empty*/}

            @Override
            public void onPageSelected(int position) {
                binding.pageIndicatorView.setSelection(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {/*empty*/}
        });

        binding.loginButton.setOnClickListener(view1 -> {
            User user = new User();
            user.university = "CHOOSE UNIVERSITY";
            NavDirections action = AuthSuggestionFragmentDirections.actionAuthSuggestionFragmentToMoreDetailsFragment(user);
            Navigation.findNavController(view1).navigate(action);
        });

    }
}