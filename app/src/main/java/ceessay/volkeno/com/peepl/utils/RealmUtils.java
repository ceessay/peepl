package ceessay.volkeno.com.peepl.utils;

import android.content.Context;
import android.util.Log;

import ceessay.volkeno.com.peepl.classes.Person;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmQuery;
import io.realm.RealmResults;


/**
 * Created by mohamed on 16/12/15.
 */
public class RealmUtils {

    private static Realm realm;
    private static RealmConfiguration realmConfiguration;

    public static void initRealm(Context context){
        //configuration du realm
        realmConfiguration = new RealmConfiguration.Builder(context)
                .name("people.realm").build();
        //definir ce realm comme celui par défaut
        Realm.setDefaultConfiguration(realmConfiguration);
        realm = Realm.getDefaultInstance();
    }

    public static void addPerson(String name, String surname, int age, String sex){

//        realm.beginTransaction();
//        User user = realm.createObject(User.class); // Create a new object
//        user.setName("John");
//        user.setEmail("john@corporation.com");
//        realm.commitTransaction();

        Person p = new Person();
        p.setId(getNextKey());
        p.setName(name);
        p.setSurname(surname);
        p.setAge(age);
        p.setSex(sex);
        realm.beginTransaction();
        realm.copyToRealm(p);
        realm.commitTransaction();
    }

    public static void updatePerson(Person p, String name, String surname, String sex, Integer age) {
        realm.beginTransaction();
        p.setName(name);
        p.setSurname(surname);
        p.setSex(sex);
        p.setAge(age);
        realm.copyToRealm(p);
        realm.commitTransaction();

    }

    public static void deletePerson(Person p) {
        realm.beginTransaction();
        RealmResults result = realm.where(Person.class).equalTo("id", p.getId()).findAll();
        result.clear();
        realm.commitTransaction();
    }


    public static RealmResults<Person> getAllPerson() {
        return realm.allObjects(Person.class);
    }

    public static void logForRealm(){
        for (Person p :
                getAllPerson()) {
            Log.d("REALM", p.getName()+" "+p.getSurname()+" "+p.getAge()+" "+p.getSex());
        }
    }

    public static void deleteRealm() {
        Realm.deleteRealm(realmConfiguration);
        Log.d("Realm","Realm deleted!!!");
    }

    //genere une une clé afin de regler les conflits
    public static int getNextKey() {
        if(RealmUtils.getAllPerson().size() == 0){
            return 0;
        }
     return  realm.where(Person.class).max("id").intValue() + 1;
    }


    public static RealmResults<Person> searchPerson(String query) {
        Log.d("Searching", "Searching");
        RealmQuery<Person> realmQuery = realm.where(Person.class);
        realmQuery.contains("name", query)
                .or()
                .contains("surname", query);

        return realmQuery.findAll();

    }
}
