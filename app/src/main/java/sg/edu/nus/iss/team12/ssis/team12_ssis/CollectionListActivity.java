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
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
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
    TextView textView_noLabel;
    TextView textView_dept_header;
    TextView textView_dId_header;
    TextView textView_prgress;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        //If else condition and set the header label to either "COLLECTIONS" or "OUTSTANDING DISBURSEMENT"
        setContentView(R.layout.activity_collection_list);
        pref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        token = pref.getString("tokenKey", "hereJustPutRandomDefaultValue");

        collectionDepList = findViewById(R.id.listview_dept);
        textView_noLabel = findViewById(R.id.textView_noList);
        textView_dept_header = findViewById(R.id.textView_Dept);
        textView_dId_header = findViewById(R.id.textView_d_id);
        textView_prgress = findViewById(R.id.textView_progress);

        textView_noLabel.setVisibility(View.GONE);
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
                if(list_collecitonpoints.size() == 0)
                {
                    textView_noLabel.setVisibility(View.VISIBLE);
                    textView_noLabel.setText("THERE IS NO OUTSTANDING RECORDS...");
                }
                else
                {
                    textView_noLabel.setVisibility(View.GONE);
                }


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

        new AsyncTask<String, Integer, List<Disbursement>>() {

            @Override
            protected List<Disbursement> doInBackground(String... params) {

                return Disbursement.jread(params[0],token);
            }

            @Override
            protected void onProgressUpdate(Integer...progress) {
                textView_prgress.setText("Retrieving data...." + "\nCompleted...." + progress[0]+ "%");
            }

            @Override
            protected void onPostExecute(List<Disbursement> rList) {
                disbursementList.clear();
                for(Disbursement d:rList){
                    if(d.get("Status").equals("Pending Collection")){
                        disbursementList.add(d);
                    }
                }
                //disbursementList = rList;
                collectionDepList.setAdapter(new MyAdaptor_DeptCollection_Row(CollectionListActivity.this, R.layout.row_dept_forcollection, disbursementList));
                if(disbursementList.size() == 0)
                {
                    textView_noLabel.setVisibility(View.VISIBLE);
                }
                else
                {
                    textView_noLabel.setVisibility(View.GONE);
                }

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
