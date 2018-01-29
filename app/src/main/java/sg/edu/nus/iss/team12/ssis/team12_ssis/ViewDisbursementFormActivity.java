package sg.edu.nus.iss.team12.ssis.team12_ssis;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import sg.edu.nus.iss.team12.ssis.team12_ssis.model.Department;
import sg.edu.nus.iss.team12.ssis.team12_ssis.model.Disbursement;
import sg.edu.nus.iss.team12.ssis.team12_ssis.model.DisbursementDetail;
import sg.edu.nus.iss.team12.ssis.team12_ssis.model.InventoryCatalogue;
import sg.edu.nus.iss.team12.ssis.team12_ssis.model.RetrivalItem;

public class ViewDisbursementFormActivity extends Activity {
    HashMap<String,String> h_disbursement;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Decide whether is it gonna be Sigature-based or PIN-based
        //Ouuuu Yeah~~!!!
        setContentView(R.layout.activity_view_disbursement_form);

        h_disbursement = new HashMap<>();

        List<DisbursementDetail> ddlist = new ArrayList<>();
        Bundle b = getIntent().getExtras();
        if (b != null) {
            h_disbursement =  (HashMap<String,String>)b.get("disbursement") ;
            ddlist  = (List<DisbursementDetail>) b.getSerializable("disbursement_detail");
        }

        //settting TextView Values
        TextView textView_Date = findViewById(R.id.textView_Date_Value);
        textView_Date.setText(h_disbursement.get("CollectionDate"));
        final TextView textView_dept = findViewById(R.id.textView_Dept_Value);
        String uri = InventoryCatalogue.URI_SERVICE + "GetDeptName/" + h_disbursement.get("DepartmentID");
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

        TextView textView_CollectionPoint = findViewById(R.id.textview_CollectionPt_Value);
        Resources res = getResources();
        String[] cp = res.getStringArray(R.array.collectionPoints);
        int index = Integer.parseInt(h_disbursement.get("CollectionPointID"));
        textView_CollectionPoint.setText(cp[index]);

        TextView textView_rep = findViewById(R.id.textView_Rep_Value);
        textView_rep.setText(h_disbursement.get("RepresentativeName"));

        //setting listview
        final ListView list = (ListView) findViewById(R.id.lv1);
        list.setAdapter(new MyAdaptor_Disbursement_Details(ViewDisbursementFormActivity.this,R.layout.row_disbursement_formdetail,ddlist));

        Button btnSign = findViewById(R.id.button_Sign);
        btnSign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {


                List<DisbursementDetail> dfinal = new ArrayList<>();

                for(int i=0;i<list.getCount();i++)
                {
                    //MyAdaptor_Disbursement_Details myAdaptor_disbursement_details = (MyAdaptor_Disbursement_Details)list.getAdapter();

                    HashMap<String,String> hitem = (HashMap<String,String>) list.getAdapter().getItem(i);
                       DisbursementDetail d = new DisbursementDetail(
                               hitem.get("ID"),
                               hitem.get("DisbursementID"),
                               hitem.get("ItemID"),
                               hitem.get("ActualQuantity"),
                               hitem.get("QuantityCollected"),
                               hitem.get("QuantityRequested"),
                               hitem.get("Remarks"),
                               hitem.get("UOM"));

                    dfinal.add(d);

                }

                //constructing back Disbursement Object
                Disbursement disbursement = new Disbursement(
                        h_disbursement.get("DisbursementID"),
                        h_disbursement.get("CollectionDate"),
                        h_disbursement.get("CollectionPointID"),
                        h_disbursement.get("DepartmentID"),
                        h_disbursement.get("RepresentativeName"),
                        "Collected");
                disbursement.disbursementDetailsList = dfinal;

                //testing data
                String str= "Disbusement"+"\n"+disbursement.get("DisbursementID")+"\n"+disbursement.get("RepresentativeName")+"\n"+disbursement.get("Status")+"\n"+"Disbursement Details"+"\n";

                for(DisbursementDetail r : dfinal)
                {
                    str += "ID"+r.get("ID")+"\t"+"Actual: "+r.get("ActualQuantity")+"\t"+" Collected: "+r.get("QuantityCollected")+"\n";
                }
                Toast.makeText(ViewDisbursementFormActivity.this, str, Toast.LENGTH_SHORT).show();


//                Intent intent = new Intent(ViewDisbursementFormActivity.this,MainActivity.class);
//                intent.addFlags((Intent.FLAG_ACTIVITY_CLEAR_TOP));
//                startActivity(intent);

            }
        });

        //setting logo onClick back to home
        ImageView img_logo = findViewById(R.id.imageView_Title);
        img_logo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ViewDisbursementFormActivity.this,MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra("Role","Clerk");
                startActivity(intent);
            }
        });

    }
}
