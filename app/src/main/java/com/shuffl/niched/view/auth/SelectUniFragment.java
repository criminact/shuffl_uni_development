package com.shuffl.niched.view.auth;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.shuffl.niched.R;
import com.shuffl.niched.databinding.FragmentSelectUniBinding;
import com.shuffl.niched.listeners.UniversityClickListener;
import com.shuffl.niched.model.University;
import com.shuffl.niched.model.User;
import com.shuffl.niched.view.adapters.UniversityAdapter;

import java.util.ArrayList;
import java.util.List;


public class SelectUniFragment extends Fragment implements UniversityClickListener {

    private FragmentSelectUniBinding binding;
    private SelectUniFragmentViewModel viewModel;
    private User user;
    private UniversityAdapter adapter = new UniversityAdapter(new ArrayList<>(), this);

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.fragment_select_uni, container, false);
        viewModel = new ViewModelProvider(this).get(SelectUniFragmentViewModel.class);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if(getArguments() != null){
            user = SelectUniFragmentArgs.fromBundle(getArguments()).getUser();
        }

        binding.uniRecyclerview.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.uniRecyclerview.setHasFixedSize(true);
        binding.uniRecyclerview.setAdapter(adapter);

        viewModel.getUnis("").observe(getActivity(), unis -> {
            if (unis != null) {
                if (unis.size() == 0) {
                    //binding.noProducts.setVisibility(View.VISIBLE);
                } else {
                    //binding.noProducts.setVisibility(View.GONE);
                    adapter.updateUniList(unis);
                }
            }
        });

        binding.searchViewUni.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                viewModel.getUnis(newText).observe(getActivity(), unis -> {
                    if (unis != null) {
                        if (unis.size() == 0) {
                            //binding.noProducts.setVisibility(View.VISIBLE);
                        } else {
                            //binding.noProducts.setVisibility(View.GONE);
                            adapter.updateUniList(unis);
                        }
                    }
                });
                return true;
            }
        });


    }

    @Override
    public void universityClicked(University university) {
        user.university = university.name;
        NavDirections action = SelectUniFragmentDirections.actionSelectUniFragmentToMoreDetailsFragment(user);
        Navigation.findNavController(binding.getRoot()).navigate(action);
    }
}