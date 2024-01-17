package com.bolt.fetchboltlist.ActivityClasses;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.bolt.fetchboltlist.Persistence.DataHandler;
import com.bolt.fetchboltlist.R;

import org.json.JSONException;

import java.io.IOException;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private ArrayAdapter<Integer> adapter;
    private DataHandler dataHandler = null;
    private final String awsS3Url = "https://fetch-hiring.s3.amazonaws.com/hiring.json";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize UI components
        ListView listView = findViewById(R.id.list_ids_listView);
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1);
        listView.setAdapter(adapter);

        // Initialize DataHandler instance
        dataHandler = DataHandler.getInstance();

        // Fetch data asynchronously
        Executor executor = Executors.newSingleThreadExecutor();
        executor.execute(() -> {
            try {
                dataHandler.fetchData(awsS3Url);

                // Update UI on the main thread with fetched listIds
                runOnUiThread(() -> adapter.addAll(dataHandler.getListIds()));

                Log.d(TAG, "Data fetched and listIds displayed successfully.");
            } catch (IOException | RuntimeException e) {
                // Handle errors during data fetching
                Log.e(TAG, "Error fetching data", e);
            } catch (JSONException e) {
                // Throw a RuntimeException for JSON parsing errors
                throw new RuntimeException(e);
            }
        });

        // Set item click listener for list items
        listView.setOnItemClickListener((parent, view, position, id) -> {
            Integer tappedItemAtPosition = (Integer) parent.getItemAtPosition(position);
            Intent intent = new Intent(MainActivity.this, ItemNamesActivity.class);
            intent.putExtra("selectedListId", tappedItemAtPosition.intValue());
            startActivity(intent);
        });
    }
}