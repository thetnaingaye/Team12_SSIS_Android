package sg.edu.nus.iss.team12.ssis.team12_ssis;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;

import sg.edu.nus.iss.team12.ssis.team12_ssis.model.InventoryCatalogue;
import sg.edu.nus.iss.team12.ssis.team12_ssis.model.RetrivalItem;
import sg.edu.nus.iss.team12.ssis.team12_ssis.model.StockCard;

public class StockCardActivity extends Activity {
    SharedPreferences pref;
    String token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stock_card);
        pref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        token = pref.getString("tokenKey", "hereJustPutRandomDefaultValue");

        Bundle b = getIntent().getExtras();
        final HashMap<String,String> item = (HashMap<String, String>) b.get("item");
        TextView textView_Desc = findViewById(R.id.textView_Description_Value);
        textView_Desc.setText(item.get("Description"));

        TextView textView_itemcode = findViewById(R.id.textView_ItemCode_Value);
        textView_itemcode.setText(item.get("ItemID"));
        final String itemCode = item.get("ItemID").toString();

        final ListView list = (ListView) findViewById(R.id.listView_StockCard);

        String uri = InventoryCatalogue.URI_SERVICE + "GetStockCard";
        new AsyncTask<String, Void, List<StockCard>>() {

            @Override
            protected List<StockCard> doInBackground(String... params) {

                return StockCard.jread_getStockCard(params[0],itemCode,token);
            }

            @Override
            protected void onPostExecute(List<StockCard> rlist) {

                list.setAdapter(new MyAdaptor_StockCard_Row(StockCardActivity.this,R.layout.row_stockcard,rlist));

            }


        }.execute(uri);

        Button btnNewAV = findViewById(R.id.button_newAV);
        btnNewAV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent(StockCardActivity.this,AdjustmentVoucherActivity.class);
                intent.putExtra("item",item);
                startActivity(intent);
            }
        });

    }
}
