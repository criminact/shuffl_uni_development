package com.shuffl.niched.repo;

import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.shuffl.niched.model.User;
import com.shuffl.niched.utils.firebase.FirebaseObject;

public class AuthRepo {

    private MutableLiveData<Boolean> isSuccess = new MutableLiveData<>();
    private MutableLiveData<String> downloadUrl = new MutableLiveData<>();
    private StorageReference reference;

    public AuthRepo() {
        reference = FirebaseObject.getStorage().getReference();
    }

    public LiveData<String> uploadProfile(Uri uri, String id) {
        final StorageReference childRef = reference.child("users/" + id + "/profile/profile.jpg");
        UploadTask uploadTask = childRef.putFile(uri);

        uploadTask.addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                childRef.getDownloadUrl()
                        .addOnCompleteListener(task1 -> {
                            if (task1.isSuccessful()) {
                                downloadUrl.postValue(task1.getResult().toString());
                            }else{
                                downloadUrl.postValue(null);
                            }
                        })
                        .addOnFailureListener(e -> downloadUrl.postValue(null));
            } else {
                downloadUrl.postValue(null);
            }
        }).addOnFailureListener(e -> {
            downloadUrl.postValue(null);
        });

        return downloadUrl;
    }

    public LiveData<Boolean> uploadUserData(User user) {
        final FirebaseFirestore db = FirebaseObject.getFirestore();
        db.collection("users").document(user.id).set(user)
                .addOnCompleteListener(task -> {
                    if(task.isSuccessful()){
                        isSuccess.postValue(true);
                    }else{
                        isSuccess.postValue(false);
                    }
                }).addOnFailureListener(e -> {
                isSuccess.postValue(false);
        });

        return isSuccess;
    }

}
