package bellakhder.abdallah.prise_rendez_vous;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
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

public class searchclient extends AppCompatActivity {
    String[] items;

    ArrayList<String> listItems;

    ArrayAdapter<String> adapter;

    ListView listView;

    EditText editText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searchclient);
        listView=(ListView)findViewById(R.id.listview);
        editText=(EditText)findViewById(R.id.edsearch);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent m=new Intent(searchclient.this,ficheclient.class);
                getIntent().putExtra("dr",listView.getItemAtPosition(i).toString());
                startActivity(m);
            }
        });

        initList();

        editText.addTextChangedListener(new TextWatcher() {

            @Override

            public void beforeTextChanged(CharSequence s, int start, int count, int
                    after) {



            }



            @Override

            public void onTextChanged(CharSequence s, int start, int before, int
                    count) {

                if(s.toString().equals("")){

                    // reset listview

                    initList();

                }

                else{

                    // perform search

                    searchItem(s.toString());

                }

            }



            @Override

            public void afterTextChanged(Editable s) {



            }

        });

    }



    public void searchItem(String textToSearch){

        for(String item:items){

            if(!item.contains(textToSearch)){

                listItems.remove(item);

            }

        }

        adapter.notifyDataSetChanged();

    }

    public void initList(){

        items=new String[]{};//{"client1","client2","client3","client4"};

        listItems=new ArrayList<>(Arrays.asList(items));

        adapter=new ArrayAdapter<String>(this,
                R.layout.list_item, R.id.txtitem, listItems);

        listView.setAdapter(adapter);


        httpRequest("http://meet.ili-studios.tn/ws1/Client/","GET","");



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
                // items=new String[]{"DR1","DR2","DR3","DR00000"};
                listItems=new ArrayList<>();

                try {
                    JSONArray jsonArray = new JSONArray(msg);
                    for(int i = 0;i<jsonArray.length();i++){
                        listItems.add(jsonArray.getJSONObject(i).getString("id_user") +" "+jsonArray.getJSONObject(i).getString("cin_client"));
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }



                adapter=new ArrayAdapter<String>(searchclient.this,R.layout.list_item, R.id.txtitem, listItems);

                listView.setAdapter(adapter);
            }
        }.execute(Url, method, xmldata);
    }




}