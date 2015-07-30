package samsunginterview.com.app.samsunginterview.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import samsunginterview.com.app.samsunginterview.R;
import samsunginterview.com.app.samsunginterview.adapters.SearchAdapter;

/**
 * Created by arjunflex on 30/07/15.
 */
public class SearchListFragment extends ListFragment {

    private String TAG = "SearchListFragment";

    public interface OnSearchItemSelectedListener {
        public void onSearchItemSelected(int position,int total);
    }





    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if(savedInstanceState != null) {
            try {
                String data = savedInstanceState.getString("listdata");
                Log.i(TAG,data);
                JSONArray array = new JSONArray(data);
                setListAdapter(new SearchAdapter(getActivity().getApplicationContext(),array));
            }catch (JSONException e) {
                Log.e(TAG,e.getMessage(),e);
            }
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        try {
            outState.putString("listdata",((SearchAdapter)getListAdapter()).getJson());
        }catch (Exception e) {
            Log.e(TAG,e.getMessage(),e);
        }

    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        ((OnSearchItemSelectedListener)getActivity()).onSearchItemSelected(position,getListAdapter().getCount());
    }
}
