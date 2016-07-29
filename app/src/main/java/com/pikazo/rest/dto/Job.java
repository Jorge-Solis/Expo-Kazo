package com.pikazo.rest.dto;

import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by jorgesolis on 7/28/16.
 */
public class Job extends RealmObject {

    @SerializedName("jobid")
    @PrimaryKey
    private String jobId;
    private long count;
    private long time;
    private int state;
    @SerializedName("userid")
    private String userId;
    private int priority;
    private String queue;
    private int deleted;
    @SerializedName("checkouttime")
    private long checkOutTime;
    @SerializedName("checkintime")
    private long checkInTime;
    @SerializedName("paintername")
    private String painterName;
    private int retries;
    private JobParameters parameters;
    private JobNotes jobNotes;

    public String getJobId() {
        return jobId;
    }

    public void setJobId(String jobId) {
        this.jobId = jobId;
    }


    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public String getQueue() {
        return queue;
    }

    public void setQueue(String queue) {
        this.queue = queue;
    }

    public int getDeleted() {
        return deleted;
    }

    public void setDeleted(int deleted) {
        this.deleted = deleted;
    }


    public String getPainterName() {
        return painterName;
    }

    public void setPainterName(String painterName) {
        this.painterName = painterName;
    }

    public int getRetries() {
        return retries;
    }

    public void setRetries(int retries) {
        this.retries = retries;
    }

    public JobParameters getParameters() {
        return parameters;
    }

    public void setParameters(JobParameters parameters) {
        this.parameters = parameters;
    }

    public JobNotes getJobNotes() {
        return jobNotes;
    }

    public void setJobNotes(JobNotes jobNotes) {
        this.jobNotes = jobNotes;
    }

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public long getCheckOutTime() {
        return checkOutTime;
    }

    public void setCheckOutTime(long checkOutTime) {
        this.checkOutTime = checkOutTime;
    }

    public long getCheckInTime() {
        return checkInTime;
    }

    public void setCheckInTime(long checkInTime) {
        this.checkInTime = checkInTime;
    }
}
