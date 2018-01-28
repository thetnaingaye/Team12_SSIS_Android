package sg.edu.nus.iss.team12.ssis.team12_ssis;

import android.app.Activity;
import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import sg.edu.nus.iss.team12.ssis.team12_ssis.model.RequisitionRecord;

/**
 * Created by mmu1t on 28/1/2018.
 */

public class MyAdaptor_RequestList_Row extends ArrayAdapter<RequisitionRecord> {

    private List<RequisitionRecord> items;
    int resource;

    public MyAdaptor_RequestList_Row(Context context, int resource, List<RequisitionRecord> items) {
        super(context, resource, items);
        this.resource = resource;
        this.items = items;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) getContext()
                .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        final View v = inflater.inflate(resource, null);

        final RequisitionRecord item = items.get(position);
        TextView textView_RequestorName = v.findViewById(R.id.textView_RequestorName_Value);
        textView_RequestorName.setText(item.get("RequestorName"));

        TextView textView_RequestDate = v.findViewById(R.id.textView_RequestDate_Value);
        textView_RequestDate.setText(item.get("RequestDate"));

        return v;
    }
}
