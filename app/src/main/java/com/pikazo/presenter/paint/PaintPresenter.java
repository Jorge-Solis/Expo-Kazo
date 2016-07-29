package com.pikazo.presenter.paint;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.amazonaws.mobileconnectors.lambdainvoker.LambdaFunctionException;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferListener;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferState;
import com.google.gson.JsonObject;
import com.pikazo.presenter.BasePresenter;
import com.pikazo.rest.dto.DeviceImage;
import com.pikazo.rest.services.PaintService;
import com.pikazo.view.activities.Paint.PaintView;

import java.util.UUID;

import javax.inject.Inject;

/**
 * Created by jsolisl-as on 05/10/2015.
 */
public class PaintPresenter extends BasePresenter implements IPaintPresenter {

    private PaintView paintView;
    private PaintService paintService;

    @Inject
    public PaintPresenter(Context context, PaintView paintView, PaintService paintService){
        super(context);
        this.paintView = paintView;
        this.paintService = paintService;
    }

    /**
     * Takes an image path ready to be uploaded, after upload success sends the painting job
     */
    @Override
    public void uploadSelectedImageAndSummitForPainting() {
        final DeviceImage deviceImage = new DeviceImage();
        deviceImage.setId(UUID.randomUUID().toString());
        deviceImage.setPath(sharedData.getSelectedImageToPaint());
        serverSubmission.uploadImageId(deviceImage,
                new TransferListener() {

                    @Override
                    public void onStateChanged(int id, TransferState state) {
                        // do something
                    }

                    @Override
                    public void onProgressChanged(int id, long bytesCurrent, long bytesTotal) {
                        int percentage = (int) (bytesCurrent / bytesTotal * 100);
                        // Finally summits the painting job
                        if (percentage == 100) {
                            serverSubmission.setContentId(deviceImage.getId());
                            serverSubmission.setInitId(deviceImage.getId());
                            serverSubmission.summit(new AsyncTask<JsonObject, Void, JsonObject>() {
                                @Override
                                protected JsonObject doInBackground(JsonObject... params) {
                                    try {
                                        return lambdaProxy.submit(params[0]);
                                    } catch (LambdaFunctionException lfe) {
                                        Log.e("AWS", "Failed to invoke summit", lfe);
                                        return null;
                                    }
                                }

                                @Override
                                protected void onPostExecute(JsonObject result) {
                                    super.onPostExecute(result);
                                    // Close the view, job is done here
                                    paintView.goBack();
                                }
                            });
                        }
                    }

                    @Override
                    public void onError(int id, Exception ex) {
                        Log.e("AWS", "Error uploading file:\n" + ex.getLocalizedMessage());
                    }

                });
    }

}
