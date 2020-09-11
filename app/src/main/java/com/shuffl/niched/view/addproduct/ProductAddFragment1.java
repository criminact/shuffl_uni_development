package com.shuffl.niched.view.addproduct;

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
import com.shuffl.niched.databinding.FragmentProductAdd1Binding;
import com.shuffl.niched.model.Product;

public class ProductAddFragment1 extends Fragment {

    private FragmentProductAdd1Binding binding;
    private Product product;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.fragment_product_add1, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.uploadPictures.setOnClickListener(view1 -> {
            if (validateTitle() && validateDescription() && validateRental()) {
                product = new Product();
                product.description = binding.subtitleAdd.getText().toString();
                product.title = binding.titleAdd.getText().toString();
                product.rentalPriceDay = Integer.valueOf(binding.priceAdd.getText().toString());
                NavDirections action = ProductAddFragment1Directions.actionProductAddFragment1ToProductAddFragment2(product);
                Navigation.findNavController(view1).navigate(action);
            }
        });
    }

    private boolean validateRental() {
        if (binding.priceAdd.getText().toString().isEmpty() || Integer.valueOf(binding.priceAdd.getText().toString()) < 20) {
            binding.priceAdd.setError("Price seems to be empty or less than 20");
            return false;
        }
        return true;
    }

    private boolean validateTitle() {
        if (binding.titleAdd.getText().toString().isEmpty()) {
            binding.titleAdd.setError("Title seems to be empty");
            return false;
        }
        return true;
    }

    private boolean validateDescription() {
        if (binding.subtitleAdd.getText().toString().isEmpty()) {
            binding.subtitleAdd.setError("Description seems to be empty");
            return false;
        }
        return true;
    }
}