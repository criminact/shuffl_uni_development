package com.shuffl.niched.view.auth;

import android.Manifest;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.shuffl.niched.BuildConfig;
import com.shuffl.niched.R;
import com.shuffl.niched.common.PermissionClass;
import com.shuffl.niched.databinding.FragmentProfileBinding;
import com.shuffl.niched.model.User;
import com.shuffl.niched.utils.glide.GlideUtil;
import com.shuffl.niched.view.home.MainActivity;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.engine.impl.GlideEngine;
import com.zhihu.matisse.internal.entity.CaptureStrategy;

import java.util.List;

public class ProfileFragment extends Fragment {

    private static final int REQUEST_CODE_CHOOSE = 2;
    private FragmentProfileBinding binding;
    private User user;
    private Uri uri;
    private PermissionClass permissionClass;
    private ProfileFragmentViewModel viewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.fragment_profile, container, false);
        viewModel = new ViewModelProvider(this).get(ProfileFragmentViewModel.class);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        permissionClass = new PermissionClass(getActivity());

        if (getArguments() != null) {
            user = ProfileFragmentArgs.fromBundle(getArguments()).getUser();
        }

        binding.chooseProfile.setOnClickListener(view1 -> {
            if (permissionClass.checkPermissions(Manifest.permission.WRITE_EXTERNAL_STORAGE) && permissionClass.checkPermissions(Manifest.permission.READ_EXTERNAL_STORAGE) && permissionClass.checkPermissions(Manifest.permission.CAMERA)) {
                openGallery();
            } else {
                permissionClass.askPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA});
            }
        });

        binding.nextProfile.setOnClickListener(view12 -> {
            hideLayout();
            viewModel.uploadProfile(uri, /*user.id*/ "1234").observe(getActivity(), downloadUri -> {
                if (downloadUri != null) {
                    user.name = user.id.substring(0,4) + user.mobileNumber.substring(0,4);
                    user.profile = downloadUri;
                    user.raters = 0;
                    user.ratings = 0;
                    user.isAuthenticated = true;
                    viewModel.uploadUserData(user).observe(getActivity(), isSuccess -> {
                        if (isSuccess) {
                            startActivity(new Intent(getContext(), MainActivity.class));
                        } else {
                            showLayout();
                            Toast.makeText(getContext(), "Some error occurred", Toast.LENGTH_SHORT).show();
                        }
                    });
                } else {
                    showLayout();
                    Toast.makeText(getContext(), "Couldn't upload the profile", Toast.LENGTH_SHORT).show();
                }
            });
        });
    }

    private void showLayout() {
        binding.mainLayoutProfile.setVisibility(View.VISIBLE);
        binding.progressBarProfile.setVisibility(View.GONE);
    }

    private void hideLayout() {
        binding.mainLayoutProfile.setVisibility(View.GONE);
        binding.progressBarProfile.setVisibility(View.VISIBLE);
    }

    private void openGallery() {
        Matisse.from(this)
                .choose(MimeType.ofImage())
                .countable(false)
                .maxSelectable(1)
                .gridExpectedSize(getResources().getDimensionPixelSize(R.dimen.grid_expected_size))
                .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)
                .thumbnailScale(0.85f)
                .imageEngine(new GlideEngine())
                .showPreview(true)
                .capture(true)
                .captureStrategy(new CaptureStrategy(true, BuildConfig.APPLICATION_ID + ".provider"))
                .forResult(REQUEST_CODE_CHOOSE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_CHOOSE && resultCode == getActivity().RESULT_OK) {
            putImageToShow(Matisse.obtainResult(data));
        }
    }

    private void putImageToShow(List<Uri> uris) {
        uri = uris.get(0);
        GlideUtil.loadImage(binding.profileImage, uri.toString(), GlideUtil.getProgressDrawable(getContext()));
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1) {
            if (grantResults.length > 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                openGallery();
            } else {
                Toast.makeText(getContext(), "Failed to open gallery, permissions were dismissed.", Toast.LENGTH_SHORT).show();
            }
        }
    }
}