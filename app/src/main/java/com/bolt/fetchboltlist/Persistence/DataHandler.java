package com.bolt.fetchboltlist.Persistence;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

public class DataHandler {
    private static final String TAG = "DataHandler";

    private final Map<Integer, List<String>> itemMap;
    private Set<Integer> listIds;

    private static DataHandler mClassInstance = null;

    private DataHandler() {
        this.itemMap = new HashMap<>();
        this.listIds = new HashSet<>();
    }

    public static DataHandler getInstance() {
        if (mClassInstance == null) {
            mClassInstance = new DataHandler();
        }
        return mClassInstance;
    }

    public void fetchData(String urlString) throws IOException, JSONException {
        Log.d(TAG, "Fetching data from URL: " + urlString);

        URL url = new URL(urlString);
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

        this.itemMap.clear();
        this.listIds.clear();

        try {
            InputStream inputStream = urlConnection.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            StringBuilder stringBuilder = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line);
            }

            JSONArray jsonArray = new JSONArray(stringBuilder.toString());
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);

                // Filter out items with blank or null name
                String name = jsonObject.optString("name");
                if (!name.isEmpty() && !name.equals("null")) {
                    int listId = jsonObject.getInt("listId");

                    // Group items by listId
                    if (!itemMap.containsKey(listId)) {
                        itemMap.put(listId, new ArrayList<>());
                    }
                    Objects.requireNonNull(itemMap.get(listId)).add(name);
                }
            }

            this.listIds = itemMap.keySet();
            Log.d(TAG, "Data fetched successfully. List IDs: " + listIds);
        } finally {
            urlConnection.disconnect();
        }
    }

    public Set<Integer> getListIds() {
        return this.listIds;
    }

    public List<String> getItemsOfListID(int listId) throws IllegalArgumentException {
        if (!this.itemMap.containsKey(listId)) {
            String errorMessage = "List Id does not exist: " + listId;
            Log.e(TAG, errorMessage);
            throw new IllegalArgumentException(errorMessage);
        }

        return this.itemMap.get(listId);
    }
}

