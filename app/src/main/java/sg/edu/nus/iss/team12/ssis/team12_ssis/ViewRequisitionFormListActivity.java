package sg.edu.nus.iss.team12.ssis.team12_ssis;

import android.app.Activity;
import android.os.Bundle;

public class ViewRequisitionFormListActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //If else condition for current and history ones. Different logic, i think....
        setContentView(R.layout.activity_view_requisition_form_list);
    }
}
