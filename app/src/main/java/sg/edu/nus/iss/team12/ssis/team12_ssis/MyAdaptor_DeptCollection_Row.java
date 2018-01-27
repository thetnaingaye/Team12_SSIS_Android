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

import sg.edu.nus.iss.team12.ssis.team12_ssis.model.Department;
import sg.edu.nus.iss.team12.ssis.team12_ssis.model.Disbursement;
import sg.edu.nus.iss.team12.ssis.team12_ssis.model.InventoryCatalogue;


/**
 * Created by mmu1t on 27/1/2018.
 */

public class MyAdaptor_DeptCollection_Row extends ArrayAdapter<Disbursement> {

    private List<Disbursement> items;
    int resource;

    public MyAdaptor_DeptCollection_Row(Context context, int resource, List<Disbursement> items) {
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


        if (item != null) {
            final TextView textView_dept = (TextView) v.findViewById(R.id.textView_Dept_Value);


            String uri = InventoryCatalogue.URI_SERVICE + "GetDeptName/" + item.get("DepartmentID");
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
        }

        return v;
    }
}
