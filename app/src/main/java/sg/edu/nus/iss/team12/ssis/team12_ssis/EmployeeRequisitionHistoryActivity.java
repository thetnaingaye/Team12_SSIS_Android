package sg.edu.nus.iss.team12.ssis.team12_ssis;

import android.app.Activity;
import android.os.Bundle;

public class EmployeeRequisitionHistoryActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //to reuse "ViewRequisitionFormDetailsActivity
        setContentView(R.layout.activity_employee_requisition_history);
    }
}
