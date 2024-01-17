package com.bolt.fetchboltlist.ActivityClasses;

import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bolt.fetchboltlist.Persistence.DataHandler;
import com.bolt.fetchboltlist.R;

import java.util.Collections;
import java.util.List;

public class ItemNamesActivity extends AppCompatActivity {

    private static final String TAG = "ItemNamesActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item_names_activity);

        // Initialize UI components
        ListView listView = findViewById(R.id.item_names_listView);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1);
        listView.setAdapter(adapter);

        // Get selected listId from intent
        int selectedListId = getIntent().getIntExtra("selectedListId", -1);

        // Set label based on selected listId
        TextView topTWLabel = findViewById(R.id.item_names_textView);
        topTWLabel.setText("Items with list Id: " + selectedListId);

        // Fetch and display item names
        try {
            DataHandler dataHandler = DataHandler.getInstance();
            List<String> itemNames = dataHandler.getItemsOfListID(selectedListId);

            // Sort item names alphabetically
            Collections.sort(itemNames);

            // Add item names to the adapter
            adapter.addAll(itemNames);

            Log.d(TAG, "Item names fetched and displayed successfully.");
        } catch (IllegalArgumentException e) {
            // Handle invalid list id
            Log.e(TAG, "Invalid list id: " + selectedListId, e);
        } catch (RuntimeException e) {
            // Handle general runtime error
            Log.e(TAG, "Error fetching data", e);
        }
    }
}
