package sg.edu.nus.iss.team12.ssis.team12_ssis;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import sg.edu.nus.iss.team12.ssis.team12_ssis.model.StockCard;

/**
 * Created by mmu1t on 27/1/2018.
 */

public class MyAdaptor_StockCard_Row extends ArrayAdapter<StockCard> {

    private List<StockCard> items;
    int resource;

    public MyAdaptor_StockCard_Row(Context context, int resource, List<StockCard> items) {
        super(context, resource, items);
        this.resource = resource;
        this.items = items;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) getContext()
                .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        final View v = inflater.inflate(resource, null);

        final StockCard item = items.get(position);

        if (item != null) {
            final TextView textView_date = (TextView) v.findViewById(R.id.textView_stock_date);
            final TextView textView_desc = (TextView) v.findViewById(R.id.textView_stock_desc);
            final TextView textView_type = (TextView) v.findViewById(R.id.textView_stock_type);
            final TextView textView_qty = (TextView) v.findViewById(R.id.textView_stock_qty);
            final TextView textView_balance = (TextView) v.findViewById(R.id.textView_stock_balance);

            textView_date.setText(item.get("Date"));
            textView_desc.setText(item.get("Description"));
            textView_type.setText(item.get("Type"));
            textView_qty.setText(item.get("Quantity"));
            textView_balance.setText(item.get("Balance"));



        }

        return v;
    }
}
