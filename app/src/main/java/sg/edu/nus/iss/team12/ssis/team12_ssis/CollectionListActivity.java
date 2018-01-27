package sg.edu.nus.iss.team12.ssis.team12_ssis;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import sg.edu.nus.iss.team12.ssis.team12_ssis.model.Disbursement;
import sg.edu.nus.iss.team12.ssis.team12_ssis.model.InventoryCatalogue;

public class CollectionListActivity extends Activity implements AdapterView.OnItemClickListener{

    List<Disbursement> disbursementList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //If else condition and set the header label to either "COLLECTIONS" or "OUTSTANDING DISBURSEMENT"
        setContentView(R.layout.activity_collection_list);

        final ListView collectionDepList = findViewById(R.id.listview_dept);

        new AsyncTask<String, Void, List<Disbursement>>() {

            @Override
            protected List<Disbursement> doInBackground(String... params) {

                return Disbursement.jread(params[0]);
            }

            @Override
            protected void onPostExecute(List<Disbursement> rList) {
                disbursementList = rList;
                collectionDepList.setAdapter(new MyAdaptor_DeptCollection_Row(CollectionListActivity.this, R.layout.row_dept_forcollection, rList));
            }


        }.execute(InventoryCatalogue.URI_SERVICE + "GetDisbursementLists");

        collectionDepList.setOnItemClickListener(this);

    }

    @Override
    public void onItemClick(AdapterView<?> av, View v, int position, long id) {

        Disbursement item = (Disbursement) av.getAdapter().getItem(position);
        Intent intent = new Intent(CollectionListActivity.this, DisbursementListActivity.class);

        intent.putExtra("item", item);
        startActivity(intent);

    }
}
