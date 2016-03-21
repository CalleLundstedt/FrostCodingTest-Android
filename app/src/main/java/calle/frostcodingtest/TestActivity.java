package calle.frostcodingtest;


import android.content.Intent;
import android.os.AsyncTask;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

public class TestActivity extends AppCompatActivity {


    private ListView lvPuffs;
    private TextView errorText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.navbar);

        DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder().build();
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(this).defaultDisplayImageOptions(defaultOptions).build();
        ImageLoader.getInstance().init(config);

        errorText = (TextView) findViewById(R.id.errorTextView);
        lvPuffs = (ListView) findViewById(R.id.puffListView);
        new GetJson().execute("http://fake-api.frostcloud.se/api");
    }

    private class GetJson extends AsyncTask<String, Void, String> {

        protected String doInBackground(String... urls) {
            String input = "";
            try {
                URL url= new URL(urls[0]);
                Scanner scan = new Scanner(url.openStream());
                while(scan.hasNext()) {
                    input += scan.nextLine();
                }
                scan.close();
            } catch (MalformedURLException e) {
               input = "Malformed URL";
            } catch (IOException e) {
                input = "No internet connection";
            }
            return input;
        }

        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            final ArrayList<Puff> puffArray = new ArrayList<>();

            if(!(result.equals("Malformed URL") || result.equals("No internet connection"))) {
                try {
                    JSONObject parent = new JSONObject(result);
                    JSONArray puffs = parent.getJSONArray("puffs");
                    JSONObject current;

                    for (int i = 0; i < puffs.length(); i++) {
                        current = puffs.getJSONObject(i);
                        puffArray.add(new Puff(current.get("url").toString(), current.get("image").toString(), current.get("buttonText").toString()));
                    }

                    PuffAdapter adapter = new PuffAdapter(TestActivity.this, R.layout.puff, puffArray);
                    lvPuffs.setAdapter(adapter);

                } catch (JSONException e) {
                    errorText.setText(e.toString());
                }
            } else {
                errorText.setText(result);
            }
        }
    }
}
