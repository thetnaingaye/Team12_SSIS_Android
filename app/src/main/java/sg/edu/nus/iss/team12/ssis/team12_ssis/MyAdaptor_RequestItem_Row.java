//-----------Team Member : THET NAING AYE's Codes-----------//
package sg.edu.nus.iss.team12.ssis.team12_ssis;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;


import sg.edu.nus.iss.team12.ssis.team12_ssis.model.InventoryCatalogue;
import sg.edu.nus.iss.team12.ssis.team12_ssis.model.RequisitionRecordDetail;

/**
 * Created by mmu1t on 28/1/2018.
 */

public class MyAdaptor_RequestItem_Row extends ArrayAdapter<RequisitionRecordDetail> {
    SharedPreferences pref;
    String token;

    private List<RequisitionRecordDetail> items;
    int resource;

    public MyAdaptor_RequestItem_Row(Context context, int resource, List<RequisitionRecordDetail> items) {
        super(context, resource, items);
        this.resource = resource;
        this.items = items;
        pref = PreferenceManager.getDefaultSharedPreferences(getContext());
        token = pref.getString("tokenKey", "hereJustPutRandomDefaultValue");

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) getContext()
                .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        final View v = inflater.inflate(resource, null);

        final RequisitionRecordDetail item = items.get(position);

        final TextView textView_ItemID = v.findViewById(R.id.textView_Item_Value);
        textView_ItemID.setText(item.get("ItemID"));

        new AsyncTask<String, Void, List<InventoryCatalogue>>() {

            @Override
            protected List<InventoryCatalogue> doInBackground(String... params) {

                return InventoryCatalogue.jread(params[0],token);
            }

            @Override
            protected void onPostExecute(List<InventoryCatalogue> resultInventoryList) {
                for(InventoryCatalogue inventoryCatalogue:resultInventoryList)
                {
                    if(inventoryCatalogue.get("ItemID").toString().equals(item.get("ItemID")))
                    {
                        textView_ItemID.setText(inventoryCatalogue.get("Description"));
                    }
                }

            }


        }.execute(InventoryCatalogue.URI_SERVICE + "GetInventoryList");

        TextView textView_reqQty = v.findViewById(R.id.textView_RequestedQty_Value);
        textView_reqQty.setText(item.get("RequestedQuantity"));

        return v;
    }

    @Override
    public boolean isEnabled(int position) {
        return false;
    }
}
