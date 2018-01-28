package sg.edu.nus.iss.team12.ssis.team12_ssis;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.ImageButton;

import java.util.HashMap;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //If else. If is Clerk, Show clerk layout
        //If DeptHead, Show DeptHead layout

        Bundle b = getIntent().getExtras();
        final String role =  b.getString("Role");
        boolean isClerk = (role.equals("Clerk"));
        boolean isHOD = (role.equals("HOD"));

        if(isClerk)
        {
            setContentView(R.layout.activity_main_clerk);
            ImageButton imgBtn = (ImageButton) findViewById(R.id.imageButton_Main);
            imgBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View arg0) {
                    Intent intent = new Intent(MainActivity.this,InventoryListActivity.class);
                    startActivity(intent);
                }
            });

            ImageButton imgBtn_Collection = (ImageButton) findViewById(R.id.imageButton_Collection);
            imgBtn_Collection.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View arg0) {
                    Intent intent = new Intent(MainActivity.this,CollectionListActivity.class);
                    startActivity(intent);
                }
            });
        }else if(isHOD)
        {
            setContentView(R.layout.activity_main_depthead);
            ImageButton imgBtn = (ImageButton) findViewById(R.id.imageButton_Requests);
            imgBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View arg0) {
                    Intent intent = new Intent(MainActivity.this,ViewRequisitionFormListActivity.class);
                    startActivity(intent);
                }
            });

            ImageButton imgBtn_history = (ImageButton) findViewById(R.id.imageButton_History);
            imgBtn_history.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View arg0) {
                    Intent intent = new Intent(MainActivity.this,ViewRequisitionFormListActivity_History.class);
                    startActivity(intent);
                }
            });

        }else
        {
            setContentView(R.layout.activity_main);
        }

    }
}
