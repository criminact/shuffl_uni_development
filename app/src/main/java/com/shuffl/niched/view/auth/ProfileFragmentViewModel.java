package com.shuffl.niched.view.auth;

import android.app.Application;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.shuffl.niched.model.User;
import com.shuffl.niched.repo.AuthRepo;

import java.util.List;

public class ProfileFragmentViewModel extends AndroidViewModel {

    MutableLiveData<String> isSuccess = new MutableLiveData<>();
    private AuthRepo authRepo;

    public ProfileFragmentViewModel(@NonNull Application application) {
        super(application);
        authRepo = new AuthRepo();
    }

    public LiveData<String> uploadProfile (Uri uri, String id){
        return authRepo.uploadProfile(uri, id);
    }

    public LiveData<Boolean> uploadUserData(User user){
        return authRepo.uploadUserData(user);
    }
}
