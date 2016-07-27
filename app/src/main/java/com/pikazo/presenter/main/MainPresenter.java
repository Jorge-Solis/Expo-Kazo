package com.pikazo.presenter.main;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.amazonaws.mobileconnectors.lambdainvoker.LambdaFunctionException;
import com.pikazo.presenter.BasePresenter;
import com.pikazo.rest.dto.StylesInformation;
import com.pikazo.rest.services.IMainService;
import com.pikazo.view.activities.main.IMainView;

import javax.inject.Inject;

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
    }

    @Override
    public void testLambda() {
        new AsyncTask<Void, Void, StylesInformation>() {

            @Override
            protected StylesInformation doInBackground(Void... params) {
                // invoke "echo" method. In case it fails, it will throw a
                // LambdaFunctionException.
                try {
                    return lambdaProxy.styles();
                } catch (LambdaFunctionException lfe) {
                    Log.e("AWS", "Failed to invoke styles", lfe);
                    return null;
                }
            }

            @Override
            protected void onPostExecute(StylesInformation stylesInformation) {
                super.onPostExecute(stylesInformation);
                Log.i("AWS", "Response:\n" + stylesInformation.toString());
            }
        }.execute();
    }
}
