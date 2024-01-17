package com.bolt.fetchboltlist.Persistence;

import org.json.JSONException;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

public class DataHandlerTest {

    @Mock
    HttpURLConnection mockUrlConnection;

    @Mock
    InputStream mockInputStream;

    @Mock
    BufferedReader mockReader;

    @Mock
    DataHandler dataHandler;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        dataHandler = DataHandler.getInstance();
    }

    @Test
    public void fetchData_successfulResponse() throws IOException, JSONException {
        //This is just a sample code for mock and unit test which is NOT fully functional yet,
        //The JSONObject and the android.uil.Log classes are to be mocked to get this running.

        // Arrange
        String urlString = "https://fetch-hiring.s3.amazonaws.com/hiring.json";
        String jsonResponse = "[{\"name\":\"Item1\",\"listId\":1},{\"name\":\"Item2\",\"listId\":2}]";
        when(mockUrlConnection.getInputStream()).thenReturn(mockInputStream);
        when(mockReader.readLine()).thenReturn(jsonResponse, null);

        // Act
        dataHandler.fetchData(urlString);

        // Assert
        Set<Integer> listIds = dataHandler.getListIds();
        List<String> itemsList1 = dataHandler.getItemsOfListID(1);
        List<String> itemsList2 = dataHandler.getItemsOfListID(2);

        assertEquals(2, listIds.size());
        assertEquals(Arrays.asList("Item1"), itemsList1);
        assertEquals(Arrays.asList("Item2"), itemsList2);
    }

    @Test(expected = IOException.class)
    public void fetchData_ioException() throws IOException, JSONException, JSONException {
        // Arrange
        String urlString = "http://example.com/data";
        doThrow(new IOException()).when(mockUrlConnection).getInputStream();

        // Act
        dataHandler.fetchData(urlString);
    }

    @Test(expected = JSONException.class)
    public void fetchData_jsonException() throws IOException, JSONException {
        // Arrange
        String urlString = "http://example.com/data";
        when(mockUrlConnection.getInputStream()).thenReturn(mockInputStream);
        when(mockReader.readLine()).thenReturn("Invalid JSON");

        // Act
        dataHandler.fetchData(urlString);
    }

    // Add more tests as needed for different scenarios
}

