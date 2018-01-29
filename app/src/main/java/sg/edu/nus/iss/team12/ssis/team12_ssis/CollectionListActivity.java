package sg.edu.nus.iss.team12.ssis.team12_ssis;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import sg.edu.nus.iss.team12.ssis.team12_ssis.model.Disbursement;
import sg.edu.nus.iss.team12.ssis.team12_ssis.model.InventoryCatalogue;

public class CollectionListActivity extends Activity implements AdapterView.OnItemClickListener{

    SharedPreferences pref;
    String token;

    List<Disbursement> disbursementList = new ArrayList<>();
    ListView collectionDepList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //If else condition and set the header label to either "COLLECTIONS" or "OUTSTANDING DISBURSEMENT"
        setContentView(R.layout.activity_collection_list);
        pref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        token = pref.getString("tokenKey", "hereJustPutRandomDefaultValue");

        collectionDepList = findViewById(R.id.listview_dept);

        bindListView();

        Spinner s =(Spinner) findViewById(R.id.spinner);
        int spinner_index = s.getSelectedItemPosition();

        s.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                List<Disbursement> list_collecitonpoints = new ArrayList<>();
                for(Disbursement d:disbursementList)
                {
                    if(position == 0)
                    {
                        bindListView();
                    }
                    if (d.get("CollectionPointID").toString().equals(Integer.toString(position)))
                    {
                        list_collecitonpoints.add((d));
                    }

                }
                collectionDepList.setAdapter(new MyAdaptor_DeptCollection_Row(CollectionListActivity.this, R.layout.row_dept_forcollection, list_collecitonpoints));


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

                bindListView();

            }
        });


        collectionDepList.setOnItemClickListener(this);

    }

    private void bindListView()
    {
        new AsyncTask<String, Void, List<Disbursement>>() {

            @Override
            protected List<Disbursement> doInBackground(String... params) {

                return Disbursement.jread(params[0],token);
            }

            @Override
            protected void onPostExecute(List<Disbursement> rList) {
                for(Disbursement d:rList){
                    if(d.get("Status").equals("Pending Collection")){
                        disbursementList.add(d);
                    }
                }
                //disbursementList = rList;
                collectionDepList.setAdapter(new MyAdaptor_DeptCollection_Row(CollectionListActivity.this, R.layout.row_dept_forcollection, disbursementList));
            }


        }.execute(InventoryCatalogue.URI_SERVICE + "GetDisbursementLists");
    }

    @Override
    public void onItemClick(AdapterView<?> av, View v, int position, long id) {

        Disbursement item = (Disbursement) av.getAdapter().getItem(position);
        Intent intent = new Intent(CollectionListActivity.this, DisbursementListActivity.class);

        intent.putExtra("item", item);
        startActivity(intent);

    }
}
