package com.shuffl.niched.view.auth;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.shuffl.niched.model.University;
import com.shuffl.niched.repo.SearchRepo;

import java.util.List;

public class SelectUniFragmentViewModel extends AndroidViewModel {

    private SearchRepo searchRepo;

    public SelectUniFragmentViewModel(@NonNull Application application) {
        super(application);
        searchRepo = new SearchRepo();
    }

    LiveData<List<University>> getUnis(String query){
        return searchRepo.getUnis(query);
    }
}
