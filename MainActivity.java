package com.example.trafficsituations;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Debug;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;
import java.util.logging.LogManager;

import io.swagger.client.ApiException;
import io.swagger.client.api.OAuthApi;
import io.swagger.client.api.TrafficSituationsV10Api;
import io.swagger.client.model.TokenApiModel;
import io.swagger.client.model.TrafficSituationApiModel;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new MyAsyncActivity().execute();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private class MyAsyncActivity extends AsyncTask<Void, Void, List<TrafficSituationApiModel>> {

        @Override
        protected List<TrafficSituationApiModel> doInBackground(Void... voids) {

            try {
                OAuthApi oAuthApi = new OAuthApi();
                TokenApiModel tokenApiModel = oAuthApi.getAccessToken();

                TrafficSituationsV10Api api = new TrafficSituationsV10Api();
                api.addHeader("Authorization", "Bearer " + tokenApiModel.getAccessToken());

                return api.tsV10TrafficSituationsGet();

            } catch (TimeoutException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ApiException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(List<TrafficSituationApiModel> trafficSituationApiModels) {

            // store traffic situation data
            TrafficSituationContent.setTrafficSituations(trafficSituationApiModels);

            // update loading text
            TextView loadingText = findViewById(R.id.loadingText);
            loadingText.setText("Done");

            // navigate to list view
            Context context = getApplicationContext();
            Intent intent = new Intent(context, TrafficSituationItemesListActivity.class);
            context.startActivity(intent);

            Log.i("onPostExecute", "Data load finished");
            // should not be able to go back to this activity
            finish();
        }
    }
}
