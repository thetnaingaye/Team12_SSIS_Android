package sg.edu.nus.iss.team12.ssis.team12_ssis;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import sg.edu.nus.iss.team12.ssis.team12_ssis.model.InventoryCatalogue;
import sg.edu.nus.iss.team12.ssis.team12_ssis.model.RetrivalItem;

public class InventoryDetailActivity extends Activity {
    List<RetrivalItem> retrivalList;
    SharedPreferences pref;
    String token;
    int unitsToRetrive;
    Button btnAllocate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventory_detail);
        pref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        token = pref.getString("tokenKey", "hereJustPutRandomDefaultValue");

        Bundle b = getIntent().getExtras();
        final HashMap<String,String> item = (HashMap<String, String>) b.get("item");
        final String itemID = item.get("ItemID").toString();

        btnAllocate = findViewById(R.id.button_Allocate);

        retrivalList = new ArrayList<>();
        String uri = InventoryCatalogue.URI_SERVICE + "GetRelevantListByDept";
        new AsyncTask<String, Void, List<RetrivalItem>>() {

            @Override
            protected List<RetrivalItem> doInBackground(String... params) {

                return RetrivalItem.jread_getItemByDept(params[0],itemID,token);
            }

            @Override
            protected void onPostExecute(List<RetrivalItem> rlist) {
                retrivalList = rlist;
                TextView textQtyToTake = (TextView)findViewById(R.id.textView_QtyToTake_Value);
                unitsToRetrive = 0;
                for (RetrivalItem r:rlist ) {

                    unitsToRetrive +=Integer.parseInt(r.get("RequestedQty"));

                }
                textQtyToTake.setText(String.valueOf(unitsToRetrive));
                if(unitsToRetrive == 0)
                {
                    btnAllocate.setEnabled(false);
                }

            }


        }.execute(uri);

        showDetails(item);


        btnAllocate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent(InventoryDetailActivity.this,InventoryRetrievalListActivity.class);
                intent.putExtra("item",item);
                startActivity(intent);

            }
        });

        Button btnStockCard = findViewById(R.id.button_StockCard);
        btnStockCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent(InventoryDetailActivity.this,StockCardActivity.class);
                intent.putExtra("item",item);
                startActivity(intent);

            }
        });
    }
    //for getting image resource id from string
    public int getResourceId(String pVariableName, String pResourcename, String pPackageName)
    {
        try {
            return getResources().getIdentifier(pVariableName, pResourcename, pPackageName);
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    protected  void showDetails(final HashMap<String,String> item)
    {
        TextView textItemCode = (TextView)findViewById(R.id.textView_ItemCode_Value);
        textItemCode.setText(item.get("ItemID"));

        TextView textCategory = (TextView)findViewById(R.id.textView_Category_Value);
        textCategory.setText(item.get("CategoryID"));

        TextView textDesc = (TextView)findViewById(R.id.textView_Description_Value);
        textDesc.setText(item.get("Description"));

        TextView textReorderLevel = (TextView)findViewById(R.id.textView_ReorderLevel_Value);
        textReorderLevel.setText(item.get("ReorderLevel"));

        TextView textReorderQty = (TextView)findViewById(R.id.textView_ReorderQty_Value);
        textReorderQty.setText(item.get("ReorderQty"));

        TextView textUOM = (TextView)findViewById(R.id.textView_MeasurementUnit_Value);
        textUOM.setText(item.get("UOM"));

        TextView textLocation = (TextView)findViewById(R.id.textView_Location_Value);
        textLocation.setText(item.get("Shelf")+"-"+item.get("Level"));



        TextView textQtyInStock = (TextView)findViewById(R.id.textView_QtyInStock_Value);
        textQtyInStock.setText(item.get("UnitsInStock"));



        ImageButton imgBtn = findViewById(R.id.imageButton_shelf);
        String imgName = item.get("Shelf").toLowerCase();
        String imgLevel = "s"+item.get("Level");

        int id = getResourceId(imgName, "drawable", getPackageName());
        final int levelId = getResourceId(imgLevel, "drawable", getPackageName());
        imgBtn.setImageResource(id);
        imgBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                final Dialog d = new Dialog(InventoryDetailActivity.this);
                d.setContentView(R.layout.layout_level);
                ImageView imgLevel = d.findViewById(R.id.imageView_Level);
                imgLevel.setImageResource(levelId);
                TextView txtShelf = d.findViewById(R.id.textView_Shelf_Value);
                TextView txtLevel = d.findViewById(R.id.textView_Level_Value);
                txtShelf.setText(item.get("Shelf"));
                txtLevel.setText((item.get("Level")));
                d.setCancelable(true);

                d.show();
            }
        });
    }
}

