//-----------Team Member : THET NAING AYE's Codes-----------//
package sg.edu.nus.iss.team12.ssis.team12_ssis;

import android.app.Activity;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.ContactsContract;
import android.support.v4.view.MenuItemCompat;
import android.view.View;
import android.widget.ImageButton;
import android.view.Menu;
import android.view.MenuItem;

import java.util.HashMap;

public class MainActivity extends Activity {
    SharedPreferences pref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        //If else. If is Clerk, Show clerk layout
        //If DeptHead, Show DeptHead layout

        pref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        Bundle b = getIntent().getExtras();
//        final String role =  b.getString("Role");

        final String role = pref.getString("role", "hereJustPutRandomDefaultValue");

        boolean isClerk = (role.equals("Clerk"));
        boolean isHOD = (role.equals("HOD"));
        //setting correct home page based on Role
        if (isClerk) {
            setContentView(R.layout.activity_main_clerk);
            ImageButton imgBtn = (ImageButton) findViewById(R.id.imageButton_Main);
            imgBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View arg0) {
                    Intent intent = new Intent(MainActivity.this, InventoryListActivity.class);
                    startActivity(intent);
                }
            });

            ImageButton imgBtn_Collection = (ImageButton) findViewById(R.id.imageButton_Collection);
            imgBtn_Collection.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View arg0) {
                    Intent intent = new Intent(MainActivity.this, CollectionListActivity.class);
                    startActivity(intent);
                }
            });
        } else if (isHOD) {
            setContentView(R.layout.activity_main_depthead);
            ImageButton imgBtn = (ImageButton) findViewById(R.id.imageButton_Requests);
            imgBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View arg0) {
                    Intent intent = new Intent(MainActivity.this, ViewRequisitionFormListActivity.class);
                    startActivity(intent);
                }
            });

            ImageButton imgBtn_history = (ImageButton) findViewById(R.id.imageButton_History);
            imgBtn_history.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View arg0) {
                    Intent intent = new Intent(MainActivity.this, ViewRequisitionFormListActivity_History.class);
                    startActivity(intent);
                }
            });

        } else {
            setContentView(R.layout.activity_main);
        }


    }

    //menu option
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case R.id.option2:
                pref.edit().clear().commit();
                Intent intent_logout = new Intent(getApplicationContext(),LoginActivity.class);
                finish();
                startActivity(intent_logout);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }


}
