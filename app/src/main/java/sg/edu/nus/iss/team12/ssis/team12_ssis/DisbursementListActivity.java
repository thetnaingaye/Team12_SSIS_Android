package sg.edu.nus.iss.team12.ssis.team12_ssis;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import sg.edu.nus.iss.team12.ssis.team12_ssis.model.Department;
import sg.edu.nus.iss.team12.ssis.team12_ssis.model.Disbursement;
import sg.edu.nus.iss.team12.ssis.team12_ssis.model.InventoryCatalogue;

public class DisbursementListActivity extends Activity {

    List<Disbursement> disbursementList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_disbursement_list);

        Bundle b = getIntent().getExtras();
        final HashMap<String,String> item = (HashMap<String, String>) b.get("item");

        final ListView list = findViewById(R.id.lv1);

        new AsyncTask<String, Void, List<Disbursement>>() {

            @Override
            protected List<Disbursement> doInBackground(String... params) {

                return Disbursement.jread(params[0]);
            }

            @Override
            protected void onPostExecute(List<Disbursement> rList) {

                for(Disbursement d: rList)
                {
                    if(d.get("DepartmentID").toString().equals(item.get("DepartmentID").toString()))
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

        Button btn = findViewById(R.id.button4);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent(DisbursementListActivity.this,ViewDisbursementFormActivity.class);
                startActivity(intent);
            }
        });
    }
}
