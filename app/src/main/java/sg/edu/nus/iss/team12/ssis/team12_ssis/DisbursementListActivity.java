//-----------Team Member : THET NAING AYE's Codes-----------//
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
import android.widget.TextView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import sg.edu.nus.iss.team12.ssis.team12_ssis.model.Department;
import sg.edu.nus.iss.team12.ssis.team12_ssis.model.Disbursement;
import sg.edu.nus.iss.team12.ssis.team12_ssis.model.InventoryCatalogue;

public class DisbursementListActivity extends Activity implements AdapterView.OnItemClickListener{

    SharedPreferences pref;
    String token;

    List<Disbursement> disbursementList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_disbursement_list);
        pref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        token = pref.getString("tokenKey", "hereJustPutRandomDefaultValue");

        Bundle b = getIntent().getExtras();
        final HashMap<String,String> item = (HashMap<String, String>) b.get("item");

        final ListView list = findViewById(R.id.lv1);
        list.setOnItemClickListener(this);

        new AsyncTask<String, Void, List<Disbursement>>() {

            @Override
            protected List<Disbursement> doInBackground(String... params) {

                return Disbursement.jread(params[0],token);
            }

            @Override
            protected void onPostExecute(List<Disbursement> rList) {

                for(Disbursement d: rList)
                {
                    if(d.get("DepartmentID").toString().equals(item.get("DepartmentID").toString()) && d.get("Status").equals("Pending Collection"))
                    {
                        disbursementList.add(d);
                    }
                }
                list.setAdapter(new MyAdaptor_DisbursementbyDept_Row(DisbursementListActivity.this, R.layout.row_disburment_bydept, disbursementList));

            }


        }.execute(InventoryCatalogue.URI_SERVICE + "GetDisbursementLists");

        final TextView textView_dept = (TextView) findViewById(R.id.textView_Dept_Value);


        String uri = InventoryCatalogue.URI_SERVICE + "GetDeptName/" + item.get("DepartmentID");
        new AsyncTask<String, Void, Department>() {

            @Override
            protected Department doInBackground(String... params) {

                return Department.jread(params[0]);
            }

            @Override
            protected void onPostExecute(Department d) {
                textView_dept.setText(d.get("DepartmentName"));

            }

        }.execute(uri);

    }

    @Override
    public void onItemClick(AdapterView<?> av, View v, int position, long id) {

        Disbursement item = (Disbursement) av.getAdapter().getItem(position);

        Intent intent = new Intent(DisbursementListActivity.this, ViewDisbursementFormActivity.class);
        Bundle b = new Bundle();
        intent.putExtra("disbursement",item);
        b.putSerializable("disbursement_detail", (Serializable) item.disbursementDetailsList);
        intent.putExtras(b);
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
