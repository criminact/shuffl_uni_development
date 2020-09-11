package com.shuffl.niched.repo;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.algolia.search.saas.Index;
import com.algolia.search.saas.Query;
import com.shuffl.niched.common.AlgoliaClient;
import com.shuffl.niched.model.University;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class SearchRepo {

    private MutableLiveData<List<University>> unis = new MutableLiveData<>();
    private AlgoliaClient algoliaClient = new AlgoliaClient();
    private Index index;

    public SearchRepo() {
        index = algoliaClient.getUniversityAlgoliaInstance();
    }

    public LiveData<List<University>> getUnis(String query) {
        fetchPosts(query);
        return unis;
    }

    private void fetchPosts(String tempQuery) {
        Query query = new Query(tempQuery)
                .setAttributesToRetrieve("Name", "objectID")
                .setHitsPerPage(50);
        index.searchAsync(query, (content, error) -> {
            if (content != null) {
                try {
                    List<University> tempPosts = new ArrayList<>();
                    JSONArray jsonArray = content.getJSONArray("hits");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject object = jsonArray.getJSONObject(i);
                        University temp = new University();
                        temp.name = object.getString("Name");
                        temp.code = object.getString("objectID");
                        tempPosts.add(temp);
                    }

                    unis.postValue(tempPosts);

                } catch (JSONException e) {
                    unis.postValue(null);
                }
            }
        });
    }

}
