package sg.edu.nus.iss.team12.ssis.team12_ssis;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

public class InventoryDetailActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventory_detail);
        ImageButton imgBtn = findViewById(R.id.imageButton_shelf);
        String imgName = "h";
        String imgLevel = "s1";
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
                txtShelf.setText("H");
                d.setCancelable(true);

                d.show();
            }
        });

        Button btnAllocate = findViewById(R.id.button_Allocate);
        btnAllocate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent(InventoryDetailActivity.this,InventoryRetrievalListActivity.class);
                startActivity(intent);

            }
        });

        Button btnStockCard = findViewById(R.id.button_StockCard);
        btnStockCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent(InventoryDetailActivity.this,StockCardActivity.class);
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
}
