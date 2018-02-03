//-----------Team Member : THET NAING AYE's Codes-----------//
package sg.edu.nus.iss.team12.ssis.team12_ssis;

import android.app.Activity;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;

import android.support.v4.view.MenuItemCompat;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.SearchView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import sg.edu.nus.iss.team12.ssis.team12_ssis.model.InventoryCatalogue;

public class InventoryListActivity extends Activity implements AdapterView.OnItemClickListener {
    SharedPreferences pref;
    String token;
    List<InventoryCatalogue> inventoryCataloguesList;
    ListView list;
    CheckBox chk;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        pref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        token = pref.getString("tokenKey", "hereJustPutRandomDefaultValue");
        inventoryCataloguesList = new ArrayList<>();
        setContentView(R.layout.activity_inventory_list);
        list = findViewById(R.id.lv1);


        new AsyncTask<String, Void, List<InventoryCatalogue>>() {

            @Override
            protected List<InventoryCatalogue> doInBackground(String... params) {


                return InventoryCatalogue.jread(params[0],token);
            }

            @Override
            protected void onPostExecute(List<InventoryCatalogue> resultInventoryList) {
                        inventoryCataloguesList = resultInventoryList;

                     list.setAdapter(new MyAdaptor_Inventorylist_Row(InventoryListActivity.this, R.layout.row_inventorylist, resultInventoryList));
            }


        }.execute(InventoryCatalogue.URI_SERVICE + "GetInventoryList");

        list.setOnItemClickListener(this);


        chk = findViewById(R.id.checkBox);

        chk.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

                                           @Override
                                           public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                                               if (isChecked) {
                                                   new AsyncTask<String, Void, List<InventoryCatalogue>>() {

                                                       @Override
                                                       protected List<InventoryCatalogue> doInBackground(String... params) {

                                                           return InventoryCatalogue.jread(params[0],token);
                                                       }

                                                       @Override
                                                       protected void onPostExecute(List<InventoryCatalogue> resultInventoryList) {

                                                           list.setAdapter(new MyAdaptor_Inventorylist_Row(InventoryListActivity.this, R.layout.row_inventorylist, resultInventoryList));
                                                       }


                                                   }.execute(InventoryCatalogue.URI_SERVICE + "GetRelevantItemList");


                                               } else {

                                                   new AsyncTask<String, Void, List<InventoryCatalogue>>() {

                                                       @Override
                                                       protected List<InventoryCatalogue> doInBackground(String... params) {

                                                           return InventoryCatalogue.jread(params[0],token);
                                                       }

                                                       @Override
                                                       protected void onPostExecute(List<InventoryCatalogue> resultInventoryList) {

                                                           list.setAdapter(new MyAdaptor_Inventorylist_Row(InventoryListActivity.this, R.layout.row_inventorylist, resultInventoryList));
                                                       }


                                                   }.execute(InventoryCatalogue.URI_SERVICE + "GetInventoryList");

                                               }


                                           }
                                       }
        );

    }

    @Override
    public void onItemClick(AdapterView<?> av, View v, int position, long id) {

        InventoryCatalogue item = (InventoryCatalogue) av.getAdapter().getItem(position);

        Intent intent = new Intent(InventoryListActivity.this, InventoryDetailActivity.class);
        intent.putExtra("item", item);
        startActivity(intent);

    }

    //menu option
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_search, menu);
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        MenuItem searchItem = menu.findItem(R.id.searchInput);
        SearchView searchView = (SearchView) menu.findItem(R.id.searchInput)
                .getActionView();
        if (null != searchView) {
            searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
            searchView.setIconifiedByDefault(false);
        }

        // Here we are listening if its close. Cos if it is, we are gonna set the
        // in the list back to its original state (populate everything)
        MenuItem searchMenuItem = menu.findItem(R.id.searchInput);
        MenuItemCompat.setOnActionExpandListener(searchMenuItem, new MenuItemCompat.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionExpand(MenuItem item) {
                return true;
            }

            @Override
            public boolean onMenuItemActionCollapse(MenuItem item) {

                // Gotta find our fragment activity in order to call our method.
                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);
                return true;
            }
        });


        // Will listen to any input entered by the user into the SearchView
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            // 'true' here keeps the SearchView in focus
            public boolean onQueryTextChange(String newText) {
                // Directing to SearchActivity with our string query
                if (newText != null)
                {
                    List<InventoryCatalogue> serachList = new ArrayList<>();
                    for(InventoryCatalogue i:inventoryCataloguesList)
                    {
                        if(i.get("Description").toString().toLowerCase().contains(newText.toLowerCase()))
                        {
                            serachList.add(i);
                        }
                    }
                    if(serachList.size() != 0)
                    {
                        list.setAdapter(new MyAdaptor_Inventorylist_Row(InventoryListActivity.this, R.layout.row_inventorylist, serachList));
                    }

                }


                // 'false' here stops the focus on SearchView (exits it)
                return false;
            }

            public boolean onQueryTextSubmit(String query) {

                // Directing to SearchActivity with our string query
                if (query != null)
                {
                    List<InventoryCatalogue> serachList = new ArrayList<>();
                    for(InventoryCatalogue i:inventoryCataloguesList)
                    {
                        if(i.get("Description").toString().toLowerCase().contains(query.toLowerCase()))
                        {
                            serachList.add(i);
                        }
                    }
                    if(serachList.size() != 0)
                    {
                        list.setAdapter(new MyAdaptor_Inventorylist_Row(InventoryListActivity.this, R.layout.row_inventorylist, serachList));
                    }

                }


                // 'false' here stops the focus on SearchView (exits it)
                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.option1:
                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                finish();
                startActivity(intent);
                return true;

            case R.id.option2:
                pref.edit().clear().commit();
                Intent intent_logout = new Intent(getApplicationContext(),LoginActivity.class);
                finish();
                startActivity(intent_logout);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }



}
