package sg.edu.nus.iss.team12.ssis.team12_ssis;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
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
        imgBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                final Dialog d = new Dialog(InventoryDetailActivity.this);
                d.setContentView(R.layout.layout_level);
                ImageView imgLevel = d.findViewById(R.id.imageView_Level);
                TextView txtShelf = d.findViewById(R.id.textView_Shelf_Value);
                txtShelf.setText("testing");
                imgLevel.setImageResource(R.drawable.brandimg);
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

    }
}
