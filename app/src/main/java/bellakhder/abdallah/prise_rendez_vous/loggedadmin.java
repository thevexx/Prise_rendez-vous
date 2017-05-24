package bellakhder.abdallah.prise_rendez_vous;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
public class loggedadmin extends AppCompatActivity implements View.OnClickListener {
    String[] items;
ImageButton b;
    ArrayList<String> listItems;

    ArrayAdapter<String> adapter;

    ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loggedadmin);
        listView=(ListView)findViewById(R.id.listview);
        b=(ImageButton)findViewById(R.id.btadd);
        b.setOnClickListener(this);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent a=new Intent(loggedadmin.this,objectifrdv.class);
                getIntent().putExtra("dr",listView.getItemAtPosition(i).toString());
                Log.v("AAAA",listView.getItemAtPosition(i).toString());
                startActivity(a);
            }
        });

        initList();
    }
    public void initList(){



        httpRequest("http://meet.ili-studios.tn/ws1/RDV/","GET","");


    }

    @Override
    public void onClick(View view) {
        Intent i = new Intent(loggedadmin.this, searchclient.class);
        startActivity(i);
    }


    public void httpRequest(String Url, String method, String xmldata) {

        new AsyncTask<String, String, String>() {
            String method;
            int tmp;
            String data="";

            protected void onPreExecute() {
            }
            @Override
            protected String doInBackground(String... params) {
                try {
                    URL url = new URL(params[0]);
                    method = params[1];
                    String urlParams = params[2];

                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();

                    //httpURLConnection.setDoOutput(true);
                    httpURLConnection.setRequestMethod(method);

//                    OutputStream os = httpURLConnection.getOutputStream();
//                    os.write(urlParams.getBytes());
//                    os.flush();
//                    os.close();

                    InputStream is = httpURLConnection.getInputStream();
                    while((tmp=is.read())!=-1){
                        data+= (char)tmp;
                    }

                    is.close();
                    httpURLConnection.disconnect();



                    return data;
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                    return "Exception: "+e.getMessage();
                } catch (IOException e) {
                    e.printStackTrace();
                    return "Exception: "+e.getMessage();
                }
            }

            @Override
            protected void onPostExecute(String msg) {
                Log.v("TEST",msg);

                ///
                items=new String[]{};//{"RDV1","RDV2","RDV3","RDV4"};

                listItems=new ArrayList<>(Arrays.asList(items));


                listView.setAdapter(adapter);

                // items=new String[]{"DR1","DR2","DR3","DR00000"};
                listItems=new ArrayList<>();

                try {
                    JSONArray jsonArray = new JSONArray(msg);
                    for(int i = 0;i<jsonArray.length();i++){
                        listItems.add(jsonArray.getJSONObject(i).getString("date_rdv") +" "+jsonArray.getJSONObject(i).getString("description_rdv"));
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }



                adapter=new ArrayAdapter<String>(loggedadmin.this,R.layout.list_item, R.id.txtitem, listItems);

                listView.setAdapter(adapter);
            }
        }.execute(Url, method, xmldata);
    }

}


