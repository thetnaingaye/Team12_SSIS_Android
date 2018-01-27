package sg.edu.nus.iss.team12.ssis.team12_ssis;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;


import sg.edu.nus.iss.team12.ssis.team12_ssis.model.Disbursement;


/**
 * Created by mmu1t on 27/1/2018.
 */

public class MyAdaptor_DisbursementbyDept_Row extends ArrayAdapter<Disbursement> {

    private List<Disbursement> items;
    int resource;

    public MyAdaptor_DisbursementbyDept_Row(Context context, int resource, List<Disbursement> items) {
        super(context, resource, items);
        this.resource = resource;
        this.items = items;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) getContext()
                .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        final View v = inflater.inflate(resource, null);

        final Disbursement item = items.get(position);

        TextView textView_DisbursementID = v.findViewById(R.id.textView_DisbursementID_Value);
        textView_DisbursementID.setText(item.get("DisbursementID"));

        TextView textView_CollectionDate = v.findViewById(R.id.textView_CollectionDate_Value);
        textView_CollectionDate.setText(item.get("CollectionDate"));

        return v;
    }
}
