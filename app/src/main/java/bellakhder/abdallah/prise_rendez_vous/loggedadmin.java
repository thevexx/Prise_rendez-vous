package bellakhder.abdallah.prise_rendez_vous;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;

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
                startActivity(a);
            }
        });

        initList();
    }
    public void initList(){

        items=new String[]{"RDV1","RDV2","RDV3","RDV4"};

        listItems=new ArrayList<>(Arrays.asList(items));

        adapter=new ArrayAdapter<String>(this,
                R.layout.list_item, R.id.txtitem, listItems);

        listView.setAdapter(adapter);

    }

    @Override
    public void onClick(View view) {
        Intent i = new Intent(loggedadmin.this, searchclient.class);
        startActivity(i);
    }
}


