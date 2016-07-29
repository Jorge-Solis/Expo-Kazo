package com.pikazo.global;

import com.pikazo.rest.dto.Job;

import java.util.ArrayList;

import javax.inject.Inject;

import io.realm.RealmResults;

/**
 * Created by jsolisl-as on 02/10/2015.
 */
public class SharedData {

    private String testData;
    private String deviceID;
    private ArrayList<String> deviceImages;
    private RealmResults<Job> userQueueJobs;
    private String selectedImageToPaint;

    @Inject
    public SharedData(){

    }

    public String getTestData() {
        return testData;
    }

    public void setTestData(String testData) {
        this.testData = testData;
    }

    public String getDeviceID() {
        return deviceID;
    }

    public void setDeviceID(String deviceID) {
        this.deviceID = deviceID;
    }

    public ArrayList<String> getDeviceImages() {
        return deviceImages;
    }

    public void setDeviceImages(ArrayList<String> deviceImages) {
        this.deviceImages = deviceImages;
    }

    public RealmResults<Job> getUserQueueJobs() {
        return userQueueJobs;
    }

    public void setUserQueueJobs(RealmResults<Job> userQueueJobs) {
        this.userQueueJobs = userQueueJobs;
    }

    public String getSelectedImageToPaint() {
        return selectedImageToPaint;
    }

    public void setSelectedImageToPaint(String selectedImageToPaint) {
        this.selectedImageToPaint = selectedImageToPaint;
    }
}
