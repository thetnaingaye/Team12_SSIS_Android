package sg.edu.nus.iss.team12.ssis.team12_ssis;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import sg.edu.nus.iss.team12.ssis.team12_ssis.model.Disbursement;
import sg.edu.nus.iss.team12.ssis.team12_ssis.model.InventoryCatalogue;
import sg.edu.nus.iss.team12.ssis.team12_ssis.model.RetrivalItem;

public class InventoryRetrievalListActivity extends Activity {
    SharedPreferences pref;
    String token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventory_retrieval_list);
        pref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        token = pref.getString("tokenKey", "hereJustPutRandomDefaultValue");

        Bundle b = getIntent().getExtras();
        final HashMap<String, String> item = (HashMap<String, String>) b.get("item");
        TextView textView_Desc = findViewById(R.id.textView_Description_Value);
        textView_Desc.setText(item.get("Description"));
        final ListView list = (ListView) findViewById(R.id.lv1);
        final String itemID = item.get("ItemID").toString();
        String uri = InventoryCatalogue.URI_SERVICE + "GetRelevantListByDept";
        new AsyncTask<String, Void, List<RetrivalItem>>() {

            @Override
            protected List<RetrivalItem> doInBackground(String... params) {

                return RetrivalItem.jread_getItemByDept(params[0], itemID, token);
            }

            @Override
            protected void onPostExecute(List<RetrivalItem> rlist) {

                list.setAdapter(new MyAdaptor_Retrivallist_Row(InventoryRetrievalListActivity.this, R.layout.row_retrivallist, rlist));

            }


        }.execute(uri);


        final Button btnSave = findViewById(R.id.button_Save);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                btnSave.setEnabled(false);
                btnSave.setClickable(false);
                btnSave.setText(R.string.db_message);
                List<RetrivalItem> finalList = new ArrayList<RetrivalItem>();
                String str = "";
                for (int i = 0; i < list.getCount(); i++) {
                    RetrivalItem item = (RetrivalItem) list.getAdapter().getItem(i);
                    finalList.add(item);

                }

//                for (RetrivalItem r : finalList) {
//                    str += "Requested: " + r.get("RequestedQty") + "\t" + " Actual: " + r.get("ActualQty") + "\n";
//                }


                final String url = InventoryCatalogue.URI_SERVICE + "SubmitRetrieval";

                new AsyncTask<List<RetrivalItem>, Void, Void>() {
                    String result;

                    @Override
                    protected Void doInBackground(List<RetrivalItem>... params) {

                        result = RetrivalItem.submitRetrival(params[0], token, url);
                        return null;

                    }

                    @Override
                    protected void onPostExecute(Void d) {
                        Toast.makeText(InventoryRetrievalListActivity.this, result, Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(InventoryRetrievalListActivity.this, InventoryListActivity.class);
                        intent.addFlags((Intent.FLAG_ACTIVITY_CLEAR_TOP));
                        startActivity(intent);

                    }

                }.execute(finalList);


//                Toast.makeText(InventoryRetrievalListActivity.this, str, Toast.LENGTH_SHORT).show();


//                Intent intent = new Intent(InventoryRetrievalListActivity.this,MainActivity.class);
//                intent.addFlags((Intent.FLAG_ACTIVITY_CLEAR_TOP));
//                startActivity(intent);
            }
        });
    }
}
