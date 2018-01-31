package sg.edu.nus.iss.team12.ssis.team12_ssis;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import sg.edu.nus.iss.team12.ssis.team12_ssis.model.Department;
import sg.edu.nus.iss.team12.ssis.team12_ssis.model.Disbursement;
import sg.edu.nus.iss.team12.ssis.team12_ssis.model.DisbursementDetail;
import sg.edu.nus.iss.team12.ssis.team12_ssis.model.InventoryCatalogue;
import sg.edu.nus.iss.team12.ssis.team12_ssis.model.RetrivalItem;

public class ViewDisbursementFormActivity extends Activity {
    SharedPreferences pref;
    String token;
    HashMap<String,String> h_disbursement;
    ImageView img_logo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        token = pref.getString("tokenKey","token");
        //Decide whether is it gonna be Sigature-based or PIN-based
        //Ouuuu Yeah~~!!!
        setContentView(R.layout.activity_view_disbursement_form);

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

        Button btn_singnature = findViewById(R.id.button_singature);
        Button btnSign = findViewById(R.id.button_Sign);
        btnSign.setEnabled(false);
        btnSign.setBackgroundColor(Color.WHITE);
        ImageView imageView_sign = findViewById(R.id.imageView_sign);
        imageView_sign.setVisibility(View.GONE);
        btn_singnature.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ViewDisbursementFormActivity.this,SignatureActivity.class);
                startActivity(intent);



            }
        });




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

    @Override
    protected void onResume(){
        super.onResume();

        final File file= new File(android.os.Environment.getExternalStorageDirectory(),"saved_signature/signature.png");
        if(file.exists())
        {
            ImageView img = findViewById(R.id.imageView_sign);
            img.setVisibility(View.VISIBLE);
            Bitmap myBitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
            img.setImageBitmap(myBitmap);
            file.delete(); //  to be delete after successfully post image

            final ListView list = (ListView) findViewById(R.id.lv1);

            final Button btnSign = findViewById(R.id.button_Sign);
            btnSign.setEnabled(true);
            btnSign.setBackgroundColor(Color.rgb(26,110,204));
            btnSign.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View arg0) {
                    btnSign.setEnabled(false);
                    btnSign.setClickable(false);
                    btnSign.setText("...");

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
                    final  String url = InventoryCatalogue.URI_SERVICE+"UpdateDisburse";

                    new AsyncTask<Disbursement, Void, Void>() {

                        @Override
                        protected Void doInBackground(Disbursement... params) {

                            Disbursement.updateDisbursement(params[0],token,url);
                            return null;

                        }

                        @Override
                        protected void onPostExecute(Void d) {
                            Toast.makeText(ViewDisbursementFormActivity.this, "disbursement collected successfully", Toast.LENGTH_SHORT).show();
                            finish();

                        }

                    }.execute(disbursement);



                    Intent intent = new Intent(ViewDisbursementFormActivity.this,CollectionListActivity.class);
                    intent.addFlags((Intent.FLAG_ACTIVITY_CLEAR_TOP));
                    startActivity(intent);

                }
            });
        }

    }


}
