package sg.edu.nus.iss.team12.ssis.team12_ssis;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.ImageButton;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //If else. If is Clerk, Show clerk layout
        //If DeptHead, Show DeptHead layout
        setContentView(R.layout.activity_main_clerk);
        ImageButton imgBtn = (ImageButton) findViewById(R.id.imageButton1);
        imgBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent(MainActivity.this,InventoryListActivity.class);
                startActivity(intent);
            }
        });
    }
}
