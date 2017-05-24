package bellakhder.abdallah.prise_rendez_vous;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class inscriptionclient extends AppCompatActivity {

    ImageButton btnvalid;
    EditText editText1,editText2,editText3,editText4,editText5,editText6,editText7,editText8;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inscriptionclient);

        editText1 = (EditText)findViewById(R.id.ed1);
        editText2 = (EditText)findViewById(R.id.ed2);
        editText3 = (EditText)findViewById(R.id.ed3);
        editText4 = (EditText)findViewById(R.id.ed4);
        editText5 = (EditText)findViewById(R.id.ed5);
        editText6 = (EditText)findViewById(R.id.editText7);
        editText7 = (EditText)findViewById(R.id.editText2);
        editText8 = (EditText)findViewById(R.id.editText3);

        btnvalid = (ImageButton)findViewById(R.id.btnan);


        btnvalid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String dataJson = "";
                JSONObject obj = new JSONObject();
                try {
                    obj.put("nom_user", editText1.getText().toString());
                    obj.put("prenom_user",editText7.getText().toString());
                    obj.put("nom_utilisateur_user",editText3.getText().toString());
                    obj.put("mot_de_passe_user",editText4.getText().toString());
                    obj.put("adresse_user","");
                    obj.put("adresse_email_user",editText5.getText().toString());
                    obj.put("date_de_naissance_user","");
                    obj.put("cin_client",editText7.getText().toString());
                    } catch (JSONException e) {
                    e.printStackTrace();
                }finally {
                    dataJson = obj.toString();
                }
                httpRequest("http://meet.ili-studios.tn/ws1/CLIENT/","POST",dataJson);
            }
        });
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
                Toast.makeText(inscriptionclient.this,"inscription valider",Toast.LENGTH_LONG).show();
                inscriptionclient.this.finish();
            }
        }.execute(Url, method, xmldata);
    }

}
