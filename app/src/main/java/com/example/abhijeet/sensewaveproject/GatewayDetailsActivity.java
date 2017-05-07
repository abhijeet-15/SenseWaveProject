package com.example.abhijeet.sensewaveproject;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ExecutionException;

public class GatewayDetailsActivity extends AppCompatActivity {
    ArrayList<HashMap<String, String>> auidList;
    public ListView lv;

    public class GatewayDetail extends AsyncTask<String, Void ,String>{
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
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            try {
                JSONArray jsonArray = new JSONArray(result);


                    for(int i = 0; i < jsonArray.length(); i++){

                        JSONObject json_data = jsonArray.getJSONObject(i);
                        String id = json_data.getString("_id");
                        String auid = json_data.getString("auid");
                        String gatewayId = json_data.getString("gatewayId");
                        String state  = json_data.getString("state");
                        String createdAt = json_data.getString("createdAt");
                        String updatedAt = json_data.getString("updatedAt");

                        HashMap<String, String> aList = new HashMap<>();

                        aList.put("_id",id);
                        aList.put("auid",auid);
                        aList.put("gatewayId",gatewayId);
                        aList.put("state",state);
                        aList.put("createdAt",createdAt);
                        aList.put("updatedAt",updatedAt);

                        auidList.add(aList);

                        ListAdapter adapter = new SimpleAdapter(GatewayDetailsActivity.this, auidList,
                                R.layout.list_item, new String[]{ "id","auid","gatewayId","state"},
                                new int[]{R.id.id, R.id.auid, R.id.gatewayId, R.id.state});
                        lv.setAdapter(adapter);
                    }



                    }





             catch (Exception e) {
                e.printStackTrace();
            }
        }


    }







    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gateway_details);
        Intent i = getIntent();
        final String value = i.getStringExtra("key");
        auidList = new ArrayList<>();
        lv = (ListView) findViewById(R.id.list);

        Toast.makeText(this,value,Toast.LENGTH_LONG).show();

        final Handler handler = new Handler();
        Runnable run = new Runnable() {
            @Override
            public void run() {

                GatewayDetail task = new GatewayDetail();
                String result = null;
                try {
                    String s = URLEncoder.encode(value,"UTF-8");
                    result = task.execute("http://107.170.80.200:1337/gatewaystat/get?gatewayId="+ s).get();
                } catch (Exception e) {
                    e.printStackTrace();
                }









                // Insert code to run after every fixed time
                Log.i("CONTENTS OF URL",result);

                handler.postDelayed(this,1000*10*60);
            }
        };
        handler.post(run);



    }



    }

