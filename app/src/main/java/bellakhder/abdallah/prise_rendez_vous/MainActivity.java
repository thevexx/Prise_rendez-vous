package bellakhder.abdallah.prise_rendez_vous;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    EditText e1, e2;
    Button b1, b2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        b1 = (Button) findViewById(R.id.bt1);
        b2 = (Button) findViewById(R.id.bt2);
        e1 = (EditText) findViewById(R.id.ed1);
        e2 = (EditText) findViewById(R.id.ed2);
        b1.setOnClickListener(this);
        b2.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.bt2) {

            Intent i = new Intent(MainActivity.this, inscriptionclient.class);
            startActivity(i);
        } else if (v.getId() == R.id.bt1 ) {
            String dataJson = "";
            JSONObject obj = new JSONObject();
            try {
                obj.put("LOGIN", e1.getText().toString());
                obj.put("PASSWORD",e2.getText().toString());
            } catch (JSONException e) {
                e.printStackTrace();
            }finally {
                dataJson = obj.toString();
            }

            httpRequest("http://meet.ili-studios.tn/ws1/auth/0/","POST",dataJson);




        }
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

                    httpURLConnection.setDoOutput(true);
                    httpURLConnection.setRequestMethod(method);

                    OutputStream os = httpURLConnection.getOutputStream();
                    os.write(urlParams.getBytes());
                    os.flush();
                    os.close();

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

                if (msg.equals("CLIENT")) {
                    Intent j = new Intent(MainActivity.this, clientlogged.class);
                    startActivity(j);
                }else if (msg.equals("ADMIN")){
                    Intent x = new Intent(MainActivity.this, loggedadmin.class);
                    startActivity(x);
                }else {
                    Toast.makeText(MainActivity.this.getApplicationContext(),"ERROR LOGIN",Toast.LENGTH_LONG).show();
                }
            }
        }.execute(Url, method, xmldata);
    }



}