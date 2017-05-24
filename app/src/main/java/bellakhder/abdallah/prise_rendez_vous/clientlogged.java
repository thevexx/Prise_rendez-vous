package bellakhder.abdallah.prise_rendez_vous;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Arrays;

public class clientlogged extends AppCompatActivity implements View.OnClickListener {
    String[] items;

    ArrayList<String> listItems;

    ArrayAdapter<String> adapter;

    ListView listView;

    EditText editText;
    ImageButton bm;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clientlogged);
        listView=(ListView)findViewById(R.id.listview);
        bm=(ImageButton)findViewById(R.id.btnan);
        bm.setOnClickListener(this);

        editText=(EditText)findViewById(R.id.txtsearch);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent a=new Intent(clientlogged.this,ficheadmin.class);
                getIntent().putExtra("dr",listView.getItemAtPosition(i).toString());
                startActivity(a);
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

        items=new String[]{"DR1","DR2","DR3","DR4"};

        listItems=new ArrayList<>(Arrays.asList(items));

        adapter=new ArrayAdapter<String>(this,
                R.layout.list_item, R.id.txtitem, listItems);

        listView.setAdapter(adapter);

    }

    @Override
    public void onClick(View view) {
        Intent i = new Intent(clientlogged.this, listrdvclient.class);
        startActivity(i);
    }
}