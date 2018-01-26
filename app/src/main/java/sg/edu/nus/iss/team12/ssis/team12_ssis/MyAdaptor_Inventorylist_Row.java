package sg.edu.nus.iss.team12.ssis.team12_ssis;

/**
 * Created by mmu1t on 26/1/2018.
 */
import android.app.Activity;
import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;
import java.util.List;

import sg.edu.nus.iss.team12.ssis.team12_ssis.model.InventoryCatalogue;


public class MyAdaptor_Inventorylist_Row extends ArrayAdapter<InventoryCatalogue> {

    private List<InventoryCatalogue> items;
    int resource;

    public MyAdaptor_Inventorylist_Row(Context context, int resource, List<InventoryCatalogue> items) {
        super(context, resource, items);
        this.resource = resource;
        this.items = items;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) getContext()
                .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        final View v = inflater.inflate(resource, null);

        final InventoryCatalogue inventoryItem = items.get(position);

        if (inventoryItem != null) {
            TextView bTitle = (TextView) v.findViewById(R.id.textView_Desc);
            bTitle.setText(inventoryItem.get("Description"));

            TextView bIsbn = (TextView) v.findViewById(R.id.textView_Qty);
            bIsbn.setText((inventoryItem.get("UnitsInStock"))+"/"+inventoryItem.get("UOM"));

        }

        return v;
    }


}