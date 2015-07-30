package samsunginterview.com.app.samsunginterview.resttasks;

import android.app.DownloadManager;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

/**
 * Created by arjunflex on 30/07/15.
 */
public class RestTask extends AsyncTask<String,Void,Object> {

    private Context context;

    public RestTask(Context context) {

        this.context = context;


    }



    @Override
    protected Object doInBackground(String... params) {



        return null;
    }


}
