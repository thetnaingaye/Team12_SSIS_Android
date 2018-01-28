package sg.edu.nus.iss.team12.ssis.team12_ssis;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import sg.edu.nus.iss.team12.ssis.team12_ssis.model.Disbursement;
import sg.edu.nus.iss.team12.ssis.team12_ssis.model.InventoryCatalogue;
import sg.edu.nus.iss.team12.ssis.team12_ssis.model.RequisitionRecord;
import sg.edu.nus.iss.team12.ssis.team12_ssis.model.RequisitionRecordDetail;

public class ViewRequisitionFormListActivity extends Activity implements AdapterView.OnItemClickListener {

    ListView requestList;
    String deptid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //If else condition for current and history ones. Different logic, i think....
        setContentView(R.layout.activity_view_requisition_form_list);
        requestList = findViewById(R.id.listview_requestlist);
        deptid = "COMM";

        bindListView();

        requestList.setOnItemClickListener(this);

    }

    private void bindListView()
    {
        new AsyncTask<String, Void, List<RequisitionRecord>>() {

            @Override
            protected List<RequisitionRecord> doInBackground(String... params) {

                return RequisitionRecord.jread(params[0]);
            }

            @Override
            protected void onPostExecute(List<RequisitionRecord> rList) {
                List<RequisitionRecord> pendingList = new ArrayList<>();
                for(RequisitionRecord r:rList)
                {
                    RequisitionRecordDetail rd = r.requisitionRecordDetailsList.get(0);
                    if(rd != null)
                    {
                        if(rd.get("Status").equals("Pending"))
                        {
                            pendingList.add(r);
                        }
                    }
                }
                requestList.setAdapter(new MyAdaptor_RequestList_Row(ViewRequisitionFormListActivity.this, R.layout.row_requestlist, pendingList));
            }


        }.execute(InventoryCatalogue.URI_SERVICE +"GetRequestsList/"+deptid);
    }

    @Override
    public void onItemClick(AdapterView<?> av, View v, int position, long id) {

        RequisitionRecord item = (RequisitionRecord) av.getAdapter().getItem(position);
        Intent intent = new Intent(ViewRequisitionFormListActivity.this, ViewRequisitionFormDetailsActivity.class);

        intent.putExtra("requisition_record", item);
        startActivity(intent);

    }
}
