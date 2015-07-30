package samsunginterview.com.app.samsunginterview.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import samsunginterview.com.app.samsunginterview.DisplayImageAcitivty;
import samsunginterview.com.app.samsunginterview.R;

/**
 * Created by arjunflex on 30/07/15.
 */
public class ImagePageDetailFragment extends Fragment {

    private static final String IMAGE_DATA_EXTRA = "resId";
    private int mImageNum;
    private ImageView mImageView;
    private String TAG = "IMGPANELFRAG";

    private JSONArray objects;

    public static ImagePageDetailFragment newInstance(int imageNum) {
        final ImagePageDetailFragment f = new ImagePageDetailFragment();
        final Bundle args = new Bundle();
        args.putInt(IMAGE_DATA_EXTRA, imageNum);
        f.setArguments(args);
        return f;
    }

    // Empty constructor, required as per Fragment docs
    public ImagePageDetailFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            String str = getActivity().getSharedPreferences("MainAct", Context.MODE_PRIVATE).getString("searchdata", "");
            Log.i(TAG,str);
            objects = new JSONArray(str);
        }catch(JSONException e) {
            Log.e(TAG,e.getMessage(),e);
            objects = new JSONArray();
        }


        mImageNum = getArguments() != null ? getArguments().getInt(IMAGE_DATA_EXTRA) : -1;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // image_detail_fragment.xml contains just an ImageView
        final View v = inflater.inflate(R.layout.image_detail_fragment, container, false);
        mImageView = (ImageView) v.findViewById(R.id.imageView);
        return v;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        try {
            JSONObject json = (JSONObject) objects.get(mImageNum);
            String url = json.getString("url");
            Picasso.with(getActivity().getApplicationContext()).load(url).resize(300,300).centerCrop().into(mImageView);

        }catch (JSONException e) {
            Log.e(TAG,e.getMessage(),e);
        }


    }
}
