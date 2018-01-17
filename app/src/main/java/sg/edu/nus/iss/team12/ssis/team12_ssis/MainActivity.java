package sg.edu.nus.iss.team12.ssis.team12_ssis;

import android.app.Activity;
import android.os.Bundle;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //If else. If is Clerk, Show clerk layout
        //If DeptHead, Show DeptHead layout
        setContentView(R.layout.activity_main);
    }
}
