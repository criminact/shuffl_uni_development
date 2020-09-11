package com.shuffl.niched.view.home;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.google.firebase.auth.FirebaseUser;
import com.shuffl.niched.model.User;
import com.shuffl.niched.repo.MainActivityRepo;

public class MainActivityViewModel extends AndroidViewModel {

    private MainActivityRepo repo;

    public MainActivityViewModel(@NonNull Application application) {
        super(application);
        repo = new MainActivityRepo(application);
    }

    LiveData<User> getFirebaseUser(String id){
        return repo.getFirebaseUserDetails(id);
    }
}
