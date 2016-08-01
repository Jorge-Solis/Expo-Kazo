package com.pikazo.presenter.main;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.amazonaws.mobileconnectors.lambdainvoker.LambdaFunctionException;
import com.google.gson.JsonObject;
import com.pikazo.presenter.BasePresenter;
import com.pikazo.rest.dto.Job;
import com.pikazo.rest.services.IMainService;
import com.pikazo.view.activities.main.IMainView;

import java.util.Arrays;

import javax.inject.Inject;

import io.realm.RealmChangeListener;
import io.realm.RealmResults;
import io.realm.Sort;

/**
 * Created by jsolisl-as on 05/10/2015.
 */
public class MainPresenter extends BasePresenter implements IMainPresenter{

    private IMainView mainView;
    private IMainService mainService;

    @Inject
    public MainPresenter(Context context, IMainView mainView, IMainService mainService){
        super(context);
        this.mainView = mainView;
        this.mainService = mainService;
        sharedData.getUserQueueJobs().addChangeListener(new RealmChangeListener<RealmResults<Job>>() {
            @Override
            public void onChange(RealmResults<Job> realmResults) {
                // Notify the view
                realmResults = realmResults.sort("time", Sort.ASCENDING);
                showLocalQueueJobs();
            }
        });
    }

    /**
     * Call the respective lambda function to get user's Pikazo queue information
     */
    @Override
    public void checkQueue() {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("userid", sharedData.getDeviceID());
        new AsyncTask<JsonObject, Void, Job[]>() {

            @Override
            protected Job[] doInBackground(JsonObject... params) {
                try {
                    return lambdaProxy.queue(params[0]);
                } catch (LambdaFunctionException lfe) {
                    Log.e("AWS", "Failed to invoke styles", lfe);
                    return null;
                }
            }

            @Override
            protected void onPostExecute(Job[] result) {
                super.onPostExecute(result);
                if (result.length > 0) {
                    updateLocalQueueJobs(result);
                }
                Log.i("AWS", "Response:\n" + result.toString());
            }
        }.execute(jsonObject);
    }

    /**
     * Loads information from the passed image result ID
     * @param imageResultId
     */
    @Override
    public void loadImageInformation(String imageResultId) {
        // TODO: Take output ID from jobs in result
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("userid", sharedData.getDeviceID());
        jsonObject.addProperty("imageid", imageResultId);
        new AsyncTask<JsonObject, Void, JsonObject>() {

            @Override
            protected JsonObject doInBackground(JsonObject... params) {
                try {
                    return lambdaProxy.image(params[0]);
                } catch (LambdaFunctionException lfe) {
                    Log.e("AWS", "Failed to invoke styles", lfe);
                    return null;
                }
            }

            @Override
            protected void onPostExecute(JsonObject result) {
                super.onPostExecute(result);
                Log.i("AWS", "Response:\n" + result.toString());
            }
        }.execute(jsonObject);
    }

    // TODO: This method should be in another place such as PaintView
    @Override
    public void getStyles() {
        new AsyncTask<JsonObject, Void, JsonObject>() {

            @Override
            protected JsonObject doInBackground(JsonObject... params) {
                try {
                    return lambdaProxy.styles();
                } catch (LambdaFunctionException lfe) {
                    Log.e("AWS", "Failed to invoke styles", lfe);
                    return null;
                }
            }

            @Override
            protected void onPostExecute(JsonObject result) {
                super.onPostExecute(result);
                Log.i("AWS", "Response:\n" + result.toString());
            }
        }.execute();
    }

    /**
     * Updates the Realm instance for queue jobs
     */
    private void updateLocalQueueJobs(Job[] jobsResult) {
        realm.beginTransaction();
        realm.copyToRealmOrUpdate(Arrays.asList(jobsResult));
        realm.commitTransaction();
        mainView.updatePortfolio();
    }

    /**
     * Triggered by the change listener of RealmResults for queue jobs,
     * notify the view to update the portfolio data and display it
     */
    private void showLocalQueueJobs() {
        mainView.updatePortfolio();
    }
}
