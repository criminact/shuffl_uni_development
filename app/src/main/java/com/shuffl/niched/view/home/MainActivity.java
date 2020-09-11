package com.shuffl.niched.view.home;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import com.google.firebase.auth.FirebaseUser;
import com.shuffl.niched.R;
import com.shuffl.niched.databinding.ActivityMainBinding;
import com.shuffl.niched.databinding.HeaderLayoutBinding;
import com.shuffl.niched.model.User;
import com.shuffl.niched.utils.firebase.FirebaseObject;
import com.shuffl.niched.utils.glide.GlideUtil;

public class MainActivity extends AppCompatActivity {

    private Fragment selectedFragment;
    public FirebaseUser mUser;
    private ActionBarDrawerToggle toggle;
    public static ActivityMainBinding binding;
    private MainActivityViewModel viewModel;
    public static User user;
    private FeedFragment feedFragment = new FeedFragment();
    private View header;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        viewModel = new ViewModelProvider(this).get(MainActivityViewModel.class);
        setFragment(feedFragment);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mUser = FirebaseObject.getFirebaseUser();
        if (mUser != null) {
            viewModel.getFirebaseUser(mUser.getUid()).observe(this, user -> {
                if (user != null && user.id != null) {
                    this.user = user;
                    setHeaderView();
                }
            });
        }
    }

    private void setHeaderView() {
        header = binding.sideNavigationView.getHeaderView(0);
        ImageView profileView = header.findViewById(R.id.profile_header);
        TextView nameView = header.findViewById(R.id.name_header);
        nameView.setText(user.name);
        GlideUtil.loadImage(profileView, user.profile, GlideUtil.getProgressDrawable(this));
    }

    private void setFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.main_frame, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
        selectedFragment = fragment;
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        toggle = FeedFragment.toggle;
        if (binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            binding.drawerLayout.closeDrawer(GravityCompat.START);
            return;
        } else {
            finish();
        }
    }
}