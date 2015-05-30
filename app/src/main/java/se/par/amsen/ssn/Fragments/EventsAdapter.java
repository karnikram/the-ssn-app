package se.par.amsen.ssn.Fragments;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

import se.par.amsen.ssnmachan.R;

class EventsAdapter extends BaseAdapter
{
    ArrayList<HashMap<String, String>> dataSource = null;
    Context context;
    LayoutInflater layoutInflater;
    Bitmap bitmap = null;

    public void setBitmap(Bitmap bitmap)
    {
        this.bitmap = bitmap;
    }

    public EventsAdapter(Context context, ArrayList<HashMap<String, String>> dataSource)
    {
        this.context = context;
        this.dataSource = dataSource;
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount()
    {
        return dataSource.size();
    }

    @Override
    public Object getItem(int position)
    {
        return dataSource.get(position);
    }

    @Override
    public long getItemId(int position)
    {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        View v = convertView;
        MyeHolder holder = null;
        if (v == null)
        {
            v = layoutInflater.inflate(R.layout.events_list_item, parent, false);
            holder = new MyeHolder(v);
            v.setTag(holder);
        }
        else
            holder = (MyeHolder) v.getTag();

        HashMap<String, String> currentItem = dataSource.get(position);
        holder.title.setText(currentItem.get("title"));
        holder.pubdate.setText(currentItem.get("pubDate"));
        holder.description.setText(currentItem.get("description"));
        new DownloadImage().execute(currentItem.get("imageURL"));
        holder.icon.setImageBitmap(bitmap);
        return v;

    }

    public class DownloadImage extends AsyncTask<String, Void, Bitmap>
    {
        private Bitmap bitmap = null;

        @Override
        protected Bitmap doInBackground(String... params)
        {
            try
            {
                InputStream in = new URL(params[0]).openStream();
                bitmap = BitmapFactory.decodeStream(in);
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
            return bitmap;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap)
        {
            setBitmap(bitmap);
        }
    }
}

class MyeHolder
{
    TextView title, description, pubdate;
    ImageView icon;

    public MyeHolder(View view)
    {
        title = (TextView) view.findViewById(R.id.etitle);
        description = (TextView) view.findViewById(R.id.edescription);
        pubdate = (TextView) view.findViewById(R.id.edate);
        icon = (ImageView) view.findViewById(R.id.eicon);
    }

}
