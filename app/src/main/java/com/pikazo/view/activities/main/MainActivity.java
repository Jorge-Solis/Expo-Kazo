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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.facebook.drawee.view.SimpleDraweeView;
import com.pikazo.PikazoApplication;
import com.pikazo.R;
import com.pikazo.adapters.GenericAdapter;
import com.pikazo.custom.CustomTextView;
import com.pikazo.di.components.DaggerMainComponent;
import com.pikazo.di.modules.MainModule;
import com.pikazo.global.AppConstants;
import com.pikazo.presenter.main.IMainPresenter;
import com.pikazo.rest.dto.Job;
import com.pikazo.utils.ImageUtils;
import com.pikazo.view.activities.BaseActivity;
import com.pikazo.view.activities.Paint.PaintActivity;
import com.pikazo.viewholders.DeviceImageViewHolder;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.realm.RealmResults;

public class MainActivity extends BaseActivity implements IMainView {

    @Inject IMainPresenter mainPresenter;
    @Bind(R.id.gridImages) GridView gridImages;
    @Bind(R.id.jobsContainer) LinearLayout jobsContainer;
    @Bind(R.id.btnOpenPaintView) ImageView btnOpenPaintView;
    private GenericAdapter adapter;
    private Timer queueTimer;
    private TimerTask queueTimerTask;
    private LayoutInflater inflater;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setActivityGraph();
        ButterKnife.bind(this);
        configureViews();
        mainPresenter.checkQueue();
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
        inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        RealmResults<Job> jobsReadyToShow = sharedData.getUserQueueJobs().where()
                .equalTo("state", 3).findAll();
        RealmResults<Job> jobsPendingToShow = sharedData.getUserQueueJobs().where()
                .equalTo("state", 2).or().equalTo("state", 1).findAll();
        displayPendingJobs(jobsPendingToShow);
        final DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.MEDIUM, Locale.US);
        adapter = new GenericAdapter(this, jobsReadyToShow, 1) {

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
                    convertView = layoutInflater.inflate(R.layout.item_portfolio_image, null);
                    holder = new DeviceImageViewHolder();
                    holder.image = (SimpleDraweeView) convertView.findViewById(R.id.imgSubject);
                    holder.imageTitle = (CustomTextView) convertView.findViewById(R.id.txtImageTitle);
                    holder.imageDate = (CustomTextView) convertView.findViewById(R.id.txtImageDate);
                    convertView.setTag(holder);
                } else {
                    holder = (DeviceImageViewHolder) convertView.getTag();
                }
                Job queueJob = sharedData.getUserQueueJobs().get(position);
                Uri uri = Uri.parse(AppConstants.AWS_S3_BASE_URL + queueJob.getParameters()
                        .getOutputId());

                holder.image.setImageURI(uri);
                holder.imageTitle.setText("Untiled No. " + String.valueOf(position + 1));
                Date date = new Date(queueJob.getTime());
                holder.imageDate.setText(dateFormat.format(date));
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
     * Displays a job progress at the bottom
     * @param pendingJobs The jobs in progress to display (Updated each 30 secs)
     */
    private void displayPendingJobs(RealmResults<Job> pendingJobs) {
        for (Job job : pendingJobs) {
            Date jobTime = new Date(job.getTime());
            Date now = new Date();
            long diff = now.getTime() - jobTime.getTime();
            long diffMinutes = TimeUnit.MILLISECONDS.toMinutes(diff);
            RelativeLayout jobView = (RelativeLayout) inflater.inflate(R.layout.item_job, jobsContainer,
                    false);
            SimpleDraweeView pendingJobImage = (SimpleDraweeView) jobView.findViewById(R.id.imgJob);
            Uri uri = Uri.parse(AppConstants.AWS_S3_BASE_URL + job.getParameters()
                    .getOutputId());
            pendingJobImage.setImageURI(uri);
            CustomTextView txtJobTime = (CustomTextView) jobView.findViewById(R.id.txtJobTime);
            // State switch
            switch (job.getState()) {
                case 1: // Queue (not painting yet)
                    if (diff > 0) {
                        txtJobTime.setText("queued about " + String.valueOf(diffMinutes) +
                                " minutes ago");
                    } else {
                        txtJobTime.setText("queued just now");
                    }
                    break;
                case 2: // Painting
                    if (diff > 0) {
                        txtJobTime.setText("enhancing about " + String.valueOf(diffMinutes) +
                                " minutes ago");
                    } else {
                        txtJobTime.setText("enhancing just now");
                    }
                    break;
            }
            jobsContainer.addView(jobView);
        }
    }

    /**
     * Because the adapter data has a Realm change listener, changes are updated automatically
     * so we just notify the adapter that data has changed to refresh and re-execute logic
     */
    @Override
    public void updatePortfolio(){
        jobsContainer.removeAllViews();
        jobsContainer.addView(btnOpenPaintView);
        RealmResults<Job> jobsReadyToShow = sharedData.getUserQueueJobs().where()
                .equalTo("state", 3).findAll();
        RealmResults<Job> jobsPendingToShow = sharedData.getUserQueueJobs().where()
                .equalTo("state", 2).or().equalTo("state", 1).findAll();
        adapter.setItems(jobsReadyToShow);
        adapter.notifyDataSetChanged();
        displayPendingJobs(jobsPendingToShow);
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
