package sg.edu.nus.iss.team12.ssis.team12_ssis;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

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
             if(b.get("history").equals("yes"))
             {
                 Button button_Approve = findViewById(R.id.button_Approve);
                 button_Approve.setVisibility(View.GONE);

                 Button button_Reject = findViewById(R.id.button_Reject);
                 button_Reject.setVisibility(View.GONE);
             }
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
        TextView textView_RequestorName = findViewById(R.id.textView_Requestorname_Value);
        textView_RequestorName.setText(record.get("RequestorName"));

        TextView textView_Date = findViewById(R.id.textView_Date_Value);
        textView_Date.setText(record.get("RequestDate"));

        TextView textView_Status = findViewById(R.id.textView_Status_Value);
        textView_Status.setText(record.requisitionRecordDetailsList.get(0).get("Status"));

        ListView list = findViewById(R.id.lv1);
        list.setAdapter(new MyAdaptor_RequestItem_Row(ViewRequisitionFormDetailsActivity.this, R.layout.row_requestitem, record.requisitionRecordDetailsList));

        //Approve Condition
        Button button_Approve = findViewById(R.id.button_Approve);
        button_Approve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                final Dialog d = new Dialog(ViewRequisitionFormDetailsActivity.this);
                d.requestWindowFeature(Window.FEATURE_NO_TITLE);
                d.setContentView(R.layout.approve_dialog);

                Button button_Cancel = d.findViewById(R.id.button_cancel);
                button_Cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        d.dismiss();
                    }
                });

                Button button_Confirm = d.findViewById(R.id.button_confirm);
                button_Confirm.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String str="";
                        for (RequisitionRecordDetail rd : record.requisitionRecordDetailsList) {
                            rd.put("Status", "Approved");
                            str += rd.get("ItemID") + ":" + rd.get("Status") + "\n";
                        }
                        Toast.makeText(ViewRequisitionFormDetailsActivity.this, str, Toast.LENGTH_SHORT).show();

                        d.dismiss();
                        Intent intent = new Intent(ViewRequisitionFormDetailsActivity.this,ViewRequisitionFormListActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                    }
                });
                d.setCancelable(false);

                d.show();
            }
        });

        //Reject Condition
        Button button_Reject = findViewById(R.id.button_Reject);
        button_Reject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                final Dialog d = new Dialog(ViewRequisitionFormDetailsActivity.this);
                d.requestWindowFeature(Window.FEATURE_NO_TITLE);
                d.setContentView(R.layout.reject_dialog);


                Button button_Cancel = d.findViewById(R.id.button_cancel);
                button_Cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        d.dismiss();
                    }
                });

                Button button_Confirm = d.findViewById(R.id.button_confirm);
                button_Confirm.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        final EditText editText_remark = d.findViewById(R.id.editText_remark_value);
                        if(editText_remark.getText().toString().trim().length() == 0)
                        {

                            record.put("Remarks","no remarks");
                        }
                        else
                        {
                            record.put("Remarks",editText_remark.getText().toString());
                        }

                        String str= record.get("Remarks")+"\n";
                        for (RequisitionRecordDetail rd : record.requisitionRecordDetailsList) {
                            rd.put("Status", "Rejected");

                            str += rd.get("ItemID") + ":" + rd.get("Status") +":"+ "\n";
                        }
                        Toast.makeText(ViewRequisitionFormDetailsActivity.this, str, Toast.LENGTH_SHORT).show();

                        d.dismiss();
                    }
                });
                d.setCancelable(false);

                d.show();
            }
        });

        //setting logo onClick back to home
        ImageView img_logo = findViewById(R.id.imageView_Title);
        img_logo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ViewRequisitionFormDetailsActivity.this,MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra("Role","HOD");
                startActivity(intent);
            }
        });

    }
}
