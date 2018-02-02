package sg.edu.nus.iss.team12.ssis.team12_ssis;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.SimpleAdapter;

import java.util.List;

import sg.edu.nus.iss.team12.ssis.team12_ssis.model.InventoryCatalogue;

public class InventoryListActivity extends Activity implements AdapterView.OnItemClickListener {
    SharedPreferences pref;
    String token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        pref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        token = pref.getString("tokenKey", "hereJustPutRandomDefaultValue");
        setContentView(R.layout.activity_inventory_list);
        final ListView list = findViewById(R.id.lv1);


        new AsyncTask<String, Void, List<InventoryCatalogue>>() {

            @Override
            protected List<InventoryCatalogue> doInBackground(String... params) {

                return InventoryCatalogue.jread(params[0],token);
            }

            @Override
            protected void onPostExecute(List<InventoryCatalogue> resultInventoryList) {

                     list.setAdapter(new MyAdaptor_Inventorylist_Row(InventoryListActivity.this, R.layout.row_inventorylist, resultInventoryList));
            }


        }.execute(InventoryCatalogue.URI_SERVICE + "GetInventoryList");

        list.setOnItemClickListener(this);


        CheckBox chk = findViewById(R.id.checkBox);
        chk.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

                                           @Override
                                           public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                                               if (isChecked) {
                                                   new AsyncTask<String, Void, List<InventoryCatalogue>>() {

                                                       @Override
                                                       protected List<InventoryCatalogue> doInBackground(String... params) {

                                                           return InventoryCatalogue.jread(params[0],token);
                                                       }

                                                       @Override
                                                       protected void onPostExecute(List<InventoryCatalogue> resultInventoryList) {

                                                           list.setAdapter(new MyAdaptor_Inventorylist_Row(InventoryListActivity.this, R.layout.row_inventorylist, resultInventoryList));
                                                       }


                                                   }.execute(InventoryCatalogue.URI_SERVICE + "GetRelevantItemList");


                                               } else {

                                                   new AsyncTask<String, Void, List<InventoryCatalogue>>() {

                                                       @Override
                                                       protected List<InventoryCatalogue> doInBackground(String... params) {

                                                           return InventoryCatalogue.jread(params[0],token);
                                                       }

                                                       @Override
                                                       protected void onPostExecute(List<InventoryCatalogue> resultInventoryList) {

                                                           list.setAdapter(new MyAdaptor_Inventorylist_Row(InventoryListActivity.this, R.layout.row_inventorylist, resultInventoryList));
                                                       }


                                                   }.execute(InventoryCatalogue.URI_SERVICE + "GetInventoryList");

                                               }


                                           }
                                       }
        );

    }

    @Override
    public void onItemClick(AdapterView<?> av, View v, int position, long id) {

        InventoryCatalogue item = (InventoryCatalogue) av.getAdapter().getItem(position);

        Intent intent = new Intent(InventoryListActivity.this, InventoryDetailActivity.class);
        intent.putExtra("item", item);
        startActivity(intent);

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
            case R.id.option1:
                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                finish();
                startActivity(intent);
                return true;

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
