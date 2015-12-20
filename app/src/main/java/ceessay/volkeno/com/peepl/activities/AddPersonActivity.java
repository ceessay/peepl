package ceessay.volkeno.com.peepl.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

import ceessay.volkeno.com.peepl.R;
import ceessay.volkeno.com.peepl.classes.Person;
import ceessay.volkeno.com.peepl.utils.RealmUtils;
import ceessay.volkeno.com.peepl.utils.Toaster;
import io.realm.RealmResults;

public class AddPersonActivity extends AppCompatActivity {


    private int PERSON_ADDED_CODE = 100;
    private EditText nameEditText, surnameEditText, ageEditText;
    private Button addButton;
    private String name, surname,sex="";
    private int age;
    private RadioGroup sexRadioGroup;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_person);

        nameEditText = (EditText) findViewById(R.id.nameEditText);
        surnameEditText = (EditText) findViewById(R.id.surnameEditText);
        sexRadioGroup = (RadioGroup) findViewById(R.id.sexRadioGroup);
        ageEditText = (EditText) findViewById(R.id.ageEditText);
        addButton = (Button) findViewById(R.id.addButton);

        sexRadioGroup.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(checkedId == R.id.femaleRadioButton)
                    sex="F";
                else
                    sex="M";
            }
        });




        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                name = nameEditText.getText().toString();
                surname = surnameEditText.getText().toString();
                age = Integer.valueOf(ageEditText.getText().toString());

               if (age < 0) {
                    Toaster.showShort(getApplicationContext(), getResources().getString(R.string.sexe_choose_error));
                } else if (sexRadioGroup.getCheckedRadioButtonId() == -1){
                    Toaster.showShort(getApplicationContext(), getResources().getString(R.string.sexe_choose_error));
                }
                else  {
                RealmUtils.addPerson(name, surname, age, sex);
//                RealmResults<Person> allPerson = RealmUtils.getAllPerson();
//                    for (Person p :
//                            allPerson) {
//                        Log.d("REALM", p.getName()+" "+p.getSurname()+" "+p.getAge()+" "+p.getSex());
//                    }
                finish();
                startActivity(new Intent(getApplicationContext(), PersonListActivity.class));
            }
        }
    });



    }
}
