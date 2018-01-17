package sg.edu.nus.iss.team12.ssis.team12_ssis;

import android.app.Activity;
import android.os.Bundle;

public class CollectionListActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //If else condition and set the header label to either "COLLECTIONS" or "OUTSTANDING DISBURSEMENT"
        setContentView(R.layout.activity_collection_list);
    }
}
