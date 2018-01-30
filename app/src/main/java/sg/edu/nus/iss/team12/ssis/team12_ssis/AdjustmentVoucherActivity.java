package sg.edu.nus.iss.team12.ssis.team12_ssis;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;

import sg.edu.nus.iss.team12.ssis.team12_ssis.model.Disbursement;
import sg.edu.nus.iss.team12.ssis.team12_ssis.model.InventoryCatalogue;
import sg.edu.nus.iss.team12.ssis.team12_ssis.model.StockCard;

public class AdjustmentVoucherActivity extends Activity {
    SharedPreferences pref;
    String token;
    String result_message = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adjustment_voucher);
        pref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        token = pref.getString("tokenKey","token");

        Bundle b = getIntent().getExtras();
        final HashMap<String,String> item = (HashMap<String, String>) b.get("item");
        TextView textView_Desc = findViewById(R.id.textView_Description_Value);
        textView_Desc.setText(item.get("Description"));

        TextView textView_itemcode = findViewById(R.id.textView_ItemCode_Value);
        textView_itemcode.setText(item.get("ItemID"));



        Button btnSave = findViewById(R.id.button_Save);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {

                EditText editText_qty = findViewById(R.id.editText_AVQty_Value);
                EditText editText_desc = findViewById(R.id.editText_Desc_Value);

                Spinner s =(Spinner) findViewById(R.id.spinner_addminus);
                int spinner_index = s.getSelectedItemPosition();


                StockCard stockCard = new StockCard();

                stockCard.put("Quantity",editText_qty.getText().toString());
                stockCard.put("Description",editText_desc.getText().toString());
                stockCard.put("ItemID",item.get("ItemID").toString());
                stockCard.put("UOM",item.get("UOM").toString());
                if(spinner_index == 0)
                {
                    stockCard.put("Type","Add");
                }
                else {
                    stockCard.put("Type","Minus");
                }


                final  String url = InventoryCatalogue.URI_SERVICE+"CreateAVRequest";

                new AsyncTask<StockCard, Void, Void>() {

                    @Override
                    protected Void doInBackground(StockCard... params) {

                        result_message = StockCard.createAVR(params[0],token,url);
                        return null;

                    }

                    @Override
                    protected void onPostExecute(Void d) {
                        result_message = (result_message.contains("Success"))? "new adjustment voucher created successfully" : "new adjust voucher failed to create";
                        Toast.makeText(AdjustmentVoucherActivity.this, result_message, Toast.LENGTH_SHORT).show();

                    }

                }.execute(stockCard);



//                Intent intent = new Intent(AdjustmentVoucherActivity.this,MainActivity.class);
//                intent.addFlags((Intent.FLAG_ACTIVITY_CLEAR_TOP));
//                startActivity(intent);
            }
        });
    }
}
