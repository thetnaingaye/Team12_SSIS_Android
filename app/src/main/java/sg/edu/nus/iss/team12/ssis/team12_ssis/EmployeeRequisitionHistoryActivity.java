//-----------Team Member : THET NAING AYE's Codes-----------//
package sg.edu.nus.iss.team12.ssis.team12_ssis;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;

public class EmployeeRequisitionHistoryActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        //to reuse "ViewRequisitionFormDetailsActivity
        setContentView(R.layout.activity_employee_requisition_history);
    }
}
