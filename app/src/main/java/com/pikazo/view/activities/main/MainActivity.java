package com.pikazo.view.activities.main;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.pikazo.PikazoApplication;
import com.pikazo.R;
import com.pikazo.adapters.GenericAdapter;
import com.pikazo.di.components.DaggerMainComponent;
import com.pikazo.di.modules.MainModule;
import com.pikazo.global.AppConstants;
import com.pikazo.presenter.main.IMainPresenter;
import com.pikazo.rest.dto.Job;
import com.pikazo.utils.ImageUtils;
import com.pikazo.view.activities.BaseActivity;
import com.pikazo.view.activities.Paint.PaintActivity;
import com.pikazo.viewholders.DeviceImageViewHolder;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends BaseActivity implements IMainView {

    @Inject IMainPresenter mainPresenter;
    @Bind(R.id.gridImages) GridView gridImages;
    private GenericAdapter adapter;
    private Timer queueTimer;
    private TimerTask queueTimerTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setActivityGraph();
        ButterKnife.bind(this);
        configureViews();
    }

    @OnClick(R.id.btnOpenPaintView)
    public void loadImagesAndOpenPaintView(View view) {
        ArrayList<String> images =  ImageUtils.getImagesPath(this);
        sharedData.setDeviceImages(images);
        Intent paintIntent = new Intent(this, PaintActivity.class);
        startActivity(paintIntent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void setActivityGraph() {
        DaggerMainComponent.builder()
                .applicationComponent(((PikazoApplication)getApplication()).getApplicationComponent())
                .mainModule(new MainModule(this)).build().inject(this);
    }

    @Override
    protected void configureViews() {
        List<Job> jobsToShow = new ArrayList<>();
        for (Job job : sharedData.getUserQueueJobs()) {
            if (job.getState() == 3) {
                jobsToShow.add(job);
            } else {
                // TODO: Add this job at the bottom as pending job
            }
        }
        adapter = new GenericAdapter(this, jobsToShow, 1) {

            @Override
            public boolean onEnable(int position) {
                return true;
            }

            @Override
            public View onRenderingView(int position, View convertView, ViewGroup parent) {
                LayoutInflater layoutInflater = (LayoutInflater) getSystemService(Context
                        .LAYOUT_INFLATER_SERVICE);
                DeviceImageViewHolder holder;
                if (convertView == null) {
                    convertView = layoutInflater.inflate(R.layout.item_image, null);
                    holder = new DeviceImageViewHolder();
                    holder.image = (SimpleDraweeView) convertView.findViewById(R.id.imgSubject);
                    convertView.setTag(holder);
                } else {
                    holder = (DeviceImageViewHolder) convertView.getTag();
                }
                Job queueJob = sharedData.getUserQueueJobs().get(position);
                Uri uri = Uri.parse(AppConstants.AWS_S3_BASE_URL + queueJob.getParameters()
                        .getOutputId());
                holder.image.setImageURI(uri);
                return convertView;
            }

            @Override
            public int onGetItemViewType(int position) {
                return 0;
            }
        };
        gridImages.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        startQueueCheckTimer();
    }

    @Override
    protected void onPause() {
        super.onPause();
        cancelQueueCheckTimer();
    }

    /**
     * Because the adapter data has a Realm change listener, changes are updated automatically
     * so we just notify the adapter that data has changed to refresh and re-execute logic
     */
    @Override
    public void updatePortfolio(){
        List<Job> jobsToShow = new ArrayList<>();
        for (Job job : sharedData.getUserQueueJobs()) {
            if (job.getState() == 3) {
                jobsToShow.add(job);
            } else {
                // TODO: Add this job at the bottom as pending job
            }
        }
        adapter.setItems(jobsToShow);
        adapter.notifyDataSetChanged();
    }

    /**
     * Checks the queue of painting jobs each 30 seconds
     */
    private void startQueueCheckTimer() {
        queueTimer = new Timer();
        queueTimerTask = new TimerTask() {
            @Override
            public void run() {
                mainPresenter.checkQueue();
            }
        };
        queueTimer.scheduleAtFixedRate(queueTimerTask, 100, 30000);
    }

    /**
     * Cancels the timer execution
     */
    private void cancelQueueCheckTimer() {
        queueTimer.cancel();
    }
}
