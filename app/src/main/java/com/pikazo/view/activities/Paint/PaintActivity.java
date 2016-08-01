package com.pikazo.view.activities.Paint;

import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;
import com.pikazo.PikazoApplication;
import com.pikazo.R;
import com.pikazo.adapters.GenericAdapter;
import com.pikazo.di.components.DaggerPaintComponent;
import com.pikazo.di.modules.PaintModule;
import com.pikazo.presenter.paint.IPaintPresenter;
import com.pikazo.view.activities.BaseActivity;
import com.pikazo.viewholders.DeviceImageViewHolder;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PaintActivity extends BaseActivity implements PaintView {

    @Inject IPaintPresenter paintPresenter;
    @Bind(R.id.gridImages) GridView gridImages;
    @Bind(R.id.imgSubject) SimpleDraweeView imgSubject;
    @Bind(R.id.imgPaint) SimpleDraweeView imgPaint;
    @Bind(R.id.prLoading) ProgressBar prgLoading;
    @Bind(R.id.loadingLayout) RelativeLayout loadingLayout;
    @Bind(R.id.imageSourceActions) LinearLayout sourceActionsPanel;
    private GenericAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paint);
        setActivityGraph();
        ButterKnife.bind(this);
        configureViews();
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
        DaggerPaintComponent.builder()
                .applicationComponent(((PikazoApplication)getApplication()).getApplicationComponent())
                .paintModule(new PaintModule(this)).build().inject(this);
    }

    @Override
    protected void configureViews() {
        prgLoading.getIndeterminateDrawable().setColorFilter(Color.BLACK,
                android.graphics.PorterDuff.Mode.MULTIPLY);
        adapter = new GenericAdapter(this, sharedData.getDeviceImages(), 1) {

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
                String imagePath = sharedData.getDeviceImages().get(position);
                Uri uri = Uri.parse("file:///" + imagePath);
                holder.image.setImageURI(uri);
                return convertView;
            }

            @Override
            public int onGetItemViewType(int position) {
                return 0;
            }
        };
        gridImages.setAdapter(adapter);
        gridImages.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                hidePaintAnimation();
                imgSubject.setImageURI("file:///" + sharedData.getDeviceImages().get(position));
                sharedData.setSelectedImageToPaint(sharedData.getDeviceImages().get(position));
                showPaintAnimation();
            }
        });
    }

    @OnClick(R.id.btnGoBack)
    public void goBack(View view) {
        finish();
    }

    @OnClick(R.id.imgPaint)
    public void paint() {
        paintPresenter.uploadSelectedImageAndSummitForPainting();
        blockPaintFeature(true);
    }

    /**
     * Used by the presenter to block/unblock the paint feature in this view
     * (Enable/Disable the paint button)
     */
    @Override
    public void blockPaintFeature(boolean block) {
        if (block) {
            imgPaint.setEnabled(false);
            imgPaint.setClickable(false);
            displayLoadingState(true);
        } else {
            imgPaint.setEnabled(true);
            imgPaint.setClickable(true);
            displayLoadingState(false);
        }
    }

    @Override
    public void goBack(){
        finish();
    }

    /**
     * Hides the Grid of images and shows a loading spinner meanwhile
     */
    private void displayLoadingState(boolean requestInProgress) {
        if (requestInProgress) {
            gridImages.setVisibility(View.GONE);
            sourceActionsPanel.setVisibility(View.GONE);
            loadingLayout.setVisibility(View.VISIBLE);
        } else {
            gridImages.setVisibility(View.VISIBLE);
            sourceActionsPanel.setVisibility(View.VISIBLE);
            loadingLayout.setVisibility(View.GONE);
        }
    }
    /**
     * Displays the paint animation gif
     */
    private void showPaintAnimation() {
        ImageRequest imageRequest = ImageRequestBuilder.newBuilderWithResourceId(R.raw.paint_50)
                .build();
        DraweeController controller = Fresco.newDraweeControllerBuilder()
                .setUri(imageRequest.getSourceUri())
                .setAutoPlayAnimations(true)
                .build();
        imgPaint.setController(controller);
        imgPaint.setVisibility(View.VISIBLE);
    }

    /**
     * Hides the paint animation gif
     */
    private void hidePaintAnimation() {
        imgPaint.setVisibility(View.INVISIBLE);
    }

}
