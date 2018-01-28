package sg.edu.nus.iss.team12.ssis.team12_ssis;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class AuthActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);

        Button button_Clerk = findViewById(R.id.button_Clerk);
        Button button_HOD = findViewById(R.id.button_HOD);

        button_Clerk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent(AuthActivity.this,MainActivity.class);
                intent.putExtra("Role","Clerk");
                startActivity(intent);
            }
        });

        button_HOD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent(AuthActivity.this,MainActivity.class);
                intent.putExtra("Role","HOD");
                startActivity(intent);
            }
        });


    }
}
