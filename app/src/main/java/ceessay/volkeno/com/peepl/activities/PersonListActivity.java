package ceessay.volkeno.com.peepl.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.SearchView;

import java.util.ArrayList;
import java.util.List;

import ceessay.volkeno.com.peepl.R;
import ceessay.volkeno.com.peepl.adapters.PersonArrayAdapter;
import ceessay.volkeno.com.peepl.classes.Person;
import ceessay.volkeno.com.peepl.utils.RealmUtils;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;


public class PersonListActivity extends AppCompatActivity {

    private static final int ADD_INTENT_CODE = 42;

    private RealmConfiguration realmConfiguration;
    private Realm realm;
    private ListView listView;
    private PersonArrayAdapter adapter;
    private RealmResults<Person> realmResults;
    private List<Person> listOfPeople;
    private SearchView searchView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person_list);
        RealmUtils.initRealm(getApplicationContext());
        listOfPeople = new ArrayList<>();
        listOfPeople.addAll(RealmUtils.getAllPerson());
        adapter = new PersonArrayAdapter(this, listOfPeople);
        listView = (ListView) findViewById(R.id.peopleListView);
        listView.setAdapter(adapter);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //realm.close();
        //Realm.deleteRealm(realmConfiguration);
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //gerer les cliques sur les elements de menu de l'actionbar

        switch (item.getItemId()){
            case R.id.action_add:
                //Toast.makeText(getApplicationContext(), "Ajouter un personne",Toast.LENGTH_LONG).showLong();
                finish();
                Intent addPeopleIntent = new Intent(getApplication(), AddPersonActivity.class);
                startActivityForResult(addPeopleIntent, ADD_INTENT_CODE);
                break;

            case R.id.action_search:

                break;

//            case android.R.id.home:
//                NavUtils.navigateUpFromSameTask(this);
//                return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        searchView =  (SearchView) menu.findItem(R.id.action_search).getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                Log.d("Query", "Querying : " + newText);
                if(newText.equals("")) {
                    //realmResults = RealmUtils.getAllPerson();
                    adapter.refresh(RealmUtils.getAllPerson());
                }else {
                    Log.d("Query", "About to search : " + newText);
                    adapter.refresh(RealmUtils.searchPerson(newText));
//                    for (Person p : realmResults) {
//                        Log.d("REALM result found: ", p.getName()+" "+p.getSurname()+" "+p.getAge()+" "+p.getSex());
//                    }
                }
                return false;
            }
        });

        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }






}
