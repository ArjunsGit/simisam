package samsunginterview.com.app.samsunginterview;

import android.app.Application;
import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import samsunginterview.com.app.samsunginterview.adapters.SearchAdapter;
import samsunginterview.com.app.samsunginterview.fragments.SearchListFragment;


public class MainActivity extends AppCompatActivity implements SearchListFragment.OnSearchItemSelectedListener {

    private static final String TAG = "MainActivity";

    private EditText mSearchTxt;
    private SearchListFragment searchResultFragment;
    private String jsonString = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mSearchTxt = (EditText) findViewById(R.id.searchedit);
        mSearchTxt.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {

                if (actionId == EditorInfo.IME_ACTION_GO) {
                    doSearch();
                    InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(mSearchTxt.getWindowToken(), 0);

                    return true;
                }

                return false;
            }
        });

        searchResultFragment = new SearchListFragment();
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.frag_container, searchResultFragment, "searchlist");
        ft.commit();

        if(savedInstanceState != null) {
            mSearchTxt.setText(savedInstanceState.getString("searchkey"));

            try {
                showResults(new JSONArray(savedInstanceState.getString("searchdata")));
            }catch(JSONException e) {
                Log.e(TAG,e.getMessage(),e);
            }

            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(mSearchTxt.getWindowToken(), 0);
        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onPause() {
        super.onPause();
        SharedPreferences.Editor prefs = getSharedPreferences("MainAct",MODE_PRIVATE).edit();
        prefs.putString("searchdata",jsonString);
        prefs.putString("searchkey",mSearchTxt.getText().toString());
        prefs.commit();

    }

    private void doSearch() {

        String searchText = mSearchTxt.getText().toString();
        String charset = "UTF-8";
        try {
            String query = String.format("v=%s&q=%s&userip=%s",
                    URLEncoder.encode("1.0", charset),
                    URLEncoder.encode(searchText, charset),
                    URLEncoder.encode("127.0.0.1", charset));

            RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
            StringBuilder sb = new StringBuilder("https://ajax.googleapis.com/ajax/services/search/images?").append(query);
            JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.GET, sb.toString(),
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject json) {
                            // Display the first 500 characters of the response string.
                            try {
                               JSONArray array =  json.getJSONObject("responseData").getJSONArray("results");
                                showResults(array);
                                Log.i(TAG, json.toString());

                            }catch(Exception e) {
                                Log.e(TAG,e.getMessage(),e);
                            }
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e("Rest", error.getMessage(), error);
                }
            });
            queue.add(jsonRequest);



        }catch (UnsupportedEncodingException e) {
            Log.e(TAG,e.getMessage(),e);
        }


    }

    private void showResults(JSONArray array) {
        jsonString = array.toString();
        SearchAdapter adapter = new SearchAdapter(getApplicationContext(),array);
        searchResultFragment.setListAdapter(adapter);

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("searchdata", jsonString);
        outState.putString("searchkey",mSearchTxt.getText().toString());
    }

    @Override
    public void onSearchItemSelected(int position,int total) {

        Intent intent = new Intent(this,DisplayImageAcitivty.class);
        intent.putExtra("position",position);
        intent.putExtra("total",total);
        Log.i(TAG,"Position count" + " " + position + " " + total);
        this.startActivity(intent);

    }
}
