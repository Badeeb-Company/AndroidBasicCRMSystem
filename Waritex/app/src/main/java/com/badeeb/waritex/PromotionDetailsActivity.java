package com.badeeb.waritex;

import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.badeeb.waritex.adapter.SlideViewPagerAdapter;
import com.badeeb.waritex.fragment.ActivePromotionsFragment;
import com.badeeb.waritex.fragment.ProductsFragment;
import com.badeeb.waritex.model.Promotion;

import java.util.ArrayList;

import me.relex.circleindicator.CircleIndicator;

public class PromotionDetailsActivity extends AppCompatActivity {

    // For logging purpose
    private final String TAG = PromotionDetailsActivity.class.getName();

    private static ViewPager mPager;
    private ArrayList<Integer> mPromotionPhotos = new ArrayList<Integer>();
    private Promotion mPromotion;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_promotion_details);

        init();
    }

    private void init() {
        Log.d(TAG, "init - Start");

        preparePromotionInfo();

        this.mPager = (ViewPager) findViewById(R.id.promotion_details_viewpager);
        mPager.setAdapter(new SlideViewPagerAdapter(this, this.mPromotionPhotos));

        CircleIndicator indicator = (CircleIndicator) findViewById(R.id.promotion_details_indicator);
        indicator.setViewPager(mPager);

        TextView promotionTitleTV = (TextView) findViewById(R.id.promotion_details_title);
        promotionTitleTV.setText(mPromotion.getTitle());

        TextView promotionDueDateTV = (TextView) findViewById(R.id.promotion_details_due_date);
        promotionDueDateTV.setText(mPromotion.getDueDateFormated());

        TextView promotionDescriptionTV = (TextView) findViewById(R.id.promotion_details_description);
        promotionDescriptionTV.setText(mPromotion.getDescription());


        Log.d(TAG, "init - End");
    }

    private void preparePromotionInfo() {
        Intent intent = getIntent();
        String title = intent.getStringExtra(ActivePromotionsFragment.PROMOTION_TITLE_EXTRA);
        String dueDate = intent.getStringExtra(ActivePromotionsFragment.PROMOTION_DUE_DATE_EXTRA);

        mPromotion = new Promotion();
        mPromotion.setTitle(title);
        mPromotion.setDueDateFormated(dueDate);
        mPromotion.setDescription("This is a simple description for testing the layout of the promotion details for now.");

        Integer[] photos = {R.drawable.photo1,R.drawable.photo2,R.drawable.photo3,R.drawable.photo4,R.drawable.photo5};

        for(Integer photo : photos){
            mPromotionPhotos.add(photo);
        }
    }


    // Create onClick event for show vendors button
    public void onClickShowVendorsBttn(View view) {

        Log.d(TAG, "onClickShowVendorsBttn - Start");

        Intent vendorsListIntent = new Intent(this, VendorsListActivity.class);
        this.startActivity(vendorsListIntent);

        Log.d(TAG, "onClickShowVendorsBttn - End");
    }
}
