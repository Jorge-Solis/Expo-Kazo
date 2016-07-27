package com.pikazo.view.activities.main;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.amazonaws.auth.CognitoCachingCredentialsProvider;
import com.amazonaws.mobileconnectors.lambdainvoker.LambdaInvokerFactory;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferUtility;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.pikazo.PikazoApplication;
import com.pikazo.R;
import com.pikazo.di.components.DaggerMainComponent;
import com.pikazo.di.modules.MainModule;
import com.pikazo.presenter.main.IMainPresenter;
import com.pikazo.rest.aws.LambdaProxy;
import com.pikazo.view.activities.BaseActivity;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends BaseActivity implements IMainView {

    @Inject IMainPresenter mainPresenter;
    private CognitoCachingCredentialsProvider credentialsProvider;
    private AmazonS3 s3;
    private LambdaInvokerFactory factory;
    private TransferUtility transferUtility;
    private LambdaProxy lambdaProxy;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setActivityGraph();
        ButterKnife.bind(this);
        configureViews();
        // Cognito Credentials Provider
        credentialsProvider = new CognitoCachingCredentialsProvider(
                getApplicationContext(),    /* get the context for the application */
                "us-east-1:7fe69aa6-888d-4514-88d5-dc28e5031699",    /* Identity Pool ID */
                Regions.US_EAST_1        /* Region for your identity pool--US_EAST_1 or EU_WEST_1*/
        );
        // S3 configuration
        s3 = new AmazonS3Client(credentialsProvider);
        transferUtility = new TransferUtility(s3, getApplicationContext());
        // Lambda configuration
        factory = new LambdaInvokerFactory(
                getApplicationContext(),
                Regions.US_EAST_1,
                credentialsProvider);
        // Create the Lambda proxy object with default Json data binder.
        // You can provide your own data binder by implementing
        // LambdaDataBinder
        lambdaProxy = factory.build(LambdaProxy.class);

    }

    @OnClick(R.id.btnTestLambda)
    public void testLambda(View view) {
        mainPresenter.testLambda();
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
    }
}
