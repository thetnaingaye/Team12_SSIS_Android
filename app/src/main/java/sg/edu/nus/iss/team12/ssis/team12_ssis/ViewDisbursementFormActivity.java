package sg.edu.nus.iss.team12.ssis.team12_ssis;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ViewDisbursementFormActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Decide whether is it gonna be Sigature-based or PIN-based
        //Ouuuu Yeah~~!!!
        setContentView(R.layout.activity_view_disbursement_form);


        Button btnSign = findViewById(R.id.button_Sign);
        btnSign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent(ViewDisbursementFormActivity.this,MainActivity.class);
                intent.addFlags((Intent.FLAG_ACTIVITY_CLEAR_TOP));
                startActivity(intent);
            }
        });
    }
}
