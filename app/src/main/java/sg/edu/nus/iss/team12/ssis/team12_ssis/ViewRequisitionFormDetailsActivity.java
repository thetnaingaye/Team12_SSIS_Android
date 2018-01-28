package sg.edu.nus.iss.team12.ssis.team12_ssis;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import sg.edu.nus.iss.team12.ssis.team12_ssis.model.DisbursementDetail;
import sg.edu.nus.iss.team12.ssis.team12_ssis.model.InventoryCatalogue;
import sg.edu.nus.iss.team12.ssis.team12_ssis.model.RequisitionRecord;
import sg.edu.nus.iss.team12.ssis.team12_ssis.model.RequisitionRecordDetail;

public class ViewRequisitionFormDetailsActivity extends Activity {
    HashMap<String,String> h_record;
    List<RequisitionRecord> r_rlist = new ArrayList<>();
    RequisitionRecord record = new RequisitionRecord();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_requisition_form_details);
        Bundle b = getIntent().getExtras();
        if (b != null) {
             h_record = (HashMap<String, String>) b.get("requisition_record");
        }
        String deptid = h_record.get("DepartmentID");

        new AsyncTask<String, Void, List<RequisitionRecord>>() {

            @Override
            protected List<RequisitionRecord> doInBackground(String... params) {

                return RequisitionRecord.jread(params[0]);
            }

            @Override
            protected void onPostExecute(List<RequisitionRecord> rList) {
                r_rlist = rList;
                proceed(r_rlist);

            }
        }.execute(InventoryCatalogue.URI_SERVICE +"GetRequestsList/"+deptid);
    }

    private void proceed(List<RequisitionRecord> rlist){
        for(RequisitionRecord r:r_rlist){
            if(r.get("RequestID").equals(h_record.get("RequestID")))
            {
                record = r;
            }
        }
        TextView t = findViewById(R.id.textView2);
        String str="";
        for(RequisitionRecordDetail rd:record.requisitionRecordDetailsList)
        {
            rd.put("Status","testing");
            str += rd.get("ItemID")+":"+rd.get("Status")+"\n";
        }
        t.setText(str);

    }
}
