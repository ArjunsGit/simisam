package samsunginterview.com.app.samsunginterview.adapters;

import android.content.Context;
import android.media.Image;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import samsunginterview.com.app.samsunginterview.R;

/**
 * Created by arjunflex on 30/07/15.
 */
public class SearchAdapter extends BaseAdapter {

    private JSONArray objects;
    private Context context;
    private static final String TAG = "SearchAdapter";

    public SearchAdapter(Context context, JSONArray objects) {
        super();
        this.objects = objects;
        this.context = context;
    }

    @Override
    public int getCount() {
        return this.objects.length();
    }

    @Override
    public Object getItem(int position) {
        try {
            return objects.get(position);
        }catch (Exception e) {
            Log.e(TAG,e.getMessage(),e);

        }

        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        JSONObject object = (JSONObject) getItem(position);

            ViewHolder vh;

            if (convertView == null) {
                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(R.layout.search_item, parent, false);
                 vh = new ViewHolder();
                vh.imageView = (ImageView) convertView.findViewById(R.id.thumbnailImg);
                vh.textView = (TextView) convertView.findViewById(R.id.titletxt);

                convertView.setTag(vh);
            } else {
                 vh = (ViewHolder) convertView.getTag();

            }

        try {

            String imgurl = object.getString("url");
            Picasso.with(context).load(imgurl).resize(100,100).centerCrop().into(vh.imageView);
            String title = object.getString("title");
            vh.textView.setText(Html.fromHtml(title));

        }catch(JSONException e) {
            Log.e(TAG,e.getMessage(),e);
        }


        return convertView;
    }

    public String getJson() {
        return objects.toString();
    }

    static class ViewHolder {
        public ImageView imageView;
        public TextView textView;
    }
}
