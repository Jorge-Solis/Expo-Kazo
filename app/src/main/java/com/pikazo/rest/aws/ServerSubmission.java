package com.pikazo.rest.aws;

import android.os.AsyncTask;

import com.amazonaws.mobileconnectors.s3.transferutility.TransferListener;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferObserver;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferUtility;
import com.google.gson.JsonObject;
import com.pikazo.global.SharedData;
import com.pikazo.rest.dto.DeviceImage;
import com.pikazo.utils.DeviceUtils;

import java.io.File;
import java.util.HashMap;

import javax.inject.Inject;

/**
 * Created by jorgesolis on 7/28/16.
 */
public class ServerSubmission {
    private String contentId;
    private String styleId;
    private String styleProxyId;
    private String initId;
    private String mode;
    private int size;
    private int priority;
    private String queue;
    private String parentJobId;
    private HashMap<String, String> notes;
    private TransferUtility transferUtility;
    private SharedData sharedData;
    private LambdaProxy lambdaProxy;

    @Inject
    public ServerSubmission(TransferUtility transferUtility, LambdaProxy lambdaProxy,
                            SharedData sharedData) {
        setMode("create");
        setSize(400);
        setPriority(0);
        setQueue("low");
        setParentJobId("");
        setNotes(new HashMap());
        this.transferUtility = transferUtility;
        this.lambdaProxy = lambdaProxy;
        this.sharedData = sharedData;
    }

    /**
     * Uploads the passed image from the device
     * @param deviceImage
     * @param transferListener
     */
    public void uploadImageId(DeviceImage deviceImage, TransferListener transferListener) {
        TransferObserver observer = transferUtility.upload(
                "pikazo-store",     /* The bucket to upload to */
                deviceImage.getId(),    /* The key for the uploaded object */
                new File(deviceImage.getPath())       /* The file where the data to upload exists */
        );
        observer.setTransferListener(transferListener);
    }

    /**
     * Sends the paint job from the current subject and style information set by the presenter
     * after upload of image is complete
     * @param handler The asynchronous handler of the request
     */
    public void summit(AsyncTask<JsonObject, Void, JsonObject> handler){
        // TODO: Remove these hardcoded values for fields
        styleId = "0CF4CC87-B34A-4376-9FDF-DEF5315AEBCD";
        styleProxyId = "ca2bf254-ecf3-46ee-bfe4-1209afb17a13";
        // Request Parameters
        final JsonObject parameters = new JsonObject();
        parameters.addProperty("contentid", contentId);
        parameters.addProperty("initid", initId);
        parameters.addProperty("styleid", styleId);
        parameters.addProperty("styleproxyid", styleProxyId);
        parameters.addProperty("mode", mode);
        parameters.addProperty("size", size);
        parameters.addProperty("parentjobid", parentJobId);
        parameters.addProperty("iospushtoken", "");
        parameters.addProperty("clientversion", DeviceUtils.getSDKVersion());
        parameters.addProperty("clientbuild", "2.1.1");
        parameters.addProperty("clientplatform", "Android");
        // Request
        JsonObject request = new JsonObject();
        request.addProperty("userid", sharedData.getDeviceID());
        request.add("notes", new JsonObject());// No notes
        request.add("parameters", parameters);
        request.addProperty("queue", queue);
        request.addProperty("priority", priority);
        // AWS Parameters
        JsonObject awsRequest = new JsonObject();
        awsRequest.add("request", request);
        // Execute the Lambda function
        handler.execute(awsRequest);
    }

    public String getContentId() {
        return contentId;
    }

    public void setContentId(String contentId) {
        this.contentId = contentId;
    }

    public String getStyleId() {
        return styleId;
    }

    public void setStyleId(String styleId) {
        this.styleId = styleId;
    }

    public String getStyleProxyId() {
        return styleProxyId;
    }

    public void setStyleProxyId(String styleProxyId) {
        this.styleProxyId = styleProxyId;
    }

    public String getInitId() {
        return initId;
    }

    public void setInitId(String initId) {
        this.initId = initId;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
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

    public String getParentJobId() {
        return parentJobId;
    }

    public void setParentJobId(String parentJobId) {
        this.parentJobId = parentJobId;
    }

    public HashMap<String, String> getNotes() {
        return notes;
    }

    public void setNotes(HashMap<String, String> notes) {
        this.notes = notes;
    }
}
