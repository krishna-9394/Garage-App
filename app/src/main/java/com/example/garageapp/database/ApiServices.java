package com.example.garageapp.database;

import android.os.AsyncTask;

import com.example.garageapp.utility.Make;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class ApiServices {

    private static final String API_URL = "https://vpic.nhtsa.dot.gov/api/vehicles/GetMakesForVehicleType/car?format=json";

    public interface OnGetMakesCallback {
        void onSuccess(List<Make> makes);
        void onError(String errorMessage);
    }

    public static void getMakes(OnGetMakesCallback callback) {
        new FetchMakesTask(callback).execute();
    }

    private static class FetchMakesTask extends AsyncTask<Void, Void, String> {

        private final OnGetMakesCallback callback;

        FetchMakesTask(OnGetMakesCallback callback) {
            this.callback = callback;
        }

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

                List<Make> makes = new ArrayList<>();

                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject makeObject = jsonArray.getJSONObject(i);
                    int makeId = makeObject.getInt("Make_ID");
                    String makeName = makeObject.getString("MakeName");
                    makes.add(new Make(makeId, makeName));
                }

                callback.onSuccess(makes);

            } catch (JSONException e) {
                e.printStackTrace();
                callback.onError("Error parsing JSON response");
            }
        }
    }
}
