package bellakhder.abdallah.prise_rendez_vous;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Arrays;

public class listrdvclient extends AppCompatActivity {
    String[] items;

    ArrayList<String> listItems;

    ArrayAdapter<String> adapter;

    ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listrdvclient);
        listView=(ListView)findViewById(R.id.listview);
        initList();
    }
    public void initList(){

        items=new String[]{"RDV1","RDV2","RDV3","RDV4"};

        listItems=new ArrayList<>(Arrays.asList(items));

        adapter=new ArrayAdapter<String>(this,
                R.layout.list_item, R.id.txtitem, listItems);

        listView.setAdapter(adapter);

    }
}

