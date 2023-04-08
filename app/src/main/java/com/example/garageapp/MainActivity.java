package com.example.garageapp;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import static com.example.garageapp.database.Database.cars;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.garageapp.database.Database;
import com.example.garageapp.screen.SignIn_Page;
import com.example.garageapp.utility.Cars;
import com.example.garageapp.utility.MyAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private Spinner spinner, spinner2;
    private ArrayList<String> makesList = new ArrayList<>();
    private ArrayList<Integer> makeIdsList = new ArrayList<>();
    private ArrayList<Integer> modelsList = new ArrayList<>();
    private ArrayAdapter<String> modelAdapter;
    private Button btn,logout;
    private String makeText;
    private String modelText;

    private ListView listView;
    private MyAdapter myAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        spinner = findViewById(R.id.spinner);
        spinner2 = findViewById(R.id.spinner2);
        btn = findViewById(R.id.btn);
        listView = findViewById(R.id.listview);
        logout = findViewById(R.id.log);

        ArrayList modelList = new ArrayList<>();
        modelAdapter = new ArrayAdapter<>(MainActivity.this, android.R.layout.simple_spinner_item, modelList);
        modelAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner2.setAdapter(modelAdapter);
        new FetchMakesTask().execute();
        myAdapter = new MyAdapter(this,cars);
        listView.setAdapter(myAdapter);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), SignIn_Page.class);
                Toast.makeText(MainActivity.this, "logging out...", Toast.LENGTH_SHORT).show();
                startActivity(intent);
            }
        });
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cars.add(new Cars(makeText,modelText,1,1,null));
                myAdapter.notifyDataSetChanged();
            }
        });

    }

    private class FetchMakesTask extends AsyncTask<Void, Void, String> {

        private final String API_URL = "https://vpic.nhtsa.dot.gov/api/vehicles/GetMakesForVehicleType/car?format=json";

        @Override
        protected String doInBackground(Void... voids) {
            String result = "";

            try {
                URL url = new URL(API_URL);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");

                InputStream inputStream = connection.getInputStream();
                int responseCode = connection.getResponseCode();

                if (responseCode == HttpURLConnection.HTTP_OK) {
                    byte[] bytes = new byte[1024];
                    int bytesRead = inputStream.read(bytes);

                    while (bytesRead != -1) {
                        result += new String(bytes, 0, bytesRead);
                        bytesRead = inputStream.read(bytes);
                    }
                }

                inputStream.close();
                connection.disconnect();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return result;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            try {
                JSONObject jsonObject = new JSONObject(s);
                JSONArray jsonArray = jsonObject.getJSONArray("Results");

                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject makeObject = jsonArray.getJSONObject(i);
                    String makeName = makeObject.getString("MakeName");
                    Integer makeId = Integer.parseInt(makeObject.getString("MakeId"));
                    makesList.add(makeName);
                    makeIdsList.add(makeId);
                }

                ArrayAdapter<String> adapter = new ArrayAdapter<>(MainActivity.this, android.R.layout.simple_spinner_item, makesList);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner.setAdapter(adapter);

                spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        String selectedMake = adapterView.getItemAtPosition(i).toString();
                        Toast.makeText(MainActivity.this, "Selected Make: " + selectedMake, Toast.LENGTH_SHORT).show();
                        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                makeText = adapterView.getItemAtPosition(i).toString();
                                new FetchModelTask(String.valueOf(makeIdsList.get(i))).execute();
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> adapterView) {
                                // Do nothing
                            }
                        });

                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {
                        // Do nothing
                    }
                });

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    private class FetchModelTask extends AsyncTask<Void, Void, String> {

        private final String API_URL = "https://vpic.nhtsa.dot.gov/api/vehicles/getmodelsformakeid/";

        private ArrayList<String> modelList = new ArrayList<>();
        private String makeId;

        public FetchModelTask(String makeId) {
            this.makeId = makeId;
        }

        @Override
        protected String doInBackground(Void... voids) {
            String result = "";

            try {
                URL url = new URL(API_URL + makeId + "?format=json");
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");

                InputStream inputStream = connection.getInputStream();
                int responseCode = connection.getResponseCode();

                if (responseCode == HttpURLConnection.HTTP_OK) {
                    byte[] bytes = new byte[1024];
                    int bytesRead = inputStream.read(bytes);

                    while (bytesRead != -1) {
                        result += new String(bytes, 0, bytesRead);
                        bytesRead = inputStream.read(bytes);
                    }
                }

                inputStream.close();
                connection.disconnect();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return result;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            try {
                JSONObject jsonObject = new JSONObject(s);
                JSONArray jsonArray = jsonObject.getJSONArray("Results");

                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject modelObject = jsonArray.getJSONObject(i);
                    String modelName = modelObject.getString("Model_Name");
                    modelList.add(modelName);
                }

                modelAdapter.clear();
                modelAdapter.addAll(modelList);
                spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        modelText = adapterView.getItemAtPosition(i).toString();
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }




}
