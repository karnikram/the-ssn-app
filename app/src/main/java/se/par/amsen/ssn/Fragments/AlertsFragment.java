package se.par.amsen.ssn.Fragments;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.melnykov.fab.FloatingActionButton;
import com.tjerkw.slideexpandable.library.SlideExpandableListAdapter;


import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import se.par.amsen.ssnmachan.R;

public class AlertsFragment extends Fragment
{
    ListView alertsList;
    Button retry;
    ProgressBar progressBar;
    FloatingActionButton fab;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        return inflater.inflate(R.layout.alerts_fragment_layout, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);
        alertsList = (ListView) getActivity().findViewById(R.id.listview2);
        retry = (Button) getActivity().findViewById(R.id.retry);
        progressBar = (ProgressBar) getActivity().findViewById(R.id.progressBar);
        fab = (FloatingActionButton) getActivity().findViewById(R.id.fab1);
        fab.attachToListView(alertsList);
        checkConnectionExecute();


    }

    private void checkConnectionExecute()

    {
        ConnectivityManager connectivityManager = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected())
        {
            progressBar.setVisibility(View.VISIBLE);
            new TechCrunchTask().execute();
        }
        else
        {
            Toast.makeText(getActivity(), "Connect to the Internet!", Toast.LENGTH_LONG).show();
            progressBar.setVisibility(View.GONE);
            retry.setVisibility(View.VISIBLE);
            retry.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    retry.setVisibility(View.GONE);
                    checkConnectionExecute();
                }
            });
        }
    }

    public class TechCrunchTask extends AsyncTask<Void, Void, ArrayList<HashMap<String, String>>>
    {
        @Override
        protected ArrayList<HashMap<String, String>> doInBackground(Void... params)
        {
            String downloadURL = "http://ssnmachanfeed.blogspot.com/feeds/posts/default";
            ArrayList<HashMap<String, String>> results = new ArrayList<>();
            try
            {
                URL url = new URL(downloadURL);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                InputStream inputStream = connection.getInputStream();
                results = processXML(inputStream);
            }
            catch (Exception e)
            {
                Log.d("Karnix", e + "");
            }
            return results;
        }

        @Override
        protected void onPostExecute(ArrayList<HashMap<String, String>> results)
        {
            AlertsAdapter adapter = new AlertsAdapter(getActivity(), results);
            progressBar.setVisibility(View.GONE);
            alertsList.setAdapter(adapter);
            fab.attachToListView(alertsList);
            alertsList.setAdapter(new SlideExpandableListAdapter(adapter, R.id.fab3, R.id.expand_view));
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v)
                {
                    new AlertDialog.Builder(getActivity())
                            .setMessage("This will take you to our facebook page where you can send in your message that you want " +
                                    "to be posted. \n\nDo note that not that push notifications won't be sent for all messages.")
                            .setPositiveButton("Continue", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which)
                                {
                                    try
                                    {
                                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("fb://page/893173980726657")));
                                    }
                                    catch(Exception e)
                                    {
                                        startActivity(new Intent(Intent.ACTION_VIEW,Uri.parse("http://m.facebook.com/ssnmachan")));
                                    }
                                }
                            })
                            .setNegativeButton("Cancel", null)
                            .show();

                }
            });
        }

        private ArrayList<HashMap<String, String>> processXML(InputStream stream) throws Exception
        {
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance(); //Singleton Class
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            Document xmlDocument = documentBuilder.parse(stream);
            Element rootElement = xmlDocument.getDocumentElement();
            NodeList itemsList = rootElement.getElementsByTagName("entry");
            NodeList itemChildren = null;
            Node currentItem = null;
            Node currentChild = null;
            HashMap<String, String> currentMap = null;
            ArrayList<HashMap<String, String>> results = new ArrayList<>();

            for (int i = 0; i < itemsList.getLength(); i++)
            {
                currentItem = itemsList.item(i);
                itemChildren = currentItem.getChildNodes();
                currentMap = new HashMap<>();   //A single hashmap stores the data of a single item.
                for (int j = 0; j < itemChildren.getLength(); j++)
                {
                    currentChild = itemChildren.item(j);
                    if (currentChild.getNodeName().equalsIgnoreCase("title"))
                    {
                        currentMap.put("title", currentChild.getTextContent());
                    }
                    if (currentChild.getNodeName().equalsIgnoreCase("summary"))
                    {
                        currentMap.put("description", currentChild.getTextContent());
                    }
                }
                if (currentMap != null && !currentMap.isEmpty())
                {
                    results.add(currentMap);
                }
            }
            return results;
        }

    }

}