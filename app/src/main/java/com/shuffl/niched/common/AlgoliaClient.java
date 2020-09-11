package com.shuffl.niched.common;

import com.algolia.search.saas.Client;
import com.algolia.search.saas.Index;

public class AlgoliaClient {
    Index index = null;

    public Index getProductAlgoliaInstance() {
        if (index != null) {
            return index;
        }
        Client client = new Client(Constants.AG_APPLICATION_ID, Constants.AG_API);
        index = client.getIndex(Constants.AG_PRODUCTS);
        return index;

    }

    public Index getUniversityAlgoliaInstance() {
        if (index != null) {
            return index;
        }
        Client client = new Client(Constants.AG_APPLICATION_ID, Constants.AG_API);
        index = client.getIndex(Constants.AG_UNIVERSITY);
        return index;
    }

}
