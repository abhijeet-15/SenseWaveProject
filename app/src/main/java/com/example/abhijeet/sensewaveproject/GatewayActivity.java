package com.example.abhijeet.sensewaveproject;

import android.os.AsyncTask;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Adapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class GatewayActivity extends AppCompatActivity  {

    public RecyclerView recyclerView;
    public GatewayAdapter mAdapter;

    public class GatewayCount extends AsyncTask<String,Void,String> {
        @Override
        protected String doInBackground(String... params) {
            String result = "";
            URL url;
            HttpURLConnection urlConnection = null;

            try {

                url = new URL(params[0]);

                urlConnection = (HttpURLConnection) url.openConnection();

                InputStream inputStream = urlConnection.getInputStream();
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                int data = inputStreamReader.read();
                while (data != -1) {
                    char current = (char) data;
                    result += current;
                    data = inputStreamReader.read();

                }
                return result;

            } catch (Exception e) {

                e.printStackTrace();
                return "Failed";
            }


        }

        @Override
        public void onPostExecute(String result) {
            super.onPostExecute(result);
            Log.i("YES","YES");


            List<GateWayViewHolder>data = new ArrayList<>();


            try {
                JSONArray jsonArray = new JSONArray(result);

                for(int i=0;i<jsonArray.length();i++) {

                    JSONObject json_data = jsonArray.getJSONObject(i);
                    GateWayViewHolder gateWayViewHolder = new GateWayViewHolder();
                    gateWayViewHolder.gatewayId = json_data.getString("gatewayId");
                    gateWayViewHolder.createdAt = json_data.getString("createdAt");
                    gateWayViewHolder.updatedAt = json_data.getString("updatedAt");
                    gateWayViewHolder.id = json_data.getString("id");
                    data.add(gateWayViewHolder);

                    recyclerView = (RecyclerView) findViewById(R.id.gatewayId_recyclerView);
                    mAdapter = new GatewayAdapter(GatewayActivity.this, data);
                    recyclerView.setAdapter(mAdapter);
                    recyclerView.setLayoutManager(new LinearLayoutManager(GatewayActivity.this));
                    recyclerView.addItemDecoration(new SimpleDividerItemDecoration(GatewayActivity.this));


                }


            } catch (Exception e) {
                e.printStackTrace();
            }


        }
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gateway);


        final Handler handler = new Handler();
        Runnable run = new Runnable() {
            @Override
            public void run() {

                GatewayCount task = new GatewayCount();
                String result = null;

                try {
                    result = task.execute("http://107.170.80.200:1337/gateway").get();
                } catch (InterruptedException e) {

                    e.printStackTrace();
                } catch (ExecutionException e) {

                    e.printStackTrace();
                }




                // Insert code to run after every fixed time
                Log.i("CONTENTS OF URL",result);

                handler.postDelayed(this,1000*60*10);
            }
        };
        handler.post(run);
    }
}
