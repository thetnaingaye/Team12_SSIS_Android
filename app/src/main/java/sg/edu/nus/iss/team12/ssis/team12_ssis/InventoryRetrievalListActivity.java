package sg.edu.nus.iss.team12.ssis.team12_ssis;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class InventoryRetrievalListActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventory_retrieval_list);

        Button btnSave = findViewById(R.id.button_Save);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent(InventoryRetrievalListActivity.this,MainActivity.class);
                intent.addFlags((Intent.FLAG_ACTIVITY_CLEAR_TOP));
                startActivity(intent);
            }
        });
    }
}
