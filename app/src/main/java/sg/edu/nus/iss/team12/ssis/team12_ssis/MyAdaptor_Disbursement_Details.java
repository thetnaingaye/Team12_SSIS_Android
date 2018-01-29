package sg.edu.nus.iss.team12.ssis.team12_ssis;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;


import javax.crypto.spec.IvParameterSpec;

import sg.edu.nus.iss.team12.ssis.team12_ssis.model.DisbursementDetail;
import sg.edu.nus.iss.team12.ssis.team12_ssis.model.InventoryCatalogue;


/**
 * Created by mmu1t on 27/1/2018.
 */

public class MyAdaptor_Disbursement_Details extends ArrayAdapter<DisbursementDetail> {

    private List<DisbursementDetail> items;
    int resource;

    public MyAdaptor_Disbursement_Details(Context context, int resource, List<DisbursementDetail> items) {
        super(context, resource, items);
        this.resource = resource;
        this.items = items;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) getContext()
                .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        final View v = inflater.inflate(resource, null);
        final HashMap<String,String> item = items.get(position);

        final EditText editText_Allocation = (EditText) v.findViewById(R.id.editText_Allocation_Value); // collected qty
        final TextView textView_item = (TextView) v.findViewById(R.id.textView_ItemCode_Value);
        final TextView textView_reqQty = (TextView)v.findViewById(R.id.textView_RequestedQty_Value); //actual qty

        new AsyncTask<String, Void, List<InventoryCatalogue>>() {

            @Override
            protected List<InventoryCatalogue> doInBackground(String... params) {

                return InventoryCatalogue.jread(params[0]);
            }

            @Override
            protected void onPostExecute(List<InventoryCatalogue> resultInventoryList) {
                for(InventoryCatalogue inventoryCatalogue:resultInventoryList)
                {
                    if(inventoryCatalogue.get("ItemID").toString().equals(item.get("ItemID")))
                    {
                        textView_item.setText(inventoryCatalogue.get("Description"));
                    }
                }

            }


        }.execute(InventoryCatalogue.URI_SERVICE + "GetInventoryList");



        textView_reqQty.setText((item.get("ActualQuantity")));

        editText_Allocation.setText((item.get("ActualQuantity")));

        editText_Allocation.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {
                //to put code here
            }

            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
                //to put code here
            }

            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                //get value from edittext and set to book object's attribute
                item.put("QuantityCollected",editText_Allocation.getText().toString());
            }
        });

        return v;
    }
}
