package samsunginterview.com.app.samsunginterview;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import samsunginterview.com.app.samsunginterview.fragments.ImagePageDetailFragment;

/**
 * Created by arjunflex on 30/07/15.
 */
public class DisplayImageAcitivty extends AppCompatActivity {

    //public static final String EXTRA_IMAGE = "extra_image";

    private ImagePagerAdapter mAdapter;
    private ViewPager mPager;
    private int count,position;
    private static final String TAG = "DisplayImageAcitivty";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.image_detail_pager); // Contains just a ViewPager



        position = getIntent().getIntExtra("position",-1);
        count = getIntent().getIntExtra("total",0);

        Log.i(TAG,"pistion   count   " + position + " " + count);

        if(savedInstanceState != null) {
            count = savedInstanceState.getInt("total");
            position = savedInstanceState.getInt("position");
        }

        mAdapter = new ImagePagerAdapter(getSupportFragmentManager(), count);
        mPager = (ViewPager) findViewById(R.id.pager);
        mPager.setAdapter(mAdapter);

        if(savedInstanceState != null) {
            mAdapter.getItem(position);
        }
    }

    public static class ImagePagerAdapter extends FragmentStatePagerAdapter {
        private final int mSize;
        private int currentPos;

        public ImagePagerAdapter(FragmentManager fm, int size) {
            super(fm);
            mSize = size;
        }

        @Override
        public int getCount() {
            return mSize;
        }

        public int getPosition() {
            return currentPos;
        }

        @Override
        public Fragment getItem(int position) {

                currentPos = position;
                return ImagePageDetailFragment.newInstance(position);


        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        outState.putInt("total",count);
        outState.putInt("position",mAdapter.getPosition());
    }
}
