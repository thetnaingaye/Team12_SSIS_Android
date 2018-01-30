package sg.edu.nus.iss.team12.ssis.team12_ssis;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import sg.edu.nus.iss.team12.ssis.team12_ssis.model.InventoryCatalogue;
import sg.edu.nus.iss.team12.ssis.team12_ssis.model.RequisitionRecord;
import sg.edu.nus.iss.team12.ssis.team12_ssis.model.RequisitionRecordDetail;

/**
 * Created by mmu1t on 28/1/2018.
 */

public class ViewRequisitionFormListActivity_History extends Activity implements AdapterView.OnItemClickListener {

    SharedPreferences pref;
    String token;
    String deptid;

    ListView requestList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //If else condition for current and history ones. Different logic, i think....
        setContentView(R.layout.activity_view_requisition_form_list_history);
        requestList = findViewById(R.id.listview_requestlist);

        pref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        token = pref.getString("tokenKey", "hereJustPutRandomDefaultValue");
        deptid = pref.getString("department", "hereJustPutRandomDefaultValue");

        bindListView();

        requestList.setOnItemClickListener(this);

    }

    private void bindListView()
    {
        new AsyncTask<String, Void, List<RequisitionRecord>>() {

            @Override
            protected List<RequisitionRecord> doInBackground(String... params) {

                return RequisitionRecord.jread_GetRequest(params[0],deptid,token);
            }

            @Override
            protected void onPostExecute(List<RequisitionRecord> rList) {
                List<RequisitionRecord> pendingList = new ArrayList<>();
                for(RequisitionRecord r:rList)
                {
                    RequisitionRecordDetail rd = r.requisitionRecordDetailsList.get(0);
                    if(rd != null)
                    {
                        if(!rd.get("Status").equals("Pending"))
                        {
                            pendingList.add(r);
                        }
                    }
                }
                requestList.setAdapter(new MyAdaptor_Reqeuestlist_history_row(ViewRequisitionFormListActivity_History.this, R.layout.row_requestlist_history, pendingList));
            }


        }.execute(InventoryCatalogue.URI_SERVICE +"GetRequestsList");
    }

    @Override
    public void onItemClick(AdapterView<?> av, View v, int position, long id) {

        RequisitionRecord item = (RequisitionRecord) av.getAdapter().getItem(position);
        Intent intent = new Intent(ViewRequisitionFormListActivity_History.this, ViewRequisitionFormDetailsActivity.class);

        intent.putExtra("requisition_record", item);
        intent.putExtra("history","yes");
        startActivity(intent);

    }
}
