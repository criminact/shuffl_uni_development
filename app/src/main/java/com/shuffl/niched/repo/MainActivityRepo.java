package com.shuffl.niched.repo;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.shuffl.niched.model.User;
import com.shuffl.niched.utils.firebase.FirebaseObject;

public class MainActivityRepo {

    private Application application;
    private MutableLiveData<User> user = new MutableLiveData<>();
    private FirebaseFirestore db;

    public MainActivityRepo(Application application) {
        this.application = application;
        db = FirebaseObject.getFirestore();
    }

    public LiveData<User> getFirebaseUserDetails(String id) {
        db.collection("users").document(id).get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        User tempUser = (User) task.getResult().toObject(User.class);
                        user.postValue(tempUser);
                    } else {
                        user.postValue(null);
                    }
                })
                .addOnFailureListener(e -> user.postValue(null));
        return user;
    }
}
