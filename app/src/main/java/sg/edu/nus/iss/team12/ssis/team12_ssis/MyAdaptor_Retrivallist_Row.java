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

import java.util.List;

import sg.edu.nus.iss.team12.ssis.team12_ssis.model.Department;
import sg.edu.nus.iss.team12.ssis.team12_ssis.model.InventoryCatalogue;
import sg.edu.nus.iss.team12.ssis.team12_ssis.model.RetrivalItem;

/**
 * Created by mmu1t on 26/1/2018.
 */

public class MyAdaptor_Retrivallist_Row extends ArrayAdapter<RetrivalItem> {

    private List<RetrivalItem> items;
    int resource;

    public MyAdaptor_Retrivallist_Row(Context context, int resource, List<RetrivalItem> items) {
        super(context, resource, items);
        this.resource = resource;
        this.items = items;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) getContext()
                .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        final View v = inflater.inflate(resource, null);

        final RetrivalItem item = items.get(position);
        final EditText editText_Allocation = (EditText) v.findViewById(R.id.editText_Allocation_Value);;

        if (item != null) {
            final TextView textView_dept = (TextView) v.findViewById(R.id.textView_Dept_Value);



            String uri = InventoryCatalogue.URI_SERVICE + "GetDeptName/"+item.get("DepartmentID");
            new AsyncTask<String, Void, Department>() {

                @Override
                protected Department doInBackground(String... params) {

                    return Department.jread(params[0]);
                }

                @Override
                protected void onPostExecute(Department d) {
                    textView_dept.setText(d.get("DepartmentName"));


                }


            }.execute(uri);





            TextView textView_reqQty = (TextView) v.findViewById(R.id.textView_RequestedQty_Value);
            textView_reqQty.setText((item.get("RequestedQty")));

            editText_Allocation.setText((item.get("ActualQty")));

        }

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
                item.put("ActualQty",editText_Allocation.getText().toString());
            }
        });

        return v;
    }

}
