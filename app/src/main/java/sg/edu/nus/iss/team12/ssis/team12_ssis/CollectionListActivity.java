package sg.edu.nus.iss.team12.ssis.team12_ssis;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class CollectionListActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //If else condition and set the header label to either "COLLECTIONS" or "OUTSTANDING DISBURSEMENT"
        setContentView(R.layout.activity_collection_list);
        Button btn = findViewById(R.id.button2);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent(CollectionListActivity.this,ViewDisbursementFormActivity.class);
                startActivity(intent);
            }
        });
    }
}
