package sg.edu.nus.iss.team12.ssis.team12_ssis;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;


import sg.edu.nus.iss.team12.ssis.team12_ssis.model.RequisitionRecordDetail;

/**
 * Created by mmu1t on 28/1/2018.
 */

public class MyAdaptor_RequestItem_Row extends ArrayAdapter<RequisitionRecordDetail> {

    private List<RequisitionRecordDetail> items;
    int resource;

    public MyAdaptor_RequestItem_Row(Context context, int resource, List<RequisitionRecordDetail> items) {
        super(context, resource, items);
        this.resource = resource;
        this.items = items;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) getContext()
                .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        final View v = inflater.inflate(resource, null);

        RequisitionRecordDetail item = items.get(position);

        TextView textView_ItemID = v.findViewById(R.id.textView_Item_Value);
        textView_ItemID.setText(item.get("ItemID"));

        TextView textView_reqQty = v.findViewById(R.id.textView_RequestedQty_Value);
        textView_reqQty.setText(item.get("RequestedQuantity"));

        return v;
    }
}
