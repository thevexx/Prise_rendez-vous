package bellakhder.abdallah.prise_rendez_vous;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.TextView;

public class objectifrdv extends AppCompatActivity {
TextView t1,t2,t3;
    ImageButton
    i1,i2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_objectifrdv);
        t1=(TextView)findViewById(R.id.tvrd1);
        t2=(TextView)findViewById(R.id.tvrd2);
        t3=(TextView)findViewById(R.id.tvrd3);

        i1=(ImageButton)findViewById(R.id.btnan);
        i2=(ImageButton)findViewById(R.id.btnva);
    }
}