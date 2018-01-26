package sg.edu.nus.iss.team12.ssis.team12_ssis;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventory_list);
        final ListView list = findViewById(R.id.lv1);

        new AsyncTask<String, Void, List<InventoryCatalogue>>() {

            @Override
            protected List<InventoryCatalogue> doInBackground(String... params) {

                return InventoryCatalogue.jread(params[0]);
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

                                                           return InventoryCatalogue.jread(params[0]);
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

                                                           return InventoryCatalogue.jread(params[0]);
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


}
