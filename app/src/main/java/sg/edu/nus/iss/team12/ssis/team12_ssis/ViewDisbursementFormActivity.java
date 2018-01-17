package sg.edu.nus.iss.team12.ssis.team12_ssis;

import android.app.Activity;
import android.os.Bundle;

public class ViewDisbursementFormActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Decide whether is it gonna be Sigature-based or PIN-based
        //Ouuuu Yeah~~!!!
        setContentView(R.layout.activity_view_disbursement_form);
    }
}
